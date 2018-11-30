(ns jiffy.chrono.chrono-local-date-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java
(defprotocol IChronoLocalDateTime
  (get-chronology [this])
  (to-local-date [this])
  (to-local-time [this])
  (format [this formatter])
  (at-zone [this zone])
  (to-instant [this offset])
  (to-epoch-second [this offset])
  (is-after [this other])
  (is-before [this other])
  (is-equal [this other]))

(s/def ::chrono-local-date-time #(satisfies? IChronoLocalDateTime %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L139
(defn time-line-order [] (wip ::time-line-order))
(s/fdef time-line-order :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L171
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-local-date-time)
