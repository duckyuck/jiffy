(ns jiffy.chrono.chronology
  (:refer-clojure :exclude [range])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java
(defprotocol IChronology
  (getId [this])
  (getCalendarType [this])
  (date
    [this temporal]
    [this proleptic-year month day-of-month]
    [this era year-of-era month day-of-month])
  (dateYearDay
    [this proleptic-year day-of-year]
    [this era year-of-era day-of-year])
  (dateEpochDay [this epoch-day])
  (dateNow [this] [this date-now--overloaded-param])
  (localDateTime [this temporal])
  (zonedDateTime
    [this temporal]
    [this instant zone])
  (isLeapYear [this proleptic-year])
  (prolepticYear [this era year-of-era])
  (eraOf [this era-value])
  (eras [this])
  (range [this field])
  (getDisplayName [this style locale])
  (resolveDate [this field-values resolver-style])
  (period [this years months days])
  (epochSecond
    [this proleptic-year month day-of-month hour minute second zone-offset]
    [this era year-of-era month day-of-month hour minute second zone-offset]))

(s/def ::chronology #(satisfies? IChronology %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L182
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L229
(s/def ::of-locale-args ::j/wip)
(defn ofLocale [locale] (wip ::ofLocale))
(s/fdef ofLocale :args ::of-locale-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L254
(s/def ::of-args ::j/wip)
(defn of [id] (wip ::of))
(s/fdef of :args ::of-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L268
(defn getAvailableChronologies [] (wip ::getAvailableChronologies))
(s/fdef getAvailableChronologies :ret ::j/wip)
