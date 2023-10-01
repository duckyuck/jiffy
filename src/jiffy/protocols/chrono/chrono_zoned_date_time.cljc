(ns jiffy.protocols.chrono.chrono-zoned-date-time
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoZonedDateTime
  (to-epoch-second [this])
  (to-local-date [this])
  (to-local-time [this])
  (to-local-date-time [this])
  (get-chronology [this])
  (get-offset [this])
  (get-zone [this])
  (with-earlier-offset-at-overlap [this])
  (with-later-offset-at-overlap [this])
  (with-zone-same-local [this zone])
  (with-zone-same-instant [this zone])
  (format [this formatter])
  (to-instant [this])
  (is-before [this other])
  (is-after [this other])
  (is-equal [this other]))

(s/def ::chrono-zoned-date-time #(satisfies? IChronoZonedDateTime %))
