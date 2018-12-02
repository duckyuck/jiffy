(ns jiffy.temporal.temporal-amount
  (:refer-clojure :exclude [get])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAmount.java
(defprotocol ITemporalAmount
  (get [this unit])
  (get-units [this])
  (add-to [this temporal])
  (subtract-from [this temporal]))

(s/def ::temporal-amount #(satisfies? ITemporalAmount %))
