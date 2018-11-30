(ns jiffy.temporal.temporal-accessor
  (:refer-clojure :exclude [get range])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java
(defprotocol ITemporalAccessor
  (is-supported [this field])
  (range [this field])
  (get [this field])
  (get-long [this field])
  (query [this query]))

(s/def ::temporal-accessor #(satisfies? ITemporalAccessor %))
