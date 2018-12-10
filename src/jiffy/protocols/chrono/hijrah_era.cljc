(ns jiffy.protocols.chrono.hijrah-era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IHijrahEra)

(s/def ::hijrah-era #(satisfies? IHijrahEra %))
