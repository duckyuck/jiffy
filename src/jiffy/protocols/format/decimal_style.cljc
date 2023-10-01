(ns jiffy.protocols.format.decimal-style
  (:require [clojure.spec.alpha :as s]))

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
