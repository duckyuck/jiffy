(ns jiffy.protocols.temporal.chrono-field
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoField
  (check-valid-value [this value])
  (check-valid-int-value [this value]))

(s/def ::chrono-field #(satisfies? IChronoField %))
