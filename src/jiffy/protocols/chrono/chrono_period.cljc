(ns jiffy.protocols.chrono.chrono-period
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java
(defprotocol IChronoPeriod
  (get-chronology [this])
  (is-zero [this])
  (is-negative [this])
  (plus [this amount-to-add])
  (minus [this amount-to-subtract])
  (multiplied-by [this scalar])
  (negated [this])
  (normalized [this]))

(s/def ::chrono-period #(satisfies? IChronoPeriod %))