(ns jiffy.protocols.duration
  (:refer-clojure :exclude [abs])
  (:require [clojure.spec.alpha :as s]))

(defprotocol IDuration
  (is-zero [this])
  (is-negative [this])
  (get-seconds [this])
  (get-nano [this])
  (with-seconds [this seconds])
  (with-nanos [this nano-of-second])
  (plus [this duration] [this amount-to-add unit])
  (plus-days [this days-to-add])
  (plus-hours [this hours-to-add])
  (plus-minutes [this minutes-to-add])
  (plus-seconds [this seconds-to-add])
  (plus-millis [this millis-to-add])
  (plus-nanos [this nanos-to-add])
  (minus [this duration] [this amount-to-subtract unit])
  (minus-days [this days-to-subtract])
  (minus-hours [this hours-to-subtract])
  (minus-minutes [this minutes-to-subtract])
  (minus-seconds [this seconds-to-subtract])
  (minus-millis [this millis-to-subtract])
  (minus-nanos [this nanos-to-subtract])
  (multiplied-by [this multiplicand])
  (divided-by [this divided-by--overloaded-param])
  (negated [this])
  (abs [this])
  (to-days [this])
  (to-hours [this])
  (to-minutes [this])
  (to-seconds [this])
  (to-millis [this])
  (to-nanos [this])
  (to-days-part [this])
  (to-hours-part [this])
  (to-minutes-part [this])
  (to-seconds-part [this])
  (to-millis-part [this])
  (to-nanos-part [this])
  (truncated-to [this unit]))

(s/def ::duration #(satisfies? IDuration %))
