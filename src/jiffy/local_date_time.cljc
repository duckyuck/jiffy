(ns jiffy.local-date-time
  (:refer-clojure :exclude [range get format second])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.chrono.chrono-local-date-time-defaults :as chrono-local-date-time-defaults]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :as local-date-impl]
            [jiffy.local-date-impl-impl :as local-date-impl-impl]
            [jiffy.local-date-time-impl :refer [#?@(:cljs [LocalDateTime])] :as impl]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.local-time-impl-impl :as local-time-impl-impl]
            [jiffy.month :as month]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
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
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math]
            [jiffy.zoned-date-time-impl :as zoned-date-time-impl]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.period :as period]
            [jiffy.asserts :as asserts]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.clock :as clock-impl]
            [jiffy.offset-date-time-impl :as offset-date-time-impl])
  #?(:clj (:import [jiffy.local_date_time_impl LocalDateTime])))

(s/def ::local-date-time ::impl/local-date-time)

(def-method get-year ::j/int
  [this ::local-date-time]
  (-> this :date local-date/get-year))

(def-method get-month-value ::j/int
  [this ::local-date-time]
  (-> this :date local-date/get-month-value))

(def-method get-month ::month/month
  [this ::local-date-time]
  (-> this :date local-date/get-month))

(def-method get-day-of-month ::j/int
  [this ::local-date-time]
  (-> this :date local-date/get-day-of-month))

(def-method get-day-of-year ::j/int
  [this ::local-date-time]
  (-> this :date local-date/get-day-of-year))

(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::local-date-time]
  (-> this :date local-date/get-day-of-week))

(def-method get-hour ::j/int
  [this ::local-date-time]
  (-> this :time local-time/get-hour))

(def-method get-minute ::j/int
  [this ::local-date-time]
  (-> this :time local-time/get-minute))

(def-method get-second ::j/int
  [this ::local-date-time]
  (-> this :time local-time/get-second))

(def-method get-nano ::j/int
  [this ::local-date-time]
  (-> this :time local-time/get-nano))

(defn --with [{:keys [date time] :as this} new-date new-time]
  (if (and (= date new-date)
           (= time new-time))
    this
    (impl/create new-date new-time)))

(def-method with-year ::local-date-time
  [this ::local-date-time
   year ::j/int]
  (--with this (local-date/with-year (:date this) year) (:time this)))

(def-method with-month ::local-date-time
  [this ::local-date-time
   month ::j/int]
  (--with this (local-date/with-month (:date this) month) (:time this)))

(def-method with-day-of-month ::local-date-time
  [this ::local-date-time
   day-of-month ::j/int]
  (--with this (local-date/with-day-of-month (:date this) day-of-month) (:time this)))

(def-method with-day-of-year ::local-date-time
  [this ::local-date-time
   day-of-year ::j/int]
  (--with this (local-date/with-day-of-year (:date this) day-of-year) (:time this)))

(def-method with-hour ::local-date-time
  [this ::local-date-time
   hour ::j/int]
  (--with this (:date this) (local-time/with-hour (:time this) hour)))

(def-method with-minute ::local-date-time
  [this ::local-date-time
   minute ::j/int]
  (--with this (:date this) (local-time/with-minute (:time this) minute)))

(def-method with-second ::local-date-time
  [this ::local-date-time
   second ::j/int]
  (--with this (:date this) (local-time/with-second (:time this) second)))

(def-method with-nano ::local-date-time
  [this ::local-date-time
   nano-of-second ::j/int]
  (--with this (:date this) (local-time/with-nano (:time this) nano-of-second)))

(def-method truncated-to ::local-date-time
  [this ::local-date-time
   unit ::temporal-unit/temporal-unit]
  (--with this (:date this) (local-time/truncated-to (:time this) unit)))

(def-method plus-years ::local-date-time
  [this ::local-date-time
   years ::j/long]
  (--with this (local-date/plus-years (:date this) years) (:time this)))

(def-method plus-months ::local-date-time
  [this ::local-date-time
   months ::j/long]
  (--with this (local-date/plus-months (:date this) months) (:time this)))

(def-method plus-weeks ::local-date-time
  [this ::local-date-time
   weeks ::j/long]
  (--with this (local-date/plus-weeks (:date this) weeks) (:time this)))

(def-method plus-days ::local-date-time
  [this ::local-date-time
   days ::j/long]
  (--with this (local-date/plus-days (:date this) days) (:time this)))

(defn- --plus-with-overflow
  [{:keys [time] :as this} new-date hours minutes seconds nanos sign]
  (if (zero? (bit-or hours minutes seconds nanos))
    (--with this new-date time)
    (let [tot-days (-> (long (/ nanos local-time-impl/NANOS_PER_DAY))
                       (math/add-exact (long (/ seconds local-time-impl/SECONDS_PER_DAY)))
                       (math/add-exact (long (/ minutes local-time-impl/MINUTES_PER_DAY)))
                       (math/add-exact (long (/ hours local-time-impl/HOURS_PER_DAY)))
                       (math/multiply-exact sign))
          tot-nanos (-> (rem nanos local-time-impl/NANOS_PER_DAY)
                        (math/add-exact (math/multiply-exact (rem seconds local-time-impl/SECONDS_PER_DAY) local-time-impl/NANOS_PER_SECOND))
                        (math/add-exact (math/multiply-exact (rem minutes local-time-impl/MINUTES_PER_DAY) local-time-impl/NANOS_PER_MINUTE))
                        (math/add-exact (math/multiply-exact (rem hours local-time-impl/HOURS_PER_DAY) local-time-impl/NANOS_PER_HOUR)))
          cur-nod (local-time/to-nano-of-day time)
          tot-nanos (math/add-exact (math/multiply-exact tot-nanos sign) cur-nod)
          tot-days (math/add-exact tot-days (math/floor-div tot-nanos local-time-impl/NANOS_PER_DAY))
          new-nod (math/floor-mod tot-nanos local-time-impl/NANOS_PER_DAY)
          new-time (if (= new-nod cur-nod)
                     time
                     (local-time-impl/of-nano-of-day new-nod))]
      (--with this (local-date/plus-days new-date tot-days) new-time))))

(def-method plus-hours ::local-date-time
  [this ::local-date-time
   hours ::j/long]
  (--plus-with-overflow this (:date this) hours 0 0 0 1))

(def-method plus-minutes ::local-date-time
  [this ::local-date-time
   minutes ::j/long]
  (--plus-with-overflow this (:date this) 0 minutes 0 0 1))

(def-method plus-seconds ::local-date-time
  [this ::local-date-time
   seconds ::j/long]
  (--plus-with-overflow this (:date this) 0 0 seconds 0 1))

(def-method plus-nanos ::local-date-time
  [this ::local-date-time
   nanos ::j/long]
  (--plus-with-overflow this (:date this) 0 0 0 nanos 1))

(def-method minus-years ::local-date-time
  [this ::local-date-time
   years ::j/long]
  (if (= years math/long-min-value)
    (-> this
        (plus-years math/long-max-value)
        (plus-years 1))
    (plus-years this (- years))))

(def-method minus-months ::local-date-time
  [this ::local-date-time
   months ::j/long]
  (if (= months math/long-min-value)
    (-> this
        (plus-months math/long-max-value)
        (plus-months 1))
    (plus-months this (- months))))

(def-method minus-weeks ::local-date-time
  [this ::local-date-time
   weeks ::j/long]
  (if (= weeks math/long-min-value)
    (-> this
        (plus-weeks math/long-max-value)
        (plus-weeks 1))
    (plus-weeks this (- weeks))))

(def-method minus-days ::local-date-time
  [this ::local-date-time
   days ::j/long]
  (if (= days math/long-min-value)
    (-> this
        (plus-days math/long-max-value)
        (plus-days 1))
    (plus-days this (- days))))

(def-method minus-hours ::local-date-time
  [this ::local-date-time
   hours ::j/long]
  (if (= hours math/long-min-value)
    (-> this
        (plus-hours math/long-max-value)
        (plus-hours 1))
    (plus-hours this (- hours))))

(def-method minus-minutes ::local-date-time
  [this ::local-date-time
   minutes ::j/long]
  (if (= minutes math/long-min-value)
    (-> this
        (plus-minutes math/long-max-value)
        (plus-minutes 1))
    (plus-minutes this (- minutes))))

(def-method minus-seconds ::local-date-time
  [this ::local-date-time
   seconds ::j/long]
  (if (= seconds math/long-min-value)
    (-> this
        (plus-seconds math/long-max-value)
        (plus-seconds 1))
    (plus-seconds this (- seconds))))

(def-method minus-nanos ::local-date-time
  [this ::local-date-time
   nanos ::j/long]
  (if (= nanos math/long-min-value)
    (-> this
        (plus-nanos math/long-max-value)
        (plus-nanos 1))
    (plus-nanos this (- nanos))))

(def-method at-offset ::offset-date-time/offset-date-time
  [this ::local-date-time
   offset ::zone-offset/zone-offset]
  (offset-date-time-impl/of this offset))

(extend-type LocalDateTime
  local-date-time/ILocalDateTime
  (get-year [this] (get-year this))
  (get-month-value [this] (get-month-value this))
  (get-month [this] (get-month this))
  (get-day-of-month [this] (get-day-of-month this))
  (get-day-of-year [this] (get-day-of-year this))
  (get-day-of-week [this] (get-day-of-week this))
  (get-hour [this] (get-hour this))
  (get-minute [this] (get-minute this))
  (get-second [this] (get-second this))
  (get-nano [this] (get-nano this))
  (with-year [this year] (with-year this year))
  (with-month [this month] (with-month this month))
  (with-day-of-month [this day-of-month] (with-day-of-month this day-of-month))
  (with-day-of-year [this day-of-year] (with-day-of-year this day-of-year))
  (with-hour [this hour] (with-hour this hour))
  (with-minute [this minute] (with-minute this minute))
  (with-second [this second] (with-second this second))
  (with-nano [this nano-of-second] (with-nano this nano-of-second))
  (truncated-to [this unit] (truncated-to this unit))
  (plus-years [this years] (plus-years this years))
  (plus-months [this months] (plus-months this months))
  (plus-weeks [this weeks] (plus-weeks this weeks))
  (plus-days [this days] (plus-days this days))
  (plus-hours [this hours] (plus-hours this hours))
  (plus-minutes [this minutes] (plus-minutes this minutes))
  (plus-seconds [this seconds] (plus-seconds this seconds))
  (plus-nanos [this nanos] (plus-nanos this nanos))
  (minus-years [this years] (minus-years this years))
  (minus-months [this months] (minus-months this months))
  (minus-weeks [this weeks] (minus-weeks this weeks))
  (minus-days [this days] (minus-days this days))
  (minus-hours [this hours] (minus-hours this hours))
  (minus-minutes [this minutes] (minus-minutes this minutes))
  (minus-seconds [this seconds] (minus-seconds this seconds))
  (minus-nanos [this nanos] (minus-nanos this nanos))
  (at-offset [this offset] (at-offset this offset)))

(def-method compare-to ::j/int
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
  (compare-to [this compare-to--overloaded-param] (compare-to this compare-to--overloaded-param)))

(def-method to-local-date ::local-date/local-date
  [this ::local-date-time]
  (:date this))

(def-method to-local-time ::local-time/local-time
  [this ::local-date-time]
  (:time this))

(def-method format string?
  [this ::local-date-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

(def-method at-zone ::zoned-date-time/zoned-date-time
  [this ::local-date-time
   zone ::zone-id/zone-id]
  (zoned-date-time-impl/of this zone))

(defn --compare-to0 [this other]
  (let [cmp (time-comparable/compare-to (:date this) (to-local-date other))]
    (if (zero? cmp)
      (time-comparable/compare-to (:time this) (to-local-time other))
      cmp)))

(def-method is-after ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (if (satisfies? local-date-time/ILocalDateTime other)
    (pos? (--compare-to0 this other))
    (chrono-local-date-time-defaults/-is-after this other)))

(def-method is-before ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (if (satisfies? local-date-time/ILocalDateTime other)
    (neg? (--compare-to0 this other))
    (chrono-local-date-time-defaults/-is-before this other)))

(def-method is-equal ::j/boolean
  [this ::local-date-time
   other ::chrono-local-date-time/chrono-local-date-time]
  (if (satisfies? local-date-time/ILocalDateTime other)
    (zero? (--compare-to0 this other))
    (chrono-local-date-time-defaults/-is-equal this other)))

(def-method to-epoch-second ::j/long
  [this ::local-date-time
   offset ::zone-offset/zone-offset]
  (chrono-local-date-time-defaults/-to-epoch-second this offset))

(def-method to-instant ::instant/instant
  [this ::local-date-time
   offset ::zone-offset/zone-offset]
  (chrono-local-date-time-defaults/-to-instant this offset))

(extend-type LocalDateTime
  chrono-local-date-time/IChronoLocalDateTime
  (to-local-date [this] (to-local-date this))
  (to-local-time [this] (to-local-time this))
  (format [this formatter] (format this formatter))
  (at-zone [this zone] (at-zone this zone))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other))
  (is-equal [this other] (is-equal this other))

  ;; defaults from ChronoLocalDateTime
  (to-epoch-second [this offset] (to-epoch-second this offset))
  (to-instant [this offset] (to-instant this offset)))

(def-method with ::local-date-time
  ([this ::local-date-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (cond
     (satisfies? local-date/ILocalDate adjuster)
     (--with this adjuster (:time this))

     (satisfies? local-time/ILocalTime adjuster)
     (--with this (:date this) adjuster)

     (satisfies? local-date-time/ILocalDateTime adjuster)
     adjuster

     :else
     (temporal-adjuster/adjust-into adjuster this)))

  ([this ::local-date-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (satisfies? chrono-field/IChronoField field)
     (temporal-field/adjust-into field this new-value)
     (if (chrono-field/-is-time-based field)
       (--with this (:date this) (temporal/with (:time this) field new-value))
       (--with this (temporal/with (:date this) field new-value) (:time this))))))

(def-method plus ::local-date-time
  ([{:keys [date time] :as this} ::local-date-time
    amount-to-add ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-add)
     (--with this (temporal/plus date amount-to-add) time)
     (do
       (asserts/require-non-nil amount-to-add "amount-to-add")
       (temporal-amount/add-to amount-to-add this))))

  ([this ::local-date-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if-not (chrono-unit/chrono-unit? unit)
     (temporal-unit/add-to unit this amount-to-add)
     (condp = unit
       chrono-unit/NANOS (plus-nanos this amount-to-add)
       chrono-unit/MICROS (-> this
                              (plus-days (long (/ amount-to-add local-time-impl/MICROS_PER_DAY)))
                              (plus-nanos (* (rem amount-to-add local-time-impl/MICROS_PER_DAY) 1000)))
       chrono-unit/MILLIS (-> this
                              (plus-days (long (/ amount-to-add local-time-impl/MILLIS_PER_DAY)))
                              (plus-nanos (* (rem amount-to-add local-time-impl/MILLIS_PER_DAY)
                                             1000000)))
       chrono-unit/SECONDS (plus-seconds this amount-to-add)
       chrono-unit/MINUTES (plus-minutes this amount-to-add)
       chrono-unit/HOURS (plus-hours this amount-to-add)
       chrono-unit/HALF_DAYS (-> this
                                 (plus-days (long (/ amount-to-add 256)))
                                 (plus-hours (* (rem amount-to-add 256) 12)))
       (--with this (temporal/plus (:date this) amount-to-add unit) (:time this))))))

(def-method minus ::local-date-time
  ([{:keys [date time] :as this} ::local-date-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-subtract)
     (--with this (temporal/minus date amount-to-subtract) time)
     (do
       (asserts/require-non-nil amount-to-subtract "amount-to-subtract")
       (temporal-amount/subtract-from amount-to-subtract this))))

  ([this ::local-date-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(declare from)

(def-method until ::j/long
  [{:keys [date time] :as this} ::local-date-time
   end-exclusive ::temporal/temporal
   unit  ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)
        end-date (to-local-date end)
        end-time (to-local-time end)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (if (temporal-unit/is-time-based unit)
        (let [amount (local-date/days-until date (to-local-date end))]
          (if (zero? amount)
            (temporal/until time end-time unit)
            (let [time-part (math/subtract-exact (local-time/to-nano-of-day end-time) (local-time/to-nano-of-day time))
                  [amount time-part] (if (pos? amount)
                                       [(dec amount) (+ time-part local-time-impl/NANOS_PER_DAY)]
                                       [(inc amount) (- time-part local-time-impl/NANOS_PER_DAY)])
                  [amount time-part] (condp = unit
                                       chrono-unit/NANOS [(math/multiply-exact amount local-time-impl/NANOS_PER_DAY) time-part]
                                       chrono-unit/MICROS [(math/multiply-exact amount local-time-impl/MICROS_PER_DAY) (long (/ time-part 1000))]
                                       chrono-unit/MILLIS [(math/multiply-exact amount local-time-impl/MILLIS_PER_DAY) (long (/ time-part 1000000))]
                                       chrono-unit/SECONDS [(math/multiply-exact amount local-time-impl/SECONDS_PER_DAY) (long (/ time-part local-time-impl/NANOS_PER_SECOND))]
                                       chrono-unit/MINUTES [(math/multiply-exact amount local-time-impl/MINUTES_PER_DAY) (long (/ time-part local-time-impl/NANOS_PER_MINUTE))]
                                       chrono-unit/HOURS [(math/multiply-exact amount local-time-impl/HOURS_PER_DAY) (long (/ time-part local-time-impl/NANOS_PER_HOUR))]
                                       chrono-unit/HALF_DAYS [(math/multiply-exact amount 2) (long (/ time-part (* local-time-impl/NANOS_PER_HOUR 12)))]
                                       [amount time-part])]
              (math/add-exact amount time-part))))
        (let [end-date (cond-> end-date
                         (and (chrono-local-date/is-after end-date date)
                              (local-time/is-before end-time time))
                         (local-date/minus-days 1)

                         (and (chrono-local-date/is-before end-date date)
                              (local-time/is-after end-time time))
                         (local-date/plus-days 1))]
          (temporal/until date end-date unit))))))

(extend-type LocalDateTime
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this new-date new-time] (with this new-date new-time)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

(def-method is-supported ::j/boolean
  [this ::local-date-time
   field-or-unit (s/or ::temporal-field/temporal-field
                       ::temporal-unit/temporal-unit)]
  (if (satisfies? temporal-unit/ITemporalUnit field-or-unit)
    (chrono-local-date-time-defaults/-is-supported this field-or-unit)
    (if (chrono-field/chrono-field? field-or-unit)
      (or (chrono-field/-is-date-based field-or-unit)
          (chrono-field/-is-time-based field-or-unit))
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))))

(def-method range ::value-range/value-range
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/range-refined-by field this)
    (if (chrono-field/-is-time-based field)
      (temporal-accessor/range (:time this) field)
      (temporal-accessor/range (:date this) field))))

(def-method get ::j/int
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-accessor-defaults/-get this field)
    (if (chrono-field/-is-time-based field)
      (temporal-accessor/get (:time this) field)
      (temporal-accessor/get (:date this) field))))

(def-method get-long ::j/long
  [this ::local-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (if (chrono-field/-is-time-based field)
      (temporal-accessor/get-long (:time this) field)
      (temporal-accessor/get-long (:date this) field))))

(def-method query ::j/wip
  [this ::local-date-time
   query ::temporal-query/temporal-query]
  (if (= query (temporal-queries/local-date))
    (:date this)
    (chrono-local-date-time-defaults/-query this query)))

(extend-type LocalDateTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (is-supported this is-supported--overloaded-param))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::local-date-time
   temporal ::temporal/temporal]
  (chrono-local-date-time-defaults/-adjust-into this temporal))

(extend-type LocalDateTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor now ::local-date-time
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (if (satisfies? zone-id/IZoneId clock-or-zone-id)
     (now (clock-impl/system clock-or-zone-id))
     (do
       (asserts/require-non-nil clock-or-zone-id "clock")
       (let [now (clock/instant clock-or-zone-id)]
         (impl/of-epoch-second (instant/get-epoch-second now)
                               (instant/get-nano now)
                               (-> clock-or-zone-id
                                   clock/get-zone
                                   zone-id/get-rules
                                   (zone-rules/get-offset now))))))))

(def-constructor of ::local-date-time
  ([date ::local-date/local-date
    time ::local-time/local-time]
   (impl/of date time))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (impl/of year month day-of-month hour minute))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (impl/of year month day-of-month hour minute second))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (impl/of year month day-of-month hour minute second nano-of-second)))

(def-constructor of-epoch-second ::local-date-time
  [epoch-second ::j/long
   nano-of-second ::j/int
   offset ::zone-offset/zone-offset]
  (impl/of-epoch-second epoch-second nano-of-second offset))

(def-constructor of-instant ::local-date-time
  [instant ::instant/instant
   zone  ::zone-id/zone-id]
  (asserts/require-non-nil instant "instant")
  (asserts/require-non-nil zone "zone")
  (let [rules (zone-id/get-rules zone)
        offset (zone-rules/get-offset rules instant)]
    (of-epoch-second (instant/get-epoch-second instant)
                     (instant/get-nano instant)
                     offset)))

(def-constructor from ::local-date-time
  [temporal ::temporal-accessor/temporal-accessor]
  (cond
    (satisfies? local-date-time/ILocalDateTime temporal)
    temporal

    (satisfies? zoned-date-time/IZonedDateTime temporal)
    (chrono-zoned-date-time/to-local-date-time temporal)

    (satisfies? offset-date-time/IOffsetDateTime temporal)
    (offset-date-time/to-local-date-time temporal)

    :else
    (try*
     (impl/create (local-date-impl-impl/from temporal)
                  (local-time-impl-impl/from temporal))
     (catch DateTimeException e
       (throw (ex DateTimeException
                  (str "Unable to obtain LocalDateTime from TemporalAccessor: "
                       temporal " of type " (type temporal))
                  {:temporal temporal}
                  e))))))

(def-constructor parse ::local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L476
  ([text ::j/char-sequence]
   (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L490
  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))

(def MIN (impl/of local-date-impl/MIN local-time-impl/MIN))
(def MAX (impl/of local-date-impl/MAX local-time-impl/MAX))
