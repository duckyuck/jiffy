(ns jiffy.local-date-time
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.clock :as Clock]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date :as LocalDate]
            [jiffy.local-date-time-impl :refer [create #?@(:cljs [LocalDateTime])] :as impl]
            [jiffy.local-time :as LocalTime]
            [jiffy.month :as Month]
            [jiffy.offset-date-time-impl :as OffsetDateTime]
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
  #?(:clj (:import [jiffy.local_date_time_impl LocalDateTime])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java
(defprotocol ILocalDateTime
  (getYear [this])
  (getMonthValue [this])
  (getMonth [this])
  (getDayOfMonth [this])
  (getDayOfYear [this])
  (getDayOfWeek [this])
  (getHour [this])
  (getMinute [this])
  (getSecond [this])
  (getNano [this])
  (withYear [this year])
  (withMonth [this month])
  (withDayOfMonth [this day-of-month])
  (withDayOfYear [this day-of-year])
  (withHour [this hour])
  (withMinute [this minute])
  (withSecond [this second])
  (withNano [this nano-of-second])
  (truncatedTo [this unit])
  (plusYears [this years])
  (plusMonths [this months])
  (plusWeeks [this weeks])
  (plusDays [this days])
  (plusHours [this hours])
  (plusMinutes [this minutes])
  (plusSeconds [this seconds])
  (plusNanos [this nanos])
  (minusYears [this years])
  (minusMonths [this months])
  (minusWeeks [this weeks])
  (minusDays [this days])
  (minusHours [this hours])
  (minusMinutes [this minutes])
  (minusSeconds [this seconds])
  (minusNanos [this nanos])
  (atOffset [this offset]))

(s/def ::local-date-time ::impl/local-date-time)

(defmacro args [& x] `(s/tuple ::local-date-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L749
(s/def ::get-year-args (args))
(defn -get-year [this] (wip ::-get-year))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L763
(s/def ::get-month-value-args (args))
(defn -get-month-value [this] (wip ::-get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L778
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L789
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this] (wip ::-get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L800
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this] (wip ::-get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L817
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::DayOfWeek/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L840
(s/def ::get-hour-args (args))
(defn -get-hour [this] (wip ::-get-hour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L849
(s/def ::get-minute-args (args))
(defn -get-minute [this] (wip ::-get-minute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L858
(s/def ::get-second-args (args))
(defn -get-second [this] (wip ::-get-second))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L867
(s/def ::get-nano-args (args))
(defn -get-nano [this] (wip ::-get-nano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L989
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year] (wip ::-with-year))
(s/fdef -with-year :args ::with-year-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1005
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month] (wip ::-with-month))
(s/fdef -with-month :args ::with-month-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1022
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1038
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1052
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour] (wip ::-with-hour))
(s/fdef -with-hour :args ::with-hour-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1066
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute] (wip ::-with-minute))
(s/fdef -with-minute :args ::with-minute-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1080
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second] (wip ::-with-second))
(s/fdef -with-second :args ::with-second-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1094
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second] (wip ::-with-nano))
(s/fdef -with-nano :args ::with-nano-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1120
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit] (wip ::-truncated-to))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1220
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1245
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1265
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1285
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1300
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours] (wip ::-plus-hours))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1313
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes] (wip ::-plus-minutes))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1326
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1339
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos] (wip ::-plus-nanos))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1419
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1443
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1462
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1481
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1495
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours] (wip ::-minus-hours))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1508
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes] (wip ::-minus-minutes))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1521
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds] (wip ::-minus-seconds))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1534
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos] (wip ::-minus-nanos))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1765
(s/def ::at-offset-args (args ::ZoneOffset/zone-offset))
(defn -at-offset [this offset] (wip ::-at-offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::OffsetDateTime/offset-date-time)

(extend-type LocalDateTime
  ILocalDateTime
  (getYear [this] (-get-year this))
  (getMonthValue [this] (-get-month-value this))
  (getMonth [this] (-get-month this))
  (getDayOfMonth [this] (-get-day-of-month this))
  (getDayOfYear [this] (-get-day-of-year this))
  (getDayOfWeek [this] (-get-day-of-week this))
  (getHour [this] (-get-hour this))
  (getMinute [this] (-get-minute this))
  (getSecond [this] (-get-second this))
  (getNano [this] (-get-nano this))
  (withYear [this year] (-with-year this year))
  (withMonth [this month] (-with-month this month))
  (withDayOfMonth [this day-of-month] (-with-day-of-month this day-of-month))
  (withDayOfYear [this day-of-year] (-with-day-of-year this day-of-year))
  (withHour [this hour] (-with-hour this hour))
  (withMinute [this minute] (-with-minute this minute))
  (withSecond [this second] (-with-second this second))
  (withNano [this nano-of-second] (-with-nano this nano-of-second))
  (truncatedTo [this unit] (-truncated-to this unit))
  (plusYears [this years] (-plus-years this years))
  (plusMonths [this months] (-plus-months this months))
  (plusWeeks [this weeks] (-plus-weeks this weeks))
  (plusDays [this days] (-plus-days this days))
  (plusHours [this hours] (-plus-hours this hours))
  (plusMinutes [this minutes] (-plus-minutes this minutes))
  (plusSeconds [this seconds] (-plus-seconds this seconds))
  (plusNanos [this nanos] (-plus-nanos this nanos))
  (minusYears [this years] (-minus-years this years))
  (minusMonths [this months] (-minus-months this months))
  (minusWeeks [this weeks] (-minus-weeks this weeks))
  (minusDays [this days] (-minus-days this days))
  (minusHours [this hours] (-minus-hours this hours))
  (minusMinutes [this minutes] (-minus-minutes this minutes))
  (minusSeconds [this seconds] (-minus-seconds this seconds))
  (minusNanos [this nanos] (-minus-nanos this nanos))
  (atOffset [this offset] (-at-offset this offset)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1819
(s/def ::compare-to-args (args ::ChronoLocalDateTime/chrono-local-date-time))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalDateTime
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L735
(s/def ::to-local-date-args (args))
(defn -to-local-date [this] (wip ::-to-local-date))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L831
(s/def ::to-local-time-args (args))
(defn -to-local-time [this] (wip ::-to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1750
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1799
(s/def ::at-zone-args (args ::ZoneId/zone-id))
(defn -at-zone [this zone] (wip ::-at-zone))
(s/fdef -at-zone :args ::at-zone-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1856
(s/def ::is-after-args (args ::ChronoLocalDateTime/chrono-local-date-time))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1885
(s/def ::is-before-args (args ::ChronoLocalDateTime/chrono-local-date-time))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1914
(s/def ::is-equal-args (args ::ChronoLocalDateTime/chrono-local-date-time))
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)

(extend-type LocalDateTime
  ChronoLocalDateTime/IChronoLocalDateTime
  (toLocalDate [this] (-to-local-date this))
  (toLocalTime [this] (-to-local-time this))
  (format [this formatter] (-format this formatter))
  (atZone [this zone] (-at-zone this zone))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other))
  (isEqual [this other] (-is-equal this other)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L917
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L515
  ([this new-date new-time] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1146
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1182
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1365
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1394
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1682
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type LocalDateTime
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this new-date new-time] (-with this new-date new-time)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L574
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L648
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L685
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L717
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1595
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalDateTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1628
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type LocalDateTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L179
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L195
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::local-date-time)

(s/def ::of-args ::impl/of-args)
(def of #'impl/of)
(s/fdef of :args ::of-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L393
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L416
(s/def ::of-epoch-second-args (args ::j/long ::j/int ::ZoneOffset/zone-offset))
(defn ofEpochSecond [epoch-second nano-of-second offset] (wip ::ofEpochSecond))
(s/fdef ofEpochSecond :args ::of-epoch-second-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L447
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-date-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L476
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L490
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L144
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L151
(def MAX ::MAX--not-implemented)
