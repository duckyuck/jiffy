(ns jiffy.day-of-week
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.conversion :refer [jiffy->java same?]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.enum #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.value-range :as value-range]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(defprotocol IDayOfWeek
  (get-value [this])
  (get-display-name [this style locale])
  (plus [this days])
  (minus [this days]))

(defrecord DayOfWeek [ordinal enum-name])

(s/def ::create-args (s/tuple ::j/long string?))
(def create ->DayOfWeek)
(s/def ::day-of-week (j/constructor-spec DayOfWeek create ::create-args))
(s/fdef create :args ::create-args :ret ::day-of-week)

(defenum create
  [MONDAY []
   TUESDAY []
   WEDNESDAY []
   THURSDAY []
   FRIDAY []
   SATURDAY []
   SUNDAY []])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(s/def ::values-args empty?)
(defn values [] (sort-by :ordinal (vals @enums)))
(s/fdef values :ret (s/coll-of #(satisfies? IDayOfWeek %)))

(defmacro args [& x] `(s/tuple ::day-of-week ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L98
(s/def ::get-value-args (args))
(defn -get-value [this] (inc (:ordinal this)))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L226
(s/def ::get-display-name-args (args ::text-style/text-style ::j/locale))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L365
(s/def ::plus-args (args ::j/long))
(defn -plus [this days]
  (nth (values)
       (rem (+ (:ordinal this)
               (int (rem days 7))
               7)
            7)))
(s/fdef -plus :args ::plus-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L381
(s/def ::minus-args (args ::j/long))
(defn -minus [this days]
  (-plus this (- (rem days 7))))
(s/fdef -minus :args ::minus-args :ret ::day-of-week)

(extend-type DayOfWeek
  IDayOfWeek
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale))
  (plus [this days] (-plus this days))
  (minus [this days] (-minus this days)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L251
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this field]
  (if (chrono-field/chrono-field? field)
    (= field chrono-field/DAY_OF_WEEK)
    (and field (temporal-field/is-supported-by field this))))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L281
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field]
  (if (= field chrono-field/DAY_OF_WEEK)
    (temporal-field/range field)
    (temporal-accessor-defaults/-range this field)))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L314
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field]
  (if (= field chrono-field/DAY_OF_WEEK)
    (get-value this)
    (temporal-accessor-defaults/-get this field)))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L344
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field]
  (cond
    (= field chrono-field/DAY_OF_WEEK)
    (get-value this)

    (chrono-field/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
               {:day-of-week this :field field}))

    :else
    (temporal-field/get-from field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L406
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query]
  (if (= query (temporal-queries/precision))
    chrono-unit/DAYS
    (temporal-accessor-defaults/-query this query)))
(s/fdef -query :args ::query-args :ret ::temporal-query/result)

(extend-type DayOfWeek
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L453
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal]
  (temporal/with temporal chrono-field/DAY_OF_WEEK (get-value this)))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type DayOfWeek
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(s/def ::value-of-args (s/tuple string?))
(defn value-of [enum-name]
  (or (@enums enum-name)
      (throw (ex JavaIllegalArgumentException (str "no enum constant " (symbol (str *ns*) (str enum-name)))))))
(s/fdef value-of :args ::value-of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L163
(s/def ::of-args (s/tuple ::j/int))
(defn of [day-of-week]
  (when (not (<= 1 day-of-week 7))
    (throw (ex DateTimeException (str "Invalid value for DayOfWeek: " day-of-week))))
  (nth (values) (dec day-of-week)))
(s/fdef of :args ::of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L187
(s/def ::from-args (s/tuple ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::day-of-week)

#?(:clj
   (defmethod jiffy->java DayOfWeek [day-of-week]
     (java.time.DayOfWeek/valueOf (:enum-name day-of-week))))

#?(:clj
   (defmethod same? DayOfWeek
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:ordinal
                                :enum-name])
        (map #(% java-object) [(memfn ordinal)
                               (memfn name)]))))
