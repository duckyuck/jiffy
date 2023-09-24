(ns jiffy.zone.zone-rules
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.protocols.zone.zone-offset-transition-rule :as transition-rule]
            [jiffy.protocols.zone.zone-offset-transition :as transition]
            [jiffy.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.asserts :as assert]
            [jiffy.zone.zone-rules-impl :refer [#?@(:cljs [ZoneRules])] :as impl]
            [jiffy.math :as math]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.local-date :as local-date-impl]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.duration-impl :as duration-impl]
            [jiffy.zone.zone-offset-transition-impl :as zone-offset-transition-impl]
            [jiffy.year :as year])
  #?(:clj (:import [jiffy.zone.zone_rules_impl ZoneRules])))

(s/def ::zone-rules ::impl/zone-rules)

(def-method is-fixed-offset ::j/boolean
  [this ::zone-rules]
  (empty? (:savings-instant-transitions this)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L478

(defn- find-year [epoch-second offset]
  (->> (math/floor-div
        (math/add-exact
         epoch-second
         (zone-offset/get-total-seconds offset))
        86400)
       local-date-impl/of-epoch-day
       local-date/get-year))

;; TODO implement caching. Maybe use memoization with a cache?
(defn find-transition-array [this year]
  (->> this
       :last-rules
       (map #(transition-rule/create-transition % year))))

(defn greater? [x y]
  (if (number? x)
    (> x y)
    (pos? (time-comparable/compare-to x y))))

(defn compare* [x y]
  (if (satisfies? time-comparable/ITimeComparable x)
    (time-comparable/compare-to x y)
    (compare x y)))

(defn find-index [coll object]
  (if (seq coll)
    (->> coll
         (sort compare*)
         (map-indexed (fn [idx n]
                        (cond
                          (= n object)
                          idx

                          (or (greater? n object))
                          (- (- idx) 1)

                          (= idx (dec (count coll)))
                          (- (- idx) 2))))
         (drop-while nil?)
         first)
    -1))

(defn get-offset-for-instant
  [{:keys [savings-instant-transitions standard-offsets last-rules wall-offsets] :as this}
   instant]
  (if (empty? savings-instant-transitions)
    (first standard-offsets)
    (let [epoch-sec (instant/get-epoch-second instant)]
      (if (and (seq last-rules)
               (> epoch-sec (last savings-instant-transitions)))
        (let [year (find-year epoch-sec (last wall-offsets))
              trans-array (find-transition-array this year)
              trans (->> trans-array
                         (drop-while #(>= epoch-sec (transition/to-epoch-second %)))
                         first)]
          (if trans
            (transition/get-offset-before trans)
            (transition/get-offset-after (last trans-array))))
        (let [index (find-index savings-instant-transitions epoch-sec)]
          (if (neg? index)
            (let [adjusted-index (- (- index) 2)]
              (nth wall-offsets (inc adjusted-index)))
            (nth wall-offsets (inc index))))))))

(defn find-offset-info [dt trans]
  (let [local-transition (transition/get-date-time-before trans)]
    (if (transition/is-gap trans)
      (cond
        (chrono-local-date-time/is-before dt local-transition)
        (transition/get-offset-before trans)

        (chrono-local-date-time/is-before dt (transition/get-date-time-after trans))
        trans

        :else
        (transition/get-offset-after trans))

      (cond
        (not (chrono-local-date-time/is-before dt local-transition))
        (transition/get-offset-after trans)

        (chrono-local-date-time/is-before dt (transition/get-date-time-after trans))
        (transition/get-offset-before trans)

        :else trans))))

(defn get-offset-info
  [{:keys [savings-instant-transitions standard-offsets last-rules
           savings-local-transitions wall-offsets] :as this}
   dt]
  (if (empty? savings-instant-transitions)
    (first standard-offsets)
    (if (and (not (empty? last-rules))
             (chrono-local-date-time/is-after dt (last savings-local-transitions)))
      (let [transitions (->> dt
                             local-date-time/get-year
                             (find-transition-array this))]
        (or (->> transitions
                 (keep (fn [trans]
                         (let [info (find-offset-info dt trans)]
                           (when (or (satisfies? transition/IZoneOffsetTransition info)
                                     (= info (transition/get-offset-before trans)))
                             info))))
                 first)
            (->> transitions
                 last
                 (find-offset-info dt))))
      (let [index (find-index savings-local-transitions dt)]
        (if (= index -1)
          (first wall-offsets)
          (let [index (if (< index 0)
                        (- (- index) 2)
                        (if (and (< index (dec (count savings-local-transitions)))
                                 (= (nth savings-local-transitions index)
                                    (nth savings-local-transitions (inc index))))
                          (inc index)
                          index))
                ]
            (if (= 0 (bit-and index 1))
              (let [dt-before (nth savings-local-transitions index)
                    dt-after (nth savings-local-transitions (inc index))
                    offset-before (nth wall-offsets (/ index 2))
                    offset-after (nth wall-offsets (/ (inc index) 2))]
                (if (> (.getTotalSeconds offset-after) (.getTotalSeconds offset-before))
                  (zone-offset-transition/of dt-before offset-before offset-after)
                  (zone-offset-transition/of dt-after offset-before offset-after)))
              (nth wall-offsets (/ (inc index) 2)))))))))

(defn get-offset-for-local-date-time [this local-date-time]
  (let [info (get-offset-info this local-date-time)]
    (if (satisfies? transition/IZoneOffsetTransition info)
      (transition/get-offset-before info)
      info)))

(def-method get-offset ::zone-offset/zone-offset
  [this ::zone-rules
   arg (s/or :instant ::instant/instant
             :local-date-time ::local-date-time/local-date-time)]
  (if (instant-impl/instant? arg)
    (get-offset-for-instant this arg)
    (get-offset-for-local-date-time this arg)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L585
(def-method get-valid-offsets ::j/wip
  [this ::zone-rules
   local-date-time ::local-date-time/local-date-time]
  (let [info (get-offset-info this local-date-time)]
    (if (satisfies? transition/IZoneOffsetTransition info)
      (transition/get-valid-offsets info)
      [info])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L628
(def-method get-transition ::zone-offset-transition/zone-offset-transition
  [this ::zone-rules
   local-date-time ::local-date-time/local-date-time]
  (let [info (get-offset-info this local-date-time)]
    (when (satisfies? transition/IZoneOffsetTransition info)
      info)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L749
(def-method get-standard-offset ::zone-offset/zone-offset
  [this ::zone-rules
   instant ::instant/instant]
  (if (empty? (:savings-instant-transitions this))
    (first (:standard-offsets this))
    (let [idx (find-index (:standard-transitions this) (instant/get-epoch-second instant))]
      (nth (:standard-offsets this)
           (if (neg? idx)
             (dec (- idx))
             (inc idx))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L779
(def-method get-daylight-savings ::duration/duration
  [this ::zone-rules
   instant ::instant/instant]
  (if (empty? (:savings-instant-transitions this))
    duration-impl/ZERO
    (let [actual-offset (get-offset this instant)
          standard-offset (get-standard-offset this instant)]
      (duration-impl/of-seconds
       (math/subtract-exact (zone-offset/get-total-seconds actual-offset)
                            (zone-offset/get-total-seconds standard-offset))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L802
(def-method is-daylight-savings ::j/boolean
  [this ::zone-rules
   instant ::instant/instant]
  (not= (get-standard-offset this instant) (get-offset this instant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L820
(def-method is-valid-offset ::j/boolean
  [this ::zone-rules
   local-date-time ::local-date-time/local-date-time
   offset ::zone-offset/zone-offset]
  (some? (some #{offset} (get-valid-offsets this local-date-time))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L835
(def-method next-transition ::zone-offset-transition/zone-offset-transition
  [{:keys [savings-instant-transitions last-rules wall-offsets] :as this} ::zone-rules
   instant ::instant/instant]
  (when (seq savings-instant-transitions)
    (let [epoch-sec (instant/get-epoch-second instant)]
      (if (>= epoch-sec (last savings-instant-transitions))
        (when (seq last-rules)
          (let [year (find-year epoch-sec (last wall-offsets))
                trans-array (find-transition-array this year)
                trans (->> trans-array
                           (drop-while #(>= epoch-sec (transition/to-epoch-second %)))
                           first)]
            (or trans
                (when (< year year/MAX_VALUE)
                  (first (find-transition-array this (inc year)))))))

        (let [index (find-index savings-instant-transitions epoch-sec)
              index (if (neg? index)
                      (dec (- index))
                      (inc index))]
          (zone-offset-transition-impl/create
           (nth savings-instant-transitions index)
           (nth wall-offsets index)
           (nth wall-offsets (inc index))))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L882
(def-method previous-transition ::zone-offset-transition/zone-offset-transition
  [{:keys [savings-instant-transitions last-rules wall-offsets] :as this} ::zone-rules
   instant ::instant/instant]
  (when (seq savings-instant-transitions)
    (let [epoch-sec (instant/get-epoch-second instant)
          epoch-sec (cond-> epoch-sec
            (and (pos? (instant/get-nano instant))
                 (< epoch-sec math/long-max-value))
            inc)
          last-historic (last savings-instant-transitions)]
      (or
       (when (and (seq last-rules)
                  (> epoch-sec last-historic))
         (let [last-historic-offset (last wall-offsets)
               year (find-year epoch-sec last-historic-offset)
               trans-array (find-transition-array this year)
               trans (->> trans-array
                          reverse
                          (drop-while #(<= epoch-sec (transition/to-epoch-second %)))
                          first)]
           (or trans
               (let [last-historic-year (find-year last-historic last-historic-offset)]
                 (when (> (dec year) last-historic-year)
                   (last (find-transition-array this (dec year))))))))
       (let [index (find-index savings-instant-transitions epoch-sec)
             index (if (neg? index)
                     (dec (- index))
                     index)]
         (when (pos? index)
           (zone-offset-transition-impl/create
            (nth savings-instant-transitions (dec index))
            (nth wall-offsets (dec index))
            (nth wall-offsets index))))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L942
(def-method get-transitions ::j/wip
  [{:keys [savings-instant-transitions wall-offsets]} ::zone-rules]
  (for [index (range 0 (count savings-instant-transitions))]
    (zone-offset-transition-impl/create
     (nth savings-instant-transitions index)
     (nth wall-offsets index)
     (nth wall-offsets (inc index)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L971
(def-method get-transition-rules ::j/wip
  [this ::zone-rules]
  (:last-rules this))

(extend-type ZoneRules
  zone-rules/IZoneRules
  (is-fixed-offset [this] (is-fixed-offset this))
  (get-offset [this arg] (get-offset this arg))
  (get-valid-offsets [this local-date-time] (get-valid-offsets this local-date-time))
  (get-transition [this local-date-time] (get-transition this local-date-time))
  (get-standard-offset [this instant] (get-standard-offset this instant))
  (get-daylight-savings [this instant] (get-daylight-savings this instant))
  (is-daylight-savings [this instant] (is-daylight-savings this instant))
  (is-valid-offset [this local-date-time offset] (is-valid-offset this local-date-time offset))
  (next-transition [this instant] (next-transition this instant))
  (previous-transition [this instant] (previous-transition this instant))
  (get-transitions [this] (get-transitions this))
  (get-transition-rules [this] (get-transition-rules this)))

(def-constructor of ::zone-rules
  ([zone-offset ::zone-offset/zone-offset]
   (impl/of zone-offset))

  ;; TODO: maybe implement?
  ;; ([base-standard-offset
  ;;   base-wall-offset
  ;;   standard-offset-transition-list
  ;;   transition-list
  ;;   last-rules]
  ;;  (wip ::of))
  )
