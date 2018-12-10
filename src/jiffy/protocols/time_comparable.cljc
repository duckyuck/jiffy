(ns jiffy.protocols.time-comparable
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/lang/Comparable.java
(defprotocol ITimeComparable
  (compare-to [this o]))

(s/def ::time-comparable #(satisfies? ITimeComparable %))