(ns jiffy.protocols.chrono.chrono-local-date
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

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
