(ns jiffy.temporal.temporal-accessor-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.exception :refer [UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L169
(s/def ::range-args ::j/wip)
(defn -range [this field]
  (if (satisfies? ChronoField/IChronoField field)
    (if (TemporalAccessor/isSupported this field)
      (TemporalField/range field)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:field field})))
    (TemporalField/rangeRefinedBy field this)))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L217
(s/def ::get-args ::j/wip)
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L308
(s/def ::query-args ::j/wip)
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)
