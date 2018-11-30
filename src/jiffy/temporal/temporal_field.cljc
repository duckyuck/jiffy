(ns jiffy.temporal.temporal-field
  (:refer-clojure :exclude [range resolve])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java
(defprotocol ITemporalField
  (get-display-name [this locale])
  (get-base-unit [this])
  (get-range-unit [this])
  (range [this])
  (is-date-based [this])
  (is-time-based [this])
  (is-supported-by [this temporal])
  (range-refined-by [this temporal])
  (get-from [this temporal])
  (adjust-into [this temporal new-value])
  (resolve [this field-values partial-temporal resolver-style]))

(s/def ::temporal-field #(satisfies? ITemporalField %))
