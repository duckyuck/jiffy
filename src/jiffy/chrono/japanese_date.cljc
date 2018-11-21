(ns jiffy.chrono.japanese-date
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-impl :as ChronoLocalDateImpl]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.chrono.era :as Era]
            [jiffy.chrono.japanese-chronology-impl :as JapaneseChronology]
            [jiffy.chrono.japanese-era :as JapaneseEra]
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

(defrecord JapaneseDate [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::japanese-date (j/constructor-spec JapaneseDate create ::create-args))
(s/fdef create :args ::create-args :ret ::japanese-date)

(defmacro args [& x] `(s/tuple ::japanese-date ~@x))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L366
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::JapaneseChronology/japanese-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L378
(s/def ::get-era-args (args))
(defn -get-era [this] (wip ::-get-era))
(s/fdef -get-era :args ::get-era-args :ret ::JapaneseEra/japanese-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L391
(s/def ::length-of-month-args (args))
(defn -length-of-month [this] (wip ::-length-of-month))
(s/fdef -length-of-month :args ::length-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L396
(s/def ::length-of-year-args (args))
(defn -length-of-year [this] (wip ::-length-of-year))
(s/fdef -length-of-year :args ::length-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L665
(s/def ::at-time-args (args ::LocalTime/local-time))
(defn -at-time [this local-time] (wip ::-at-time))
(s/fdef -at-time :args ::at-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L670
(s/def ::until-args (args ::ChronoLocalDate/chrono-local-date))
(defn -until [this end-date] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::ChronoPeriod/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L676
(s/def ::to-epoch-day-args (args))
(defn -to-epoch-day [this] (wip ::-to-epoch-day))
(s/fdef -to-epoch-day :args ::to-epoch-day-args :ret ::j/long)

(extend-type JapaneseDate
  ChronoLocalDate/IChronoLocalDate
  (getChronology [this] (-get-chronology this))
  (getEra [this] (-get-era this))
  (lengthOfMonth [this] (-length-of-month this))
  (lengthOfYear [this] (-length-of-year this))
  (atTime [this local-time] (-at-time this local-time))
  (until [this end-date] (-until this end-date))
  (toEpochDay [this] (-to-epoch-day this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L610
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L615
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L620
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L625
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L640
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L645
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L650
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L655
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::japanese-date)

(extend-type JapaneseDate
  ChronoLocalDateImpl/IChronoLocalDateImpl
  (plusYears [this years] (-plus-years this years))
  (plusMonths [this months] (-plus-months this months))
  (plusWeeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plusDays [this days] (-plus-days this days))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (minusMonths [this months-to-subtract] (-minus-months this months-to-subtract))
  (minusWeeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java
(s/def ::until-temporal-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until-temporal [this until-temporal--unknown-param-name-1 until-temporal--unknown-param-name-2] (wip ::-until-temporal))
(s/fdef -until-temporal :args ::until-temporal-args :ret ::j/long)

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L548
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L514
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::japanese-date)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L568
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L635
  ([this amount-to-add unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::japanese-date)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L558
  ([this amount] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L630
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::ChronoLocalDate/chrono-local-date)

(extend-type JapaneseDate
  Temporal/ITemporal
  (until [this until--unknown-param-name-1 until--unknown-param-name-2] (-until-temporal this until--unknown-param-name-1 until--unknown-param-name-2))
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (minus
    ([this amount] (-minus this amount))
    ([this amount-to-add unit] (-minus this amount-to-add unit)))
  (plus
    ([this amount] (-plus this amount))
    ([this amount-to-add unit] (-plus this amount-to-add unit))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L435
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L444
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L466
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

(extend-type JapaneseDate
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (getLong [this field] (-get-long this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L165
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L181
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::japanese-date)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L254
  ([proleptic-year month day-of-month] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L226
  ([era year-of-era month day-of-month] (wip ::of)))
(s/fdef of :args ::of-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L283
(s/def ::of-year-day-args (args ::JapaneseEra/japanese-era ::j/int ::j/int))
(defn ofYearDay [era year-of-era day-of-year] (wip ::ofYearDay))
(s/fdef ofYearDay :args ::of-year-day-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L319
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::japanese-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseDate.java#L151
(def MEIJI_6_ISODATE ::MEIJI_6_ISODATE--not-implemented)
