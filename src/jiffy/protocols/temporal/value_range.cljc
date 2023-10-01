(ns jiffy.protocols.temporal.value-range
  (:require [clojure.spec.alpha :as s]))

(defprotocol IValueRange
  (is-fixed [this])
  (get-minimum [this])
  (get-largest-minimum [this])
  (get-smallest-maximum [this])
  (get-maximum [this])
  (is-int-value [this])
  (is-valid-value [this value])
  (is-valid-int-value [this value])
  (check-valid-value [this value field])
  (check-valid-int-value [this value field]))

(s/def ::value-range #(satisfies? IValueRange %))
