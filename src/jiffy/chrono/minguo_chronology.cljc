(ns jiffy.chrono.minguo-chronology
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.chrono.abstract-chronology :as AbstractChronology]
            [jiffy.chrono.chronology :as Chronology]))

(defrecord MinguoChronology [])

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; FIXME: no implementation found from inherited class class java.time.chrono.AbstractChronology

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L140
(defn -get-id [this] (wip ::-get-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L157
(defn -get-calendar-type [this] (wip ::-get-calendar-type))

(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L252
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L190
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L175
  ([this era year-of-era month day-of-month] (wip ::-date)))

(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L220
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L206
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L232
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))

(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L237
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L242
  ([this date-now--overloaded-param] (wip ::-date-now)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L261
(defn -local-date-time [this temporal] (wip ::-local-date-time))

(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L267
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L273
  ([this instant zone] (wip ::-zoned-date-time)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L289
(defn -is-leap-year [this proleptic-year] (wip ::-is-leap-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L294
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L302
(defn -era-of [this era-value] (wip ::-era-of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L307
(defn -eras [this] (wip ::-eras))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L313
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L333
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))

(extend-type MinguoChronology
  Chronology/IChronology
  (getId [this] (-get-id this))
  (getCalendarType [this] (-get-calendar-type this))
  (date
    ([this temporal] (-date this temporal))
    ([this proleptic-year month day-of-month] (-date this proleptic-year month day-of-month))
    ([this era year-of-era month day-of-month] (-date this era year-of-era month day-of-month)))
  (dateYearDay
    ([this proleptic-year day-of-year] (-date-year-day this proleptic-year day-of-year))
    ([this era year-of-era day-of-year] (-date-year-day this era year-of-era day-of-year)))
  (dateEpochDay [this epoch-day] (-date-epoch-day this epoch-day))
  (dateNow
    ([this] (-date-now this))
    ([this date-now--overloaded-param] (-date-now this date-now--overloaded-param)))
  (localDateTime [this temporal] (-local-date-time this temporal))
  (zonedDateTime
    ([this temporal] (-zoned-date-time this temporal))
    ([this instant zone] (-zoned-date-time this instant zone)))
  (isLeapYear [this proleptic-year] (-is-leap-year this proleptic-year))
  (prolepticYear [this era year-of-era] (-proleptic-year this era year-of-era))
  (eraOf [this era-value] (-era-of this era-value))
  (eras [this] (-eras this))
  (range [this field] (-range this field))
  (resolveDate [this field-values resolver-style] (-resolve-date this field-values resolver-style)))


;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L112
(def INSTANCE ::INSTANCE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoChronology.java#L121
(def YEARS_DIFFERENCE ::YEARS_DIFFERENCE--not-implemented)