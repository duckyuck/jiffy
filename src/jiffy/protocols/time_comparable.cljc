(ns jiffy.protocols.time-comparable
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITimeComparable
  (compare-to [this o]))

(s/def ::time-comparable #(satisfies? ITimeComparable %))
