(ns jiffy.zone-id
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java
(defprotocol IZoneId
  (normalized [this])
  (getId [this])
  (getDisplayName [this style locale])
  (getRules [this]))

(defrecord ZoneId [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L125
(defn -normalized [this] (wip ::-normalized))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L487
(defn -get-id [this] (wip ::-get-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L504
(defn -get-display-name [this style locale] (wip ::-get-display-name))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L562
(defn -get-rules [this] (wip ::-get-rules))

(extend-type ZoneId
  IZoneId
  (normalized [this] (-normalized this))
  (getId [this] (-get-id this))
  (getDisplayName [this style locale] (-get-display-name this style locale))
  (getRules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L271
(defn systemDefault [] (wip ::systemDefault))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L287
(defn getAvailableZoneIds [] (wip ::getAvailableZoneIds))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L355
  ([zone-id] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L308
  ([of--overloaded-param-1 of--overloaded-param-2] (wip ::of)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L372
(defn ofOffset [prefix offset] (wip ::ofOffset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L459
(defn from [temporal] (wip ::from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L224
(def SHORT_IDS ::SHORT_IDS--not-implemented)