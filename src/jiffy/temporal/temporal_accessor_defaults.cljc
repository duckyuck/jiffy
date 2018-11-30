(ns jiffy.temporal.temporal-accessor-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.exception :refer [UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L169
(s/def ::range-args ::j/wip)
(defn -range [this field]
  (if (satisfies? chrono-field/IChronoField field)
    (if (temporal-accessor/is-supported this field)
      (temporal-field/range field)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:field field})))
    (temporal-field/range-refined-by field this)))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L217
(s/def ::get-args ::j/wip)
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L308
(s/def ::query-args ::j/wip)
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)
