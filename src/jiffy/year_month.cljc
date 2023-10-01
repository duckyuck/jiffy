(ns jiffy.year-month
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeParseException DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.month :as month]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.year-month :as year-month]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.month-day :as month-day]
            [jiffy.clock :as clock-impl]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.math :as math]
            [jiffy.local-date :as local-date-impl]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.value-range :as value-range-impl]
            [jiffy.asserts :as asserts]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.year-impl :as year-impl]
            [jiffy.protocols.string :as string]))

(def-record YearMonth ::year-month-record
  [year ::j/int
   month ::j/month-of-year])

(defn- valid? [{:keys [year month]}]
  (try*
   (chrono-field/check-valid-value chrono-field/YEAR year)
   (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR month)
   (catch :default e
     false)))

(s/def ::year-month (s/and ::year-month-record valid?))

(def-method get-year ::j/int
  [this ::year-month]
  (:year this))

(def-method get-month-value ::j/int
  [this ::year-month]
  (:month this))

(def-method get-month ::month/month
  [this ::year-month]
  (-> this :month month/of))

(def-method is-leap-year ::j/boolean
  [this ::year-month]
  (iso-chronology/is-leap-year iso-chronology/INSTANCE (:year this)))

(def-method length-of-month ::j/int
  [this ::year-month]
  (-> this
      get-month
      (month/length (is-leap-year this))))

(def-method is-valid-day ::j/boolean
  [this ::year-month
   day-of-month ::j/int]
  (<= 1 day-of-month (length-of-month this)))

(def-method length-of-year ::j/int
  [this ::year-month]
  (if (is-leap-year this)
    366
    365))

(defn- --with [this new-year new-month]
  (if (and (= (:year this) new-year)
           (= (:month this) new-month))
    this
    (->YearMonth new-year new-month)))

(def-method with-year ::year-month
  [this ::year-month
   year ::j/int]
  (chrono-field/check-valid-value chrono-field/YEAR year)
  (--with this year (:month this)))

(def-method with-month ::year-month
  [this ::year-month
   month ::j/int]
  (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR month)
  (--with this (:year this) month))

(def-method plus-years ::year-month
  [this ::year-month
   years-to-add ::j/long]
  (if (zero? years-to-add)
    this
    (--with this
            (chrono-field/check-valid-int-value
             chrono-field/YEAR
             (math/add-exact (long (:year this)) years-to-add))
            (:month this))))

(def-method plus-months ::year-month
  [this ::year-month
   months-to-add ::j/long]
  (if (zero? months-to-add)
    this
    (let [calc-months (-> (:year this)
                          (math/multiply-exact 12)
                          (math/add-exact (math/subtract-exact (:month this) 1))
                          (math/add-exact months-to-add))]
      (--with this
              (chrono-field/check-valid-int-value
               chrono-field/YEAR
               (math/floor-div calc-months 12))
              (math/add-exact
               (math/floor-mod calc-months 12)
               1)))))

(def-method minus-years ::year-month
  [this ::year-month
   years-to-subtract ::j/long]
  (if (= years-to-subtract math/long-min-value)
    (-> this
        (plus-years math/long-min-value)
        (plus-years 1))
    (plus-years this (- years-to-subtract))))

(def-method minus-months ::year-month
  [this ::year-month
   months-to-subtract ::j/long]
  (if (= months-to-subtract math/long-min-value)
    (-> this
        (plus-months math/long-min-value)
        (plus-months 1))
    (plus-months this (- months-to-subtract))))

(def-method format string?
  [this ::year-month
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

(def-method at-day ::local-date/local-date
  [this ::year-month
   day-of-month ::j/int]
  (local-date-impl/of (:year this) (:month this) day-of-month))

(def-method at-end-of-month ::local-date/local-date
  [this ::year-month]
  (local-date-impl/of (:year this) (:month this) (length-of-month this)))

(def-method compare-to ::j/int
  [this ::year-month
   other ::year-month]
  (let [cmp (math/subtract-exact (:year this) (:year other))]
    (if (zero? cmp)
      (math/subtract-exact (:month this) (:month other))
      cmp)))

(def-method is-after ::j/boolean
  [this ::year-month
   other ::year-month]
  (pos? (compare-to this other)))

(def-method is-before ::j/boolean
  [this ::year-month
   other ::year-month]
  (neg? (compare-to this other)))

(extend-type YearMonth
  year-month/IYearMonth
  (get-year [this] (get-year this))
  (get-month-value [this] (get-month-value this))
  (get-month [this] (get-month this))
  (is-leap-year [this] (is-leap-year this))
  (is-valid-day [this day-of-month] (is-valid-day this day-of-month))
  (length-of-month [this] (length-of-month this))
  (length-of-year [this] (length-of-year this))
  (with-year [this year] (with-year this year))
  (with-month [this month] (with-month this month))
  (plus-years [this years-to-add] (plus-years this years-to-add))
  (plus-months [this months-to-add] (plus-months this months-to-add))
  (minus-years [this years-to-subtract] (minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (minus-months this months-to-subtract))
  (format [this formatter] (format this formatter))
  (at-day [this day-of-month] (at-day this day-of-month))
  (at-end-of-month [this] (at-end-of-month this))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other)))

(extend-type YearMonth
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(defn --get-proleptic-month [this]
  (-> (:year this)
      (math/multiply-exact 12)
      (math/add-exact (:month this))
      (math/subtract-exact 1)))

(def-method get-long ::j/long
  [{:keys [month year day] :as this} ::year-month
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (condp = field
      chrono-field/MONTH_OF_YEAR
      month

      chrono-field/PROLEPTIC_MONTH
      (--get-proleptic-month this)

      chrono-field/YEAR_OF_ERA
      (if (< year 1) (- 1 year) year)

      chrono-field/YEAR
      year

      chrono-field/ERA
      (if (< year 1) 0 1)

      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
                 {:this this :field field})))))

(def-method with ::month-day/month-day
  ([this ::year-month
    adjuster ::temporal-adjuster/temporal-adjuster]
   (temporal-adjuster/adjust-into adjuster this))

  ([this ::year-month
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (satisfies? temporal-field/ITemporalField field)
     (temporal-field/adjust-into field this new-value)
     (do
       (chrono-field/check-valid-value field new-value)
       (condp = field
         chrono-field/MONTH_OF_YEAR
         (with-month this (int new-value))

         chrono-field/PROLEPTIC_MONTH
         (plus-months this (- new-value (--get-proleptic-month this)))

         chrono-field/YEAR_OF_ERA
         (with-year this (int (if (< (get-year this) 1) (- 1 new-value) new-value)))

         chrono-field/YEAR
         (with-year this (int new-value))

         chrono-field/ERA
         (if (= (get-long this chrono-field/ERA) new-value)
           this
           (with-year this (- 1 (:year this))))

         (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " (pr-str field)) {:this this :field field})))))))

(def-method plus ::year-month
  ([this ::year-month
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ([this ::year-month
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if-not (chrono-unit/chrono-unit? unit)
     (temporal-unit/add-to unit this amount-to-add)
     (condp = unit
       chrono-unit/MONTHS (plus-months this amount-to-add)
       chrono-unit/YEARS (plus-years this amount-to-add)
       chrono-unit/DECADES (plus-years this (math/multiply-exact amount-to-add 10))
       chrono-unit/CENTURIES (plus-years this (math/multiply-exact amount-to-add 100))
       chrono-unit/MILLENNIA (plus-years this (math/multiply-exact amount-to-add 1000))
       chrono-unit/ERAS (with this
                              chrono-field/ERA
                              (math/add-exact (get-long this chrono-field/ERA) amount-to-add))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                  {:this this :unit unit :amount-to-add amount-to-add}))))))

(def-method minus ::year-month
  ([this ::year-month
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ([this ::year-month
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(declare from)

(def-method until ::j/long
  [this ::year-month
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (let [months-until (math/subtract-exact (--get-proleptic-month end) (--get-proleptic-month this))]
        (condp = unit
          chrono-unit/MONTHS months-until
          chrono-unit/YEARS (long (/ months-until 12))
          chrono-unit/DECADES (long (/ months-until 120))
          chrono-unit/CENTURIES (long (/ months-until 1200))
          chrono-unit/MILLENNIA (long (/ months-until 12000))
          chrono-unit/ERAS (math/subtract-exact (get-long end chrono-field/ERA)
                                                (get-long this chrono-field/ERA))
          (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                     {:this this :unit unit :end-exclusive end-exclusive})))))))

(extend-type YearMonth
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this new-year new-month] (with this new-year new-month)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

(def-method is-supported ::j/boolean
  [this ::year-month
   field-or-unit (s/or ::temporal-field/temporal-field
                       ::temporal-unit/temporal-unit)]
  (condp satisfies? field-or-unit
    temporal-field/ITemporalField
    (if (chrono-field/chrono-field? field-or-unit)
      (some? (#{chrono-field/YEAR
                chrono-field/MONTH_OF_YEAR
                chrono-field/PROLEPTIC_MONTH
                chrono-field/YEAR_OF_ERA
                chrono-field/ERA} field-or-unit))
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    temporal-unit/ITemporalUnit
    (if (chrono-unit/chrono-unit? field-or-unit)
      (some? (#{chrono-unit/MONTHS
                chrono-unit/YEARS
                chrono-unit/DECADES
                chrono-unit/CENTURIES
                chrono-unit/MILLENNIA
                chrono-unit/ERAS} field-or-unit))
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))))

(def-method range ::value-range/value-range
  [this ::year-month
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/YEAR_OF_ERA)
    (if (<= (get-year this) 0)
      (value-range-impl/of 1 (inc year-impl/MAX_VALUE))
      (value-range-impl/of 1 year-impl/MAX_VALUE))
    (temporal-accessor-defaults/-range this field)))

(def-method get ::j/int
  [this ::year-month
   field ::temporal-field/temporal-field]
  (-> this
      (range field)
      (value-range/check-valid-int-value (get-long this field) field)))

(def-method query ::temporal-query/result
  [this ::year-month
   query ::temporal-query/temporal-query]
  (condp = query
    (temporal-queries/chronology)
    iso-chronology/INSTANCE

    (temporal-queries/precision)
    chrono-unit/MONTHS

    (temporal-accessor-defaults/-query this query)))

(extend-type YearMonth
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::year-month
   temporal ::temporal/temporal]
  (if-not (= (chronology/from temporal) iso-chronology/INSTANCE)
    (throw (ex DateTimeException "Adjustment only supported on ISO date-time"
               {:this this :temporal temporal}))
    (temporal/with temporal chrono-field/PROLEPTIC_MONTH (--get-proleptic-month this))))

(extend-type YearMonth
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor of ::year-month
  [year ::j/int
   month (s/or ::month
               ::j/int)]
  (if (number? month)
    (do
      (chrono-field/check-valid-value chrono-field/YEAR year)
      (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR month)
      (->YearMonth year month))
    (do
      (asserts/require-non-nil month "month")
      (of year (month/get-value month)))))

(def-constructor now ::year-month
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (now (clock-impl/system clock-or-zone-id))
     clock/IClock
     (let [local-date (local-date-impl/now clock-or-zone-id)]
       (of (local-date/get-year local-date)
           (local-date/get-month local-date))))))

(def-constructor from ::year-month
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? year-month/IYearMonth temporal)
    temporal
    (do
      (asserts/require-non-nil temporal "temporal")
      (try*
       (let [temporal (if-not (= iso-chronology/INSTANCE
                                 (chronology/from temporal))
                        (local-date-impl/from temporal)
                        temporal)]
         (of (temporal-accessor/get temporal chrono-field/YEAR)
             (temporal-accessor/get temporal chrono-field/MONTH_OF_YEAR)))
       (catch DateTimeException e
         (throw (ex DateTimeException
                    (str "Unable to obtain YearMonth from TemporalAccessor: "
                         temporal " of type " (pr-str temporal))
                    {:temporal temporal}
                    e)))))))

(s/def ::string string?)

(def PATTERN (delay #"([+-]?\d*)-(\d{2})"))

(def-constructor parse ::year-month
  ([text ::string]
   (if-let [[year month]
            (some->> (re-matches @PATTERN text)
                     rest
                     (map math/parse-long))]
     (of year month)
     (throw (ex DateTimeParseException (str "Failed to parse YearMonth: '" text "'")))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/YearMonth.java#L293
  ;; ([text ::j/char-sequence
  ;;   formatter ::date-time-formatter/date-time-formatter]
  ;;  (wip ::parse))
  )

(def-method to-string string?
  [{:keys [year month]} ::year-month]
  (let [abs-year (math/abs year)]
    (cond-> ""
      (< abs-year 1000)
      (cond->
          (neg? year)
        (str (string/delete-char-at (str (- year 10000)) 1))

        (not (neg? year))
        (str (string/delete-char-at (str (+ year 10000)) 0)))

      (not (< abs-year 1000))
      (-> (str (str year)))

      true
      (-> (str (if (< month 10) "-0" "-"))
          (str month)))))

(extend-type YearMonth
  string/IString
  (to-string [this] (to-string this)))
