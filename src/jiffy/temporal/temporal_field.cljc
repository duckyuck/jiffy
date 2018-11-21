(ns jiffy.temporal.temporal-field
  (:refer-clojure :exclude [range resolve])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalField.java
(defprotocol ITemporalField
  (getDisplayName [this locale])
  (getBaseUnit [this])
  (getRangeUnit [this])
  (range [this])
  (isDateBased [this])
  (isTimeBased [this])
  (isSupportedBy [this temporal])
  (rangeRefinedBy [this temporal])
  (getFrom [this temporal])
  (adjustInto [this temporal new-value])
  (resolve [this field-values partial-temporal resolver-style]))

(s/def ::temporal-field #(satisfies? ITemporalField %))
