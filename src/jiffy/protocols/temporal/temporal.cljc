(ns jiffy.protocols.temporal.temporal
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporal
  (with [this adjuster] [this field new-value])
  (plus
    [this amount]
    [this amount-to-add unit])
  (minus
    [this amount]
    [this amount-to-subtract unit])
  (until [this end-exclusive unit]))

(s/def ::temporal #(satisfies? ITemporal %))
