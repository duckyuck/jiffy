(ns jiffy.chrono.thai-buddhist-chronology
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.abstract-chronology :as AbstractChronology]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.thai-buddhist-chronology-impl :refer [create #?@(:cljs [ThaiBuddhistChronology])] :as impl]
            [jiffy.chrono.thai-buddhist-date :as ThaiBuddhistDate]
            [jiffy.chrono.thai-buddhist-era :as ThaiBuddhistEra]
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
  #?(:clj (:import [jiffy.chrono.thai_buddhist_chronology_impl ThaiBuddhistChronology])))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; FIXME: no implementation found from inherited class class java.time.chrono.AbstractChronology

(s/def ::thai-buddhist-chronology ::impl/thai-buddhist-chronology)

(defmacro args [& x] `(s/tuple ::thai-buddhist-chronology ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L177
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L194
(s/def ::get-calendar-type-args (args))
(defn -get-calendar-type [this] (wip ::-get-calendar-type))
(s/fdef -get-calendar-type :args ::get-calendar-type-args :ret string?)

(s/def ::date-args (args ::j/wip))
(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L289
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L227
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L212
  ([this era year-of-era month day-of-month] (wip ::-date)))
(s/fdef -date :args ::date-args :ret ::ChronoLocalDate/chrono-local-date)

(s/def ::date-year-day-args (args ::j/wip))
(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L257
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L243
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))
(s/fdef -date-year-day :args ::date-year-day-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L269
(s/def ::date-epoch-day-args (args ::j/long))
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))
(s/fdef -date-epoch-day :args ::date-epoch-day-args :ret ::ChronoLocalDate/chrono-local-date)

(s/def ::date-now-args (args ::j/wip))
(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L274
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L279
  ([this date-now--overloaded-param] (wip ::-date-now)))
(s/fdef -date-now :args ::date-now-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L298
(s/def ::local-date-time-args (args ::TemporalAccessor/temporal-accessor))
(defn -local-date-time [this temporal] (wip ::-local-date-time))
(s/fdef -local-date-time :args ::local-date-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

(s/def ::zoned-date-time-args (args ::j/wip))
(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L304
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L310
  ([this instant zone] (wip ::-zoned-date-time)))
(s/fdef -zoned-date-time :args ::zoned-date-time-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L326
(s/def ::is-leap-year-args (args ::j/long))
(defn -is-leap-year [this proleptic-year] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L331
(s/def ::proleptic-year-args (args ::Era/era ::j/int))
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))
(s/fdef -proleptic-year :args ::proleptic-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L339
(s/def ::era-of-args (args ::j/int))
(defn -era-of [this era-value] (wip ::-era-of))
(s/fdef -era-of :args ::era-of-args :ret ::ThaiBuddhistEra/thai-buddhist-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L344
(s/def ::eras-args (args))
(defn -eras [this] (wip ::-eras))
(s/fdef -eras :args ::eras-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L350
(s/def ::range-args (args ::ChronoField/chrono-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L370
(s/def ::resolve-date-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))
(s/fdef -resolve-date :args ::resolve-date-args :ret ::ThaiBuddhistDate/thai-buddhist-date)

(extend-type ThaiBuddhistChronology
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


;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L114
(def INSTANCE ::INSTANCE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistChronology.java#L123
(def YEARS_DIFFERENCE ::YEARS_DIFFERENCE--not-implemented)
