(ns jiffy.local-date
  (:require [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.time-comparable :as TimeComparable]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java
(defprotocol ILocalDate
  (getMonth [this])
  (getDayOfWeek [this])
  (getDayOfYear [this])
  (getYear [this])
  (getMonthValue [this])
  (getDayOfMonth [this])
  (withYear [this year])
  (withMonth [this month])
  (withDayOfMonth [this day-of-month])
  (withDayOfYear [this day-of-year])
  (plusYears [this years-to-add])
  (plusMonths [this months-to-add])
  (plusWeeks [this weeks-to-add])
  (plusDays [this days-to-add])
  (minusYears [this years-to-subtract])
  (minusMonths [this months-to-subtract])
  (minusWeeks [this weeks-to-subtract])
  (minusDays [this days-to-subtract])
  (daysUntil [this end])
  (datesUntil [this end-exclusive] [this end-exclusive step])
  (atTime [this hour minute] [this hour minute second] [this hour minute second nano-of-second])
  (atStartOfDay [this] [this zone])
  (toEpochSecond [this time offset])
  (compareTo0 [this other-date]))

(defrecord LocalDate [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(defn -get-month [this] (wip ::-get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L694
(defn -get-day-of-week [this] (wip ::-get-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L698
(defn -get-day-of-year [this] (wip ::-get-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L761
(defn -get-year [this] (wip ::-get-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L775
(defn -get-month-value [this] (wip ::-get-month-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L801
(defn -get-day-of-month [this] (wip ::-get-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1081
(defn -with-year [this year] (wip ::-with-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1100
(defn -with-month [this month] (wip ::-with-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1120
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1139
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1298
(defn -plus-years [this years-to-add] (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1326
(defn -plus-months [this months-to-add] (wip ::-plus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1352
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1371
(defn -plus-days [this days-to-add] (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1472
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1496
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1515
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1534
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1661
(defn -days-until [this end] (wip ::-days-until))

(defn -dates-until
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1732
  ([this end-exclusive] (wip ::-dates-until))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1759
  ([this end-exclusive step] (wip ::-dates-until)))

(defn -at-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1848
  ([this hour minute] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1867
  ([this hour minute second] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1886
  ([this hour minute second nano-of-second] (wip ::-at-time)))

(defn -at-start-of-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1912
  ([this] (wip ::-at-start-of-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1936
  ([this zone] (wip ::-at-start-of-day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1989
(defn -to-epoch-second [this time offset] (wip ::-to-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2020
(defn -compare-to0 [this other-date] (wip ::-compare-to0))

(extend-type LocalDate
  ILocalDate
  (getMonth [this] (-get-month this))
  (getDayOfWeek [this] (-get-day-of-week this))
  (getDayOfYear [this] (-get-day-of-year this))
  (getYear [this] (-get-year this))
  (getMonthValue [this] (-get-month-value this))
  (getDayOfMonth [this] (-get-day-of-month this))
  (withYear [this year] (-with-year this year))
  (withMonth [this month] (-with-month this month))
  (withDayOfMonth [this day-of-month] (-with-day-of-month this day-of-month))
  (withDayOfYear [this day-of-year] (-with-day-of-year this day-of-year))
  (plusYears [this years-to-add] (-plus-years this years-to-add))
  (plusMonths [this months-to-add] (-plus-months this months-to-add))
  (plusWeeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plusDays [this days-to-add] (-plus-days this days-to-add))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (minusMonths [this months-to-subtract] (-minus-months this months-to-subtract))
  (minusWeeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract))
  (daysUntil [this end] (-days-until this end))
  (datesUntil
    ([this end-exclusive] (-dates-until this end-exclusive))
    ([this end-exclusive step] (-dates-until this end-exclusive step)))
  (atTime
    ([this hour minute] (-at-time this hour minute))
    ([this hour minute second] (-at-time this hour minute second))
    ([this hour minute second nano-of-second] (-at-time this hour minute second nano-of-second)))
  (atStartOfDay
    ([this] (-at-start-of-day this))
    ([this zone] (-at-start-of-day this zone)))
  (toEpochSecond [this time offset] (-to-epoch-second this time offset))
  (compareTo0 [this other-date] (-compare-to0 this other-date)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2013
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type LocalDate
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L607
(defn -length-of-month [this] (wip ::-length-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L608
(defn -length-of-year [this] (wip ::-length-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(defn -is-leap-year [this] (wip ::-is-leap-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L682
(defn -to-epoch-day [this] (wip ::-to-epoch-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L727
(defn -get-chronology [this] (wip ::-get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L747
(defn -get-era [this] (wip ::-get-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1704
(defn -until-chrono [this end-date-exclusive] (wip ::-until))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1814
(defn -format [this formatter] (wip ::-format))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1830
(defn -at-time-chrono [this at-time--overloaded-param] (wip ::-at-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2053
(defn -is-after [this other] (wip ::-is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2082
(defn -is-before [this other] (wip ::-is-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2111
(defn -is-equal [this other] (wip ::-is-equal))

(extend-type LocalDate
  ChronoLocalDate/IChronoLocalDate
  (lengthOfMonth [this] (-length-of-month this))
  (lengthOfYear [this] (-length-of-year this))
  (isLeapYear [this] (-is-leap-year this))
  (toEpochDay [this] (-to-epoch-day this))
  (getChronology [this] (-get-chronology this))
  (getEra [this] (-get-era this))
  (until [this end-date-exclusive] (-until-chrono this end-date-exclusive))
  (format [this formatter] (-format this formatter))
  (atTime [this at-time--overloaded-param] (-at-time-chrono this at-time--overloaded-param))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other))
  (isEqual [this other] (-is-equal this other)))

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L932
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1045
  ([this field new-value] (wip ::-with)))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1168
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1259
  ([this amount-to-add unit] (wip ::-plus)))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1418
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1447
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1643
(defn -until [this end-exclusive unit] (wip ::-until))

(extend-type LocalDate
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L539
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L602
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L648
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L679
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1559
(defn -query [this query] (wip ::-query))

(extend-type LocalDate
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1591
(defn -adjust-into [this temporal] (wip ::-adjust-into))

(extend-type LocalDate
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L197
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L213
  ([now--overloaded-param] (wip ::now)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L247
(defn of [of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L287
(defn ofYearDay [year day-of-year] (wip ::ofYearDay))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L318
(defn ofInstant [instant zone] (wip ::ofInstant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L340
(defn ofEpochDay [epoch-day] (wip ::ofEpochDay))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L391
(defn from [temporal] (wip ::from))

(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L412
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L426
  ([text formatter] (wip ::parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L146
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L151
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L155
(def EPOCH ::EPOCH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L170
(def DAYS_0000_TO_1970 ::DAYS_0000_TO_1970--not-implemented)
