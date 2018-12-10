(ns jiffy.chrono.chrono-period-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]))

(s/def ::chrono-period ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L172
(s/def ::is-zero-args ::j/wip)
(defn -is-zero [this] (wip ::-is-zero))
(s/fdef -is-zero :args ::is-zero-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L186
(s/def ::is-negative-args ::j/wip)
(defn -is-negative [this] (wip ::-is-negative))
(s/fdef -is-negative :args ::is-negative-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L255
(s/def ::negated-args ::j/wip)
(defn -negated [this] (wip ::-negated))
(s/fdef -negated :args ::negated-args :ret ::chrono-period)