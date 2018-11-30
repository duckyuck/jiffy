(ns jiffy.chrono.chrono-local-date
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java
(defprotocol IChronoLocalDate
  (get-chronology [this])
  (get-era [this])
  (is-leap-year [this])
  (length-of-month [this])
  (length-of-year [this])
  (to-epoch-day [this])
  (until [this end-date-exclusive])
  (format [this formatter])
  (at-time [this local-time])
  (is-after [this other])
  (is-before [this other])
  (is-equal [this other]))

(s/def ::chrono-local-date #(satisfies? IChronoLocalDate %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L259
(defn time-line-order [] (wip ::time-line-order))
(s/fdef time-line-order :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L287
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-local-date)
