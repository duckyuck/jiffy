(ns jiffy.zone-id
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java
(defprotocol IZoneId
  (normalized [this])
  (getId [this])
  (getDisplayName [this style locale])
  (getRules [this]))

(s/def ::zone-id #(satisfies? IZoneId %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L271
(defn systemDefault [] (wip ::systemDefault))
(s/fdef systemDefault :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L287
(defn getAvailableZoneIds [] (wip ::getAvailableZoneIds))
(s/fdef getAvailableZoneIds :ret ::j/wip)

(s/def ::of-args (s/tuple ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L355
  ([zone-id] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L308
  ([of--overloaded-param-1 of--overloaded-param-2] (wip ::of)))
(s/fdef of :args ::of-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L372
(s/def ::of-offset-args (s/tuple string? :ijffy.zone-offset/zone-offset))
(defn ofOffset [prefix offset] (wip ::ofOffset))
(s/fdef ofOffset :args ::of-offset-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L459
(s/def ::from-args (s/tuple ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L224
(def SHORT_IDS ::SHORT_IDS--not-implemented)
