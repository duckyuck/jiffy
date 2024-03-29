(ns jiffy.zoned-date-time
  (:refer-clojure :exclude [range get format])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.asserts :as asserts]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [DateTimeParseException UnsupportedTemporalTypeException DateTimeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.date-time-formatter :as date-time-formatter-impl]
            [jiffy.clock :as clock-impl]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.local-date :as local-date-impl]
            [jiffy.local-date-time :as local-date-time-impl]
            [jiffy.local-time :as local-time-impl]
            [jiffy.chrono.chrono-zoned-date-time-defaults :as chrono-zoned-date-time-defaults]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as offset-date-time-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.period :as period]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.zoned-date-time-impl :refer [#?@(:cljs [ZonedDateTime])] :as impl]
            [jiffy.zone-id :as zone-id-impl]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.string :as string])
  #?(:clj (:import [jiffy.zoned_date_time_impl ZonedDateTime])))

(s/def ::zoned-date-time ::impl/zoned-date-time)

(def-method with-fixed-offset-zone ::zoned-date-time
  [{:keys [zone offset date-time] :as this} ::zoned-date-time]
  (if (= zone offset)
    this
    (impl/->ZonedDateTime date-time offset offset)))

(def-method get-year ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-year))

(def-method get-month-value ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-month-value))

(def-method get-month ::month/month
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-month))

(def-method get-day-of-month ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-day-of-month))

(def-method get-day-of-year ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-day-of-year))

(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-day-of-week))

(def-method get-hour ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-hour))

(def-method get-minute ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-minute))

(def-method get-second ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-second))

(def-method get-nano ::j/int
  [this ::zoned-date-time]
  (-> this :date-time local-date-time/get-nano))

(declare of-local)
(declare of-instant)

(defn- resolve-local [this date-time]
  (of-local date-time (:zone this) (:offset this)))

(defn- resolve-instant [this date-time]
  (of-instant date-time (:offset this) (:zone this)))

(def-method with-year ::zoned-date-time
  [this ::zoned-date-time
   year ::j/int]
  (resolve-local this (local-date-time/with-year (:date-time this) year)))

(def-method with-month ::zoned-date-time
  [this ::zoned-date-time
   month ::j/int]
  (resolve-local this (local-date-time/with-month (:date-time this) month)))

(def-method with-day-of-month ::zoned-date-time
  [this ::zoned-date-time
   day-of-month ::j/int]
  (resolve-local this (local-date-time/with-day-of-month (:date-time this) day-of-month)))

(def-method with-day-of-year ::zoned-date-time
  [this ::zoned-date-time
   day-of-year ::j/int]
  (resolve-local this (local-date-time/with-day-of-year (:date-time this) day-of-year)))

(def-method with-hour ::zoned-date-time
  [this ::zoned-date-time
   hour ::j/int]
  (resolve-local this (local-date-time/with-hour (:date-time this) hour)))

(def-method with-minute ::zoned-date-time
  [this ::zoned-date-time
   minute ::j/int]
  (resolve-local this (local-date-time/with-minute (:date-time this) minute)))

(def-method with-second ::zoned-date-time
  [this ::zoned-date-time
   second ::j/int]
  (resolve-local this (local-date-time/with-second (:date-time this) second)))

(def-method with-nano ::zoned-date-time
  [this ::zoned-date-time
   nano-of-second ::j/int]
  (resolve-local this (local-date-time/with-nano (:date-time this) nano-of-second)))

(def-method truncated-to ::zoned-date-time
  [this ::zoned-date-time
   unit ::temporal-unit/temporal-unit]
  (resolve-local this (local-date-time/truncated-to (:date-time this) unit)))

(def-method plus-years ::zoned-date-time
  [this ::zoned-date-time
   years ::j/long]
  (resolve-local this (local-date-time/plus-years (:date-time this) years)))

(def-method plus-months ::zoned-date-time
  [this ::zoned-date-time
   months ::j/long]
  (resolve-local this (local-date-time/plus-months (:date-time this) months)))

(def-method plus-weeks ::zoned-date-time
  [this ::zoned-date-time
   weeks ::j/long]
  (resolve-local this (local-date-time/plus-weeks (:date-time this) weeks)))

(def-method plus-days ::zoned-date-time
  [this ::zoned-date-time
   days ::j/long]
  (resolve-local this (local-date-time/plus-days (:date-time this) days)))

(def-method plus-hours ::zoned-date-time
  [this ::zoned-date-time
   hours ::j/long]
  (resolve-instant this (local-date-time/plus-hours (:date-time this) hours)))

(def-method plus-minutes ::zoned-date-time
  [this ::zoned-date-time
   minutes ::j/long]
  (resolve-instant this (local-date-time/plus-minutes (:date-time this) minutes)))

(def-method plus-seconds ::zoned-date-time
  [this ::zoned-date-time
   seconds ::j/long]
  (resolve-instant this (local-date-time/plus-seconds (:date-time this) seconds)))

(def-method plus-nanos ::zoned-date-time
  [this ::zoned-date-time
   nanos ::j/long]
  (resolve-instant this (local-date-time/plus-nanos (:date-time this) nanos)))

(def-method minus-years ::zoned-date-time
  [this ::zoned-date-time
   years ::j/long]
  (if (= math/long-min-value years)
    (-> this
        (plus-years math/long-max-value)
        (plus-years 1))
    (plus-years this (- years))))

(def-method minus-months ::zoned-date-time
  [this ::zoned-date-time
   months ::j/long]
  (if (= math/long-min-value months)
    (-> this
        (plus-months math/long-max-value)
        (plus-months 1))
    (plus-months this (- months))))

(def-method minus-weeks ::zoned-date-time
  [this ::zoned-date-time
   weeks ::j/long]
  (if (= math/long-min-value weeks)
    (-> this
        (plus-weeks math/long-max-value)
        (plus-weeks 1))
    (plus-weeks this (- weeks))))

(def-method minus-days ::zoned-date-time
  [this ::zoned-date-time
   days ::j/long]
  (if (= math/long-min-value days)
    (-> this
        (plus-days math/long-max-value)
        (plus-days 1))
    (plus-days this (- days))))

(def-method minus-hours ::zoned-date-time
  [this ::zoned-date-time
   hours ::j/long]
  (if (= math/long-min-value hours)
    (-> this
        (plus-hours math/long-max-value)
        (plus-hours 1))
    (plus-hours this (- hours))))

(def-method minus-minutes ::zoned-date-time
  [this ::zoned-date-time
   minutes ::j/long]
  (if (= math/long-min-value minutes)
    (-> this
        (plus-minutes math/long-max-value)
        (plus-minutes 1))
    (plus-minutes this (- minutes))))

(def-method minus-seconds ::zoned-date-time
  [this ::zoned-date-time
   seconds ::j/long]
  (if (= math/long-min-value seconds)
    (-> this
        (plus-seconds math/long-max-value)
        (plus-seconds 1))
    (plus-seconds this (- seconds))))

(def-method minus-nanos ::zoned-date-time
  [this ::zoned-date-time
   nanos ::j/long]
  (if (= math/long-min-value nanos)
    (-> this
        (plus-nanos math/long-max-value)
        (plus-nanos 1))
    (plus-nanos this (- nanos))))

(def-method to-offset-date-time ::offset-date-time/offset-date-time
  [this ::zoned-date-time]
  (offset-date-time-impl/of (:date-time this) (:offset this)))

(extend-type ZonedDateTime
  zoned-date-time/IZonedDateTime
  (with-fixed-offset-zone [this] (with-fixed-offset-zone this))
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
  (to-offset-date-time [this] (to-offset-date-time this)))


(def-method get-offset ::zone-offset/zone-offset
  [this ::zoned-date-time]
  (:offset this))

(def-method with-earlier-offset-at-overlap ::chrono-zoned-date-time/chrono-zoned-date-time
  [this ::zoned-date-time]
  (let [trans (-> this :zone zone-id/get-rules (zone-rules/get-transition (:date-time this)))]
    (if (and (not (nil? trans)) (zone-offset-transition/is-overlap trans))
      (let [earlier-offset (zone-offset-transition/get-offset-before trans)]
        (if (not= earlier-offset (:offset this))
          (impl/->ZonedDateTime (:date-time this) earlier-offset (:zone this))
          this))
      this)))

(def-method get-zone ::zone-id/zone-id
  [this ::zoned-date-time]
  (:zone this))

(def-method with-later-offset-at-overlap ::zoned-date-time
  [this ::zoned-date-time]
  (if-let [trans (-> this :zone zone-id/get-rules (zone-rules/get-transition (:date-time this)))]
    (let [later-offset (zone-offset-transition/get-offset-after trans)]
      (if (not= later-offset (:offset this))
        (impl/->ZonedDateTime (:date-time this) later-offset (:zone this))
        this))
    this))

(def-method with-zone-same-local ::zoned-date-time
  [this ::zoned-date-time
   zone ::zone-id/zone-id]
  (asserts/require-non-nil zone "zone")
  (if (= (:zone this) zone)
    this
    (of-local (:date-time this) zone (:offset this))))

(def-method with-zone-same-instant ::zoned-date-time
  [this ::zoned-date-time
   zone ::zone-id/zone-id]
  (asserts/require-non-nil zone "zone")
  (if (= (:zone this) zone)
    this
    (impl/create (chrono-local-date-time/to-epoch-second (:date-time this) (:offset this))
                 (local-date-time/get-nano (:date-time this))
                 zone)))

(def-method to-local-date-time ::local-date-time/local-date-time
  [this ::zoned-date-time]
  (:date-time this))

(def-method to-local-date ::chrono-local-date/chrono-local-date
  [this ::zoned-date-time]
  (-> this :date-time chrono-local-date-time/to-local-date))

(def-method to-local-time ::local-time/local-time
  [this ::zoned-date-time]
  (-> this :date-time chrono-local-date-time/to-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L2152
(def-method format string?
  [this ::zoned-date-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::format))

(def-method to-epoch-second ::j/long
  [this ::zoned-date-time]
  (chrono-zoned-date-time-defaults/to-epoch-second this))

(def-method is-before ::j/boolean
  [this ::zoned-date-time
   other ::zoned-date-time]
  (chrono-zoned-date-time-defaults/is-before this other))

(def-method is-after ::j/boolean
  [this ::zoned-date-time
   other ::zoned-date-time]
  (chrono-zoned-date-time-defaults/is-after this other))

(def-method is-equal ::j/boolean
  [this ::zoned-date-time
   other ::zoned-date-time]
  (chrono-zoned-date-time-defaults/is-equal this other))

(def-method get-chronology ::chronology/chronology
  [this ::zoned-date-time]
  (chrono-zoned-date-time-defaults/get-chronology this))

(def-method to-instant ::instant/instant
  [this ::zoned-date-time]
  (chrono-zoned-date-time-defaults/to-instant this))

(extend-type ZonedDateTime
  chrono-zoned-date-time/IChronoZonedDateTime
  (to-epoch-second [this] (to-epoch-second this))
  (get-offset [this] (get-offset this))
  (with-earlier-offset-at-overlap [this] (with-earlier-offset-at-overlap this))
  (get-zone [this] (get-zone this))
  (with-later-offset-at-overlap [this] (with-later-offset-at-overlap this))
  (with-zone-same-local [this zone] (with-zone-same-local this zone))
  (with-zone-same-instant [this zone] (with-zone-same-instant this zone))
  (get-chronology [this] (get-chronology this))
  (to-local-date-time [this] (to-local-date-time this))
  (to-local-date [this] (to-local-date this))
  (to-local-time [this] (to-local-time this))
  (to-instant [this] (to-instant this))
  (format [this formatter] (format this formatter))
  (is-before [this other] (is-before this other))
  (is-after [this other] (is-after this other))
  (is-equal [this other] (is-equal this other)))

(def-method compare-to ::j/int
  [this ::zoned-date-time
   other ::zoned-date-time]
  (chrono-zoned-date-time-defaults/compare-to this other))

(extend-type ZonedDateTime
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(defn- resolve-offset [this offset]
  (if (and (not= offset (:offset this))
           (-> this :zone zone-id/get-rules (zone-rules/is-valid-offset (:date-time this) offset)))
    (ZonedDateTime. (:date-time this) offset (:zone this))
    this))

(def-method with ::temporal/temporal
  ([{:keys [date-time zone] :as this} ::zoned-date-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (cond
     (satisfies? local-date/ILocalDate adjuster)
     (resolve-local this (local-date-time-impl/of adjuster (chrono-local-date-time/to-local-time date-time)))

     (satisfies? local-time/ILocalTime adjuster)
     (resolve-local this (local-date-time-impl/of (chrono-local-date-time/to-local-date date-time) adjuster))

     (satisfies? local-date-time/ILocalDateTime adjuster)
     (resolve-local this adjuster)

     (satisfies? offset-date-time/IOffsetDateTime adjuster)
     (of-local (offset-date-time/to-local-date-time adjuster) zone (offset-date-time/get-offset adjuster))

     (satisfies? instant/IInstant adjuster)
     (impl/create (instant/get-epoch-second adjuster) (instant/get-nano adjuster) zone)

     (satisfies? zone-offset/IZoneOffset adjuster)
     (resolve-offset this adjuster)

     :default (temporal-adjuster/adjust-into adjuster this)))

  ([this ::zoned-date-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if (chrono-field/chrono-field? field)
     (condp = field
       chrono-field/INSTANT_SECONDS
       (impl/create new-value (get-nano this) (:zone this))

       chrono-field/OFFSET_SECONDS
       (resolve-offset this (-> field
                                (chrono-field/check-valid-int-value new-value)
                                zone-offset-impl/of-total-seconds))

       (resolve-local this (temporal/with (:date-time this) field new-value))))))

(def-method plus ::zoned-date-time
  ([this ::zoned-date-time
    amount-to-add ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-add)
     (resolve-local this (temporal/plus (:date-time this) amount-to-add))
     (do
       (asserts/require-non-nil amount-to-add "amount-to-add")
       (temporal-amount/add-to amount-to-add this))))

  ([this ::zoned-date-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (chrono-unit/chrono-unit? unit)
     (if (temporal-unit/is-date-based unit)
       (resolve-local this (temporal/plus (:date-time this) amount-to-add unit))
       (resolve-instant this (temporal/plus (:date-time this) amount-to-add unit)))
     (temporal-unit/add-to unit this amount-to-add))))

(def-method minus ::zoned-date-time
  ([this ::zoned-date-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-subtract)
     (resolve-local this (temporal/minus (:date-time this) amount-to-subtract))
     (do
       (asserts/require-non-nil amount-to-subtract "amount-to-subtract")
       (temporal-amount/subtract-from amount-to-subtract this))))

  ([this ::zoned-date-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= math/long-min-value amount-to-subtract)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(declare from to-offset-date-time)

(def-method until ::j/long
  [this ::zoned-date-time
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if (chrono-unit/chrono-unit? unit)
      (let [end (with-zone-same-instant end (:zone this))]
        (if (temporal-unit/is-date-based unit)
          (temporal/until (:date-time this) (:date-time end) unit)
          (temporal/until (to-offset-date-time this) (to-offset-date-time end) unit)))
      (temporal-unit/between unit this end))))

(extend-type ZonedDateTime
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this field new-value] (with this field new-value)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

(def-method is-supported ::j/boolean
  [this ::zoned-date-time
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (condp satisfies? field-or-unit
    temporal-field/ITemporalField
    (or (chrono-field/chrono-field? field-or-unit)
        (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    temporal-unit/ITemporalUnit
    (chrono-zoned-date-time-defaults/is-supported this field-or-unit)))

(def-method range ::value-range/value-range
  [this ::zoned-date-time
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (if (or (= field chrono-field/INSTANT_SECONDS)
            (= field chrono-field/OFFSET_SECONDS))
      (temporal-field/range field)
      (temporal-accessor/range (:date-time this) field))
    (temporal-field/range-refined-by field this)))

(def-method get ::j/int
  [this ::zoned-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (chrono-zoned-date-time-defaults/get this field)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (throw (ex UnsupportedTemporalTypeException (str "Invalid field 'InstantSeconds' for get() method, use getLong() instead")))

      (= chrono-field/OFFSET_SECONDS field)
      (-> this get-offset zone-offset/get-total-seconds)

      :else
      (temporal-accessor/get (:date-time this) field))))

(def-method get-long ::j/long
  [this ::zoned-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (chrono-zoned-date-time-defaults/to-epoch-second this)

      (= chrono-field/OFFSET_SECONDS field)
      (-> this get-offset zone-offset/get-total-seconds)

      :else
      (temporal-accessor/get-long (:date-time this) field))))

(def-method query ::temporal-query/result
  [this ::zoned-date-time
   query ::temporal-query/temporal-query]
  (if (= query (temporal-queries/local-date))
    (to-local-date this)
    (chrono-zoned-date-time-defaults/query this query)))

(extend-type ZonedDateTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (is-supported this is-supported--overloaded-param))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-constructor now ::zoned-date-time
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId
     (now (clock-impl/system clock-or-zone-id))

     clock/IClock
     (do
       (asserts/require-non-nil clock-or-zone-id "clock")
       (of-instant (clock/instant clock-or-zone-id) (clock/get-zone clock-or-zone-id))))))

(def-constructor of ::zoned-date-time
  ([local-date-time ::local-date-time/local-date-time
    zone ::zone-id/zone-id]
   (impl/of local-date-time zone))

  ([date ::local-date/local-date
    time ::local-time/local-time
    zone ::zone-id/zone-id]
   (impl/of date time zone))

  ([year ::j/year
    month ::j/month
    day-of-month ::j/day
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    zone ::zone-id/zone-id]
   (impl/of year month day-of-month hour minute second nano-of-second zone)))

(def-constructor of-local ::zoned-date-time
  [local-date-time ::local-date-time/local-date-time
   zone ::zone-id/zone-id
   preferred-offset ::zone-offset/zone-offset]
  (impl/of-local local-date-time zone preferred-offset))

(def-constructor of-instant ::zoned-date-time
  ([instant ::instant/instant
    zone ::zone-id/zone-id]
   (impl/of-instant instant zone))

  ([local-date-time ::local-date-time/local-date-time
    offset ::zone-offset/zone-offset
    zone ::zone-id/zone-id]
   (impl/of-instant local-date-time offset zone)))

(def-constructor of-strict ::zoned-date-time
  [local-date-time ::local-date-time/local-date-time
   offset ::zone-offset/zone-offset
   zone ::zone-id/zone-id]
  (asserts/require-non-nil local-date-time "local-date-time")
  (asserts/require-non-nil offset "offset")
  (asserts/require-non-nil zone "zone")
  (let [rules (zone-id/get-rules zone)]
    (if (zone-rules/is-valid-offset rules local-date-time offset)
      (impl/->ZonedDateTime local-date-time offset zone)
      (let [trans (zone-rules/get-transition rules local-date-time)]
        (if (and trans (zone-offset-transition/is-gap trans))
          (throw (ex DateTimeException (str "LocalDateTime '" local-date-time
                                            "' does not exist in zone '" zone
                                            "' due to a gap in the local time-line, typically caused by daylight savings")))
          (throw (ex DateTimeException (str "ZoneOffset '" (pr-str offset) "' is not valid for LocalDateTime '"
                                            local-date-time "' in zone '" zone "'"))))))))

(def-constructor from ::zoned-date-time
  [temporal ::temporal-accessor/temporal-accessor]
  (if (instance? ZonedDateTime temporal)
    temporal
    (try*
      (let [zone (zone-id-impl/from temporal)]
        (if (temporal-accessor/is-supported temporal chrono-field/INSTANT_SECONDS)
          (impl/create (temporal-accessor/get-long temporal chrono-field/INSTANT_SECONDS)
                       (temporal-accessor/get temporal chrono-field/NANO_OF_SECOND)
                       zone)
          (of (local-date-impl/from temporal) (local-time-impl/from temporal))))
      (catch :default e
        (throw (ex DateTimeException
                   (str "Unable to obtain ZonedDateTime from TemporalAccessor: "
                        temporal " of type " (type temporal))
                   {:temporal temporal}
                   e))))))

(s/def ::string string?)

(def PATTERN
  (delay (re-pattern (str "(" @local-date-time-impl/PATTERN ")"
                          (str #"(([+-][\d:]*)|(Z))(\[(.*)\])?")))))

(def-constructor parse ::zoned-date-time
  ([text ::string]
   (if-let [[date-time _ _ _ _ _ _ _ _ _ _ _ _ offset zulu-offset _ zone]
            (some->> (re-matches @PATTERN text)
                     rest)]
     (let [offset (or offset zulu-offset)
           zone (or zone zulu-offset offset)]
       (if offset
         (of-instant (local-date-time-impl/parse date-time)
                     (zone-id-impl/of offset)
                     (zone-id-impl/of zone))
         (of (local-date-time-impl/parse date-time)
             (zone-id-impl/of zone))))
     (throw (ex DateTimeParseException (str "Failed to parse ZonedDateTime: '" text "'")))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L596
  ;; ([text ::j/char-sequence
  ;;   formatter ::date-time-formatter/date-time-formatter]
  ;;  (wip ::parse))
  )

(def-method to-string string?
  [{:keys [date-time offset zone]} ::zoned-date-time]
  (cond-> (str (string/to-string date-time)
               (when offset
                 (string/to-string offset)))
    (not= offset zone)
    (str "[" (string/to-string zone) "]")))

(extend-type ZonedDateTime
  string/IString
  (to-string [this] (to-string this)))
