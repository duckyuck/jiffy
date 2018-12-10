(ns jiffy.protocols.temporal.temporal-queries
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalQueries)

(s/def ::temporal-queries #(satisfies? ITemporalQueries %))
