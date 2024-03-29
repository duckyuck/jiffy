(ns jiffy.protocols.local-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]))

(defprotocol ILocalTime
  (to-nano-of-day [this])
  (to-second-of-day [this])
  (get-hour [this])
  (get-minute [this])
  (get-second [this])
  (get-nano [this])
  (with-hour [this hour])
  (with-minute [this minute])
  (with-second [this second])
  (with-nano [this nano-of-second])
  (truncated-to [this unit])
  (plus-hours [this hours-to-add])
  (plus-minutes [this minutes-to-add])
  (plus-seconds [this secondsto-add])
  (plus-nanos [this nanos-to-add])
  (minus-hours [this hours-to-subtract])
  (minus-minutes [this minutes-to-subtract])
  (minus-seconds [this seconds-to-subtract])
  (minus-nanos [this nanos-to-subtract])
  (format [this formatter])
  (at-date [this date])
  (at-offset [this offset])
  (to-epoch-second [this date offset])
  (is-after [this other])
  (is-before [this other]))

(s/def ::local-time #(satisfies? ILocalTime %))
(s/def ::local-time-no-nano #(satisfies? ILocalTime %))
