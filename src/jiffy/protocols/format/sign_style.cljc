(ns jiffy.protocols.format.sign-style
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defprotocol ISignStyle
  (parse [this positive strict fixed-width]))

(s/def ::sign-style #(satisfies? ISignStyle %))