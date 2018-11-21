(ns jiffy.local-date
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.iso-chronology-impl :as IsoChronology]
            [jiffy.chrono.iso-era :as IsoEra]
            [jiffy.clock :as Clock]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date-impl :refer [create #?@(:cljs [LocalDate])] :as impl]
            [jiffy.local-date-time-impl :as LocalDateTime]
            [jiffy.local-time :as LocalTime]
            [jiffy.month :as Month]
            [jiffy.offset-date-time-impl :as OffsetDateTime]
            [jiffy.offset-time-impl :as OffsetTime]
            [jiffy.period :as Period]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]
            [jiffy.zoned-date-time-impl :as ZonedDateTime])
  #?(:clj (:import [jiffy.local_date_impl LocalDate])))

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


(s/def ::local-date ::impl/local-date)

(defmacro args [& x] `(s/tuple ::local-date ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L694
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::DayOfWeek/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L698
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this] (wip ::-get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L761
(s/def ::get-year-args (args))
(defn -get-year [this] (wip ::-get-year))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L775
(s/def ::get-month-value-args (args))
(defn -get-month-value [this] (wip ::-get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L801
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this] (wip ::-get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1081
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year] (wip ::-with-year))
(s/fdef -with-year :args ::with-year-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1100
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month] (wip ::-with-month))
(s/fdef -with-month :args ::with-month-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1120
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1139
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1298
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years-to-add] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1326
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months-to-add] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1352
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1371
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days-to-add] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1472
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1496
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1515
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1534
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1661
(s/def ::days-until-args (args ::local-date))
(defn -days-until [this end] (wip ::-days-until))
(s/fdef -days-until :args ::days-until-args :ret ::j/long)

(s/def ::dates-until-args (args ::j/wip))
(defn -dates-until
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1732
  ([this end-exclusive] (wip ::-dates-until))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1759
  ([this end-exclusive step] (wip ::-dates-until)))
(s/fdef -dates-until :args ::dates-until-args :ret ::j/wip)

(s/def ::at-time-args (args ::j/wip))
(defn -at-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1848
  ([this hour minute] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1867
  ([this hour minute second] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1886
  ([this hour minute second nano-of-second] (wip ::-at-time)))
(s/fdef -at-time :args ::at-time-args :ret ::LocalDateTime/local-date-time)

(s/def ::at-start-of-day-args (args ::j/wip))
(defn -at-start-of-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1912
  ([this] (wip ::-at-start-of-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1936
  ([this zone] (wip ::-at-start-of-day)))
(s/fdef -at-start-of-day :args ::at-start-of-day-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1989
(s/def ::to-epoch-second-args (args ::LocalTime/local-time ::ZoneOffset/zone-offset))
(defn -to-epoch-second [this time offset] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2020
(s/def ::compare-to0-args (args ::local-date))
(defn -compare-to0 [this other-date] (wip ::-compare-to0))
(s/fdef -compare-to0 :args ::compare-to0-args :ret ::j/int)

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
(s/def ::compare-to-args (args ::ChronoLocalDate/chrono-local-date))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalDate
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L607
(s/def ::length-of-month-args (args))
(defn -length-of-month [this] (wip ::-length-of-month))
(s/fdef -length-of-month :args ::length-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L608
(s/def ::length-of-year-args (args))
(defn -length-of-year [this] (wip ::-length-of-year))
(s/fdef -length-of-year :args ::length-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(s/def ::is-leap-year-args (args))
(defn -is-leap-year [this] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L682
(s/def ::to-epoch-day-args (args))
(defn -to-epoch-day [this] (wip ::-to-epoch-day))
(s/fdef -to-epoch-day :args ::to-epoch-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L727
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L747
(s/def ::get-era-args (args))
(defn -get-era [this] (wip ::-get-era))
(s/fdef -get-era :args ::get-era-args :ret ::Era/era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1704
(s/def ::until-chrono-args (args ::ChronoLocalDate/chrono-local-date))
(defn -until-chrono [this end-date-exclusive] (wip ::-until-chrono))
(s/fdef -until-chrono :args ::until-chrono-args :ret ::ChronoPeriod/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1814
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1830
(s/def ::at-time-chrono-args (args ::OffsetTime/offset-time))
(defn -at-time-chrono [this at-time-chrono--overloaded-param] (wip ::-at-time-chrono))
(s/fdef -at-time-chrono :args ::at-time-chrono-args :ret ::OffsetDateTime/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2053
(s/def ::is-after-args (args ::ChronoLocalDate/chrono-local-date))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2082
(s/def ::is-before-args (args ::ChronoLocalDate/chrono-local-date))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2111
(s/def ::is-equal-args (args ::ChronoLocalDate/chrono-local-date))
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)

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

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L932
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1045
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::ChronoLocalDate/chrono-local-date)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1168
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1259
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1418
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1447
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1643
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

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
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L602
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L648
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L679
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1559
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalDate
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1591
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type LocalDate
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L197
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L213
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::local-date)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L247
(s/def ::of-args (args ::j/int ::Month/month ::j/int))
(defn of [of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3] (wip ::of))
(s/fdef of :args ::of-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L287
(s/def ::of-year-day-args (args ::j/int ::j/int))
(defn ofYearDay [year day-of-year] (wip ::ofYearDay))
(s/fdef ofYearDay :args ::of-year-day-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L318
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L340
(s/def ::of-epoch-day-args (args ::j/long))
(defn ofEpochDay [epoch-day] (wip ::ofEpochDay))
(s/fdef ofEpochDay :args ::of-epoch-day-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L391
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-date)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L412
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L426
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L146
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L151
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L155
(def EPOCH ::EPOCH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L170
(def DAYS_0000_TO_1970 ::DAYS_0000_TO_1970--not-implemented)
