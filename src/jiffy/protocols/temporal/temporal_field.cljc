(ns jiffy.protocols.temporal.temporal-field
  (:refer-clojure :exclude [range resolve])
  (:require [clojure.spec.alpha :as s]))

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
