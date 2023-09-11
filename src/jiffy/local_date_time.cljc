(ns jiffy.local-date-time
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.chrono.chrono-local-date-time-defaults :as chrono-local-date-time-defaults]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date :as local-date]
            [jiffy.local-date-time-impl :refer [#?@(:cljs [LocalDateTime])] :as impl]
            [jiffy.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as LocalDate]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as LocalTime]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query])
  #?(:clj (:import [jiffy.local_date_time_impl LocalDateTime])))

(s/def ::local-date-time ::impl/local-date-time)

(defmacro args [& x] `(s/tuple ::local-date-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L749
(def-method -get-year ::j/int
  [this ::local-date-time]
  (-> this :date :year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L763
(def-method -get-month-value ::j/int
  [this ::local-date-time]
  (wip ::-get-month-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L778
(def-method -get-month ::month/month
  [this ::local-date-time]
  (wip ::-get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L789
(def-method -get-day-of-month ::j/int
  [this ::local-date-time]
  (wip ::-get-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L800
(def-method -get-day-of-year ::j/int
  [this ::local-date-time]
  (wip ::-get-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L817
(def-method -get-day-of-week ::day-of-week/day-of-week
  [this ::local-date-time]
  (wip ::-get-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L840
(def-method -get-hour ::j/int
  [this ::local-date-time]
  (wip ::-get-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L849
(def-method -get-minute ::j/int
  [this ::local-date-time]
  (wip ::-get-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L858
(def-method -get-second ::j/int
  [this ::local-date-time]
  (wip ::-get-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L867
(def-method -get-nano ::j/int
  [this ::local-date-time]
  (wip ::-get-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L989
(def-method -with-year ::local-date-time
  [this ::local-date-time
   year ::j/int]
  (wip ::-with-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1005
(def-method -with-month ::local-date-time
  [this ::local-date-time
   month ::j/int]
  (wip ::-with-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1022
(def-method -with-day-of-month ::local-date-time
  [this ::local-date-time
   day-of-month ::j/int]
  (wip ::-with-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1038
(def-method -with-day-of-year ::local-date-time
  [this ::local-date-time
   day-of-year ::j/int]
  (wip ::-with-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1052
(def-method -with-hour ::local-date-time
  [this ::local-date-time
   hour ::j/int]
  (wip ::-with-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1066
(def-method -with-minute ::local-date-time
  [this ::local-date-time
   minute ::j/int]
  (wip ::-with-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1080
(def-method -with-second ::local-date-time
  [this ::local-date-time
   second ::j/int]
  (wip ::-with-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1094
(def-method -with-nano ::local-date-time
  [this ::local-date-time
   nano-of-second ::j/int]
  (wip ::-with-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1120
(def-method -truncated-to ::local-date-time
  [this ::local-date-time
   unit ::temporal-unit/temporal-unit]
  (wip ::-truncated-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1220
(def-method -plus-years ::local-date-time
  [this ::local-date-time
   years ::j/long]
  (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1245
(def-method -plus-months ::local-date-time
  [this ::local-date-time
   months ::j/long]
  (wip ::-plus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1265
(def-method -plus-weeks ::local-date-time
  [this ::local-date-time
   weeks ::j/long]
  (wip ::-plus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1285
(def-method -plus-days ::local-date-time
  [this ::local-date-time
   days ::j/long]
  (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1300
(def-method -plus-hours ::local-date-time
  [this ::local-date-time
   hours ::j/long]
  (wip ::-plus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1313
(def-method -plus-minutes ::local-date-time
  [this ::local-date-time
   minutes ::j/long]
  (wip ::-plus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1326
(def-method -plus-seconds ::local-date-time
  [this ::local-date-time
   seconds ::j/long]
  (wip ::-plus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1339
(def-method -plus-nanos ::local-date-time
  [this ::local-date-time
   nanos ::j/long]
  (wip ::-plus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1419
(def-method -minus-years ::local-date-time
  [this ::local-date-time
   years ::j/long]
  (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1443
(def-method -minus-months ::local-date-time
  [this ::local-date-time
   months ::j/long]
  (wip ::-minus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1462
(def-method -minus-weeks ::local-date-time
  [this ::local-date-time
   weeks ::j/long]
  (wip ::-minus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1481
(def-method -minus-days ::local-date-time
  [this ::local-date-time
   days ::j/long]
  (wip ::-minus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1495
(def-method -minus-hours ::local-date-time
  [this ::local-date-time
   hours ::j/long]
  (wip ::-minus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1508
(def-method -minus-minutes ::local-date-time
  [this ::local-date-time
   minutes ::j/long]
  (wip ::-minus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1521
(def-method -minus-seconds ::local-date-time
  [this ::local-date-time
   seconds ::j/long]
  (wip ::-minus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1534
(def-method -minus-nanos ::local-date-time
  [this ::local-date-time
   nanos ::j/long]
  (wip ::-minus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1765
(def-method -at-offset ::offset-date-time/offset-date-time
  [this ::local-date-time
   offset ::zone-offset/zone-offset]
  (wip ::-at-offset))

(extend-type LocalDateTime
  local-date-time/ILocalDateTime
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
  (at-offset [this offset] (-at-offset this offset)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1819
(def-method -compare-to ::j/int
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (if-not (satisfies? local-date-time/ILocalDateTime other)
    (chrono-local-date-time-defaults/-compare-to this other)
    (let [cmp (time-comparable/compare-to (:date this) (:date other))]
      (if (zero? cmp)
        (time-comparable/compare-to (:time this) (:time other))
        cmp))))

(extend-type LocalDateTime
  time-comparable/ITimeComparable
  (compare-to [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L735
(def-method -to-local-date ::chrono-local-date/chrono-local-date
  [this ::instant]
  (:date this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L831
(def-method -to-local-time ::LocalTime/local-time
  [this ::instant]
  (:time this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1750
(def-method -format string?
  [this ::local-date-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1799
(def-method -at-zone ::zoned-date-time/zoned-date-time
  [this ::local-date-time
   zone ::zone-id/zone-id]
  (wip ::-at-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1856
(def-method -is-after ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (chrono-local-date-time-defaults/-is-after this other))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1885
(def-method -is-before ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (wip ::-is-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1914
(def-method -is-equal ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (wip ::-is-equal))

(extend-type LocalDateTime
  chrono-local-date-time/IChronoLocalDateTime
  (to-local-date [this] (-to-local-date this))
  (to-local-time [this] (-to-local-time this))
  (format [this formatter] (-format this formatter))
  (at-zone [this zone] (-at-zone this zone))
  (is-after [this other] (-is-after this other))
  (is-before [this other] (-is-before this other))
  (is-equal [this other] (-is-equal this other))
  (to-epoch-second [this offset]
    (chrono-local-date-time-defaults/-to-epoch-second this offset)))

(def-method -with ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L917
  ([this ::local-date-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L515
  ([this ::local-date-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (wip ::-with)))

(def-method -plus ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1146
  ([this ::local-date-time
    amount-to-add ::temporal-amount/temporal-amount]
   (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1182
  ([this ::local-date-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-plus)))

(def-method -minus ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1365
  ([this ::local-date-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1394
  ([this ::local-date-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1682
(def-method -until ::j/long
  [this ::local-date-time
   end-exclusive ::temporal/temporal
   unit  ::temporal-unit/temporal-unit]
  (wip ::-until))

(extend-type LocalDateTime
  temporal/ITemporal
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
(def-method -is-supported ::j/boolean
  [this ::local-date-time
   is-supported--overloaded-param ::temporal-field/temporal-field]
  (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L648
(def-method -range ::value-range/value-range
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L685
(def-method -get ::j/int
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L717
(def-method -get-long ::j/long
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1595
(def-method -query ::j/wip
  [this ::local-date-time
   query ::temporal-query/temporal-query]
  (wip ::-query))

(extend-type LocalDateTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L1628
(def-method -adjust-into ::temporal/temporal
  [this ::local-date-time
   temporal ::temporal/temporal]
  (wip ::-adjust-into))

(extend-type LocalDateTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

(def-constructor now ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L179
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L195
  ([now--overloaded-param (s/or :zone-id ::zone-id/zone-id
                                :clock ::clock/clock)]
   (wip ::now)))

(def-constructor of ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L373
  ([date ::LocalDate/local-date
    time ::LocalTime/local-time]
   (impl/of date time))

    ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L235
  ([year ::j/year
    month (s/or :number ::j/month-of-yearh
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (impl/of (local-date/of year month day-of-month)
            (local-time/of hour minute)))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L260
  ([year ::j/year
    month (s/or :number ::j/month-of-yearh
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (impl/of (local-date/of year month day-of-month)
            (local-time/of hour minute second)))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L285
  ([year ::j/year
    month (s/or :number ::j/month-of-yearh
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (impl/of (local-date/of year month day-of-month)
            (local-time/of hour minute second nano-of-second))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L393
(def-constructor of-instant ::local-date-time
  [instant ::instant/instant
   zone  ::zone-id/zone-id]
  (wip ::of-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L416
(def-constructor of-epoch-second ::local-date-time
  [epoch-second ::j/long
   nano-of-second ::j/int
   offset ::zone-offset/zone-offset]
  (wip ::of-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L447
(def-constructor from ::local-date-time
  [temporal ::temporal-accessor/temporal-accessor]
  (wip ::from))

(def-constructor parse ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L476
  ([text ::j/char-sequence]
   (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L490
  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L144
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L151
(def MAX ::MAX--not-implemented)
