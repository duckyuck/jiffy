(ns jiffy.zone.zone-rules-provider
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.protocols.zone.zone-rules-provider :as zone-rules-provider]
            [jiffy.specs :as j]))

(defrecord ZoneRulesProvider [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-rules-provider (j/constructor-spec ZoneRulesProvider create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-rules-provider)

(defmacro args [& x] `(s/tuple ::zone-rules-provider ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L376
(s/def ::provide-zone-ids-args (args))
(defn -provide-zone-ids [this] (wip ::-provide-zone-ids))
(s/fdef -provide-zone-ids :args ::provide-zone-ids-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L404
(s/def ::provide-rules-args (args string? ::j/boolean))
(defn -provide-rules [this zone-id for-caching] (wip ::-provide-rules))
(s/fdef -provide-rules :args ::provide-rules-args :ret ::zone-rules/zone-rules)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L429
(s/def ::provide-versions-args (args string?))
(defn -provide-versions [this zone-id] (wip ::-provide-versions))
(s/fdef -provide-versions :args ::provide-versions-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L444
(s/def ::provide-refresh-args (args))
(defn -provide-refresh [this] (wip ::-provide-refresh))
(s/fdef -provide-refresh :args ::provide-refresh-args :ret ::j/boolean)

(extend-type ZoneRulesProvider
  zone-rules-provider/IZoneRulesProvider
  (provide-zone-ids [this] (-provide-zone-ids this))
  (provide-rules [this zone-id for-caching] (-provide-rules this zone-id for-caching))
  (provide-versions [this zone-id] (-provide-versions this zone-id))
  (provide-refresh [this] (-provide-refresh this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L205
(defn get-available-zone-ids [] (wip ::get-available-zone-ids))
(s/fdef get-available-zone-ids :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L232
(s/def ::get-rules-args (args string? ::j/boolean))
(defn get-rules [zone-id for-caching] (wip ::get-rules))
(s/fdef get-rules :args ::get-rules-args :ret ::zone-rules/zone-rules)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L261
(s/def ::get-versions-args (args string?))
(defn get-versions [zone-id] (wip ::get-versions))
(s/fdef get-versions :args ::get-versions-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L300
(s/def ::register-provider-args (args ::zone-rules-provider))
(defn register-provider [provider] (wip ::register-provider))
(s/fdef register-provider :args ::register-provider-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRulesProvider.java#L349
(defn refresh [] (wip ::refresh))
(s/fdef refresh :ret ::j/boolean)
