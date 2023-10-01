(ns jiffy.year
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.month :as month]
            [jiffy.protocols.month-day :as month-day]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.year :as year]
            [jiffy.protocols.year-month :as year-month]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.year-impl :as impl]
            [jiffy.year-month :as year-month-impl]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.math :as math]
            [jiffy.local-date :as local-date-impl]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.asserts :as asserts]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.temporal.value-range :as value-range-impl]
            [jiffy.year-impl :as year-impl]
            [jiffy.clock :as clock-impl]
            [jiffy.protocols.string :as string]))

(def-record Year ::year-record
  [year ::j/int])

(defn- valid? [{:keys [year]}]
  (try*
   (chrono-field/check-valid-value chrono-field/YEAR year)
   (catch :default e
     false)))

(s/def ::year (s/and ::year-record valid?))

(def MIN_VALUE impl/MIN_VALUE)
(def MAX_VALUE impl/MAX_VALUE)

(def-method is-leap ::j/boolean
  [year ::j/long]
  (cond
    (satisfies? year/IYear year)
    (is-leap (:year year))

    (number? year)
    (and (zero? (bit-and year 3))
         (or (not= (mod year 100) 0)
             (zero? (mod year 400))))))

(def-method is-leap--proto ::j/boolean
  [year ::year]
  (is-leap (:year year)))

(def-method get-value ::j/int
  [this ::year]
  (:year this))

(def-method is-valid-month-day ::j/boolean
  [this ::year
   month-day ::month-day/month-day]
  (and month-day (month-day/is-valid-year month-day (:year this))))

(def-method length ::j/int
  [this ::year]
  (if (is-leap this)
    366
    365))

(declare of)
(def-method plus-years ::year
  [this ::year
   years-to-add ::j/long]
  (if (zero? years-to-add)
    this
    (of (chrono-field/check-valid-int-value chrono-field/YEAR (+ (:year this) years-to-add)))))

(def-method minus-years ::year
  [this ::year
   years-to-subtract ::j/long]
  (if (= years-to-subtract math/long-min-value)
    (-> this
        (plus-years math/long-min-value)
        (plus-years 1))
    (plus-years this (- years-to-subtract))))

(def-method format string?
  [this ::year
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::format))

(def-method at-day ::local-date/local-date
  [this ::year
   day-of-year ::j/int]
  (local-date-impl/of-year-day (:year this) day-of-year))

(def-method at-month ::year-month/year-month
  [this ::year
   month (s/or :month ::month/month
               :int ::j/int)]
  (year-month-impl/of (:year this) month))

(def-method at-month-day ::local-date/local-date
  [this ::year
   month-day ::month-day/month-day]
  (month-day/at-year month-day (:year this)))

(def-method is-after ::j/boolean
  [this ::year
   other ::year]
  (> (:year this) (:year other)))

(def-method is-before ::j/boolean
  [this ::year
   other ::year]
  (< (:year this) (:year other)))

(extend-type Year
  year/IYear
  (get-value [this] (get-value this))
  (is-leap [this] (is-leap--proto this))
  (is-valid-month-day [this month-day] (is-valid-month-day this month-day))
  (length [this] (length this))
  (plus-years [this years-to-add] (plus-years this years-to-add))
  (minus-years [this years-to-subtract] (minus-years this years-to-subtract))
  (format [this formatter] (format this formatter))
  (at-day [this day-of-year] (at-day this day-of-year))
  (at-month [this at-month--overloaded-param] (at-month this at-month--overloaded-param))
  (at-month-day [this month-day] (at-month-day this month-day))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other)))

(def-method compare-to ::j/int
  [this ::year
   other ::year]
  (math/subtract-exact (:year this) (:year other)))

(extend-type Year
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(declare get-long)

(def-method with ::year
  ([this ::year
    adjuster ::temporal-adjuster/temporal-adjuster]
   (temporal-adjuster/adjust-into adjuster this))

  ([{:keys [year] :as this} ::year
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (chrono-field/chrono-field? field)
     (temporal-field/adjust-into field this new-value)
     (do
       (chrono-field/check-valid-value field new-value)
       (condp = field
         chrono-field/YEAR_OF_ERA
         (of (if (< year 1)
               (math/subtract-exact 1 new-value)
               new-value))

         chrono-field/YEAR
         (of new-value)

         chrono-field/ERA
         (if (= (get-long this chrono-field/ERA) new-value)
           this
           (of (math/subtract-exact 1 (:year this))))

         (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
                    {:this this :field field})))))))

(def-method plus ::year
  ([this ::year
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ([this ::year
    amount-to-add ::j/int
    unit ::temporal-unit/temporal-unit]
   (if-not (chrono-unit/chrono-unit? unit)
     (temporal-unit/add-to unit this amount-to-add)
     (condp = unit
       chrono-unit/YEARS (plus-years this amount-to-add)
       chrono-unit/DECADES (plus-years this (math/multiply-exact amount-to-add 10))
       chrono-unit/CENTURIES (plus-years this (math/multiply-exact amount-to-add 100))
       chrono-unit/MILLENNIA (plus-years this (math/multiply-exact amount-to-add 1000))
       chrono-unit/ERAS (with this
                              chrono-field/ERA
                              (math/add-exact (get-long this chrono-field/ERA) amount-to-add))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                  {:this this :unit unit :amount-to-add amount-to-add}))))))

(def-method minus ::year
  ([this ::year
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ([this ::year
    amount-to-subtract ::j/int
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-max-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(declare from)

(def-method until ::j/long
  [this ::year
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (let [year-until (math/subtract-exact (:year end) (:year this))]
        (condp = unit
          chrono-unit/YEARS year-until
          chrono-unit/DECADES (long (/ year-until 10))
          chrono-unit/CENTURIES (long (/ year-until 100))
          chrono-unit/MILLENNIA (long (/ year-until 1000))
          chrono-unit/ERAS (math/subtract-exact (get-long end chrono-field/ERA)
                                                (get-long this chrono-field/ERA))
          (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                     {:this this :unit unit :end-exclusive end-exclusive})))))))

(extend-type Year
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
  [this ::year
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (condp satisfies? field-or-unit
    temporal-field/ITemporalField
    (if (chrono-field/chrono-field? field-or-unit)
      (some? (#{chrono-field/YEAR
                chrono-field/YEAR_OF_ERA
                chrono-field/ERA} field-or-unit))
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    temporal-unit/ITemporalUnit
    (if (chrono-unit/chrono-unit? field-or-unit)
      (some? (#{chrono-unit/YEARS
                chrono-unit/DECADES
                chrono-unit/CENTURIES
                chrono-unit/MILLENNIA
                chrono-unit/ERAS} field-or-unit))
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))))

(def-method range ::value-range/value-range
  [this ::year
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/YEAR_OF_ERA)
    (if (<= (:year this) 0)
      (value-range-impl/of 1 (inc MAX_VALUE))
      (value-range-impl/of 1 MAX_VALUE))
    (temporal-accessor-defaults/-range this field)))

(def-method get ::j/int
  [this ::year
   field ::temporal-field/temporal-field]
  (-> field
      temporal-field/range
      (value-range/check-valid-int-value (get-long this field) field)))

(def-method get-long ::j/long
  [this ::year
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (condp = field
      chrono-field/YEAR_OF_ERA
      (if (< (:year this) 1)
        (math/subtract-exact 1 (:year this))
        (:year this))

      chrono-field/YEAR
      (:year this)

      chrono-field/ERA
      (if (< (:year this) 1) 0 1)

      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
                 {:this this :field field})))))

(def-method query ::temporal-query/result
  [this ::year
   query ::temporal-query/temporal-query]
  (condp = query
    (temporal-queries/chronology)
    iso-chronology/INSTANCE

    (temporal-queries/precision)
    chrono-unit/YEARS

    (temporal-accessor-defaults/-query this query)))

(extend-type Year
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::year
   temporal ::temporal/temporal]
  (if-not (= (chronology/from temporal) iso-chronology/INSTANCE)
    (throw (ex DateTimeException "Adjustment only supported on ISO date-time"
               {:this this :temporal temporal}))
    (temporal/with temporal chrono-field/YEAR (:year this))))

(extend-type Year
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor now ::year
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (-> clock-or-zone-id clock-impl/system now)
     clock/IClock (-> clock-or-zone-id local-date-impl/now local-date/get-year of))))

(def-constructor of ::year
  [iso-year ::j/int]
  (chrono-field/check-valid-value chrono-field/YEAR iso-year)
  (->Year iso-year))

(def-constructor from ::year
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? year/IYear temporal)
    temporal
    (do
      (asserts/require-non-nil temporal "temporal")
      (try*
       (let [temporal (if-not (= iso-chronology/INSTANCE
                                 (chronology/from temporal))
                        (local-date-impl/from temporal)
                        temporal)]
         (of (temporal-accessor/get temporal chrono-field/YEAR)))
       (catch DateTimeException e
         (throw (ex DateTimeException
                    (str "Unable to obtain Year from TemporalAccessor: "
                         temporal " of type " (pr-str temporal))
                    {:temporal temporal}
                    e)))))))

(s/def ::string string?)

(def-constructor parse ::year
  ([text ::string]
   (of (math/parse-long text)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L290
  ;; ([text ::j/char-sequence
  ;;   formatter ::date-time-formatter/date-time-formatter]
  ;;  (wip ::parse))
  )

(def-method to-string string?
  [{:keys [year]} ::year]
  (str year))

(extend-type Year
  string/IString
  (to-string [this] (to-string this)))
