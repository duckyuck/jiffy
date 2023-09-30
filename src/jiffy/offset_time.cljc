(ns jiffy.offset-time
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeParseException DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.offset-time-impl :refer [#?@(:cljs [OffsetTime])] :as impl]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math]
            [jiffy.offset-date-time-impl :as offset-date-time-impl]
            [jiffy.asserts :as asserts]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.local-time-impl-impl :as local-time-impl-impl]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.clock :as clock-impl]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.protocols.string :as string])
  #?(:clj (:import [jiffy.offset_time_impl OffsetTime])))

(s/def ::offset-time ::impl/offset-time)

(def-method get-offset ::zone-offset/zone-offset
  [this ::offset-time]
  (:offset this))

(def-method with-offset-same-local ::offset-time
  [this ::offset-time
   offset ::zone-offset/zone-offset]
  (if (and offset (= offset (:offset this)))
    this
    (impl/->OffsetTime (:time this) offset)))

(def-method with-offset-same-instant ::offset-time
  [this ::offset-time
   offset ::zone-offset/zone-offset]
  (if (= (:offset this) offset)
    this
    (impl/of (local-time/plus-seconds
              (:time this)
              (math/subtract-exact (zone-offset/get-total-seconds offset)
                                   (zone-offset/get-total-seconds (:offset this))))
             offset)))

(def-method to-local-time ::local-time/local-time
  [this ::offset-time]
  (:time this))

(def-method get-hour ::j/int
  [this ::offset-time]
  (-> this :time local-time/get-hour))

(def-method get-minute ::j/int
  [this ::offset-time]
  (-> this :time local-time/get-minute))

(def-method get-second ::j/int
  [this ::offset-time]
  (-> this :time local-time/get-second))

(def-method get-nano ::j/int
  [this ::offset-time]
  (-> this :time local-time/get-nano))

(defn --with [this time offset]
  (if (and (= time (:time this))
           (= offset (:offset this)))
    this
    (impl/->OffsetTime time offset)))

(def-method with-hour ::offset-time
  [this ::offset-time
   hour ::j/int]
  (--with this
          (-> this :time (local-time/with-hour hour))
          (:offset this)))

(def-method with-minute ::offset-time
  [this ::offset-time
   minute ::j/int]
  (--with this
          (-> this :time (local-time/with-minute minute))
          (:offset this)))

(def-method with-second ::offset-time
  [this ::offset-time
   second ::j/int]
  (--with this
          (-> this :time (local-time/with-second second))
          (:offset this)))

(def-method with-nano ::offset-time
  [this ::offset-time
   nano-of-second ::j/int]
  (--with this
          (-> this :time (local-time/with-nano nano-of-second))
          (:offset this)))

(def-method truncated-to ::offset-time
  [this ::offset-time
   unit ::temporal-unit/temporal-unit]
  (--with this
          (-> this :time (local-time/truncated-to unit))
          (:offset this)))

(def-method plus-hours ::offset-time
  [this ::offset-time
   hours ::j/long]
  (--with this
          (-> this :time (local-time/plus-hours hours))
          (:offset this)))

(def-method plus-minutes ::offset-time
  [this ::offset-time
   minutes ::j/long]
  (--with this
          (-> this :time (local-time/plus-minutes minutes))
          (:offset this)))

(def-method plus-seconds ::offset-time
  [this ::offset-time
   seconds ::j/long]
  (--with this
          (-> this :time (local-time/plus-seconds seconds))
          (:offset this)))

(def-method plus-nanos ::offset-time
  [this ::offset-time
   nanos ::j/long]
  (--with this
          (-> this :time (local-time/plus-nanos nanos))
          (:offset this)))

(def-method minus-hours ::offset-time
  [this ::offset-time
   hours ::j/long]
  (--with this
          (-> this :time (local-time/minus-hours hours))
          (:offset this)))

(def-method minus-minutes ::offset-time
  [this ::offset-time
   minutes ::j/long]
  (--with this
          (-> this :time (local-time/minus-minutes minutes))
          (:offset this)))

(def-method minus-seconds ::offset-time
  [this ::offset-time
   seconds ::j/long]
  (--with this
          (-> this :time (local-time/minus-seconds seconds))
          (:offset this)))

(def-method minus-nanos ::offset-time
  [this ::offset-time
   nanos ::j/long]
  (--with this
          (-> this :time (local-time/minus-nanos nanos))
          (:offset this)))

(def-method format string?
  [this ::offset-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

(def-method at-date ::offset-date-time/offset-date-time
  [this ::offset-time
   date ::local-date/local-date]
  (offset-date-time-impl/of date (:time this) (:offset this)))

(def-method to-epoch-second ::j/long
  [this ::offset-time
   date ::local-date/local-date]
  (asserts/require-non-nil date "date")
  (-> date
      chrono-local-date/to-epoch-day
      (math/multiply-exact 86400)
      (math/add-exact (local-time/to-second-of-day (:time this)))
      (math/subtract-exact (zone-offset/get-total-seconds (:offset this)))))

(defn to-epoch-nano [this]
  (-> (local-time/to-nano-of-day (:time this))
      (math/subtract-exact (math/multiply-exact (zone-offset/get-total-seconds (:offset this))
                                                local-time-impl/NANOS_PER_SECOND))))

(def-method is-after ::j/boolean
  [this ::offset-time
   other ::offset-time]
  (> (to-epoch-nano this) (to-epoch-nano other)))

(def-method is-before ::j/boolean
  [this ::offset-time
   other ::offset-time]
  (< (to-epoch-nano this) (to-epoch-nano other)))

(def-method is-equal ::j/boolean
  [this ::offset-time
   other ::offset-time]
  (= (to-epoch-nano this) (to-epoch-nano other)))

(extend-type OffsetTime
  offset-time/IOffsetTime
  (get-offset [this] (get-offset this))
  (with-offset-same-local [this offset] (with-offset-same-local this offset))
  (with-offset-same-instant [this offset] (with-offset-same-instant this offset))
  (to-local-time [this] (to-local-time this))
  (get-hour [this] (get-hour this))
  (get-minute [this] (get-minute this))
  (get-second [this] (get-second this))
  (get-nano [this] (get-nano this))
  (with-hour [this hour] (with-hour this hour))
  (with-minute [this minute] (with-minute this minute))
  (with-second [this second] (with-second this second))
  (with-nano [this nano-of-second] (with-nano this nano-of-second))
  (truncated-to [this unit] (truncated-to this unit))
  (plus-hours [this hours] (plus-hours this hours))
  (plus-minutes [this minutes] (plus-minutes this minutes))
  (plus-seconds [this seconds] (plus-seconds this seconds))
  (plus-nanos [this nanos] (plus-nanos this nanos))
  (minus-hours [this hours] (minus-hours this hours))
  (minus-minutes [this minutes] (minus-minutes this minutes))
  (minus-seconds [this seconds] (minus-seconds this seconds))
  (minus-nanos [this nanos] (minus-nanos this nanos))
  (format [this formatter] (format this formatter))
  (at-date [this date] (at-date this date))
  (to-epoch-second [this date] (to-epoch-second this date))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other))
  (is-equal [this other] (is-equal this other)))

(def-method compare-to ::j/int
  [this ::offset-time
   other ::offset-time]
  (if (= (:offset this) (:offset other))
    (time-comparable/compare-to (:time this) (:time other))
    (let [cmp (math/compare (to-epoch-nano this) (to-epoch-nano other))]
      (if (zero? cmp)
        (time-comparable/compare-to (:time this) (:time other))
        cmp))))

(extend-type OffsetTime
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(def-method with ::offset-time
  ([this ::offset-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (condp satisfies? adjuster
     local-time/ILocalTime
     (--with this adjuster (:offset this))

     zone-offset/IZoneOffset
     (--with this (:time this) adjuster)

     offset-time/IOffsetTime
     adjuster

     (temporal-adjuster/adjust-into adjuster this)))

  ([this ::offset-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (chrono-field/chrono-field? field)
     (temporal-field/adjust-into field this new-value)
     (if (= field chrono-field/OFFSET_SECONDS)
       (--with this
               (:time this)
               (zone-offset-impl/of-total-seconds (chrono-field/check-valid-int-value field new-value)))
       (--with this
               (temporal/with (:time this) field new-value)
               (:offset this))))))

(def-method plus ::offset-time
  ([this ::offset-time
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ([this ::offset-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (chrono-unit/chrono-unit? unit)
     (--with this
             (temporal/plus (:time this) amount-to-add unit)
             (:offset this))
     (temporal-unit/add-to unit this amount-to-add))))

(def-method minus ::offset-time
  ([this ::offset-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ([this ::offset-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-max-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(declare from)
(def-method until ::j/long
  [this ::offset-time
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (let [nanos-until (math/subtract-exact (to-epoch-nano end) (to-epoch-nano this))]
        (condp = unit
          chrono-unit/NANOS nanos-until
          chrono-unit/MICROS (long (/ nanos-until 1000))
          chrono-unit/MILLIS (long (/ nanos-until 1000000))
          chrono-unit/SECONDS (long (/ nanos-until local-time-impl/NANOS_PER_SECOND))
          chrono-unit/MINUTES (long (/ nanos-until local-time-impl/NANOS_PER_MINUTE))
          chrono-unit/HOURS (long (/ nanos-until local-time-impl/NANOS_PER_HOUR))
          chrono-unit/HALF_DAYS (long (/ nanos-until (* 12 local-time-impl/NANOS_PER_HOUR)))
          (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                     {:this this :unit unit :end-exclusive end-exclusive})))))))

(extend-type OffsetTime
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this time offset] (with this time offset)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

(def-method is-supported ::j/boolean
  [this ::offset-time
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (condp satisfies? field-or-unit
    temporal-field/ITemporalField
    (if (chrono-field/chrono-field? field-or-unit)
      (or (temporal-field/is-time-based field-or-unit)
          (= field-or-unit chrono-field/OFFSET_SECONDS))
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    temporal-unit/ITemporalUnit
    (if (chrono-unit/chrono-unit? field-or-unit)
      (temporal-unit/is-time-based field-or-unit)
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))))

(def-method range ::value-range/value-range
  [this ::offset-time
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (if (= field chrono-field/OFFSET_SECONDS)
      (temporal-field/range field)
      (temporal-accessor/range (:time this) field))
    (temporal-field/range-refined-by field this)))

(def-method get ::j/int
  [this ::offset-time
   field ::temporal-field/temporal-field]
  (temporal-accessor-defaults/-get this field))

(def-method get-long ::j/long
  [this ::offset-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (if (= field chrono-field/OFFSET_SECONDS)
      (-> this get-offset zone-offset/get-total-seconds)
      (temporal-accessor/get-long (:time this) field))))

(def-method query ::temporal-query/result
  [this ::offset-time
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/offset)
       (temporal-queries/zone)} query)
    (:offset this)

    (#{(temporal-queries/zone-id)
       (temporal-queries/chronology)
       (temporal-queries/local-date)} query)
    nil

    (= query (temporal-queries/local-time))
    (:time this)

    (= query (temporal-queries/precision))
    chrono-unit/NANOS

    :else
    (temporal-query/query-from query this)))

(extend-type OffsetTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::offset-time
   temporal ::temporal/temporal]
  (-> temporal
      (temporal/with chrono-field/NANO_OF_DAY (-> this :time local-time/to-nano-of-day))
      (temporal/with chrono-field/OFFSET_SECONDS (-> this :offset zone-offset/get-total-seconds))))

(extend-type OffsetTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor of ::offset-time
  ([time ::local-time/local-time
    offset ::zone-offset/zone-offset]
   (impl/of time offset))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    offset ::zone-offset/zone-offset]
   (impl/of hour minute second nano-of-second offset)))

(def-constructor of-instant ::offset-time
  [instant ::instant/instant
   zone ::zone-id/zone-id]
  (asserts/require-non-nil instant "instant")
  (asserts/require-non-nil zone "zone")
  (let [offset (-> zone
                   (zone-id/get-rules)
                   (zone-rules/get-offset instant))]
    (impl/->OffsetTime
     (local-time-impl/of-nano-of-day
      (-> (instant/get-epoch-second instant)
          (math/add-exact (zone-offset/get-total-seconds offset))
          (math/floor-mod local-time-impl/SECONDS_PER_DAY)
          (math/multiply-exact local-time-impl/NANOS_PER_SECOND)
          (math/add-exact (instant/get-nano instant))))
     offset)))

(def-constructor now ::offset-time
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (now (clock-impl/system clock-or-zone-id))
     clock/IClock
     (do
       (asserts/require-non-nil clock-or-zone-id "clock")
       (let [now (clock/instant clock-or-zone-id)]
         (of-instant now
                     (-> clock-or-zone-id clock/get-zone zone-id/get-rules (zone-rules/get-offset now))))))))

(def-constructor from ::offset-time
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? offset-time/IOffsetTime temporal)
    temporal
    (try*
     (let [offset (zone-offset-impl/from temporal)
           time (local-time-impl-impl/from temporal)]
       (impl/->OffsetTime time offset))
     (catch :default e
       (throw (ex DateTimeException (str "Unable to obtain OffsetTime from TemporalAccessor: "
                                         temporal " of type "
                                         (type temporal)
                                         e)))))))

(s/def ::string string?)

(def PATTERN
  (delay (re-pattern (str "(" @local-time-impl/PATTERN ")"
                          (str #"(([+-][\d:]*)|(Z))")))))

(def-constructor parse ::offset-time
  ([text ::string]
   (if-let [[time _ _ _ _ _ _ _ offset zulu-offset]
            (some->> (re-matches @PATTERN text)
                     rest)]
     (of (local-time-impl/parse time)
         (zone-offset-impl/of (or offset zulu-offset)))
     (throw (ex DateTimeParseException (str "Failed to parse OffsetTime: '" text "'")))))

  ;; ([text ::j/char-sequence
  ;;   formatter ::date-time-formatter/date-time-formatter]
  ;;  (wip ::parse))
  )

(def MIN (local-time/at-offset local-time-impl/MIN zone-offset-impl/MAX))
(def MAX (local-time/at-offset local-time-impl/MAX zone-offset-impl/MIN))

(def-method to-string string?
  [{:keys [time offset]} ::offset-time]
  (str (string/to-string time)
       (string/to-string offset)))

(extend-type OffsetTime
  string/IString
  (to-string [this] (to-string this)))
