(ns jiffy.temporal.temporal-field-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as resolver-style]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java#L107
(s/def ::get-display-name-args ::j/wip)
(defn -get-display-name [this locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java#L374
(s/def ::resolve-args ::j/wip)
(defn -resolve [this field-values partial-temporal resolver-style] (wip ::-resolve))
(s/fdef -resolve :args ::resolve-args :ret ::temporal-accessor/temporal-accessor)
