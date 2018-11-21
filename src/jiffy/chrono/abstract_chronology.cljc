(ns jiffy.chrono.abstract-chronology
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.time-comparable :as TimeComparable]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java
(defprotocol IAbstractChronology
  (resolveProlepticMonth [this field-values resolver-style])
  (resolveYearOfEra [this field-values resolver-style])
  (resolveYMD [this field-values resolver-style])
  (resolveYD [this field-values resolver-style])
  (resolveYMAA [this field-values resolver-style])
  (resolveYMAD [this field-values resolver-style])
  (resolveYAA [this field-values resolver-style])
  (resolveYAD [this field-values resolver-style])
  (resolveAligned [this base months weeks dow])
  (addFieldValue [this field-values field value]))

(defrecord AbstractChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::abstract-chronology (j/constructor-spec AbstractChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::abstract-chronology)

(defmacro args [& x] `(s/tuple ::abstract-chronology ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L468
(s/def ::resolve-proleptic-month-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-proleptic-month [this field-values resolver-style] (wip ::-resolve-proleptic-month))
(s/fdef -resolve-proleptic-month :args ::resolve-proleptic-month-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L483
(s/def ::resolve-year-of-era-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-year-of-era [this field-values resolver-style] (wip ::-resolve-year-of-era))
(s/fdef -resolve-year-of-era :args ::resolve-year-of-era-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L521
(s/def ::resolve-ymd-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-ymd [this field-values resolver-style] (wip ::-resolve-ymd))
(s/fdef -resolve-ymd :args ::resolve-ymd-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L541
(s/def ::resolve-yd-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-yd [this field-values resolver-style] (wip ::-resolve-yd))
(s/fdef -resolve-yd :args ::resolve-yd-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L551
(s/def ::resolve-ymaa-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-ymaa [this field-values resolver-style] (wip ::-resolve-ymaa))
(s/fdef -resolve-ymaa :args ::resolve-ymaa-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L569
(s/def ::resolve-ymad-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-ymad [this field-values resolver-style] (wip ::-resolve-ymad))
(s/fdef -resolve-ymad :args ::resolve-ymad-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L587
(s/def ::resolve-yaa-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-yaa [this field-values resolver-style] (wip ::-resolve-yaa))
(s/fdef -resolve-yaa :args ::resolve-yaa-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L603
(s/def ::resolve-yad-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-yad [this field-values resolver-style] (wip ::-resolve-yad))
(s/fdef -resolve-yad :args ::resolve-yad-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L619
(s/def ::resolve-aligned-args (args ::ChronoLocalDate/chrono-local-date ::j/long ::j/long ::j/long))
(defn -resolve-aligned [this base months weeks dow] (wip ::-resolve-aligned))
(s/fdef -resolve-aligned :args ::resolve-aligned-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L643
(s/def ::add-field-value-args (args ::j/wip ::ChronoField/chrono-field ::j/long))
(defn -add-field-value [this field-values field value] (wip ::-add-field-value))
(s/fdef -add-field-value :args ::add-field-value-args :ret ::j/void)

(extend-type AbstractChronology
  IAbstractChronology
  (resolveProlepticMonth [this field-values resolver-style] (-resolve-proleptic-month this field-values resolver-style))
  (resolveYearOfEra [this field-values resolver-style] (-resolve-year-of-era this field-values resolver-style))
  (resolveYMD [this field-values resolver-style] (-resolve-ymd this field-values resolver-style))
  (resolveYD [this field-values resolver-style] (-resolve-yd this field-values resolver-style))
  (resolveYMAA [this field-values resolver-style] (-resolve-ymaa this field-values resolver-style))
  (resolveYMAD [this field-values resolver-style] (-resolve-ymad this field-values resolver-style))
  (resolveYAA [this field-values resolver-style] (-resolve-yaa this field-values resolver-style))
  (resolveYAD [this field-values resolver-style] (-resolve-yad this field-values resolver-style))
  (resolveAligned [this base months weeks dow] (-resolve-aligned this base months weeks dow))
  (addFieldValue [this field-values field value] (-add-field-value this field-values field value)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L667
(s/def ::compare-to-args (args ::j/wip))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type AbstractChronology
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L423
(s/def ::resolve-date-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))
(s/fdef -resolve-date :args ::resolve-date-args :ret ::ChronoLocalDate/chrono-local-date)

(extend-type AbstractChronology
  Chronology/IChronology
  (resolveDate [this field-values resolver-style] (-resolve-date this field-values resolver-style)))

(s/def ::register-chrono-args (args ::j/wip))
(defn registerChrono
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L146
  ([chrono] (wip ::registerChrono))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L159
  ([chrono id] (wip ::registerChrono)))
(s/fdef registerChrono :args ::register-chrono-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L224
(s/def ::of-locale-args (args ::j/wip))
(defn ofLocale [locale] (wip ::ofLocale))
(s/fdef ofLocale :args ::of-locale-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L262
(s/def ::of-args (args string?))
(defn of [id] (wip ::of))
(s/fdef of :args ::of-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L309
(defn getAvailableChronologies [] (wip ::getAvailableChronologies))
(s/fdef getAvailableChronologies :ret ::j/wip)
