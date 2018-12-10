(ns jiffy.protocols.chrono.thai-buddhist-era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IThaiBuddhistEra)

(s/def ::thai-buddhist-era #(satisfies? IThaiBuddhistEra %))
