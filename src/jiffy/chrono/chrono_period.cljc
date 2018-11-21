(ns jiffy.chrono.chrono-period
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]))

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

(s/def ::chrono-period #(satisfies? IChronoPeriod %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L116
(s/def ::between-args (s/tuple ::ChronoLocalDate/chrono-local-date ::ChronoLocalDate/chrono-local-date))
(defn between [start-date-inclusive end-date-exclusive]
  (ChronoLocalDate/until start-date-inclusive end-date-exclusive))
(s/fdef between :args ::between-args :ret ::chrono-period)
