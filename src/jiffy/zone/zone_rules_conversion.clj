(ns jiffy.zone.zone-rules-conversion
  (:require [clojure.reflect :as reflect]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.local-date :as local-date]
            [jiffy.local-date-time :as local-date-time]
            [jiffy.local-time :as local-time]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.month :as month]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.zone.zone-offset-transition-rule :as transition-rule]
            [jiffy.zone.zone-rules-impl :as zone-rules-impl]
            [jiffy.zone.zone-rules-store :as rules-store]))

(defn get-private-field [obj field-name]
  (let [field (.getDeclaredField (class obj) field-name)]
    (.setAccessible field true)
    (.get field obj)))

(defmulti java->jiffy type)

(defmethod java->jiffy :default
  [java-object]
  (throw (ex-info (str "Shit! We're missing implementation of multimethod 'jiffy.conversion/java->jiffy' for class "
                       (type java-object) ". Please do some more programming!") {})))

(defmethod java->jiffy clojure.lang.PersistentVector [coll]
  (mapv java->jiffy coll))

(defmethod java->jiffy java.lang.Long [n] n)

(defmethod java->jiffy java.time.ZoneOffset [object]
  (-> object .getId zone-offset/of))

(defmethod java->jiffy java.time.LocalDateTime [object]
  (let [{:keys [year monthValue dayOfMonth hour minute second nano]} (bean object)]
    (local-date-time/of (local-date/of year monthValue dayOfMonth)
                        (local-time/of hour minute second nano))))

(defmethod java->jiffy java.time.zone.ZoneOffsetTransitionRule$TimeDefinition [object]
  (or ({"UTC" ::transition-rule/UTC
        "WALL" ::transition-rule/WALL
        "STANDARD"::transition-rule/STANDARD}
       (.toString object))
      (throw (ex-info (str "Unknown TimeDefinition: " (.toString object))))))

(defmethod java->jiffy java.time.zone.ZoneOffsetTransitionRule [object]
  (let [{:keys [timeDefinition
                midnightEndOfDay
                localTime
                month
                class
                dayOfMonthIndicator
                dayOfWeek
                standardOffset
                offsetAfter
                offsetBefore]} (bean object)]
    (transition-rule/create
     (java->jiffy timeDefinition)
     midnightEndOfDay
     (->> [:hour :minute :second :nano] (map (bean localTime)) (apply local-time-impl/create))
     (-> month .toString month/value-of)
     dayOfMonthIndicator
     (-> dayOfWeek .toString day-of-week/value-of)
     (-> standardOffset .toString zone-offset/of)
     (-> offsetAfter .toString zone-offset/of)
     (-> offsetBefore .toString zone-offset/of))))

(defn extract-zone-rules-properties [^java.time.zone.ZoneRules object]
  (->> (for [[k property] {:standard-transitions "standardTransitions"
                           :standard-offsets "standardOffsets"
                           :savings-instant-transitions "savingsInstantTransitions"
                           :savings-local-transitions "savingsLocalTransitions"
                           :wall-offsets "wallOffsets"
                           :last-rules "lastRules"}]
         [k (into [] (get-private-field object property))])
       (into {})))

(defmethod java->jiffy java.time.zone.ZoneRules [object]
  (let [props (extract-zone-rules-properties object)]
    (zone-rules-impl/map->ZoneRules
     (zipmap (keys props)
             (map java->jiffy (vals props))))))

(defn load-zone-rules []
  (let [zone-ids (java.time.zone.ZoneRulesProvider/getAvailableZoneIds)]
    (zipmap zone-ids
            (map #(java->jiffy (java.time.zone.ZoneRulesProvider/getRules % false))
                 zone-ids))))

(do
  (reset! rules-store/zone-id->rules (load-zone-rules))
  nil)

(comment

  (extract-zone-rules-properties (java.time.zone.ZoneRulesProvider/getRules "Europe/Oslo" false))
  (reflect/reflect (java.time.zone.ZoneRulesProvider/getRules "Europe/Oslo" false))


  (->> (java.time.zone.ZoneRulesProvider/getRules "Europe/Oslo" false)
       convert-zone-rules
       :transitions
       (map :epoch-second))

  (->> (java.time.zone.ZoneRulesProvider/getRules "Europe/Oslo" false)
       bean
       :transitions
       (map #(.toEpochSecond %))
       )


  )
