(ns jiffy.day-of-week
  (:refer-clojure :exclude [range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.text-style :as text-style]
            [jiffy.protocols.day-of-week :as day-of-week]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]))

(def-record DayOfWeek ::day-of-week
  [ordinal ::j/long
   enum-name string?])

(def day-of-week? (partial instance? DayOfWeek))

(def-constructor create ::day-of-week
  [ordinal ::j/long
   enum-name string?]
  (->DayOfWeek ordinal enum-name))

(defenum create
  [MONDAY []
   TUESDAY []
   WEDNESDAY []
   THURSDAY []
   FRIDAY []
   SATURDAY []
   SUNDAY []])

(def-constructor values (s/coll-of #(satisfies? day-of-week/IDayOfWeek %))
  []
  (sort-by :ordinal (vals @enums)))

(def-method get-value ::j/int
  [this ::day-of-week]
  (inc (:ordinal this)))

(def-method get-display-name string?
  [this ::day-of-week
   style ::text-style/text-style
   locale ::j/locale]
  (wip ::-get-display-name))

(def-method plus ::day-of-week
  [this ::day-of-week
   days ::j/long]
  (nth (values)
       (rem (+ (:ordinal this)
               (int (rem days 7))
               7)
            7)))

(def-method minus ::day-of-week
  [this ::day-of-week
   days ::j/long]
  (plus this (- (rem days 7))))

(extend-type DayOfWeek
  day-of-week/IDayOfWeek
  (get-value [this] (get-value this))
  (get-display-name [this style locale] (get-display-name this style locale))
  (plus [this days] (plus this days))
  (minus [this days] (minus this days)))

(def-method is-supported ::j/boolean
  [this ::day-of-week
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (= field chrono-field/DAY_OF_WEEK)
    (and field (temporal-field/is-supported-by field this))))

(def-method range ::value-range/value-range
  [this ::day-of-week
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/DAY_OF_WEEK)
    (temporal-field/range field)
    (temporal-accessor-defaults/-range this field)))

(def-method get ::j/int
  [this ::day-of-week
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/DAY_OF_WEEK)
    (get-value this)
    (temporal-accessor-defaults/-get this field)))

(def-method get-long ::j/long
  [this ::day-of-week
   field ::temporal-field/temporal-field]
  (cond
    (= field chrono-field/DAY_OF_WEEK)
    (get-value this)

    (chrono-field/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
               {:day-of-week this :field field}))

    :else
    (temporal-field/get-from field this)))

(def-method query ::temporal-query/result
  [this ::day-of-week
   query ::temporal-query/temporal-query]
  (if (= query (temporal-queries/precision))
    chrono-unit/DAYS
    (temporal-accessor-defaults/-query this query)))

(extend-type DayOfWeek
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::day-of-week
   temporal ::temporal/temporal]
  (temporal/with temporal chrono-field/DAY_OF_WEEK (get-value this)))

(extend-type DayOfWeek
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor value-of ::day-of-week
  [enum-name string?]
  (or (@enums enum-name)
      (throw (ex JavaIllegalArgumentException (str "no enum constant " (symbol (str *ns*) (str enum-name)))))))

(def-constructor of ::day-of-week
  [day-of-week ::j/int]
  (when (not (<= 1 day-of-week 7))
    (throw (ex DateTimeException (str "Invalid value for DayOfWeek: " day-of-week))))
  (nth (values) (dec day-of-week)))

(def-constructor from ::day-of-week
  [temporal ::temporal-accessor/temporal-accessor]
  (wip ::from))
