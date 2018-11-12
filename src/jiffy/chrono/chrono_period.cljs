(ns jiffy.chrono.chrono-period
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-amount :as TemporalAmount]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java
(defprotocol IChronoPeriod
  (getChronology [this])
  (isZero [this])
  (isNegative [this])
  (plus [this amount-to-add])
  (minus [this amount-to-subtract])
  (multipliedBy [this scalar])
  (negated [this])
  (normalized [this]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L172
(defn -is-zero [this] (wip ::-is-zero))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L186
(defn -is-negative [this] (wip ::-is-negative))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L255
(defn -negated [this] (wip ::-negated))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L116
(defn between [start-date-inclusive end-date-exclusive] (wip ::between))
