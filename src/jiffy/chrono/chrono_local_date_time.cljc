(ns jiffy.chrono.chrono-local-date-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java
(defprotocol IChronoLocalDateTime
  (getChronology [this])
  (toLocalDate [this])
  (toLocalTime [this])
  (format [this formatter])
  (atZone [this zone])
  (toInstant [this offset])
  (toEpochSecond [this offset])
  (isAfter [this other])
  (isBefore [this other])
  (isEqual [this other]))

(s/def ::chrono-local-date-time #(satisfies? IChronoLocalDateTime %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L139
(defn timeLineOrder [] (wip ::timeLineOrder))
(s/fdef timeLineOrder :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L171
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-local-date-time)
