(ns jiffy.zoned-date-time
  (:require [clojure.spec.alpha :as s]
            [jiffy.asserts :as asserts]
            [jiffy.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.clock :as clock :refer [#?@(:cljs [IClock])]]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as duration]
            [jiffy.exception :refer [ex try* UnsupportedTemporalTypeException DateTimeException]]
            [jiffy.format.date-time-formatter :as date-time-formatter]
            [jiffy.instant-impl :as instant :refer [#?@(:cljs [Instant])]]
            [jiffy.local-date :as local-date]
            [jiffy.local-date-impl :refer [#?@(:cljs [LocalDate])]]
            [jiffy.local-date-time :as local-date-time]
            [jiffy.local-date-time-impl :refer [#?@(:cljs [LocalDateTime])]]
            [jiffy.local-time-impl  :refer [#?@(:cljs [LocalTime])]]
            [jiffy.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as offset-date-time]
            [jiffy.offset-date-time-impl :refer [#?@(:cljs [OffsetDateTime])]]
            [jiffy.period :refer [#?@(:cljs [Period])]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.chrono-unit :refer [#?@(:cljs [ChronoUnit])]]
            [jiffy.temporal.temporal-field :as temporal-field :refer [#?@(:cljs [ITemporalField])]]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-unit :as temporal-unit :refer [#?@(:cljs [ITemporalUnit])]]
            [jiffy.temporal.chrono-field :as chrono-field :refer [#?@(:cljs [ChronoField])]]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zoned-date-time-impl :refer [#?@(:cljs [ZonedDateTime])] :as impl]
            [jiffy.zone-id :as zone-id :refer [#?@(:cljs [IZoneId])]]
            [jiffy.zone.zone-rules :as zone-rules]
            #?(:clj [jiffy.conversion :as converstion])
            [jiffy.zone-offset :as zone-offset]
            [jiffy.zone-offset-impl :refer [#?@(:cljs [ZoneOffset])]]
            [jiffy.zone.zone-offset-transition :as zone-offset-transition])
  #?(:clj (:import [jiffy.clock IClock]
                   [jiffy.instant_impl Instant]
                   [jiffy.local_date_impl LocalDate]
                   [jiffy.local_time_impl LocalTime]
                   [jiffy.local_date_time_impl LocalDateTime]
                   [jiffy.offset_date_time_impl OffsetDateTime]
                   [jiffy.period Period]
                   [jiffy.temporal.chrono_unit ChronoUnit]
                   [jiffy.temporal.chrono_field ChronoField]
                   [jiffy.temporal.temporal_field ITemporalField]
                   [jiffy.temporal.temporal_unit ITemporalUnit]
                   [jiffy.zone_id IZoneId]
                   [jiffy.zone_offset_impl ZoneOffset]
                   [jiffy.zoned_date_time_impl ZonedDateTime])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java
(defprotocol IZonedDateTime
  (with-fixed-offset-zone [this])
  (get-year [this])
  (get-month-value [this])
  (get-month [this])
  (get-day-of-month [this])
  (get-day-of-year [this])
  (get-day-of-week [this])
  (get-hour [this])
  (get-minute [this])
  (get-second [this])
  (get-nano [this])
  (with-year [this year])
  (with-month [this month])
  (with-day-of-month [this day-of-month])
  (with-day-of-year [this day-of-year])
  (with-hour [this hour])
  (with-minute [this minute])
  (with-second [this second])
  (with-nano [this nano-of-second])
  (truncated-to [this unit])
  (plus-years [this years])
  (plus-months [this months])
  (plus-weeks [this weeks])
  (plus-days [this days])
  (plus-hours [this hours])
  (plus-minutes [this minutes])
  (plus-seconds [this seconds])
  (plus-nanos [this nanos])
  (minus-years [this years])
  (minus-months [this months])
  (minus-weeks [this weeks])
  (minus-days [this days])
  (minus-hours [this hours])
  (minus-minutes [this minutes])
  (minus-seconds [this seconds])
  (minus-nanos [this nanos])
  (to-offset-date-time [this]))

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
  (-> this :date-time local-date-time/get-year))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1069
(s/def ::get-month-value-args (args))
(defn -get-month-value [this]
  (-> this :date-time local-date-time/get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1084
(s/def ::get-month-args (args))
(defn -get-month [this]
  (-> this :date-time local-date-time/get-month))
(s/fdef -get-month :args ::get-month-args :ret ::month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1095
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this]
  (-> this :date-time local-date-time/get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1106
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this]
  (-> this :date-time local-date-time/get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1123
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this]
  (-> this :date-time local-date-time/get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::day-of-week/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1146
(s/def ::get-hour-args (args))
(defn -get-hour [this]
  (-> this :date-time local-date-time/get-hour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1155
(s/def ::get-minute-args (args))
(defn -get-minute [this]
  (-> this :date-time local-date-time/get-minute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1164
(s/def ::get-second-args (args))
(defn -get-second [this]
  (-> this :date-time local-date-time/get-second))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1173
(s/def ::get-nano-args (args))
(defn -get-nano [this]
  (-> this :date-time local-date-time/get-nano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

(declare of-local)
(declare of-instant)

(defn- resolve-local [this date-time]
  (of-local date-time (:zone this) (:offset this)))

(defn- resolve-instant [this date-time]
  (of-instant date-time (:offset this) (:zone this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1336
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year]
  (resolve-local this (local-date-time/with-year (:date-time this) year)))
(s/fdef -with-year :args ::with-year-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1358
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month]
  (resolve-local this (local-date-time/with-month (:date-time this) month)))
(s/fdef -with-month :args ::with-month-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1381
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month]
  (resolve-local this (local-date-time/with-day-of-month (:date-time this) day-of-month)))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1404
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year]
  (resolve-local this (local-date-time/with-day-of-year (:date-time this) day-of-year)))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1427
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour]
  (resolve-local this (local-date-time/with-hour (:date-time this) hour)))
(s/fdef -with-hour :args ::with-hour-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1449
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute]
  (resolve-local this (local-date-time/with-minute (:date-time this) minute)))
(s/fdef -with-minute :args ::with-minute-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1471
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second]
  (resolve-local this (local-date-time/with-second (:date-time this) second)))
(s/fdef -with-second :args ::with-second-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1493
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second]
  (resolve-local this (local-date-time/with-nano (:date-time this) nano-of-second)))
(s/fdef -with-nano :args ::with-nano-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1527
(s/def ::truncated-to-args (args ::temporal-unit/temporal-unit))
(defn -truncated-to [this unit]
  (resolve-local this (local-date-time/truncated-to (:date-time this) unit)))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1630
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years]
  (resolve-local this (local-date-time/plus-years (:date-time this) years)))
(s/fdef -plus-years :args ::plus-years-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1652
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months]
  (resolve-local this (local-date-time/plus-months (:date-time this) months)))
(s/fdef -plus-months :args ::plus-months-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1674
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks]
  (resolve-local this (local-date-time/plus-weeks (:date-time this) weeks)))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1696
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days]
  (resolve-local this (local-date-time/plus-days (:date-time this) days)))
(s/fdef -plus-days :args ::plus-days-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1730
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours]
  (resolve-local this (local-date-time/plus-hours (:date-time this) hours)))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1748
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes]
  (resolve-local this (local-date-time/plus-minutes (:date-time this) minutes)))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1766
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds]
  (resolve-local this (local-date-time/plus-seconds (:date-time this) seconds)))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1784
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos]
  (resolve-local this (local-date-time/plus-nanos (:date-time this) nanos)))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1876
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years]
  (resolve-local this (local-date-time/minus-years (:date-time this) years)))
(s/fdef -minus-years :args ::minus-years-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1898
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months]
  (resolve-local this (local-date-time/minus-months (:date-time this) months)))
(s/fdef -minus-months :args ::minus-months-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1920
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks]
  (resolve-local this (local-date-time/minus-weeks (:date-time this) weeks)))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1942
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days]
  (resolve-local this (local-date-time/minus-days (:date-time this) days)))
(s/fdef -minus-days :args ::minus-days-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1976
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours]
  (resolve-local this (local-date-time/minus-hours (:date-time this) hours)))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1994
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes]
  (resolve-local this (local-date-time/minus-minutes (:date-time this) minutes)))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2012
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds]
  (resolve-local this (local-date-time/minus-seconds (:date-time this) seconds)))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2030
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos]
  (resolve-local this (local-date-time/minus-nanos (:date-time this) nanos)))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2136
(s/def ::to-offset-date-time-args (args))
(defn -to-offset-date-time [this]
  (offset-date-time/of (:date-time this) (:offset this)))
(s/fdef -to-offset-date-time :args ::to-offset-date-time-args :ret ::offset-date-time/offset-date-time)

(extend-type ZonedDateTime
  IZonedDateTime
  (with-fixed-offset-zone [this] (-with-fixed-offset-zone this))
  (get-year [this] (-get-year this))
  (get-month-value [this] (-get-month-value this))
  (get-month [this] (-get-month this))
  (get-day-of-month [this] (-get-day-of-month this))
  (get-day-of-year [this] (-get-day-of-year this))
  (get-day-of-week [this] (-get-day-of-week this))
  (get-hour [this] (-get-hour this))
  (get-minute [this] (-get-minute this))
  (get-second [this] (-get-second this))
  (get-nano [this] (-get-nano this))
  (with-year [this year] (-with-year this year))
  (with-month [this month] (-with-month this month))
  (with-day-of-month [this day-of-month] (-with-day-of-month this day-of-month))
  (with-day-of-year [this day-of-year] (-with-day-of-year this day-of-year))
  (with-hour [this hour] (-with-hour this hour))
  (with-minute [this minute] (-with-minute this minute))
  (with-second [this second] (-with-second this second))
  (with-nano [this nano-of-second] (-with-nano this nano-of-second))
  (truncated-to [this unit] (-truncated-to this unit))
  (plus-years [this years] (-plus-years this years))
  (plus-months [this months] (-plus-months this months))
  (plus-weeks [this weeks] (-plus-weeks this weeks))
  (plus-days [this days] (-plus-days this days))
  (plus-hours [this hours] (-plus-hours this hours))
  (plus-minutes [this minutes] (-plus-minutes this minutes))
  (plus-seconds [this seconds] (-plus-seconds this seconds))
  (plus-nanos [this nanos] (-plus-nanos this nanos))
  (minus-years [this years] (-minus-years this years))
  (minus-months [this months] (-minus-months this months))
  (minus-weeks [this weeks] (-minus-weeks this weeks))
  (minus-days [this days] (-minus-days this days))
  (minus-hours [this hours] (-minus-hours this hours))
  (minus-minutes [this minutes] (-minus-minutes this minutes))
  (minus-seconds [this seconds] (-minus-seconds this seconds))
  (minus-nanos [this nanos] (-minus-nanos this nanos))
  (to-offset-date-time [this] (-to-offset-date-time this)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L819
(s/def ::get-offset-args (args))
(defn -get-offset [this]
  (:offset this))
(s/fdef -get-offset :args ::get-offset-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L891
(s/def ::with-earlier-offset-at-overlap-args (args))
(defn -with-earlier-offset-at-overlap [this]
  (let [trans (-> this :zone zone-id/get-rules (zone-rules/get-transition (:date-time this)))]
    (if (and (not (nil? trans)) (zone-offset-transition/is-overlap trans))
      (let [earlier-offset (zone-offset-transition/get-offset-before trans)]
        (if (not= earlier-offset (:offset this))
          (impl/create (:date-time this) earlier-offset (:zone this))
          this))
      this)))
(s/fdef -with-earlier-offset-at-overlap :args ::with-earlier-offset-at-overlap-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L892
(s/def ::get-zone-args (args))
(defn -get-zone [this]
  (:zone this))
(s/fdef -get-zone :args ::get-zone-args :ret ::zone-id/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L919
(s/def ::with-later-offset-at-overlap-args (args))
(defn -with-later-offset-at-overlap [this]
  (let [trans (-> this :zone zone-id/get-rules (zone-rules/get-transition (:date-time this)))]
    (if (not (nil? trans))
      (let [later-offset (zone-offset-transition/get-offset-after trans)]
        (if (not= later-offset (:offset this))
          (impl/create (:date-time this) later-offset (:zone this))
          this))
      this)))
(s/fdef -with-later-offset-at-overlap :args ::with-later-offset-at-overlap-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L967
(s/def ::with-zone-same-local-args (args ::zone-id/zone-id))
(defn -with-zone-same-local [this zone]
  (asserts/require-non-nil zone "zone")
  (if (= (:zone this) zone)
    this
    (of-local (:date-time this) zone (:offset this))))
(s/fdef -with-zone-same-local :args ::with-zone-same-local-args :ret ::zoned-date-time)

(defn- -create [epoch-second nano-of-second zone]
  (let [rules (zone-id/get-rules zone)
        instant (instant/of-epoch-second epoch-second nano-of-second)
        offset (zone-rules/get-offset rules instant)]
    (impl/create (local-date-time/of-epoch-second epoch-second nano-of-second offset) offset zone)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L990
(s/def ::with-zone-same-instant-args (args ::zone-id/zone-id))
(defn -with-zone-same-instant [this zone]
  (asserts/require-non-nil zone "zone")
  (if (= (:zone this) zone)
    this
    (-create (chrono-local-date-time/to-epoch-second (:date-time this) (:offset this)) (local-date-time/get-nano (:date-time this)) zone)))
(s/fdef -with-zone-same-instant :args ::with-zone-same-instant-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1027
(s/def ::to-local-date-time-args (args))
(defn -to-local-date-time [this]
  (:local-date this))
(s/fdef -to-local-date-time :args ::to-local-date-time-args :ret ::local-date-time/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1041
(s/def ::to-local-date-args (args))
(defn -to-local-date [this]
  (-> this :local-date chrono-local-date-time/to-local-date))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1137
(s/def ::to-local-time-args (args))
(defn -to-local-time [this]
  (-> this :local-date chrono-local-date-time/to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::local-time/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2152
(s/def ::format-args (args ::date-time-formatter/date-time-formatter))
(defn -format [this formatter]
  (asserts/require-non-nil formatter "formatter")
  (date-time-formatter/format formatter this))
(s/fdef -format :args ::format-args :ret string?)

(extend-type ZonedDateTime
  chrono-zoned-date-time/IChronoZonedDateTime
  (get-offset [this] (-get-offset this))
  (with-earlier-offset-at-overlap [this] (-with-earlier-offset-at-overlap this))
  (get-zone [this] (-get-zone this))
  (with-later-offset-at-overlap [this] (-with-later-offset-at-overlap this))
  (with-zone-same-local [this zone] (-with-zone-same-local this zone))
  (with-zone-same-instant [this zone] (-with-zone-same-instant this zone))
  (to-local-date-time [this] (-to-local-date-time this))
  (to-local-date [this] (-to-local-date this))
  (to-local-time [this] (-to-local-time this))
  (format [this formatter] (-format this formatter)))

(defn- resolve-offset [this offset]
  (if (and (not= offset (:offset this))
           (-> this :zone zone-id/get-rules (zone-rules/is-valid-offset (:date-time this) offset)))
    (ZonedDateTime. (:date-time this) offset (:zone this))
    this))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1229
  ([this adjuster]
   (cond
     (instance? LocalDate adjuster)
     (resolve-local this (local-date-time/of adjuster (chrono-local-date-time/to-local-time (:time this))))

     (instance? LocalTime adjuster)
     (resolve-local this (local-date-time/of (chrono-local-date-time/to-local-date (:date this)) adjuster))

     (instance? LocalDateTime adjuster)
     (resolve-local this adjuster)

     (instance? OffsetDateTime adjuster)
     (of-local (offset-date-time/to-local-date-time adjuster) (:zone this) (offset-date-time/get-offset adjuster))

     (instance? Instant adjuster)
     (-create (instant/-get-epoch-second adjuster) (instant/-get-nano adjuster) (:zone this))

     (instance? ZoneOffset adjuster)
     (resolve-offset this adjuster)

     :default (temporal-adjuster/adjust-into adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1302
  ([this field new-value]
   (if (instance? ChronoField field)
     (case field
       chrono-field/INSTANT_SECONDS
       (-create new-value (-get-nano this) (:zone this))

       chrono-field/OFFSET_SECONDS
       (resolve-offset this (-> new-value
                                (chrono-field/check-valid-int-value new-value)
                                zone-offset/of-total-seconds))

       (resolve-local this (temporal/with (:date-time this) field new-value))))))
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1553
  ([this amount-to-add]
   (if (instance? Period amount-to-add)
     (resolve-local this (temporal/plus (:date-time this) amount-to-add))
     (do
       (asserts/require-non-nil amount-to-add "amount-to-add")
       (temporal-amount/add-to amount-to-add this))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1600
  ([this amount-to-add unit]
   (if (instance? ChronoUnit unit)
     (if (temporal-unit/is-date-based unit)
       (resolve-local this (temporal/plus (:date-time this) amount-to-add unit))
       (resolve-instant this (temporal/plus (:date-time this) amount-to-add unit)))
     (temporal-unit/add-to unit this amount-to-add))))
(s/fdef -plus :args ::plus-args :ret ::temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1810
  ([this amount-to-subtract]
   (if (instance? Period amount-to-subtract)
     (resolve-local this (temporal/minus (:date-time this) amount-to-subtract))
     (do
       (asserts/require-non-nil amount-to-subtract "amount-to-subtract")
       (temporal-amount/add-to amount-to-subtract this))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1853
  ([this amount-to-subtract unit]
   (if (= math/long-min-value amount-to-subtract)
     (-> this
         (-plus math/long-max-value unit)
         (-plus 1 unit))
     (-plus this (- amount-to-subtract) unit))))
(s/fdef -minus :args ::minus-args :ret ::temporal/temporal)

(declare from)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2129
(s/def ::until-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until [this end-exclusive unit]
  (let [end (from end-exclusive)]
    (if (instance? ChronoUnit unit)
      (let [end (-with-zone-same-instant this (:zone this))]
        (if (temporal-unit/is-date-based unit)
          (temporal/until (:date-time this) (:date-time end) unit)
          (temporal/until (-to-offset-date-time this) (to-offset-date-time end) unit)))
      (temporal-unit/between unit this end))))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type ZonedDateTime
  temporal/ITemporal
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L704
(s/def ::is-supported-args (args ::temporal-field/temporal-field)) ;; TODO: temporal-field OR temporal-unit
(defn -is-supported [this arg]
  (cond
    (satisfies? ITemporalField arg)
    (or (instance? ChronoField arg)
        (and (not= arg nil) (temporal-field/is-supported-by arg this)))

    (satisfies? ITemporalUnit arg)
    (do ;; ChronoZonedDateTime.super.isSupported(unit)
      )))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L774
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field]
  (if (instance? ChronoField field)
    (if (or (= field chrono-field/INSTANT_SECONDS)
            (= field chrono-field/OFFSET_SECONDS))
      (temporal-field/range field)
      (temporal-accessor/range (:date-time this) field))
    (temporal-field/range-refined-by field this)))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L813
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field]
  (if (instance? ChronoField field)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (throw (ex UnsupportedTemporalTypeException (str "Invalid field 'InstantSeconds' for get() method, use getLong() instead")))

      (= chrono-field/OFFSET_SECONDS field)
      (-> this -get-offset zone-offset/get-total-seconds)

      :default (temporal-accessor/get (:date-time this) field))
    ;; TODO: ChronoZonedDateTime.super.get(field);
    ))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L850
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field]
  (if (instance? ChronoField field)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (chrono-zoned-date-time/to-epoch-second this)

      (= chrono-field/OFFSET_SECONDS field)
      (-> this -get-offset zone-offset/get-total-seconds)

      :default (temporal-accessor/get-long (:date-time this) field))
    (temporal-field/get-from field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2055
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query]
  (if (= query (temporal-queries/local-date))
    (-to-local-date this)
    ;; TODO: return ChronoZonedDateTime.super.query(query);
    ))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type ZonedDateTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L198
  ([]
   (now (clock/system-default-zone)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L215
  ([arg]
   (cond
     (satisfies? IZoneId arg)
     (clock/system arg)

     (satisfies? IClock arg)
     (do
       (asserts/require-non-nil arg "clock")
       (of-instant (clock/instant arg) (clock/get-zone arg))))))
(s/fdef now :args ::now-args :ret ::zoned-date-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L292
  ([local-date-time zone]
   (of-local local-date-time zone nil))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L264
  ([date time zone]
   (of (local-date-time/of date time) zone))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L336
  ([year month day-of-month hour minute second nano-of-second zone]
   (of-local (local-date-time/of year month day-of-month hour minute second nano-of-second) zone nil)))

(s/fdef of :args ::of-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L366
(s/def ::of-local-args (args ::local-date-time/local-date-time ::zone-id/zone-id ::zone-offset/zone-offset))
(defn of-local [local-date-time zone preferred-offset]
  (asserts/require-non-nil local-date-time "LocalDateTime")
  (asserts/require-non-nil zone "zone")
  (if (instance? ZoneOffset zone)
    (impl/create local-date-time zone zone)
    (let [rules (zone-id/get-rules zone)
          valid-offsets (zone-rules/get-valid-offsets rules local-date-time)]
      (cond
        (= (count valid-offsets) 1)
        (impl/create local-date-time (first valid-offsets) zone)

        (= (count valid-offsets) 0)
        (let [trans (zone-rules/get-transition rules local-date-time)]
          (impl/create
           (local-date-time/plus-seconds local-date-time (-> trans zone-offset-transition/get-duration duration/get-seconds))
           (zone-offset-transition/get-offset-after trans)
           zone))

        :default (if (and (not (nil? preferred-offset))
                          ((set valid-offsets) preferred-offset))
                   (impl/create local-date-time preferred-offset zone)
                   (do
                     (asserts/require-non-nil (first valid-offsets) "offset")
                     (impl/create local-date-time (first valid-offsets) zone)))))))
(s/fdef of-local :args ::of-local-args :ret ::zoned-date-time)

(s/def ::of-instant-args (args ::j/wip))
(defn of-instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L406
  ([instant zone]
   (asserts/require-non-nil instant "instant")
   (asserts/require-non-nil zone "zone")
   (-create (instant/-get-epoch-second instant) (instant/-get-nano instant) zone))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L432
  ([local-date-time offset zone]
   (asserts/require-non-nil local-date-time "LocalDateTime")
   (asserts/require-non-nil offset "offset")
   (asserts/require-non-nil zone "zone")
   (if (-> zone zone-id/get-rules (zone-rules/is-valid-offset local-date-time offset))
     (ZonedDateTime. local-date-time offset zone)
     (-create (chrono-local-date-time/to-epoch-second local-date-time offset)
              (local-date-time/get-nano local-date-time)
              zone))))
(s/fdef of-instant :args ::of-instant-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L475
(s/def ::of-strict-args (args ::local-date-time/local-date-time ::zone-offset/zone-offset ::zone-id/zone-id))
(defn of-strict [local-date-time offset zone]
  (asserts/require-non-nil local-date-time "LocalDateTime")
  (asserts/require-non-nil offset "offset")
  (asserts/require-non-nil zone "zone")
  (let [rules (-> zone zone-id/get-rules)]
    (if (-> rules (zone-rules/is-valid-offset local-date-time offset) not)
      (let [trans (-> rules (zone-rules/get-transition local-date-time))]
        (if (and (not (nil? trans)) (zone-offset-transition/is-gap trans))
          (throw
           ;; error message says daylight savings for simplicity
           ;; even though there are other kinds of gaps
           (ex DateTimeException (str "LocalDateTime '" local-date-time
                                      "' does not exist in zone '" zone
                                      "' due to a gap in the local time-line, typically caused by daylight savings")))
          (ZonedDateTime. local-date-time offset zone))))))
(s/fdef of-strict :args ::of-strict-args :ret ::zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L549
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
(defn from [temporal]
  (if (instance? ZonedDateTime temporal)
    temporal
    (try*
      (let [zone (zone-id/from temporal)]
        (if (temporal-accessor/is-supported temporal chrono-field/INSTANT_SECONDS)
          (-create (temporal-accessor/get-long temporal chrono-field/INSTANT_SECONDS)
                   (temporal-accessor/get temporal chrono-field/NANO_OF_SECOND)
                   zone)
          (of (local-date/from temporal) (local-time/from temporal))))
      (catch :default e
        (throw (ex DateTimeException (str "Unable to obtain ZonedDateTime from TemporalAccessor: "
                                          temporal " of type "
                                          (name (type temporal))
                                          e)))))))
(s/fdef from :args ::from-args :ret ::zoned-date-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L582
  ([text]
   (parse text date-time-formatter/ISO_ZONED_DATE_TIME))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L596
  ([text formatter]
   (asserts/require-non-nil formatter "formatter")
   (date-time-formatter/parse text from)))
(s/fdef parse :args ::parse-args :ret ::zoned-date-time)

#?(:clj
   (defmethod converstion/jiffy->java ZonedDateTime [{:keys [local-date zone offset]}]
     (java.time.ZonedDateTime/ofLocal local-date zone offset)))

#?(:clj
   (defmethod converstion/same? ZonedDateTime
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:local-date :zone :offset])
        (map #(% java-object) [(memfn toLocalDateTime) (memfn getZone) (memfn getOffset)]))))
