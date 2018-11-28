(ns jiffy.zone-offset
  (:require [clojure.spec.alpha :as s]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset-impl :refer [create #?@(:cljs [ZoneOffset])] :as impl]
            [jiffy.zone.zone-rules-impl :as ZoneRules]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-queries :as TemporalQueries]
            [clojure.string :as str]
            [jiffy.local-time-impl :as LocalTime])
  #?(:clj (:import [jiffy.zone_offset_impl ZoneOffset])))

;; TODO: implement caching via ID_CACHE (see java.time source code)
;; TODO: implement caching via SECONDS_CACHE (see java.time source code)

(def MAX_SECONDS (* 18 LocalTime/SECONDS_PER_HOUR))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java
(defprotocol IZoneOffset
  (getTotalSeconds [this]))

(s/def ::zone-offset ::impl/zone-offset)

(defmacro args [& x] `(s/tuple ::zone-offset ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L473
(s/def ::get-total-seconds-args (args))
(defn -get-total-seconds [this] (:total-seconds this))
(s/fdef -get-total-seconds :args ::get-total-seconds-args :ret ::j/int)

(extend-type ZoneOffset
  IZoneOffset
  (getTotalSeconds [this] (-get-total-seconds this)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L703
(s/def ::compare-to-args (args ::zone-offset))
(defn -compare-to [this other]
  (- (:total-seconds other) (:total-seconds this)))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type ZoneOffset
  TimeComparable/ITimeComparable
  (compareTo [this other] (-compare-to this other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L491
(s/def ::get-id-args (args))
(defn -get-id [this] (:id this))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L504
(s/def ::get-rules-args (args))
(defn -get-rules [this] (ZoneRules/of this))
(s/fdef -get-rules :args ::get-rules-args :ret ::ZoneRules/zone-rules)

(extend-type ZoneOffset
  ZoneId/IZoneId
  (getId [this] (-get-id this))
  (getRules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L529
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field]
  (if (ChronoField/chrono-field? field)
    (= field ChronoField/OFFSET_SECONDS)
    (and field (TemporalField/isSupportedBy field this))))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L560
(s/def ::range-args (args ::TemporalField/temporal-field))
(def -range temporal-accessor-defaults/-range)
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L622
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field]
  (cond
    (= field ChronoField/OFFSET_SECONDS)
    (:total-seconds this)

    (ChronoField/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:zone-offset this :field field}))

    :else
    (TemporalField/getFrom field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L590
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field]
  (cond
    (= field ChronoField/OFFSET_SECONDS)
    (:total-seconds this)

    (ChronoField/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:zone-offset this :field field}))

    :else
    (-> (range this field)
        (ValueRange/checkValidIntValue (-get-long this field)
                                       field))))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L652
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query]
  (if (or (= query (TemporalQueries/offset))
          (= query (TemporalQueries/zone)))
    this
    (temporal-accessor-defaults/-query this query)))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type ZoneOffset
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L684
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal]
  (Temporal/with temporal ChronoField/OFFSET_SECONDS (:total-seconds this)))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type ZoneOffset
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn- --parse-number [offset-id pos preceded-by-colon]
  )

(defn --total-seconds [hours minutes seconds]
  (+ (* hours LocalTime/SECONDS_PER_HOUR)
     (* minutes LocalTime/SECONDS_PER_MINUTE)
     seconds))

(s/def ::hours (s/int-in -18 19))
(s/def ::minutes (s/int-in -59 60))
(s/def ::seconds (s/int-in -59 60))
(defn --validate [hours minutes seconds]
  (cond
    (not (<= -18 hours 18))
    (throw (ex DateTimeException (str "Zone offset hours not in valid range: value " hours " is not in the range -18 to 18")))

    (and (pos? hours) (or (neg? minutes) (neg? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must be positive because hours is positive"))

    (and (neg? hours) (or (pos? minutes) (pos? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must be negative because hours is negative"))

    (or (and (pos? minutes) (neg? seconds))
        (and (neg? minutes) (pos? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must have the same sign"))

    (not (<= -59 minutes 59))
    (throw (ex DateTimeException (str "Zone offset minutes not in valid range: value " minutes  " is not in the range -59 to 59")))

    (not (<= -59 seconds 59))
    (throw (ex DateTimeException (str "Zone offset seconds not in valid range: value " seconds " is not in the range -59 to 59")))

    (and (= (Math/abs hours) 18)
         (not (zero? (bit-or minutes seconds))))
    (throw (ex DateTimeException "Zone offset not in valid range: -18:00 to +18:00"))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L316
(declare ofTotalSeconds)
(s/def ::of-hours-minutes-seconds-args (args ::hours ::minutes ::seconds))
(defn ofHoursMinutesSeconds [hours minutes seconds]
  (--validate hours minutes seconds)
  (ofTotalSeconds (--total-seconds hours minutes seconds)))
(s/fdef ofHoursMinutesSeconds :args ::of-hours-minutes-seconds-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L202
(s/def ::of-args (args string?))
(defn of [offset-id]
  (let [[hours minutes seconds new-offset-id]
        (case (count offset-id)
          2 (let [offset-id (str (first offset-id) \0 (second offset-id))]
              [(--parse-number offset-id 1 false) 0 0 offset-id])
          3 [(--parse-number offset-id 1 false) 0 0]
          5 [(--parse-number offset-id 1 false) (--parse-number offset-id 3 false) 0]
          6 [(--parse-number offset-id 1 false) (--parse-number offset-id 4 true) 0]
          7 [(--parse-number offset-id 1 false) (--parse-number offset-id 3 false) (--parse-number offset-id 5 false)]
          9 [(--parse-number offset-id 1 false) (--parse-number offset-id 4 true) (--parse-number offset-id 7 true)]
          (throw (ex DateTimeException (str "Invalid ID for ZoneOffset, invalid format: " offset-id) {:offset-id offset-id})))
        offset-id-prefix (first (or new-offset-id offset-id))]
    (when (and (not= offset-id-prefix \+)
               (not= offset-id-prefix \-))
      (throw (ex DateTimeException (str "Invalid ID for ZoneOffset, plus/minus not found when expected: " offset-id))))
    (if (= offset-id-prefix \-)
      (ofHoursMinutesSeconds (- hours) (- minutes) (- seconds))
      (ofHoursMinutesSeconds hours minutes seconds))))
(s/fdef of :args ::of-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L282
(s/def ::of-hours-args (s/tuple ::hours))
(defn ofHours [hours]
  (ofHoursMinutesSeconds hours 0 0))
(s/fdef ofHours :args ::of-hours-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L299
(s/def ::of-hours-minutes-args (s/tuple ::hours ::minutes))
(defn ofHoursMinutes [hours minutes]
  (ofHoursMinutesSeconds hours minutes 0))
(s/fdef ofHoursMinutes :args ::of-hours-minutes-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L343
(s/def ::from-args (s/tuple ::TemporalAccessor/temporal-accessor))
(defn from [temporal]
  (or (TemporalAccessor/query temporal (TemporalQueries/offset))
      (throw (ex DateTimeException (str "Unable to obtain ZoneOffset from TemporalAccessor: " temporal  " of type " (type temporal))))))
(s/fdef from :args ::from-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L413
(s/def ::of-total-seconds-args (s/tuple ::impl/total-seconds))
(defn ofTotalSeconds [total-seconds]
  (when (not (<= (- MAX_SECONDS) total-seconds MAX_SECONDS))
    (throw (ex DateTimeException "Zone offset not in valid range: -18:00 to +18:00")))
  (create total-seconds))
(s/fdef ofTotalSeconds :args ::of-total-seconds-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L151
(def UTC (ofTotalSeconds 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L155
(def MIN (ofTotalSeconds (- MAX_SECONDS)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L159
(def MAX (ofTotalSeconds MAX_SECONDS))
