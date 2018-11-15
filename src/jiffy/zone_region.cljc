(ns jiffy.zone-region
  (:require [jiffy.dev.wip :refer [wip]] [jiffy.zone-id :as ZoneId]))

(defrecord ZoneRegion [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L169
(defn -get-id [this] (wip ::-get-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L174
(defn -get-rules [this] (wip ::-get-rules))

(extend-type ZoneRegion
  ZoneId/IZoneId
  (getId [this] (-get-id this))
  (getRules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L114
(defn ofId [zone-id check-available] (wip ::ofId))
