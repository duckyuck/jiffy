(ns jiffy.chrono.hijrah-date
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-impl :as ChronoLocalDateImpl]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.hijrah-chronology-impl :as HijrahChronology]
            [jiffy.chrono.hijrah-era :as HijrahEra]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time :as LocalTime]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java
(defprotocol IHijrahDate
  (withVariant [this chronology]))

(defrecord HijrahDate [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::hijrah-date (j/constructor-spec HijrahDate create ::create-args))
(s/fdef create :args ::create-args :ret ::hijrah-date)

(defmacro args [& x] `(s/tuple ::hijrah-date ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L449
(s/def ::with-variant-args (args ::HijrahChronology/hijrah-chronology))
(defn -with-variant [this chronology] (wip ::-with-variant))
(s/fdef -with-variant :args ::with-variant-args :ret ::hijrah-date)

(extend-type HijrahDate
  IHijrahDate
  (withVariant [this chronology] (-with-variant this chronology)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L302
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::HijrahChronology/hijrah-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L315
(s/def ::get-era-args (args))
(defn -get-era [this] (wip ::-get-era))
(s/fdef -get-era :args ::get-era-args :ret ::Era/era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L328
(s/def ::length-of-month-args (args))
(defn -length-of-month [this] (wip ::-length-of-month))
(s/fdef -length-of-month :args ::length-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L342
(s/def ::length-of-year-args (args))
(defn -length-of-year [this] (wip ::-length-of-year))
(s/fdef -length-of-year :args ::length-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L375
(s/def ::to-epoch-day-args (args))
(defn -to-epoch-day [this] (wip ::-to-epoch-day))
(s/fdef -to-epoch-day :args ::to-epoch-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L520
(s/def ::is-leap-year-args (args))
(defn -is-leap-year [this] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L588
(s/def ::at-time-args (args ::LocalTime/local-time))
(defn -at-time [this local-time] (wip ::-at-time))
(s/fdef -at-time :args ::at-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L593
(s/def ::until-args (args ::ChronoLocalDate/chrono-local-date))
(defn -until [this end-date] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::ChronoPeriod/chrono-period)

(extend-type HijrahDate
  ChronoLocalDate/IChronoLocalDate
  (getChronology [this] (-get-chronology this))
  (getEra [this] (-get-era this))
  (lengthOfMonth [this] (-length-of-month this))
  (lengthOfYear [this] (-length-of-year this))
  (toEpochDay [this] (-to-epoch-day this))
  (isLeapYear [this] (-is-leap-year this))
  (atTime [this local-time] (-at-time this local-time))
  (until [this end-date] (-until this end-date)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L526
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L535
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months-to-add] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L547
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L552
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L567
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L572
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L577
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L582
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::ChronoLocalDate/chrono-local-date)

(extend-type HijrahDate
  ChronoLocalDateImpl/IChronoLocalDateImpl
  (plusYears [this years] (-plus-years this years))
  (plusMonths [this months-to-add] (-plus-months this months-to-add))
  (plusWeeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plusDays [this days] (-plus-days this days))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (minusMonths [this months-to-subtract] (-minus-months this months-to-subtract))
  (minusWeeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java
(s/def ::until-temporal-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until-temporal [this until-temporal--unknown-param-name-1 until-temporal--unknown-param-name-2] (wip ::-until-temporal))
(s/fdef -until-temporal :args ::until-temporal-args :ret ::j/long)

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L435
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L394
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::hijrah-date)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L464
  ([this amount] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L557
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::ChronoLocalDate/chrono-local-date)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L474
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L562
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::hijrah-date)

(extend-type HijrahDate
  Temporal/ITemporal
  (until [this until--unknown-param-name-1 until--unknown-param-name-2] (-until-temporal this until--unknown-param-name-1 until--unknown-param-name-2))
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount] (-plus this amount))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount] (-minus this amount))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L348
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L367
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

(extend-type HijrahDate
  TemporalAccessor/ITemporalAccessor
  (range [this field] (-range this field))
  (getLong [this field] (-get-long this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L233
  ([proleptic-year month day-of-month] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L155
  ([chrono proleptic-year month-of-year day-of-month] (wip ::of)))
(s/fdef of :args ::of-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L165
(s/def ::of-epoch-day-args (args ::HijrahChronology/hijrah-chronology ::j/long))
(defn ofEpochDay [chrono epoch-day] (wip ::ofEpochDay))
(s/fdef ofEpochDay :args ::of-epoch-day-args :ret ::hijrah-date)

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L182
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L199
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::hijrah-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java#L254
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::hijrah-date)
