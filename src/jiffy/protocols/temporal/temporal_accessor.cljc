(ns jiffy.protocols.temporal.temporal-accessor
  (:refer-clojure :exclude [get range])
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalAccessor
  (is-supported [this field])
  (range [this field])
  (get [this field])
  (get-long [this field])
  (query [this query]))

(s/def ::temporal-accessor #(satisfies? ITemporalAccessor %))
