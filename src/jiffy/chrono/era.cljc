(ns jiffy.chrono.era
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as TextStyle]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Era.java
(defprotocol IEra
  (getValue [this])
  (getDisplayName [this style locale]))

(s/def ::era #(satisfies? IEra %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Era.java#L320
(s/def ::get-display-name-args ::j/wip)
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; TODO: port default methods from interface and delegate in IEra records
