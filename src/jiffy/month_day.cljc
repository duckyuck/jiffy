(ns jiffy.month-day
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.month-day :as month-day]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.value-range :as value-range-impl]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.month :as month]
            [jiffy.asserts :as asserts]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.year :as year]
            [jiffy.local-date-impl-impl :as local-date-impl-impl]
            [jiffy.local-date :as local-date-impl]
            [jiffy.math :as math]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.clock :as clock-impl]))

(def-record MonthDay ::month-day-record
  [month ::j/month-of-year
   day ::j/day-of-month])

(defn valid? [{:keys [month day]}]
  (asserts/require-non-nil month "month")
  (chrono-field/check-valid-value chrono-field/DAY_OF_MONTH day)
  (<= day (month/max-length (month/of month))))

(s/def ::month-day (s/and ::month-day-record valid?))

(def-method get-month ::month/month
  [this ::month-day]
  (month/of (:month this)))

(def-method get-month-value ::j/int
  [this ::month-day]
  (:month this))

(def-method get-day-of-month ::j/int
  [this ::month-day]
  (:day this))

(def-method is-valid-year ::j/boolean
  [this ::month-day
   year ::j/int]
  (not
   (and
    (= (:day this) 29)
    (= (:month this) 2)
    (not (year/is-leap year)))))

(def-method with ::month-day
  [this ::month-day
   month ::month/month]
  (asserts/require-non-nil month "month")
  (if (= (month/get-value month) (:month this))
    this
    (->MonthDay (month/get-value month)
                (min (:day this) (month/max-length month)))))

(def-method with-month ::month-day
  [this ::month-day
   month ::j/int]
  (with this (month/of month)))

(declare of)
(def-method with-day-of-month ::month-day
  [this ::month-day
   day-of-month ::j/int]
  (if (= day-of-month (:day this))
    this
    (of (:month this) day-of-month)))

(def-method format string?
  [this ::month-day
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::format))

(def-method at-year ::local-date/local-date
  [this ::month-day
   year ::j/int]
  (local-date-impl/of
   year
   (:month this)
   (if (is-valid-year this year)
     (:day this)
     28)))

(def-method compare-to ::j/int
  [this ::month-day
   other ::month-day]
  (let [cmp (math/subtract-exact (:month this) (:month other))]
    (if (zero? cmp)
      (math/subtract-exact (:day this) (:day other))
      cmp)))

(extend-type MonthDay
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(def-method is-after ::j/boolean
  [this ::month-day
   other ::month-day]
  (pos? (compare-to this other)))

(def-method is-before ::j/boolean
  [this ::month-day
   other ::month-day]
  (neg? (compare-to this other)))

(extend-type MonthDay
  month-day/IMonthDay
  (get-month [this] (get-month this))
  (get-month-value [this] (get-month-value this))
  (get-day-of-month [this] (get-day-of-month this))
  (is-valid-year [this year] (is-valid-year this year))
  (with-month [this month] (with-month this month))
  (with [this month] (with this month))
  (with-day-of-month [this day-of-month] (with-day-of-month this day-of-month))
  (format [this formatter] (format this formatter))
  (at-year [this year] (at-year this year))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other)))

(def-method is-supported ::j/boolean
  [this ::month-day
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (or (= field chrono-field/MONTH_OF_YEAR)
        (= field chrono-field/DAY_OF_MONTH))
    (and field (temporal-field/is-supported-by field this))))

(def-method range ::value-range/value-range
  [this ::month-day
   field ::temporal-field/temporal-field]
  (condp = field
    chrono-field/MONTH_OF_YEAR
    (temporal-field/range field)

    chrono-field/DAY_OF_MONTH
    (value-range-impl/of 1
                         (month/min-length (get-month this))
                         (month/max-length (get-month this)))

    (temporal-accessor-defaults/-range this field)))

(def-method get-long ::j/long
  [this ::month-day
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (condp = field
      chrono-field/DAY_OF_MONTH
      (:day this)

      chrono-field/MONTH_OF_YEAR
      (:month this)

      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
                 {:this this :field field})))))

(def-method get ::j/int
  [this ::month-day
   field ::temporal-field/temporal-field]
  (-> this
      (range field)
      (value-range/check-valid-int-value (get-long this field) field)))

(def-method query ::j/wip
  [this ::month-day
   query ::temporal-query/temporal-query]
  (if (= query (temporal-queries/chronology))
    iso-chronology/INSTANCE
    (temporal-accessor-defaults/-query this query)))

(extend-type MonthDay
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::month-day
   temporal ::temporal/temporal]
  (if-not (= (chronology/from temporal) iso-chronology/INSTANCE)
    (throw (ex DateTimeException "Adjustment only supported on ISO date-time"
               {:this this :temporal temporal}))
    (let [temporal (temporal/with temporal chrono-field/MONTH_OF_YEAR (:month this))]
      (temporal/with temporal
                     chrono-field/DAY_OF_MONTH
                     (min (-> temporal
                              (temporal-accessor/range chrono-field/DAY_OF_MONTH)
                              (value-range/get-maximum))
                          (:day this))))))

(extend-type MonthDay
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor now ::month-day
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (now (clock-impl/system clock-or-zone-id))
     clock/IClock
     (let [local-date (local-date-impl/now clock-or-zone-id)]
       (of (local-date/get-month local-date)
           (local-date/get-day-of-month local-date))))))

(def-constructor of ::month-day
  [month (s/or ::month
               ::j/int)
   day-of-month ::j/int]
  (if (number? month)
    (of (month/of month) day-of-month)
    (do
      (asserts/require-non-nil month "month")
      (chrono-field/check-valid-value chrono-field/DAY_OF_MONTH day-of-month)
      (let [month-day (->MonthDay (month/get-value month) day-of-month)]
        (if (valid? month-day)
          month-day
          (throw (ex DateTimeException (str "Illegal value for DayOfMonth field, value "
                                            day-of-month " is not valid for month "
                                            (:enum-name month)))))))))

(def-constructor from ::month-day
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? month-day/IMonthDay temporal)
    temporal
    (try*
     (let [temporal (if-not (= iso-chronology/INSTANCE
                               (chronology/from temporal))
                      (local-date-impl/from temporal)
                      temporal)]
       (of (temporal-accessor/get temporal chrono-field/MONTH_OF_YEAR)
           (temporal-accessor/get temporal chrono-field/DAY_OF_MONTH)))
     (catch DateTimeException e
       (throw (ex DateTimeException
                  (str "Unable to obtain MonthDay from TemporalAccessor: "
                       temporal " of type " (pr-str temporal))
                  {:temporal temporal}
                  e))))))

(def-constructor parse ::month-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L293
  ([text ::j/char-sequence]
   (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L307
  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))
