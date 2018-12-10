(ns jiffy.protocols.instant
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java
(defprotocol IInstant
  (get-epoch-second [this])
  (get-nano [this])
  (truncated-to [this unit])
  (plus-seconds [this seconds-to-add])
  (plus-millis [this millis-to-add])
  (plus-nanos [this nanos-to-add])
  (minus-seconds [this seconds-to-subtract])
  (minus-millis [this millis-to-subtract])
  (minus-nanos [this nanos-to-subtract])
  (to-epoch-milli [this])
  (at-offset [this offset])
  (at-zone [this zone])
  (is-after [this other-instant])
  (is-before [this other-instant]))

(s/def ::instant #(satisfies? IInstant %))