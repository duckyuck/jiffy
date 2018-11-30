(ns jiffy.zone.tzdb-zone-rules-provider
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.zone.zone-rules :as zone-rules]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]))

(defrecord TzdbZoneRulesProvider [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::tzdb-zone-rules-provider (j/constructor-spec TzdbZoneRulesProvider create ::create-args))
(s/fdef create :args ::create-args :ret ::tzdb-zone-rules-provider)

(defmacro args [& x] `(s/tuple ::tzdb-zone-rules-provider ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L121
(s/def ::provide-zone-ids-args (args))
(defn -provide-zone-ids [this] (wip ::-provide-zone-ids))
(s/fdef -provide-zone-ids :args ::provide-zone-ids-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L126
(s/def ::provide-rules-args (args string? ::j/boolean))
(defn -provide-rules [this zone-id for-caching] (wip ::-provide-rules))
(s/fdef -provide-rules :args ::provide-rules-args :ret ::zone-rules/zone-rules)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/TzdbZoneRulesProvider.java#L146
(s/def ::provide-versions-args (args string?))
(defn -provide-versions [this zone-id] (wip ::-provide-versions))
(s/fdef -provide-versions :args ::provide-versions-args :ret ::j/wip)

(extend-type TzdbZoneRulesProvider
  zone-rules-provider/IZoneRulesProvider
  (provide-zone-ids [this] (-provide-zone-ids this))
  (provide-rules [this zone-id for-caching] (-provide-rules this zone-id for-caching))
  (provide-versions [this zone-id] (-provide-versions this zone-id)))

