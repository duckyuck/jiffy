(ns jiffy.zoned-date-time
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

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

(defrecord ZonedDateTime [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1013
(defn -with-fixed-offset-zone [this] (wip ::-with-fixed-offset-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1055
(defn -get-year [this] (wip ::-get-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1069
(defn -get-month-value [this] (wip ::-get-month-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1084
(defn -get-month [this] (wip ::-get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1095
(defn -get-day-of-month [this] (wip ::-get-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1106
(defn -get-day-of-year [this] (wip ::-get-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1123
(defn -get-day-of-week [this] (wip ::-get-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1146
(defn -get-hour [this] (wip ::-get-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1155
(defn -get-minute [this] (wip ::-get-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1164
(defn -get-second [this] (wip ::-get-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1173
(defn -get-nano [this] (wip ::-get-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1336
(defn -with-year [this year] (wip ::-with-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1358
(defn -with-month [this month] (wip ::-with-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1381
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1404
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1427
(defn -with-hour [this hour] (wip ::-with-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1449
(defn -with-minute [this minute] (wip ::-with-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1471
(defn -with-second [this second] (wip ::-with-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1493
(defn -with-nano [this nano-of-second] (wip ::-with-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1527
(defn -truncated-to [this unit] (wip ::-truncated-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1630
(defn -plus-years [this years] (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1652
(defn -plus-months [this months] (wip ::-plus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1674
(defn -plus-weeks [this weeks] (wip ::-plus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1696
(defn -plus-days [this days] (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1730
(defn -plus-hours [this hours] (wip ::-plus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1748
(defn -plus-minutes [this minutes] (wip ::-plus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1766
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1784
(defn -plus-nanos [this nanos] (wip ::-plus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1876
(defn -minus-years [this years] (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1898
(defn -minus-months [this months] (wip ::-minus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1920
(defn -minus-weeks [this weeks] (wip ::-minus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1942
(defn -minus-days [this days] (wip ::-minus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1976
(defn -minus-hours [this hours] (wip ::-minus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1994
(defn -minus-minutes [this minutes] (wip ::-minus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2012
(defn -minus-seconds [this seconds] (wip ::-minus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2030
(defn -minus-nanos [this nanos] (wip ::-minus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2136
(defn -to-offset-date-time [this] (wip ::-to-offset-date-time))

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
(defn -get-offset [this] (wip ::-get-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L891
(defn -with-earlier-offset-at-overlap [this] (wip ::-with-earlier-offset-at-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L892
(defn -get-zone [this] (wip ::-get-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L919
(defn -with-later-offset-at-overlap [this] (wip ::-with-later-offset-at-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L967
(defn -with-zone-same-local [this zone] (wip ::-with-zone-same-local))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L990
(defn -with-zone-same-instant [this zone] (wip ::-with-zone-same-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1027
(defn -to-local-date-time [this] (wip ::-to-local-date-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1041
(defn -to-local-date [this] (wip ::-to-local-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1137
(defn -to-local-time [this] (wip ::-to-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2152
(defn -format [this formatter] (wip ::-format))

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

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1229
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1302
  ([this field new-value] (wip ::-with)))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1553
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1600
  ([this amount-to-add unit] (wip ::-plus)))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1810
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L1853
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2129
(defn -until [this end-exclusive unit] (wip ::-until))

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
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L774
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L813
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L850
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2055
(defn -query [this query] (wip ::-query))

(extend-type ZonedDateTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L198
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L215
  ([now--overloaded-param] (wip ::now)))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L292
  ([local-date-time zone] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L264
  ([date time zone] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L336
  ([year month day-of-month hour minute second nano-of-second zone] (wip ::of)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L366
(defn ofLocal [local-date-time zone preferred-offset] (wip ::ofLocal))

(defn ofInstant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L406
  ([instant zone] (wip ::ofInstant))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L432
  ([local-date-time offset zone] (wip ::ofInstant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L475
(defn ofStrict [local-date-time offset zone] (wip ::ofStrict))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L549
(defn from [temporal] (wip ::from))

(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L582
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L596
  ([text formatter] (wip ::parse)))
