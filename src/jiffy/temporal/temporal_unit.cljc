(ns jiffy.temporal.temporal-unit
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalUnit.java
(defprotocol ITemporalUnit
  (getDuration [this])
  (isDurationEstimated [this])
  (isDateBased [this])
  (isTimeBased [this])
  (isSupportedBy [this temporal])
  (addTo [this temporal amount])
  (between [this temporal1inclusive temporal2exclusive]))

(s/def ::temporal-unit #(satisfies? ITemporalUnit %))
