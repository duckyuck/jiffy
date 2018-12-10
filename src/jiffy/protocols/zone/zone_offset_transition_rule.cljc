(ns jiffy.protocols.zone.zone-offset-transition-rule
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java
(defprotocol IZoneOffsetTransitionRule
  (get-month [this])
  (get-day-of-month-indicator [this])
  (get-day-of-week [this])
  (get-local-time [this])
  (is-midnight-end-of-day [this])
  (get-time-definition [this])
  (get-standard-offset [this])
  (get-offset-before [this])
  (get-offset-after [this])
  (create-transition [this year]))

(s/def ::zone-offset-transition-rule #(satisfies? IZoneOffsetTransitionRule %))