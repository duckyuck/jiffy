(ns jiffy.temporal.temporal-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L189
(s/def ::with-args ::j/wip)
(defn -with [this adjuster] (wip ::-with))
(s/fdef -with :args ::with-args :ret ::temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L261
(s/def ::plus-args ::j/wip)
(defn -plus [this amount] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::temporal)

(s/def ::minus-args ::j/wip)
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L333
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L369
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::temporal)
