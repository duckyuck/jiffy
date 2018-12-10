(ns jiffy.protocols.chrono.thai-buddhist-chronology
  (:require [clojure.spec.alpha :as s]))

(defprotocol IThaiBuddhistChronology)

(s/def ::thai-buddhist-chronology #(satisfies? IThaiBuddhistChronology %))
