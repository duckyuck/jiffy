(ns jiffy.protocols.temporal.temporal-adjusters
  (:refer-clojure :exclude [next ])
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalAdjusters)

(s/def ::temporal-adjusters #(satisfies? ITemporalAdjusters %))
