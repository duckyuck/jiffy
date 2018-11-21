(ns jiffy.chrono.iso-chronology
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.abstract-chronology :as AbstractChronology]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.iso-chronology-impl :refer [create #?@(:cljs [IsoChronology])] :as impl]
            [jiffy.chrono.iso-era :as IsoEra]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date :as LocalDate]
            [jiffy.local-date-time :as LocalDateTime]
            [jiffy.period :as Period]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]
            [jiffy.zoned-date-time :as ZonedDateTime])
  #?(:clj (:import [jiffy.chrono.iso_chronology_impl IsoChronology])))

(s/def ::iso-chronology ::impl/iso-chronology)

(defmacro args [& x] `(s/tuple ::iso-chronology ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L589
(s/def ::resolve-proleptic-month-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-proleptic-month [this field-values resolver-style] (wip ::-resolve-proleptic-month))
(s/fdef -resolve-proleptic-month :args ::resolve-proleptic-month-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L601
(s/def ::resolve-year-of-era-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-year-of-era [this field-values resolver-style] (wip ::-resolve-year-of-era))
(s/fdef -resolve-year-of-era :args ::resolve-year-of-era-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L636
(s/def ::resolve-ymd-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-ymd [this field-values resolver-style] (wip ::-resolve-ymd))
(s/fdef -resolve-ymd :args ::resolve-ymd-args :ret ::ChronoLocalDate/chrono-local-date)

(extend-type IsoChronology
  AbstractChronology/IAbstractChronology
  (resolveProlepticMonth [this field-values resolver-style] (-resolve-proleptic-month this field-values resolver-style))
  (resolveYearOfEra [this field-values resolver-style] (-resolve-year-of-era this field-values resolver-style))
  (resolveYMD [this field-values resolver-style] (-resolve-ymd this field-values resolver-style)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L157
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L174
(s/def ::get-calendar-type-args (args))
(defn -get-calendar-type [this] (wip ::-get-calendar-type))
(s/fdef -get-calendar-type :args ::get-calendar-type-args :ret string?)

(s/def ::date-args (args ::j/wip))
(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L267
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L209
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L192
  ([this era year-of-era month day-of-month] (wip ::-date)))
(s/fdef -date :args ::date-args :ret ::LocalDate/local-date)

(s/def ::date-year-day-args (args ::j/wip))
(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L238
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L223
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))
(s/fdef -date-year-day :args ::date-year-day-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L252
(s/def ::date-epoch-day-args (args ::j/long))
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))
(s/fdef -date-epoch-day :args ::date-epoch-day-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L291
(s/def ::epoch-second-args (args ::j/int ::j/int ::j/int ::j/int ::j/int ::j/int ::ZoneOffset/zone-offset))
(defn -epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] (wip ::-epoch-second))
(s/fdef -epoch-second :args ::epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L369
(s/def ::local-date-time-args (args ::TemporalAccessor/temporal-accessor))
(defn -local-date-time [this temporal] (wip ::-local-date-time))
(s/fdef -local-date-time :args ::local-date-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

(s/def ::zoned-date-time-args (args ::j/wip))
(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L383
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L398
  ([this instant zone] (wip ::-zoned-date-time)))
(s/fdef -zoned-date-time :args ::zoned-date-time-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

(s/def ::date-now-args (args ::j/wip))
(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L416
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L433
  ([this date-now--overloaded-param] (wip ::-date-now)))
(s/fdef -date-now :args ::date-now-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L475
(s/def ::is-leap-year-args (args ::j/long))
(defn -is-leap-year [this proleptic-year] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L480
(s/def ::proleptic-year-args (args ::Era/era ::j/int))
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))
(s/fdef -proleptic-year :args ::proleptic-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L488
(s/def ::era-of-args (args ::j/int))
(defn -era-of [this era-value] (wip ::-era-of))
(s/fdef -era-of :args ::era-of-args :ret ::IsoEra/iso-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L493
(s/def ::eras-args (args))
(defn -eras [this] (wip ::-eras))
(s/fdef -eras :args ::eras-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L584
(s/def ::resolve-date-args (args ::j/wip ::ResolverStyle/resolver-style))
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))
(s/fdef -resolve-date :args ::resolve-date-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L658
(s/def ::range-args (args ::ChronoField/chrono-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L676
(s/def ::period-args (args ::j/int ::j/int ::j/int))
(defn -period [this years months days] (wip ::-period))
(s/fdef -period :args ::period-args :ret ::Period/period)

(extend-type IsoChronology
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
  (epochSecond [this proleptic-year month day-of-month hour minute second zone-offset] (-epoch-second this proleptic-year month day-of-month hour minute second zone-offset))
  (localDateTime [this temporal] (-local-date-time this temporal))
  (zonedDateTime
    ([this temporal] (-zoned-date-time this temporal))
    ([this instant zone] (-zoned-date-time this instant zone)))
  (dateNow
    ([this] (-date-now this))
    ([this date-now--overloaded-param] (-date-now this date-now--overloaded-param)))
  (isLeapYear [this proleptic-year] (-is-leap-year this proleptic-year))
  (prolepticYear [this era year-of-era] (-proleptic-year this era year-of-era))
  (eraOf [this era-value] (-era-of this era-value))
  (eras [this] (-eras this))
  (resolveDate [this field-values resolver-style] (-resolve-date this field-values resolver-style))
  (range [this field] (-range this field))
  (period [this years months days] (-period this years months days)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L131
(def INSTANCE impl/INSTANCE)
