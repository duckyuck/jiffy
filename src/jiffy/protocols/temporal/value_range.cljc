(ns jiffy.protocols.temporal.value-range
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java
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