(ns jiffy.protocols.format.text-style
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defprotocol ITextStyle
  (is-standalone [this])
  (as-standalone [this])
  (as-normal [this])
  (to-calendar-style [this])
  (zone-name-style-index [this]))

(s/def ::text-style #(satisfies? ITextStyle %))