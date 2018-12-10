(ns jiffy.temporal.temporal-accessor-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.protocols.temporal.value-range :as value-range]))

(defmacro args [& x] `(s/tuple ::temporal-accessor/temporal-accessor ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L169
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field]
  (if (satisfies? chrono-field/IChronoField field)
    (if (temporal-accessor/is-supported this field)
      (temporal-field/range field)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:field field})))
    (temporal-field/range-refined-by field this)))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L217
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field]
  (let [range (-range this field)]
    (when-not (value-range/is-int-value range)
      (throw (ex UnsupportedTemporalTypeException (str "Invalid field " field " for get method, use get-long instead"))))
    (let [value (temporal-accessor/get-long this field)]
      (if-not (value-range/is-valid-value range value)
        (throw (ex DateTimeException (str "Invalid value for " field " (valid values " range "): " value)))
        (int value)))))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L308
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query]
  (when-not (some #(= query %) [(temporal-queries/zone-id)
                                (temporal-queries/chronology)
                                (temporal-queries/precision)])
    (temporal-query/query-from query this)))
(s/fdef -query :args ::query-args :ret ::temporal-query/result)
