(ns jiffy.zone.zone-rules-provider
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java
(defprotocol IZoneRulesProvider
  (provideZoneIds [this])
  (provideRules [this zone-id for-caching])
  (provideVersions [this zone-id])
  (provideRefresh [this]))

(defrecord ZoneRulesProvider [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L376
(defn -provide-zone-ids [this] (wip ::-provide-zone-ids))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L404
(defn -provide-rules [this zone-id for-caching] (wip ::-provide-rules))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L429
(defn -provide-versions [this zone-id] (wip ::-provide-versions))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L444
(defn -provide-refresh [this] (wip ::-provide-refresh))

(extend-type ZoneRulesProvider
  IZoneRulesProvider
  (provideZoneIds [this] (-provide-zone-ids this))
  (provideRules [this zone-id for-caching] (-provide-rules this zone-id for-caching))
  (provideVersions [this zone-id] (-provide-versions this zone-id))
  (provideRefresh [this] (-provide-refresh this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L205
(defn getAvailableZoneIds [] (wip ::getAvailableZoneIds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L232
(defn getRules [zone-id for-caching] (wip ::getRules))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L261
(defn getVersions [zone-id] (wip ::getVersions))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L300
(defn registerProvider [provider] (wip ::registerProvider))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L349
(defn refresh [] (wip ::refresh))
