(ns jiffy.protocols.format.decimal-style
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java
(defprotocol IDecimalStyle
  (get-zero-digit [this])
  (with-zero-digit [this zero-digit])
  (get-positive-sign [this])
  (with-positive-sign [this positive-sign])
  (get-negative-sign [this])
  (with-negative-sign [this negative-sign])
  (get-decimal-separator [this])
  (with-decimal-separator [this decimal-separator])
  (convert-to-digit [this ch])
  (convert-number-to-i18n [this numeric-text]))

(s/def ::decimal-style #(satisfies? IDecimalStyle %))