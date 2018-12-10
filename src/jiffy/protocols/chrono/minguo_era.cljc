(ns jiffy.protocols.chrono.minguo-era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IMinguoEra)

(s/def ::minguo-era #(satisfies? IMinguoEra %))
