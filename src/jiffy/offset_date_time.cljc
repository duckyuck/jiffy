(ns jiffy.offset-date-time
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date :as LocalDate]
            [jiffy.local-date-time :as LocalDateTime]
            [jiffy.local-time :as LocalTime]
            [jiffy.month :as Month]
            [jiffy.offset-date-time-impl :refer [create #?@(:cljs [OffsetDateTime])] :as impl]
            [jiffy.offset-time :as OffsetTime]
            [jiffy.offset-time-impl :as OffsetTimeImpl]
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
            [jiffy.zone-offset-impl :as ZoneOffset]
            [jiffy.zoned-date-time-impl :as ZonedDateTime])
  #?(:clj (:import [jiffy.offset_date_time_impl OffsetDateTime])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java
(defprotocol IOffsetDateTime
  (getOffset [this])
  (toEpochSecond [this])
  (withOffsetSameLocal [this offset])
  (withOffsetSameInstant [this offset])
  (toLocalDateTime [this])
  (toLocalDate [this])
  (getYear [this])
  (getMonthValue [this])
  (getMonth [this])
  (getDayOfMonth [this])
  (getDayOfYear [this])
  (getDayOfWeek [this])
  (toLocalTime [this])
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
  (format [this formatter])
  (atZoneSameInstant [this zone])
  (atZoneSimilarLocal [this zone])
  (toOffsetTime [this])
  (toZonedDateTime [this])
  (toInstant [this])
  (isAfter [this other])
  (isBefore [this other])
  (isEqual [this other]))

(s/def ::offset-date-time ::impl/offset-date-time)

(defmacro args [& x] `(s/tuple ::offset-date-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L602
(s/def ::get-offset-args (args))
(defn -get-offset [this] (wip ::-get-offset))
(s/fdef -get-offset :args ::get-offset-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L636
(s/def ::to-epoch-second-args (args))
(defn -to-epoch-second [this] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L673
(s/def ::with-offset-same-local-args (args ::ZoneOffset/zone-offset))
(defn -with-offset-same-local [this offset] (wip ::-with-offset-same-local))
(s/fdef -with-offset-same-local :args ::with-offset-same-local-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L696
(s/def ::with-offset-same-instant-args (args ::ZoneOffset/zone-offset))
(defn -with-offset-same-instant [this offset] (wip ::-with-offset-same-instant))
(s/fdef -with-offset-same-instant :args ::with-offset-same-instant-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L714
(s/def ::to-local-date-time-args (args))
(defn -to-local-date-time [this] (wip ::-to-local-date-time))
(s/fdef -to-local-date-time :args ::to-local-date-time-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L727
(s/def ::to-local-date-args (args))
(defn -to-local-date [this] (wip ::-to-local-date))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L741
(s/def ::get-year-args (args))
(defn -get-year [this] (wip ::-get-year))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L755
(s/def ::get-month-value-args (args))
(defn -get-month-value [this] (wip ::-get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L770
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L781
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this] (wip ::-get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L792
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this] (wip ::-get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L809
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::DayOfWeek/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L822
(s/def ::to-local-time-args (args))
(defn -to-local-time [this] (wip ::-to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L831
(s/def ::get-hour-args (args))
(defn -get-hour [this] (wip ::-get-hour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L840
(s/def ::get-minute-args (args))
(defn -get-minute [this] (wip ::-get-minute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L849
(s/def ::get-second-args (args))
(defn -get-second [this] (wip ::-get-second))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L858
(s/def ::get-nano-args (args))
(defn -get-nano [this] (wip ::-get-nano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L994
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year] (wip ::-with-year))
(s/fdef -with-year :args ::with-year-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1010
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month] (wip ::-with-month))
(s/fdef -with-month :args ::with-month-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1027
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1044
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1060
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour] (wip ::-with-hour))
(s/fdef -with-hour :args ::with-hour-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1075
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute] (wip ::-with-minute))
(s/fdef -with-minute :args ::with-minute-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1090
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second] (wip ::-with-second))
(s/fdef -with-second :args ::with-second-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1105
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second] (wip ::-with-nano))
(s/fdef -with-nano :args ::with-nano-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1132
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit] (wip ::-truncated-to))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1216
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1240
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1259
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1278
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1291
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours] (wip ::-plus-hours))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1304
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes] (wip ::-plus-minutes))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1317
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1330
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos] (wip ::-plus-nanos))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1405
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1429
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1448
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1467
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1480
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours] (wip ::-minus-hours))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1493
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes] (wip ::-minus-minutes))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1506
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds] (wip ::-minus-seconds))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1519
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos] (wip ::-minus-nanos))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1672
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1693
(s/def ::at-zone-same-instant-args (args ::ZoneId/zone-id))
(defn -at-zone-same-instant [this zone] (wip ::-at-zone-same-instant))
(s/fdef -at-zone-same-instant :args ::at-zone-same-instant-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1721
(s/def ::at-zone-similar-local-args (args ::ZoneId/zone-id))
(defn -at-zone-similar-local [this zone] (wip ::-at-zone-similar-local))
(s/fdef -at-zone-similar-local :args ::at-zone-similar-local-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1733
(s/def ::to-offset-time-args (args))
(defn -to-offset-time [this] (wip ::-to-offset-time))
(s/fdef -to-offset-time :args ::to-offset-time-args :ret ::OffsetTimeImpl/offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1748
(s/def ::to-zoned-date-time-args (args))
(defn -to-zoned-date-time [this] (wip ::-to-zoned-date-time))
(s/fdef -to-zoned-date-time :args ::to-zoned-date-time-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1760
(s/def ::to-instant-args (args))
(defn -to-instant [this] (wip ::-to-instant))
(s/fdef -to-instant :args ::to-instant-args :ret ::Instant/instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1821
(s/def ::is-after-args (args ::offset-date-time))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1838
(s/def ::is-before-args (args ::offset-date-time))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1855
(s/def ::is-equal-args (args ::offset-date-time))
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)

(extend-type OffsetDateTime
  IOffsetDateTime
  (getOffset [this] (-get-offset this))
  (toEpochSecond [this] (-to-epoch-second this))
  (withOffsetSameLocal [this offset] (-with-offset-same-local this offset))
  (withOffsetSameInstant [this offset] (-with-offset-same-instant this offset))
  (toLocalDateTime [this] (-to-local-date-time this))
  (toLocalDate [this] (-to-local-date this))
  (getYear [this] (-get-year this))
  (getMonthValue [this] (-get-month-value this))
  (getMonth [this] (-get-month this))
  (getDayOfMonth [this] (-get-day-of-month this))
  (getDayOfYear [this] (-get-day-of-year this))
  (getDayOfWeek [this] (-get-day-of-week this))
  (toLocalTime [this] (-to-local-time this))
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
  (format [this formatter] (-format this formatter))
  (atZoneSameInstant [this zone] (-at-zone-same-instant this zone))
  (atZoneSimilarLocal [this zone] (-at-zone-similar-local this zone))
  (toOffsetTime [this] (-to-offset-time this))
  (toZonedDateTime [this] (-to-zoned-date-time this))
  (toInstant [this] (-to-instant this))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other))
  (isEqual [this other] (-is-equal this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1802
(s/def ::compare-to-args (args ::j/wip))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type OffsetDateTime
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L908
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L423
  ([this date-time offset] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::offset-date-time)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1158
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1188
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::offset-date-time)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1356
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1380
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1654
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type OffsetDateTime
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this date-time offset] (-with this date-time offset)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L484
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L557
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L596
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L633
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1544
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type OffsetDateTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1590
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type OffsetDateTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L161
(defn timeLineOrder [] (wip ::timeLineOrder))
(s/fdef timeLineOrder :ret ::j/wip)

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L211
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L228
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::offset-date-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L275
  ([date-time offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L261
  ([date time offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L303
  ([year month day-of-month hour minute second nano-of-second offset] (wip ::of)))
(s/fdef of :args ::of-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L323
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L354
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::offset-date-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L386
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L400
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L138
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L146
(def MAX ::MAX--not-implemented)
