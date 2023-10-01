(ns jiffy.protocols.temporal.temporal-adjuster
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalAdjuster
  (adjust-into [this temporal]))

(s/def ::temporal-adjuster #(satisfies? ITemporalAdjuster %))
