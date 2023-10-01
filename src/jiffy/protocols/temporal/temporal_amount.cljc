(ns jiffy.protocols.temporal.temporal-amount
  (:refer-clojure :exclude [get ])
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalAmount
  (get [this unit])
  (get-units [this])
  (add-to [this temporal])
  (subtract-from [this temporal]))

(s/def ::temporal-amount #(satisfies? ITemporalAmount %))
