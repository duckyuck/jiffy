(ns jiffy.zone.zone-rules-provider
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.protocols.zone.zone-rules-provider :as zone-rules-provider]
            [jiffy.specs :as j]
            [jiffy.zone.zone-rules-store :as store]))

(def-record ZoneRulesProvider ::zone-rules-provider
  [wip ::j/wip])

(def-constructor create ::zone-rules-provider
  []
  (ZoneRulesProvider. ::wip))

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
(def-constructor get-rules ::zone-rules/zone-rules
  [zone-id string?
   for-caching ::j/boolean]
  (get @store/zone-id->rules zone-id))

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
