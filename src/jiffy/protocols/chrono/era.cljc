(ns jiffy.protocols.chrono.era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IEra
  (get-value [this])
  (get-display-name [this style locale]))

(s/def ::era #(satisfies? IEra %))
