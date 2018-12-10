(ns jiffy.protocols.zone-offset
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java
(defprotocol IZoneOffset
  (get-total-seconds [this]))

(s/def ::zone-offset #(satisfies? IZoneOffset %))