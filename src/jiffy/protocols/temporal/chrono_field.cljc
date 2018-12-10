(ns jiffy.protocols.temporal.chrono-field
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defprotocol IChronoField
  (check-valid-value [this value])
  (check-valid-int-value [this value]))

(s/def ::chrono-field #(satisfies? IChronoField %))