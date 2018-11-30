(ns jiffy.temporal.temporal-unit-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as duration]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalUnit.java#L168
(s/def ::is-supported-by-args ::j/wip)
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))
(s/fdef -is-supported-by :args ::is-supported-by-args :ret ::j/boolean)
