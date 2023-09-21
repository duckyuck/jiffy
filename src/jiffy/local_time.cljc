(ns jiffy.local-time
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [JavaNullPointerException DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.clock :as clock-impl]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.local-time-impl :refer [create #?@(:cljs [LocalTime])] :as impl]
            [jiffy.offset-time-impl :as offset-time-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.local-time-impl-impl :as impl-impl]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.asserts :as asserts]
            [jiffy.protocols.zone.zone-rules :as zone-rules])
  #?(:clj (:import [jiffy.local_time_impl LocalTime])))

(def MIN impl/MIN)
(def MAX impl/MAX)
(def MIDNIGHT impl/MIDNIGHT)
(def NOON impl/NOON)

(def HOURS_PER_DAY impl/HOURS_PER_DAY)
(def MINUTES_PER_HOUR impl/MINUTES_PER_HOUR)
(def MINUTES_PER_DAY impl/MINUTES_PER_DAY)
(def SECONDS_PER_MINUTE impl/SECONDS_PER_MINUTE)
(def SECONDS_PER_HOUR impl/SECONDS_PER_HOUR)
(def SECONDS_PER_DAY impl/SECONDS_PER_DAY)
(def MILLIS_PER_DAY impl/MILLIS_PER_DAY)
(def MICROS_PER_DAY impl/MICROS_PER_DAY)
(def NANOS_PER_MILLI impl/NANOS_PER_MILLI)
(def NANOS_PER_SECOND impl/NANOS_PER_SECOND)
(def NANOS_PER_MINUTE impl/NANOS_PER_MINUTE)
(def NANOS_PER_HOUR impl/NANOS_PER_HOUR)
(def NANOS_PER_DAY impl/NANOS_PER_DAY)

(s/def ::local-time ::impl/local-time)

(def-method to-nano-of-day ::j/long
  [this ::local-time]
  (+ (* (:hour this) NANOS_PER_HOUR)
     (* (:minute this) NANOS_PER_MINUTE)
     (* (:second this) NANOS_PER_SECOND)
     (:nano this)))

(def-method to-second-of-day ::j/int
  [this ::local-time]
  (math/add-exact
   (math/add-exact (math/multiply-exact (:hour this) SECONDS_PER_HOUR)
                   (math/multiply-exact (:minute this) SECONDS_PER_MINUTE))
   (:second this)))

(def-method get-hour ::j/int
  [this ::local-time]
  (:hour this))

(def-method get-minute ::j/int
  [this ::local-time]
  (:minute this))

(def-method get-second ::j/int
  [this ::local-time]
  (:second this))

(def-method get-nano ::j/int
  [this ::local-time]
  (:nano this))

(def-method with-hour ::local-time
  [this ::local-time
   hour ::j/hour-of-day]
  (if (= hour (:hour this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/HOUR_OF_DAY hour)
      (create hour (:minute this) (:second this) (:nano this)))))

(def-method with-minute ::local-time
  [this ::local-time
   minute ::j/minute-of-hour]
  (if (= minute (:minute this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/MINUTE_OF_HOUR minute)
      (create (:hour this) minute (:second this) (:nano this)))))

(def-method with-second ::local-time
  [this ::local-time
   second ::j/second-of-minute]
  (if (= second (:second this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/SECOND_OF_MINUTE second)
      (create (:hour this) (:minute this) second (:nano this)))))

(def-method with-nano ::local-time
  [this ::local-time
   nano-of-second ::j/nano-of-second]
  (if (= nano-of-second (:nano this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/NANO_OF_SECOND nano-of-second)
      (create (:hour this) (:minute this) (:second this) nano-of-second))))

(def-constructor of-nano-of-day ::local-time
  [nano-of-day ::j/long]
  (impl/of-nano-of-day nano-of-day))

(def-method truncated-to ::local-time
  [this ::local-time
   unit ::temporal-unit/temporal-unit]
  (if (= unit chrono-unit/NANOS)
    this
    (let [unit-dur (temporal-unit/get-duration unit)]
      (when (> (duration/get-seconds unit-dur) SECONDS_PER_DAY)
        (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:local-time this :unit unit})))
      (let [dur (duration/to-nanos unit-dur)]
        (when (not= 0 (mod NANOS_PER_DAY dur))
          (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:local-time this :unit unit})))
        (of-nano-of-day (* dur (long (/ (to-nano-of-day this) dur))))))))

(def-method plus-hours ::local-time
  [this ::local-time
   hours-to-add ::j/long]
  (if (= 0 hours-to-add)
    this
    (create (mod (+ (int (mod hours-to-add HOURS_PER_DAY))
                    (:hour this)
                    HOURS_PER_DAY)
                 HOURS_PER_DAY)
            (:minute this)
            (:second this)
            (:nano this))))

(def-method plus-minutes ::local-time
  [this ::local-time
   minutes-to-add ::j/long]
  (if (= 0 minutes-to-add)
    this
    (let [mofd (+ (:minute this)
                  (* (:hour this) MINUTES_PER_HOUR))
          new-mofd (mod (+ (int (mod minutes-to-add MINUTES_PER_DAY))
                           mofd
                           MINUTES_PER_DAY)
                        MINUTES_PER_DAY)]
      (if (= mofd new-mofd)
        this
        (create (int (/ new-mofd MINUTES_PER_HOUR))
                (mod new-mofd MINUTES_PER_HOUR)
                (:second this)
                (:nano this))))))

(def-method plus-seconds ::local-time
  [this ::local-time
   seconds-to-add ::j/long]
  (if (= 0 seconds-to-add)
    this
    (let [sofd (+ (:second this)
                  (* (:minute this) SECONDS_PER_MINUTE)
                  (* (:hour this) SECONDS_PER_HOUR))
          new-sofd (mod (+ (int (mod seconds-to-add SECONDS_PER_DAY))
                           sofd
                           SECONDS_PER_DAY)
                        SECONDS_PER_DAY)]
      (if (= sofd new-sofd)
        this
        (create (int (/ new-sofd SECONDS_PER_HOUR))
                (mod (int (/ new-sofd SECONDS_PER_MINUTE)) MINUTES_PER_HOUR)
                (mod new-sofd SECONDS_PER_MINUTE)
                (:nano this))))))

(def-method plus-nanos ::local-time
  [this ::local-time
   nanos-to-add ::j/long]
  (if (= 0 nanos-to-add)
    this
    (let [nofd (to-nano-of-day this)
          new-nofd (mod (+ (mod nanos-to-add NANOS_PER_DAY)
                           nofd
                           NANOS_PER_DAY)
                        NANOS_PER_DAY)]
      (if (= nofd new-nofd)
        this
        (create (int (/ new-nofd NANOS_PER_HOUR))
                (mod (int (/ new-nofd NANOS_PER_MINUTE)) MINUTES_PER_HOUR)
                (mod (int (/ new-nofd NANOS_PER_SECOND)) SECONDS_PER_MINUTE)
                (mod new-nofd NANOS_PER_SECOND))))))

(def-method minus-hours ::local-time
  [this ::local-time
   hours-to-subtract ::j/long]
  (plus-hours this (- (mod hours-to-subtract HOURS_PER_DAY))))

(def-method minus-minutes ::local-time
  [this ::local-time
   minutes-to-subtract ::j/long]
  (plus-minutes this (- (mod minutes-to-subtract MINUTES_PER_DAY))))

(def-method minus-seconds ::local-time
  [this ::local-time
   seconds-to-subtract ::j/long]
  (plus-seconds this (- (mod seconds-to-subtract SECONDS_PER_DAY))))

(def-method minus-nanos ::local-time
  [this ::local-time
   nanos-to-subtract ::j/long]
  (plus-nanos this (- (mod nanos-to-subtract NANOS_PER_DAY))))

(def-method format string?
  [this ::local-time
   formatter ::date-time-formatter/date-time-formatter]
  (asserts/require-non-nil formatter "formatter")
  (date-time-formatter/format formatter this))

(def-method at-date ::local-date-time/local-date-time
  [this ::local-time
   date ::local-date/local-date]
  (local-date-time-impl/of date this))

(def-method at-offset ::offset-time/offset-time
  [this ::local-time
   offset ::zone-offset/zone-offset]
  (offset-time-impl/of this offset))

(def-method to-epoch-second ::j/long
  [this ::local-time
   date ::local-date/local-date
   offset ::zone-offset/zone-offset]
  (asserts/require-non-nil date "date")
  (asserts/require-non-nil offset "offset")
  (-> (chrono-local-date/to-epoch-day date)
      (math/multiply-exact 86400)
      (math/add-exact (to-second-of-day this))
      (math/subtract-exact (zone-offset/get-total-seconds offset))))

(def-method is-after ::j/boolean
  [this ::local-time
   other ::local-time]
  (> (time-comparable/compare-to this other) 0))

(def-method is-before ::j/boolean
  [this ::local-time
   other ::local-time]
  (< (time-comparable/compare-to this other) 0))

(extend-type LocalTime
  local-time/ILocalTime
  (to-nano-of-day [this] (to-nano-of-day this))
  (to-second-of-day [this] (to-second-of-day this))
  (get-hour [this] (get-hour this))
  (get-minute [this] (get-minute this))
  (get-second [this] (get-second this))
  (get-nano [this] (get-nano this))
  (with-hour [this hour] (with-hour this hour))
  (with-minute [this minute] (with-minute this minute))
  (with-second [this second] (with-second this second))
  (with-nano [this nano-of-second] (with-nano this nano-of-second))
  (truncated-to [this unit] (truncated-to this unit))
  (plus-hours [this hours-to-add] (plus-hours this hours-to-add))
  (plus-minutes [this minutes-to-add] (plus-minutes this minutes-to-add))
  (plus-seconds [this secondsto-add] (plus-seconds this secondsto-add))
  (plus-nanos [this nanos-to-add] (plus-nanos this nanos-to-add))
  (minus-hours [this hours-to-subtract] (minus-hours this hours-to-subtract))
  (minus-minutes [this minutes-to-subtract] (minus-minutes this minutes-to-subtract))
  (minus-seconds [this seconds-to-subtract] (minus-seconds this seconds-to-subtract))
  (minus-nanos [this nanos-to-subtract] (minus-nanos this nanos-to-subtract))
  (format [this formatter] (format this formatter))
  (at-date [this date] (at-date this date))
  (at-offset [this offset] (at-offset this offset))
  (to-epoch-second [this date offset] (to-epoch-second this date offset))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other)))

(def-method compare-to ::j/int
  [this ::local-time
   other ::local-time]
  (compare (mapv #(% this)  [:hour :minute :second :nano])
           (mapv #(% other) [:hour :minute :second :nano])))

(extend-type LocalTime
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(declare -plus-seconds
         -plus-minutes
         -plus-hours)

(def-method with ::local-time
  ([this ::local-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (if (instance? LocalTime adjuster)
     adjuster
     (temporal-adjuster/adjust-into adjuster this)))

  ([this ::local-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if (chrono-field/chrono-field? field)
     (do (chrono-field/check-valid-value field new-value)
         (cond
           (= field chrono-field/NANO_OF_SECOND) (with-nano this (int new-value))
           (= field chrono-field/NANO_OF_DAY) (of-nano-of-day new-value)
           (= field chrono-field/MICRO_OF_SECOND) (with-nano this (* (int new-value) 1000))
           (= field chrono-field/MICRO_OF_DAY) (of-nano-of-day (* new-value 1000))
           (= field chrono-field/MILLI_OF_SECOND) (with-nano this (* (int new-value) 1000000))
           (= field chrono-field/MILLI_OF_DAY) (of-nano-of-day (* new-value 1000000))
           (= field chrono-field/SECOND_OF_MINUTE) (with-second this (int new-value))
           (= field chrono-field/SECOND_OF_DAY) (plus-seconds this (- new-value (to-second-of-day this)))
           (= field chrono-field/MINUTE_OF_HOUR) (with-minute this (int new-value))
           (= field chrono-field/MINUTE_OF_DAY) (plus-minutes this (- new-value (+ (* (:hour this) 60) (:minute this))))
           (= field chrono-field/HOUR_OF_AMPM) (plus-hours this (- new-value (mod (:hour this) 12)))
           (= field chrono-field/CLOCK_HOUR_OF_AMPM) (plus-hours this (- (if (= 12 new-value) 0 new-value) (mod (:hour this) 12)))
           (= field chrono-field/HOUR_OF_DAY) (with-hour this (int new-value))
           (= field chrono-field/CLOCK_HOUR_OF_DAY) (with-hour this (int (if (= 24 new-value) 0 new-value)))
           (= field chrono-field/AMPM_OF_DAY) (plus-hours this (* 12 (- new-value (long (/ (:hour this) 12)))))
           :else (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:local-time this :field field :new-value new-value}))))
     (temporal-field/adjust-into field this new-value))))

(def-method plus ::local-time
  ([this ::local-time
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ([this ::local-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if-not (chrono-unit/chrono-unit? unit)
     (temporal-unit/add-to unit this amount-to-add)
     (condp = unit
       chrono-unit/NANOS (plus-nanos this amount-to-add)
       chrono-unit/MICROS (plus-nanos this (math/multiply-exact (mod amount-to-add MICROS_PER_DAY) 1000))
       chrono-unit/MILLIS (plus-nanos this (math/multiply-exact (mod amount-to-add MICROS_PER_DAY) 1000000))
       chrono-unit/SECONDS (plus-seconds this amount-to-add)
       chrono-unit/MINUTES (plus-minutes this amount-to-add)
       chrono-unit/HOURS (plus-hours this amount-to-add)
       chrono-unit/HALF_DAYS (plus-hours this (* (rem amount-to-add 2) 12))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                  {:this this :unit unit :amount-to-add amount-to-add}))))))

(def-method minus ::local-time
  ([this ::local-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ([this ::local-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(def-method until ::j/long
  [this ::local-time
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (impl-impl/from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (let [nanos-until (math/subtract-exact (to-nano-of-day end) (to-nano-of-day this))]
        (condp = unit
          chrono-unit/NANOS nanos-until
          chrono-unit/MICROS (long (/ nanos-until 1000))
          chrono-unit/MILLIS (long (/ nanos-until 1000000))
          chrono-unit/SECONDS (long (/ nanos-until NANOS_PER_SECOND))
          chrono-unit/MINUTES (long (/ nanos-until NANOS_PER_MINUTE))
          chrono-unit/HOURS (long (/ nanos-until NANOS_PER_HOUR))
          chrono-unit/HALF_DAYS (long (/ nanos-until (* 12 NANOS_PER_HOUR)))
          (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                     {:this this :unit unit :end-exclusive end-exclusive})))))))

(extend-type LocalTime
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
  [this ::local-time
   field-or-unit (s/or ::temporal-field/temporal-field
                       ::temporal-unit/temporal-unit)]
  (cond
    (satisfies? temporal-field/ITemporalField field-or-unit)
    (if (chrono-field/chrono-field? field-or-unit)
      (temporal-field/is-time-based field-or-unit)
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    (satisfies? temporal-unit/ITemporalUnit field-or-unit)
    (if (chrono-unit/chrono-unit? field-or-unit)
      (temporal-unit/is-time-based field-or-unit)
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))

    :else
    (throw (ex-info "Unsupported field or unit" {:instant this :field-or-unit field-or-unit}))))

(def-method range ::value-range/value-range
  [this ::local-time
   field ::temporal-field/temporal-field]
  (temporal-accessor-defaults/-range this field))

(defn --get0 [{:keys [nano second minute hour] :as this} field]
  (condp = field
    chrono-field/NANO_OF_SECOND nano
    chrono-field/NANO_OF_DAY (throw (ex UnsupportedTemporalTypeException "Invalid field 'NanoOfDay' for get() method, use getLong() instead"))
    chrono-field/MICRO_OF_SECOND (long (/ nano 1000))
    chrono-field/MICRO_OF_DAY (throw (ex UnsupportedTemporalTypeException "Invalid field 'MicroOfDay' for get() method, use getLong() instead"))
    chrono-field/MILLI_OF_SECOND (long (/ nano 1000000))
    chrono-field/MILLI_OF_DAY (long (/ (to-nano-of-day this) 1000000))
    chrono-field/SECOND_OF_MINUTE second
    chrono-field/SECOND_OF_DAY (to-second-of-day this)
    chrono-field/MINUTE_OF_HOUR minute
    chrono-field/MINUTE_OF_DAY (+ (* hour 60) minute)
    chrono-field/HOUR_OF_AMPM (mod hour 12)
    chrono-field/CLOCK_HOUR_OF_AMPM (let [ham (mod hour 12)]
                                      (if (zero? (mod ham 12))
                                        12
                                        ham))
    chrono-field/HOUR_OF_DAY hour
    chrono-field/CLOCK_HOUR_OF_DAY (if (zero? hour) 24 hour)
    chrono-field/AMPM_OF_DAY (long (/ hour 12))
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)))))

(def-method get ::j/int
  [this ::local-time
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (--get0 this field)
    (temporal-accessor-defaults/-get this field)))

(def-method get-long ::j/long
  [this ::local-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (condp = field
      chrono-field/NANO_OF_DAY (to-nano-of-day this)
      chrono-field/MICRO_OF_DAY (long (/ (to-nano-of-day this) 1000))
      (--get0 this field))))

(def-method query ::j/wip
  [this ::local-time
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/chronology)
       (temporal-queries/zone-id)
       (temporal-queries/zone)
       (temporal-queries/offset)
       (temporal-queries/local-date)} query)
    nil

    (= query (temporal-queries/local-time))
    this

    (= query (temporal-queries/precision))
    chrono-unit/NANOS

    :else
    (temporal-query/query-from query this)))

(extend-type LocalTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (is-supported this is-supported--overloaded-param))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::local-time
   temporal ::temporal/temporal]
  (temporal/with temporal chrono-field/NANO_OF_DAY (to-nano-of-day this)))

(extend-type LocalTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor of-instant ::local-time
  [instant ::instant/instant
   zone ::zone-id/zone-id]
  (asserts/require-non-nil instant "instant")
  (asserts/require-non-nil zone "zone")
  (of-nano-of-day (-> (instant/get-epoch-second instant)
                      (math/add-exact (-> (zone-id/get-rules zone)
                                          (zone-rules/get-offset instant)
                                          zone-offset/get-total-seconds))
                      (math/floor-mod SECONDS_PER_DAY)
                      (math/multiply-exact NANOS_PER_SECOND)
                      (math/add-exact (instant/get-nano instant)))))

(def-constructor now ::local-time
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (now (clock-impl/system clock-or-zone-id))
     clock/IClock (of-instant (clock/instant clock-or-zone-id)
                              (clock/get-zone clock-or-zone-id)))))

(def-constructor of ::local-time
  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (of hour minute 0 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (of hour minute second 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (impl/of hour minute second nano-of-second)))

(def-constructor of-second-of-day ::local-time
  [second-of-day ::j/long]
  (chrono-field/check-valid-value chrono-field/SECOND_OF_DAY second-of-day)
  (let [hours (long (/ second-of-day SECONDS_PER_HOUR))
        second-of-day (- second-of-day (math/multiply-exact-int hours SECONDS_PER_HOUR))
        minutes (long (/ second-of-day SECONDS_PER_MINUTE))
        second-of-day (- second-of-day (math/multiply-exact-int minutes SECONDS_PER_MINUTE))]
    (impl/create hours minutes second-of-day 0)))

(def-constructor from ::local-time
  [temporal ::temporal-accessor/temporal-accessor]
  (impl-impl/from temporal))

(def-constructor parse ::local-time
  ([text ::j/char-sequence]
   (wip ::parse))

  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))
