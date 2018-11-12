(ns jiffy.chrono.chronology
  (:refer-clojure :exclude [range ])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java
(defprotocol IChronology
  (getId [this])
  (getCalendarType [this])
  (date [this temporal] [this proleptic-year month day-of-month] [this era year-of-era month day-of-month])
  (dateYearDay [this proleptic-year day-of-year] [this era year-of-era day-of-year])
  (dateEpochDay [this epoch-day])
  (dateNow [this] [this date-now--overloaded-param])
  (localDateTime [this temporal])
  (zonedDateTime [this temporal] [this instant zone])
  (isLeapYear [this proleptic-year])
  (prolepticYear [this era year-of-era])
  (eraOf [this era-value])
  (eras [this])
  (range [this field])
  (getDisplayName [this style locale])
  (resolveDate [this field-values resolver-style])
  (period [this years months days])
  (epochSecond [this proleptic-year month day-of-month hour minute second zone-offset] [this era year-of-era month day-of-month hour minute second zone-offset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L316
(defn -date [this era year-of-era month day-of-month] (wip ::-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L347
(defn -date-year-day [this era year-of-era day-of-year] (wip ::-date-year-day))

(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L126
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L410
  ([this date-now--overloaded-param] (wip ::-date-now)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L475
(defn -local-date-time [this temporal] (wip ::-local-date-time))

(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L507
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L533
  ([this instant zone] (wip ::-zoned-date-time)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L645
(defn -get-display-name [this style locale] (wip ::-get-display-name))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L716
(defn -period [this years months days] (wip ::-period))

(defn -epoch-second
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L739
  ([this proleptic-year month day-of-month hour minute second zone-offset] (wip ::-epoch-second))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L768
  ([this era year-of-era month day-of-month hour minute second zone-offset] (wip ::-epoch-second)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L182
(defn from [temporal] (wip ::from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L229
(defn ofLocale [locale] (wip ::ofLocale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L254
(defn of [id] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L268
(defn getAvailableChronologies [] (wip ::getAvailableChronologies))
