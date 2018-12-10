(ns jiffy.protocols.chrono.thai-buddhist-date
  (:require [clojure.spec.alpha :as s]))

(defprotocol IThaiBuddhistDate)

(s/def ::thai-buddhist-date #(satisfies? IThaiBuddhistDate %))
