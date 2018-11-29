(ns jiffy.zoned-date-time
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.clock :as Clock]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as Duration]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date :as LocalDate]
            [jiffy.local-date-impl :refer [#?@(:cljs [LocalDate])]]
            [jiffy.local-date-time :as LocalDateTime]
            [jiffy.local-time :as LocalTime]
            [jiffy.month :as Month]
            [jiffy.offset-date-time :as OffsetDateTime]
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
            [jiffy.zoned-date-time-impl :refer [create #?@(:cljs [ZonedDateTime])] :as impl]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone.zone-rules :as ZoneRules]
            [jiffy.conversion :as converstion]
            [jiffy.zone-offset :as ZoneOffset]
            [jiffy.zone-offset-impl :refer [#?@(:cljs [ZoneOffset])]]
            [jiffy.zone.zone-offset-transition :as ZoneOffsetTransition])
  #?(:clj (:import [jiffy.zoned_date_time_impl ZonedDateTime]
                   [jiffy.zone_offset_impl ZoneOffset]
                   [jiffy.local_date_impl LocalDate])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java
(defprotocol IZonedDateTime
  (withFixedOffsetZone [this])
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
  (toOffsetDateTime [this]))

(s/def ::zoned-date-time ::impl/zoned-date-time)

(defmacro args [& x] `(s/tuple ::zoned-date-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1013
(s/def ::with-fixed-offset-zone-args (args))
(defn -with-fixed-offset-zone [this]
  (if (= (:zone this) (:offset this))
    this
    (impl/create (:date-time this) (:offset this) (:offset this))))
(s/fdef -with-fixed-offset-zone :args ::with-fixed-offset-zone-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1055
(s/def ::get-year-args (args))
(defn -get-year [this]
  (-> this :date-time LocalDateTime/getYear))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1069
(s/def ::get-month-value-args (args))
(defn -get-month-value [this]
  (-> this :date-time LocalDateTime/getMonthValue))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1084
(s/def ::get-month-args (args))
(defn -get-month [this]
  (-> this :date-time LocalDateTime/getMonth))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1095
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this]
  (-> this :date-time LocalDateTime/getDayOfMonth))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1106
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this]
  (-> this :date-time LocalDateTime/getDayOfYear))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1123
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this]
  (-> this :date-time LocalDateTime/getDayOfWeek))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::DayOfWeek/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1146
(s/def ::get-hour-args (args))
(defn -get-hour [this]
  (-> this :date-time LocalDateTime/getHour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1155
(s/def ::get-minute-args (args))
(defn -get-minute [this]
  (-> this :date-time LocalDateTime/getMinute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1164
(s/def ::get-second-args (args))
(defn -get-second [this]
  (-> this :date-time LocalDateTime/getSecond))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1173
(s/def ::get-nano-args (args))
(defn -get-nano [this]
  (-> this :date-time LocalDateTime/getNano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

(declare ofLocal)

(defn- resolve-local [this date-time]
  (ofLocal date-time (:zone this) (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1336
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year]
  (resolve-local this (LocalDateTime/withYear (:date-time this) year)))
(s/fdef -with-year :args ::with-year-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1358
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month]
  (resolve-local this (LocalDateTime/withMonth (:date-time this) month)))
(s/fdef -with-month :args ::with-month-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1381
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month]
  (resolve-local this (LocalDateTime/withDayOfMonth (:date-time this) day-of-month)))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1404
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year]
  (resolve-local this (LocalDateTime/withDayOfYear (:date-time this) day-of-year)))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1427
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour]
  (resolve-local this (LocalDateTime/withHour (:date-time this) hour)))
(s/fdef -with-hour :args ::with-hour-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1449
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute]
  (resolve-local this (LocalDateTime/withMinute (:date-time this) minute)))
(s/fdef -with-minute :args ::with-minute-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1471
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second]
  (resolve-local this (LocalDateTime/withSecond (:date-time this) second)))
(s/fdef -with-second :args ::with-second-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1493
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second]
  (resolve-local this (LocalDateTime/withNano (:date-time this) nano-of-second)))
(s/fdef -with-nano :args ::with-nano-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1527
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit]
  (resolve-local this (LocalDateTime/truncatedTo (:date-time this) unit)))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1630
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years]
  (resolve-local this (LocalDateTime/plusYears (:date-time this) years)))
(s/fdef -plus-years :args ::plus-years-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1652
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months]
  (resolve-local this (LocalDateTime/plusMonths (:date-time this) months)))
(s/fdef -plus-months :args ::plus-months-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1674
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks]
  (resolve-local this (LocalDateTime/plusWeeks (:date-time this) weeks)))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1696
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days]
  (resolve-local this (LocalDateTime/plusDays (:date-time this) days)))
(s/fdef -plus-days :args ::plus-days-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1730
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours]
  (resolve-local this (LocalDateTime/plusHours (:date-time this) hours)))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1748
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes]
  (resolve-local this (LocalDateTime/plusMinutes (:date-time this) minutes)))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1766
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds]
  (resolve-local this (LocalDateTime/plusSeconds (:date-time this) seconds)))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1784
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos]
  (resolve-local this (LocalDateTime/plusNanos (:date-time this) nanos)))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1876
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years]
  (resolve-local this (LocalDateTime/minusYears (:date-time this) years)))
(s/fdef -minus-years :args ::minus-years-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1898
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months]
  (resolve-local this (LocalDateTime/minusMonths (:date-time this) months)))
(s/fdef -minus-months :args ::minus-months-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1920
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks]
  (resolve-local this (LocalDateTime/minusWeeks (:date-time this) weeks)))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1942
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days]
  (resolve-local this (LocalDateTime/minusDays (:date-time this) days)))
(s/fdef -minus-days :args ::minus-days-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1976
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours]
  (resolve-local this (LocalDateTime/minusHours (:date-time this) hours)))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1994
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes]
  (resolve-local this (LocalDateTime/minusMinutes (:date-time this) minutes)))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2012
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds]
  (resolve-local this (LocalDateTime/minusSeconds (:date-time this) seconds)))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2030
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos]
  (resolve-local this (LocalDateTime/minusNanos (:date-time this) nanos)))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2136
(s/def ::to-offset-date-time-args (args))
(defn -to-offset-date-time [this]
  (OffsetDateTime/of (:date-time this) (:offset this)))
(s/fdef -to-offset-date-time :args ::to-offset-date-time-args :ret ::OffsetDateTime/offset-date-time)

(extend-type ZonedDateTime
  IZonedDateTime
  (withFixedOffsetZone [this] (-with-fixed-offset-zone this))
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
  (toOffsetDateTime [this] (-to-offset-date-time this)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L819
(s/def ::get-offset-args (args))
(defn -get-offset [this]
  (:offset this))
(s/fdef -get-offset :args ::get-offset-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L891
(s/def ::with-earlier-offset-at-overlap-args (args))
(defn -with-earlier-offset-at-overlap [this]
  (let [trans (-> this :zone ZoneId/getRules (ZoneRules/getTransition (:date-time this)))]
    (if (and (not (nil? trans)) (ZoneOffsetTransition/isOverlap trans))
      (let [earlier-offset (ZoneOffsetTransition/getOffsetBefore trans)]
        (if (not= earlier-offset (:offset this))
          (impl/create (:date-time this) earlier-offset (:zone this))
          this))
      this)))
(s/fdef -with-earlier-offset-at-overlap :args ::with-earlier-offset-at-overlap-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L892
(s/def ::get-zone-args (args))
(defn -get-zone [this]
  (:zone this))
(s/fdef -get-zone :args ::get-zone-args :ret ::ZoneId/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L919
(s/def ::with-later-offset-at-overlap-args (args))
(defn -with-later-offset-at-overlap [this]
  (let [trans (-> this :zone ZoneId/getRules (ZoneRules/getTransition (:date-time this)))]
    (if (not (nil? trans))
      (let [later-offset (ZoneOffsetTransition/getOffsetAfter trans)]
        (if (not= later-offset (:offset this))
          (impl/create (:date-time this) later-offset (:zone this))
          this))
      this)))
(s/fdef -with-later-offset-at-overlap :args ::with-later-offset-at-overlap-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L967
(s/def ::with-zone-same-local-args (args ::ZoneId/zone-id))
(defn -with-zone-same-local [this zone]
  ;; TODO: Objects.requireNonNull(zone, "zone");
  (if (= (:zone this) zone)
    this
    (ofLocal (:date-time this) zone (:offset this))))
(s/fdef -with-zone-same-local :args ::with-zone-same-local-args :ret ::zoned-date-time)

(defn- -create [epoch-second nano-of-second zone]
  (let [rules (ZoneId/getRules zone)
        instant (Instant/ofEpochSecond epoch-second nano-of-second)
        offset (ZoneRules/getOffset rules instant)]
    (impl/create (LocalDateTime/ofEpochSecond epoch-second nano-of-second offset) offset zone)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L990
(s/def ::with-zone-same-instant-args (args ::ZoneId/zone-id))
(defn -with-zone-same-instant [this zone]
  ;;Objects.requireNonNull(zone, "zone");
  (if (= (:zone this) zone)
    this
    (-create (LocalDateTime/ofEpochSecond (:date-time this) (:offset this)) (LocalDateTime/getNano (:date-time this)) zone)))
(s/fdef -with-zone-same-instant :args ::with-zone-same-instant-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1027
(s/def ::to-local-date-time-args (args))
(defn -to-local-date-time [this]
  (:local-date this))
(s/fdef -to-local-date-time :args ::to-local-date-time-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1041
(s/def ::to-local-date-args (args))
(defn -to-local-date [this]
  (-> this :local-date LocalDateTime/toLocalDate))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1137
(s/def ::to-local-time-args (args))
(defn -to-local-time [this]
  (-> this :local-date LocalDateTime/toLocalTime))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2152
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter]
  ;; TODO: Objects.requireNonNull(formatter, "formatter");
  (DateTimeFormatter/format formatter this))
(s/fdef -format :args ::format-args :ret string?)

(extend-type ZonedDateTime
  ChronoZonedDateTime/IChronoZonedDateTime
  (getOffset [this] (-get-offset this))
  (withEarlierOffsetAtOverlap [this] (-with-earlier-offset-at-overlap this))
  (getZone [this] (-get-zone this))
  (withLaterOffsetAtOverlap [this] (-with-later-offset-at-overlap this))
  (withZoneSameLocal [this zone] (-with-zone-same-local this zone))
  (withZoneSameInstant [this zone] (-with-zone-same-instant this zone))
  (toLocalDateTime [this] (-to-local-date-time this))
  (toLocalDate [this] (-to-local-date this))
  (toLocalTime [this] (-to-local-time this))
  (format [this formatter] (-format this formatter)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1229
  ([this adjuster]
   (cond
     (instance? LocalDate adjuster)
     (resolve-local (LocalDateTime/of adjuster (LocalDateTime/toLocalTime (:date-time this))))

     (instance? LocalTime adjuster)
     (resolve-local (LocalDateTime/of (LocalDateTime/toLocalDate (:date-time this))) adjuster)

     (instance? LocalDateTime adjuster)
     (resolve-local )

     (instance? OffsetDateTime adjuster)
     (resolve-local )

     (instance? Instant adjuster)
     (resolve-local )

     (instance? ZoneOffset adjuster)
     (resolve-local )

     :default (TemporalAdjuster/adjustInto adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1302
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)


        if (adjuster instanceof LocalDate) {
            return resolveLocal(LocalDateTime.of((LocalDate) adjuster, dateTime.toLocalTime()));

        } else if (adjuster instanceof LocalTime) {
            return resolveLocal(LocalDateTime.of(dateTime.toLocalDate(), (LocalTime) adjuster));

        } else if (adjuster instanceof LocalDateTime) {
            return resolveLocal((LocalDateTime) adjuster);

        } else if (adjuster instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) adjuster;
            return ofLocal(odt.toLocalDateTime(), zone, odt.getOffset());

        } else if (adjuster instanceof Instant) {
            Instant instant = (Instant) adjuster;
            return create(instant.getEpochSecond(), instant.getNano(), zone);

        } else if (adjuster instanceof ZoneOffset) {
            return resolveOffset((ZoneOffset) adjuster);
        }

        return (ZonedDateTime) adjuster.adjustInto(this);

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1553
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1600
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1810
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1853
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2129
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type ZonedDateTime
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
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L704
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L774
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L813
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L850
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2055
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type ZonedDateTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L198
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L215
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::zoned-date-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L292
  ([local-date-time zone] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L264
  ([date time zone] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L336
  ([year month day-of-month hour minute second nano-of-second zone] (wip ::of)))
(s/fdef of :args ::of-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L366
(s/def ::of-local-args (args ::LocalDateTime/local-date-time ::ZoneId/zone-id ::ZoneOffset/zone-offset))
(defn ofLocal [local-date-time zone preferred-offset]
  ;; TODO: Objects.requireNonNull(localDateTime, "localDateTime");
  ;; TODO: Objects.requireNonNull(zone, "zone");

  (if (instance? ZoneOffset zone)
    (impl/create local-date-time zone zone)
    (let [rules (ZoneId/getRules zone)
          valid-offsets (ZoneRules/getValidOffsets rules local-date-time)]
      (cond
        (= (count valid-offsets) 1)
        (impl/create local-date-time (first valid-offsets) zone)

        (= (count valid-offsets) 0)
        (let [trans (ZoneRules/getTransition rules local-date-time)]
          (impl/create
           (LocalDateTime/plusSeconds local-date-time (-> trans ZoneOffsetTransition/getDuration Duration/getSeconds))
           (ZoneOffsetTransition/getOffsetAfter trans)
           zone))

        :default (if (and (not (nil? preferred-offset))
                          ((set valid-offsets) preferred-offset))
                   (impl/create local-date-time preferred-offset zone)
                   (do
                     ;; TODO: Objects.requireNonNull(validOffsets.get(0), "offset")
                     (impl/create local-date-time (first valid-offsets) zone)))))))
(s/fdef ofLocal :args ::of-local-args :ret ::zoned-date-time)

(s/def ::of-instant-args (args ::j/wip))
(defn ofInstant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L406
  ([instant zone] (wip ::ofInstant))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L432
  ([local-date-time offset zone] (wip ::ofInstant)))
(s/fdef ofInstant :args ::of-instant-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L475
(s/def ::of-strict-args (args ::LocalDateTime/local-date-time ::ZoneOffset/zone-offset ::ZoneId/zone-id))
(defn ofStrict [local-date-time offset zone] (wip ::ofStrict))
(s/fdef ofStrict :args ::of-strict-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L549
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::zoned-date-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L582
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L596
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::zoned-date-time)

#?(:clj
   (defmethod converstion/jiffy->java ZonedDateTime [{:keys [local-date zone offset]}]
     (java.time.ZonedDateTime/ofLocal local-date zone offset)))

#?(:clj
   (defmethod converstion/same? ZonedDateTime
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:local-date :zone :offset])
        (map #(% java-object) [(memfn toLocalDateTime) (memfn getZone) (memfn getOffset)]))))
