(ns jiffy.protocols.chrono.japanese-era
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java
(defprotocol IJapaneseEra
  (get-private-era [this])
  (get-abbreviation [this])
  (get-name [this]))

(s/def ::japanese-era #(satisfies? IJapaneseEra %))