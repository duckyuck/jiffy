(ns jiffy.protocols.zone.zone-offset-transition
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java
(defprotocol IZoneOffsetTransition
  (get-instant [this])
  (to-epoch-second [this])
  (get-date-time-before [this])
  (get-date-time-after [this])
  (get-offset-before [this])
  (get-offset-after [this])
  (get-duration [this])
  (is-gap [this])
  (is-overlap [this])
  (is-valid-offset [this offset])
  (get-valid-offsets [this]))

(s/def ::zone-offset-transition #(satisfies? IZoneOffsetTransition %))