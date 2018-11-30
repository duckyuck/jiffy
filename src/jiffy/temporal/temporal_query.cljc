(ns jiffy.temporal.temporal-query
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQuery.java
(defprotocol ITemporalQuery
  (query-from [this temporal]))

(s/def ::temporal-query #(satisfies? ITemporalQuery %))
