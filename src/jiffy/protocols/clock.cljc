(ns jiffy.protocols.clock
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java
(defprotocol IClock
  (get-zone [this])
  (with-zone [this zone])
  (millis [this])
  (instant [this]))

(s/def ::clock #(satisfies? IClock %))