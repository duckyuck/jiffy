(ns jiffy.month
  (:refer-clojure :exclude [range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.chrono.chronology :as chronology]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.protocols.format.text-style :as text-style]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.month :as month]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.local-date-impl-impl :as local-date-impl-impl]))

(def-record Month ::month
  [ordinal ::j/long
   enum-name string?])

(def month? (partial instance? Month))

(def-constructor create ::month
  [ordinal ::j/long
   enum-name string?]
  (->Month ordinal enum-name))

(defenum create
  [JANUARY []
   FEBRUARY []
   MARCH []
   APRIL []
   MAY []
   JUNE []
   JULY []
   AUGUST []
   SEPTEMBER []
   OCTOBER []
   NOVEMBER []
   DECEMBER []])

(def-constructor values (s/coll-of (partial satisfies? month/IMonth))
  []
  (sort-by :ordinal (vals @enums)))

(defmacro args [& x] `(s/tuple ::month ~@x))

(def-method get-value ::j/int
  [this ::month]
  (inc (:ordinal this)))

(def-method get-display-name string?
  [this ::month
   style ::text-style/text-style
   locale ::j/locale]
  (wip ::-get-display-name))

(def-method plus ::month
  [this ::month
   months ::j/long]
  (nth (values)
       (rem (+ (:ordinal this)
               (int (rem months 12))
               12)
            12)))

(def-method minus ::month
  [this ::month
   months ::j/long]
  (plus this (- (rem months 12))))

(def-method length ::j/int
  [this ::month
   leap-year ::j/boolean]
  (cond
    (= this FEBRUARY) (if leap-year 29 28)
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))


(def-method min-length ::j/int
  [this ::month]
  (cond
    (= this FEBRUARY) 28
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))

(def-method max-length ::j/int
  [this ::month]
  (cond
    (= this FEBRUARY) 29
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))

(def-method first-day-of-year ::j/int
  [this ::month
   leap-year boolean?]
  (let [leap (if leap-year 1 0)]
    (condp = this
      JANUARY 1
      FEBRUARY 32
      MARCH (+ 60 leap)
      APRIL (+ 91 leap)
      MAY (+ 121 leap)
      JUNE (+ 152 leap)
      JULY (+ 182 leap)
      AUGUST (+ 213 leap)
      SEPTEMBER (+ 244 leap)
      OCTOBER (+ 274 leap)
      NOVEMBER (+ 305 leap)
      (+ 335 leap))))

(def-method first-month-of-quarter ::month
  [this ::month]
  (nth (values) (* (long (/ (:ordinal this) 3)) 3)))

(extend-type Month
  month/IMonth
  (get-value [this] (get-value this))
  (get-display-name [this style locale] (get-display-name this style locale))
  (plus [this months] (plus this months))
  (minus [this months] (minus this months))
  (length [this leap-year] (length this leap-year))
  (min-length [this] (min-length this))
  (max-length [this] (max-length this))
  (first-day-of-year [this leap-year] (first-day-of-year this leap-year))
  (first-month-of-quarter [this] (first-month-of-quarter this)))

(def-method is-supported ::j/boolean
  [this ::month
   field ::temporal-field/temporal-field]

  (if (chrono-field/chrono-field? field)
    (= field chrono-field/MONTH_OF_YEAR)
    (and field (temporal-field/is-supported-by field this))))

(def-method range ::value-range/value-range
  [this ::month
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/MONTH_OF_YEAR)
    (temporal-field/range field)
    (temporal-accessor-defaults/-range this field)))

(def-method get ::j/int
  [this ::month
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/MONTH_OF_YEAR)
    (get-value this)
    (temporal-accessor-defaults/-get this field)))

(def-method get-long ::j/long
  [this ::month
   field ::temporal-field/temporal-field]
  (cond
    (= field chrono-field/MONTH_OF_YEAR)
    (get-value this)

    (chrono-field/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
               {:month this :field field}))

    :else
    (temporal-field/get-from field this)))

(def-method query ::temporal-query/result
  [this ::month
   query ::temporal-query/temporal-query]

  (condp = query
    (temporal-queries/chronology)
    iso-chronology/INSTANCE

    (temporal-queries/precision)
    chrono-unit/MONTHS

    (temporal-accessor-defaults/-query this query)))

(extend-type Month
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::month
   temporal ::temporal/temporal]
  (if-not (= (chronology/from temporal) iso-chronology/INSTANCE)
    (throw (ex DateTimeException "Adjustment only supported on ISO date-time"))
    (temporal/with temporal chrono-field/MONTH_OF_YEAR (get-value this))))

(extend-type Month
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor value-of ::month
  [enum-name string?]
  (or (@enums enum-name)
      (throw (ex JavaIllegalArgumentException (str "no enum constant " (symbol (str *ns*) (str enum-name)))))))

(def-constructor of ::month
  [month ::j/month-of-year]
  (when (not (<= 1 month 12))
    (throw (ex DateTimeException (str "Invalid value for MonthOfYear: " month))))
  (nth (values) (dec month)))

(def-constructor from ::month
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? month/IMonth temporal)
    temporal
    (try*
     (let [temporal (if (not= iso-chronology/INSTANCE (chronology/from temporal))
                      (local-date-impl-impl/from temporal)
                      temporal)]
       (of (temporal-accessor/get temporal chrono-field/MONTH_OF_YEAR)))
     (catch DateTimeException e
       (throw (ex DateTimeException (str "Unable to obtain Month from TemporalAccessor: " temporal " of type " (type temporal))
                  {:temporal temporal}))))))
