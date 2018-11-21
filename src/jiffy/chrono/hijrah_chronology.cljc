(ns jiffy.chrono.hijrah-chronology
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.abstract-chronology :as AbstractChronology]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.hijrah-chronology-impl :refer [create #?@(:cljs [HijrahChronology])] :as impl]
            [jiffy.chrono.hijrah-date :as HijrahDate]
            [jiffy.chrono.hijrah-era :as HijrahEra]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.instant :as Instant]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId])
  #?(:clj (:import [jiffy.chrono.hijrah_chronology_impl HijrahChronology])))

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

(s/def ::hijrah-chronology ::impl/hijrah-chronology)

(defmacro args [& x] `(s/tuple ::hijrah-chronology ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L476
(s/def ::get-minimum-year-args (args))
(defn -get-minimum-year [this] (wip ::-get-minimum-year))
(s/fdef -get-minimum-year :args ::get-minimum-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L476
(s/def ::get-maximum-year-args (args))
(defn -get-maximum-year [this] (wip ::-get-maximum-year))
(s/fdef -get-maximum-year :args ::get-maximum-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L524
(s/def ::get-maximum-month-length-args (args))
(defn -get-maximum-month-length [this] (wip ::-get-maximum-month-length))
(s/fdef -get-maximum-month-length :args ::get-maximum-month-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L524
(s/def ::get-minimum-month-length-args (args))
(defn -get-minimum-month-length [this] (wip ::-get-minimum-month-length))
(s/fdef -get-minimum-month-length :args ::get-minimum-month-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L526
(s/def ::get-maximum-day-of-year-args (args))
(defn -get-maximum-day-of-year [this] (wip ::-get-maximum-day-of-year))
(s/fdef -get-maximum-day-of-year :args ::get-maximum-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L553
(s/def ::check-valid-year-args (args ::j/long))
(defn -check-valid-year [this proleptic-year] (wip ::-check-valid-year))
(s/fdef -check-valid-year :args ::check-valid-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L560
(s/def ::check-valid-day-of-year-args (args ::j/int))
(defn -check-valid-day-of-year [this day-of-year] (wip ::-check-valid-day-of-year))
(s/fdef -check-valid-day-of-year :args ::check-valid-day-of-year-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L566
(s/def ::check-valid-month-args (args ::j/int))
(defn -check-valid-month [this month] (wip ::-check-valid-month))
(s/fdef -check-valid-month :args ::check-valid-month-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L580
(s/def ::get-hijrah-date-info-args (args ::j/int))
(defn -get-hijrah-date-info [this epoch-day] (wip ::-get-hijrah-date-info))
(s/fdef -get-hijrah-date-info :args ::get-hijrah-date-info-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L607
(s/def ::get-epoch-day-args (args ::j/int ::j/int ::j/int))
(defn -get-epoch-day [this proleptic-year month-of-year day-of-month] (wip ::-get-epoch-day))
(s/fdef -get-epoch-day :args ::get-epoch-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L628
(s/def ::get-day-of-year-args (args ::j/int ::j/int))
(defn -get-day-of-year [this proleptic-year month] (wip ::-get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L639
(s/def ::get-month-length-args (args ::j/int ::j/int))
(defn -get-month-length [this proleptic-year month-of-year] (wip ::-get-month-length))
(s/fdef -get-month-length :args ::get-month-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L655
(s/def ::get-year-length-args (args ::j/int))
(defn -get-year-length [this proleptic-year] (wip ::-get-year-length))
(s/fdef -get-year-length :args ::get-year-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L709
(s/def ::get-smallest-maximum-day-of-year-args (args))
(defn -get-smallest-maximum-day-of-year [this] (wip ::-get-smallest-maximum-day-of-year))
(s/fdef -get-smallest-maximum-day-of-year :args ::get-smallest-maximum-day-of-year-args :ret ::j/int)

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
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L347
(s/def ::get-calendar-type-args (args))
(defn -get-calendar-type [this] (wip ::-get-calendar-type))
(s/fdef -get-calendar-type :args ::get-calendar-type-args :ret string?)

(s/def ::date-year-day-args (args ::j/wip))
(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L411
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L396
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))
(s/fdef -date-year-day :args ::date-year-day-args :ret ::HijrahDate/hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L427
(s/def ::date-epoch-day-args (args ::j/long))
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))
(s/fdef -date-epoch-day :args ::date-epoch-day-args :ret ::HijrahDate/hijrah-date)

(s/def ::date-now-args (args ::j/wip))
(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L432
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L437
  ([this date-now--overloaded-param] (wip ::-date-now)))
(s/fdef -date-now :args ::date-now-args :ret ::ChronoLocalDate/chrono-local-date)

(s/def ::date-args (args ::j/wip))
(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L447
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L380
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L365
  ([this era year-of-era month day-of-month] (wip ::-date)))
(s/fdef -date :args ::date-args :ret ::HijrahDate/hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L456
(s/def ::local-date-time-args (args ::TemporalAccessor/temporal-accessor))
(defn -local-date-time [this temporal] (wip ::-local-date-time))
(s/fdef -local-date-time :args ::local-date-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

(s/def ::zoned-date-time-args (args ::j/wip))
(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L462
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L468
  ([this instant zone] (wip ::-zoned-date-time)))
(s/fdef -zoned-date-time :args ::zoned-date-time-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L474
(s/def ::is-leap-year-args (args ::j/long))
(defn -is-leap-year [this proleptic-year] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L484
(s/def ::proleptic-year-args (args ::Era/era ::j/int))
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))
(s/fdef -proleptic-year :args ::proleptic-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L502
(s/def ::era-of-args (args ::j/int))
(defn -era-of [this era-value] (wip ::-era-of))
(s/fdef -era-of :args ::era-of-args :ret ::Era/era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L512
(s/def ::eras-args (args))
(defn -eras [this] (wip ::-eras))
(s/fdef -eras :args ::eras-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L518
(s/def ::range-args (args ::ChronoField/chrono-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java#L543
(s/def ::resolve-date-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))
(s/fdef -resolve-date :args ::resolve-date-args :ret ::HijrahDate/hijrah-date)

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
