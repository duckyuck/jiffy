(ns jiffy.protocols.chrono.japanese-chronology
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseChronology.java
(defprotocol IJapaneseChronology
  (get-current-era [this]))

(s/def ::japanese-chronology #(satisfies? IJapaneseChronology %))