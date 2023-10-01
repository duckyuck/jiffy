(ns jiffy.protocols.chrono.chrono-local-date-time
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

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
