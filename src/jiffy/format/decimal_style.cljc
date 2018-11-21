(ns jiffy.format.decimal-style
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::decimal-style (j/constructor-spec DecimalStyle create ::create-args))
(s/fdef create :args ::create-args :ret ::decimal-style)

(defmacro args [& x] `(s/tuple ::decimal-style ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L207
(s/def ::get-zero-digit-args (args))
(defn -get-zero-digit [this] (wip ::-get-zero-digit))
(s/fdef -get-zero-digit :args ::get-zero-digit-args :ret ::j/char)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L220
(s/def ::with-zero-digit-args (args ::j/char))
(defn -with-zero-digit [this zero-digit] (wip ::-with-zero-digit))
(s/fdef -with-zero-digit :args ::with-zero-digit-args :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L236
(s/def ::get-positive-sign-args (args))
(defn -get-positive-sign [this] (wip ::-get-positive-sign))
(s/fdef -get-positive-sign :args ::get-positive-sign-args :ret ::j/char)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L249
(s/def ::with-positive-sign-args (args ::j/char))
(defn -with-positive-sign [this positive-sign] (wip ::-with-positive-sign))
(s/fdef -with-positive-sign :args ::with-positive-sign-args :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L265
(s/def ::get-negative-sign-args (args))
(defn -get-negative-sign [this] (wip ::-get-negative-sign))
(s/fdef -get-negative-sign :args ::get-negative-sign-args :ret ::j/char)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L278
(s/def ::with-negative-sign-args (args ::j/char))
(defn -with-negative-sign [this negative-sign] (wip ::-with-negative-sign))
(s/fdef -with-negative-sign :args ::with-negative-sign-args :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L294
(s/def ::get-decimal-separator-args (args))
(defn -get-decimal-separator [this] (wip ::-get-decimal-separator))
(s/fdef -get-decimal-separator :args ::get-decimal-separator-args :ret ::j/char)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L307
(s/def ::with-decimal-separator-args (args ::j/char))
(defn -with-decimal-separator [this decimal-separator] (wip ::-with-decimal-separator))
(s/fdef -with-decimal-separator :args ::with-decimal-separator-args :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L321
(s/def ::convert-to-digit-args (args ::j/char))
(defn -convert-to-digit [this ch] (wip ::-convert-to-digit))
(s/fdef -convert-to-digit :args ::convert-to-digit-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L332
(s/def ::convert-number-to-i18n-args (args string?))
(defn -convert-number-to-i18n [this numeric-text] (wip ::-convert-number-to-i18n))
(s/fdef -convert-number-to-i18n :args ::convert-number-to-i18n-args :ret string?)

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
(s/fdef getAvailableLocales :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L142
(defn ofDefaultLocale [] (wip ::ofDefaultLocale))
(s/fdef ofDefaultLocale :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L159
(s/def ::of-args (args ::j/wip))
(defn of [locale] (wip ::of))
(s/fdef of :args ::of-args :ret ::decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DecimalStyle.java#L91
(def STANDARD ::STANDARD--not-implemented)
