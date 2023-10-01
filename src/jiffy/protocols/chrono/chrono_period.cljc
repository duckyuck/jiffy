(ns jiffy.protocols.chrono.chrono-period
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoPeriod
  (get-chronology [this])
  (is-zero [this])
  (is-negative [this])
  (plus [this amount-to-add])
  (minus [this amount-to-subtract])
  (multiplied-by [this scalar])
  (negated [this])
  (normalized [this]))

(s/def ::chrono-period #(satisfies? IChronoPeriod %))
