(ns jiffy.zone.tzdb-zone-rules-provider
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.zone.zone-rules-provider :as ZoneRulesProvider]))

(defrecord TzdbZoneRulesProvider [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L121
(defn -provide-zone-ids [this] (wip ::-provide-zone-ids))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L126
(defn -provide-rules [this zone-id for-caching] (wip ::-provide-rules))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L146
(defn -provide-versions [this zone-id] (wip ::-provide-versions))

(extend-type TzdbZoneRulesProvider
  ZoneRulesProvider/IZoneRulesProvider
  (provideZoneIds [this] (-provide-zone-ids this))
  (provideRules [this zone-id for-caching] (-provide-rules this zone-id for-caching))
  (provideVersions [this zone-id] (-provide-versions this zone-id)))

