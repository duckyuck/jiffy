(ns jiffy.temporal.temporal-field-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.format.resolver-style :as resolver-style]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]))

(s/def ::temporal-field ::temporal-field/temporal-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java#L107
(s/def ::get-display-name-args ::j/wip)
(defn -get-display-name [this locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java#L374
(s/def ::resolve-args ::j/wip)
(defn -resolve [this field-values partial-temporal resolver-style] (wip ::-resolve))
(s/fdef -resolve :args ::resolve-args :ret ::temporal-accessor/temporal-accessor)
