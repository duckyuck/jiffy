(ns jiffy.temporal.temporal-unit
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalUnit.java
(defprotocol ITemporalUnit
  (get-duration [this])
  (is-duration-estimated [this])
  (is-date-based [this])
  (is-time-based [this])
  (is-supported-by [this temporal])
  (add-to [this temporal amount])
  (between [this temporal-1-inclusive temporal-2-exclusive]))

(s/def ::temporal-unit #(satisfies? ITemporalUnit %))
