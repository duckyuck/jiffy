(ns jiffy.protocols.chrono.era
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Era.java
(defprotocol IEra
  (get-value [this])
  (get-display-name [this style locale]))

(s/def ::era #(satisfies? IEra %))