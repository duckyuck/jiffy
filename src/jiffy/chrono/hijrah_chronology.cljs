(ns jiffy.chrono.hijrah-chronology
  (:require [jiffy.chrono.abstract-chronology :as AbstractChronology]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java
(defprotocol IHijrahChronology
  (getMinimumYear [this])
  (getMaximumYear [this])
  (getMaximumMonthLength [this])
  (getMinimumMonthLength [this])
  (getMaximumDayOfYear [this])
  (checkValidYear [this proleptic-year])
  (checkValidDayOfYear [this day-of-year])
  (checkValidMonth [this month])
  (getHijrahDateInfo [this epoch-day])
  (getEpochDay [this proleptic-year month-of-year day-of-month])
  (getDayOfYear [this proleptic-year month])
  (getMonthLength [this proleptic-year month-of-year])
  (getYearLength [this proleptic-year])
  (getSmallestMaximumDayOfYear [this]))

(defrecord HijrahChronology [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L476
(defn -get-minimum-year [this] (wip ::-get-minimum-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L476
(defn -get-maximum-year [this] (wip ::-get-maximum-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L524
(defn -get-maximum-month-length [this] (wip ::-get-maximum-month-length))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L524
(defn -get-minimum-month-length [this] (wip ::-get-minimum-month-length))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L526
(defn -get-maximum-day-of-year [this] (wip ::-get-maximum-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L553
(defn -check-valid-year [this proleptic-year] (wip ::-check-valid-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L560
(defn -check-valid-day-of-year [this day-of-year] (wip ::-check-valid-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L566
(defn -check-valid-month [this month] (wip ::-check-valid-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L580
(defn -get-hijrah-date-info [this epoch-day] (wip ::-get-hijrah-date-info))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L607
(defn -get-epoch-day [this proleptic-year month-of-year day-of-month] (wip ::-get-epoch-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L628
(defn -get-day-of-year [this proleptic-year month] (wip ::-get-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L639
(defn -get-month-length [this proleptic-year month-of-year] (wip ::-get-month-length))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L655
(defn -get-year-length [this proleptic-year] (wip ::-get-year-length))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L709
(defn -get-smallest-maximum-day-of-year [this] (wip ::-get-smallest-maximum-day-of-year))

(extend-type HijrahChronology
  IHijrahChronology
  (getMinimumYear [this] (-get-minimum-year this))
  (getMaximumYear [this] (-get-maximum-year this))
  (getMaximumMonthLength [this] (-get-maximum-month-length this))
  (getMinimumMonthLength [this] (-get-minimum-month-length this))
  (getMaximumDayOfYear [this] (-get-maximum-day-of-year this))
  (checkValidYear [this proleptic-year] (-check-valid-year this proleptic-year))
  (checkValidDayOfYear [this day-of-year] (-check-valid-day-of-year this day-of-year))
  (checkValidMonth [this month] (-check-valid-month this month))
  (getHijrahDateInfo [this epoch-day] (-get-hijrah-date-info this epoch-day))
  (getEpochDay [this proleptic-year month-of-year day-of-month] (-get-epoch-day this proleptic-year month-of-year day-of-month))
  (getDayOfYear [this proleptic-year month] (-get-day-of-year this proleptic-year month))
  (getMonthLength [this proleptic-year month-of-year] (-get-month-length this proleptic-year month-of-year))
  (getYearLength [this proleptic-year] (-get-year-length this proleptic-year))
  (getSmallestMaximumDayOfYear [this] (-get-smallest-maximum-day-of-year this)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; FIXME: no implementation found from inherited class class java.time.chrono.AbstractChronology

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L331
(defn -get-id [this] (wip ::-get-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L347
(defn -get-calendar-type [this] (wip ::-get-calendar-type))

(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L411
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L396
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L427
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))

(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L432
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L437
  ([this date-now--overloaded-param] (wip ::-date-now)))

(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L447
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L380
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L365
  ([this era year-of-era month day-of-month] (wip ::-date)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L456
(defn -local-date-time [this temporal] (wip ::-local-date-time))

(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L462
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L468
  ([this instant zone] (wip ::-zoned-date-time)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L474
(defn -is-leap-year [this proleptic-year] (wip ::-is-leap-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L484
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L502
(defn -era-of [this era-value] (wip ::-era-of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L512
(defn -eras [this] (wip ::-eras))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L518
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L543
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))

(extend-type HijrahChronology
  Chronology/IChronology
  (getId [this] (-get-id this))
  (getCalendarType [this] (-get-calendar-type this))
  (dateYearDay
    ([this proleptic-year day-of-year] (-date-year-day this proleptic-year day-of-year))
    ([this era year-of-era day-of-year] (-date-year-day this era year-of-era day-of-year)))
  (dateEpochDay [this epoch-day] (-date-epoch-day this epoch-day))
  (dateNow
    ([this] (-date-now this))
    ([this date-now--overloaded-param] (-date-now this date-now--overloaded-param)))
  (date
    ([this temporal] (-date this temporal))
    ([this proleptic-year month day-of-month] (-date this proleptic-year month day-of-month))
    ([this era year-of-era month day-of-month] (-date this era year-of-era month day-of-month)))
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


;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L214
(def INSTANCE ::INSTANCE--not-implemented)
