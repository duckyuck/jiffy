(ns jiffy.zone-region
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone.zone-rules :as ZoneRules]))

(defrecord ZoneRegion [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-region (j/constructor-spec ZoneRegion create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-region)

(defmacro args [& x] `(s/tuple ::zone-region ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L169
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L174
(s/def ::get-rules-args (args))
(defn -get-rules [this] (wip ::-get-rules))
(s/fdef -get-rules :args ::get-rules-args :ret ::ZoneRules/zone-rules)

(extend-type ZoneRegion
  ZoneId/IZoneId
  (getId [this] (-get-id this))
  (getRules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L114
(s/def ::of-id-args (args string? ::j/boolean))
(defn ofId [zone-id check-available] (wip ::ofId))
(s/fdef ofId :args ::of-id-args :ret ::zone-region)
