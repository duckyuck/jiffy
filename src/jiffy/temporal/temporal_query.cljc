(ns jiffy.temporal.temporal-query
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalQuery
  (query-from [this temporal]))

(s/def ::temporal-query #(satisfies? ITemporalQuery %))
(s/def ::result any?)

(defrecord TemporalQuery [name query-from-fn]
  ITemporalQuery
  (query-from [this temporal] (query-from-fn temporal)))

(defmacro defquery [sym name & query-from]
  `(def ~sym (->TemporalQuery ~name (fn ~@query-from))))
