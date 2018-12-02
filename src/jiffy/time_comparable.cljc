(ns jiffy.time-comparable
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/lang/Comparable.java
(defprotocol ITimeComparable
  (compare-to [this o]))
