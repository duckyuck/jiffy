(ns jiffy.protocols.zone.zone-rules-provider
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java
(defprotocol IZoneRulesProvider
  (provide-zone-ids [this])
  (provide-rules [this zone-id for-caching])
  (provide-versions [this zone-id])
  (provide-refresh [this]))

(s/def ::zone-rules-provider #(satisfies? IZoneRulesProvider %))