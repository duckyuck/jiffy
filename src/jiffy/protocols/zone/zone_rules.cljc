(ns jiffy.protocols.zone.zone-rules
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java
(defprotocol IZoneRules
  (is-fixed-offset [this])
  (get-offset [this get-offset--overloaded-param])
  (get-valid-offsets [this local-date-time])
  (get-transition [this local-date-time])
  (get-standard-offset [this instant])
  (get-daylight-savings [this instant])
  (is-daylight-savings [this instant])
  (is-valid-offset [this local-date-time offset])
  (next-transition [this instant])
  (previous-transition [this instant])
  (get-transitions [this])
  (get-transition-rules [this]))

(s/def ::zone-rules #(satisfies? IZoneRules %))