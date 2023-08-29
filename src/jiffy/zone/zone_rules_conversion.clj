(ns jiffy.zone.zone-rules-conversion
  (:require [jiffy.day-of-week :as day-of-week]
            [jiffy.local-date :as local-date]
            [jiffy.local-date-time :as local-date-time]
            [jiffy.local-time :as local-time]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.month :as month]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.zone.zone-offset-transition :as transition]
            [jiffy.zone.zone-offset-transition-rule :as transition-rule]
            [jiffy.zone.zone-rules :as zone-rules]))

(def transition-rule-keys
  [:timeDefinition
   :midnightEndOfDay
   :localTime
   :month
   :class
   :dayOfMonthIndicator
   :dayOfWeek
   :standardOffset
   :offsetAfter
   :offsetBefore])

(defn convert-time-definition [time-definition]
  (or ({"UTC" ::transition-rule/UTC
        "WALL" ::transition-rule/WALL
        "STANDARD"::transition-rule/STANDARD}
       (.toString time-definition))
      (throw (ex-info (str "Unknown TimeDefinition: " (.toString time-definition))))))

(defn convert-transition-rule [transition-rule]
  (let [{:keys [timeDefinition
                midnightEndOfDay
                localTime
                month
                class
                dayOfMonthIndicator
                dayOfWeek
                standardOffset
                offsetAfter
                offsetBefore]} (bean transition-rule)]
    (transition-rule/create
     (convert-time-definition timeDefinition)
     midnightEndOfDay
     (->> [:hour :minute :second :nano] (map (bean localTime)) (apply local-time-impl/create))
     (-> month .toString month/value-of)
     dayOfMonthIndicator
     (-> dayOfWeek .toString day-of-week/value-of)
     (-> standardOffset .toString zone-offset/of)
     (-> offsetAfter .toString zone-offset/of)
     (-> offsetBefore .toString zone-offset/of))))

(defn convert-local-date-time [local-date-time]
  (let [{:keys [year monthValue dayOfMonth hour minute second nano]} (bean local-date-time)]
    (local-date-time/of (local-date/of year monthValue dayOfMonth)
                        (local-time/of hour minute second nano))))

(defn convert-transition [transition]
  (let [{:keys [dateTimeBefore offsetBefore offsetAfter]} (bean transition)]
    (transition/create
     (convert-local-date-time dateTimeBefore)
     (-> offsetBefore bean :id zone-offset/of)
     (-> offsetAfter bean :id zone-offset/of))))

(defn convert-zone-rules [^java.time.zone.ZoneRules object]
  (let [rules (bean object)]
    (zone-rules/create
     (:fixedOffset rules)
     (map convert-transition-rule (:transitionRules rules))
     (map convert-transition (:transitions rules)))))

(defn load-zone-rules []
  (let [zone-ids (java.time.zone.ZoneRulesProvider/getAvailableZoneIds)]
    (zipmap zone-ids
            (map #(convert-zone-rules (java.time.zone.ZoneRulesProvider/getRules % false)) zone-ids))))

(comment

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
