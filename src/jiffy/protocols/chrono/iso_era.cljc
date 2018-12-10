(ns jiffy.protocols.chrono.iso-era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IIsoEra)

(s/def ::iso-era #(satisfies? IIsoEra %))
