(ns jiffy.chrono.abstract-chronology
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.chrono.chronology :as Chronology]))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L468
(defn -resolve-proleptic-month [this field-values resolver-style] (wip ::-resolve-proleptic-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L483
(defn -resolve-year-of-era [this field-values resolver-style] (wip ::-resolve-year-of-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L521
(defn -resolve-ymd [this field-values resolver-style] (wip ::-resolve-ymd))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L541
(defn -resolve-yd [this field-values resolver-style] (wip ::-resolve-yd))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L551
(defn -resolve-ymaa [this field-values resolver-style] (wip ::-resolve-ymaa))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L569
(defn -resolve-ymad [this field-values resolver-style] (wip ::-resolve-ymad))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L587
(defn -resolve-yaa [this field-values resolver-style] (wip ::-resolve-yaa))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L603
(defn -resolve-yad [this field-values resolver-style] (wip ::-resolve-yad))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L619
(defn -resolve-aligned [this base months weeks dow] (wip ::-resolve-aligned))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L643
(defn -add-field-value [this field-values field value] (wip ::-add-field-value))

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
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type AbstractChronology
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L423
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))

(extend-type AbstractChronology
  Chronology/IChronology
  (resolveDate [this field-values resolver-style] (-resolve-date this field-values resolver-style)))

(defn registerChrono
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L146
  ([chrono] (wip ::registerChrono))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L159
  ([chrono id] (wip ::registerChrono)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L224
(defn ofLocale [locale] (wip ::ofLocale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L262
(defn of [id] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java#L309
(defn getAvailableChronologies [] (wip ::getAvailableChronologies))
