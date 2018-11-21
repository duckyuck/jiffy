(ns jiffy.chrono.chrono-local-date
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java
(defprotocol IChronoLocalDate
  (getChronology [this])
  (getEra [this])
  (isLeapYear [this])
  (lengthOfMonth [this])
  (lengthOfYear [this])
  (toEpochDay [this])
  (until [this end-date-exclusive])
  (format [this formatter])
  (atTime [this local-time])
  (isAfter [this other])
  (isBefore [this other])
  (isEqual [this other]))

(s/def ::chrono-local-date #(satisfies? IChronoLocalDate %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L259
(defn timeLineOrder [] (wip ::timeLineOrder))
(s/fdef timeLineOrder :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L287
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-local-date)
