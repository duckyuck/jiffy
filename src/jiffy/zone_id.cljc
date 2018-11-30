(ns jiffy.zone-id
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java
(defprotocol IZoneId
  (normalized [this])
  (get-id [this])
  (get-display-name [this style locale])
  (get-rules [this]))

(s/def ::zone-id #(satisfies? IZoneId %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L271
(defn system-default [] (wip ::system-default))
(s/fdef system-default :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L287
(defn get-available-zone-ids [] (wip ::get-available-zone-ids))
(s/fdef get-available-zone-ids :ret ::j/wip)

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
(defn of-offset [prefix offset] (wip ::of-offset))
(s/fdef of-offset :args ::of-offset-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L459
(s/def ::from-args (s/tuple ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L224
(def SHORT_IDS ::SHORT_IDS--not-implemented)
