(ns jiffy.protocols.zone-id
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java
(defprotocol IZoneId
  (normalized [this])
  (get-id [this])
  (get-display-name [this style locale])
  (get-rules [this]))

(s/def ::zone-id #(satisfies? IZoneId %))