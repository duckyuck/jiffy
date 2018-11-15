(ns jiffy.format.decimal-style
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java
(defprotocol IDecimalStyle
  (getZeroDigit [this])
  (withZeroDigit [this zero-digit])
  (getPositiveSign [this])
  (withPositiveSign [this positive-sign])
  (getNegativeSign [this])
  (withNegativeSign [this negative-sign])
  (getDecimalSeparator [this])
  (withDecimalSeparator [this decimal-separator])
  (convertToDigit [this ch])
  (convertNumberToI18N [this numeric-text]))

(defrecord DecimalStyle [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L207
(defn -get-zero-digit [this] (wip ::-get-zero-digit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L220
(defn -with-zero-digit [this zero-digit] (wip ::-with-zero-digit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L236
(defn -get-positive-sign [this] (wip ::-get-positive-sign))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L249
(defn -with-positive-sign [this positive-sign] (wip ::-with-positive-sign))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L265
(defn -get-negative-sign [this] (wip ::-get-negative-sign))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L278
(defn -with-negative-sign [this negative-sign] (wip ::-with-negative-sign))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L294
(defn -get-decimal-separator [this] (wip ::-get-decimal-separator))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L307
(defn -with-decimal-separator [this decimal-separator] (wip ::-with-decimal-separator))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L321
(defn -convert-to-digit [this ch] (wip ::-convert-to-digit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L332
(defn -convert-number-to-i18n [this numeric-text] (wip ::-convert-number-to-i18n))

(extend-type DecimalStyle
  IDecimalStyle
  (getZeroDigit [this] (-get-zero-digit this))
  (withZeroDigit [this zero-digit] (-with-zero-digit this zero-digit))
  (getPositiveSign [this] (-get-positive-sign this))
  (withPositiveSign [this positive-sign] (-with-positive-sign this positive-sign))
  (getNegativeSign [this] (-get-negative-sign this))
  (withNegativeSign [this negative-sign] (-with-negative-sign this negative-sign))
  (getDecimalSeparator [this] (-get-decimal-separator this))
  (withDecimalSeparator [this decimal-separator] (-with-decimal-separator this decimal-separator))
  (convertToDigit [this ch] (-convert-to-digit this ch))
  (convertNumberToI18N [this numeric-text] (-convert-number-to-i18n this numeric-text)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L122
(defn getAvailableLocales [] (wip ::getAvailableLocales))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L142
(defn ofDefaultLocale [] (wip ::ofDefaultLocale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L159
(defn of [locale] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L91
(def STANDARD ::STANDARD--not-implemented)