(ns jiffy.chrono.thai-buddhist-date
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.chrono.chrono-local-date-impl :as chrono-local-date-impl]
            [jiffy.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.chrono.chrono-period :as chrono-period]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.chrono.era :as era]
            [jiffy.chrono.thai-buddhist-chronology-impl :as thai-buddhist-chronology]
            [jiffy.chrono.thai-buddhist-era :as thai-buddhist-era]
            [jiffy.clock :as clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time :as local-time]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zone-id :as zone-id]))

(defrecord ThaiBuddhistDate [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::thai-buddhist-date (j/constructor-spec ThaiBuddhistDate create ::create-args))
(s/fdef create :args ::create-args :ret ::thai-buddhist-date)

(defmacro args [& x] `(s/tuple ::thai-buddhist-date ~@x))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L227
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::thai-buddhist-chronology/thai-buddhist-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L240
(s/def ::get-era-args (args))
(defn -get-era [this] (wip ::-get-era))
(s/fdef -get-era :args ::get-era-args :ret ::thai-buddhist-era/thai-buddhist-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L253
(s/def ::length-of-month-args (args))
(defn -length-of-month [this] (wip ::-length-of-month))
(s/fdef -length-of-month :args ::length-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L427
(s/def ::at-time-args (args ::local-time/local-time))
(defn -at-time [this local-time] (wip ::-at-time))
(s/fdef -at-time :args ::at-time-args :ret ::chrono-local-date-time/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L432
(s/def ::until-args (args ::chrono-local-date/chrono-local-date))
(defn -until [this end-date] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L438
(s/def ::to-epoch-day-args (args))
(defn -to-epoch-day [this] (wip ::-to-epoch-day))
(s/fdef -to-epoch-day :args ::to-epoch-day-args :ret ::j/long)

(extend-type ThaiBuddhistDate
  chrono-local-date/IChronoLocalDate
  (get-chronology [this] (-get-chronology this))
  (get-era [this] (-get-era this))
  (length-of-month [this] (-length-of-month this))
  (at-time [this local-time] (-at-time this local-time))
  (until [this end-date] (-until this end-date))
  (to-epoch-day [this] (-to-epoch-day this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L372
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L377
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L382
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L387
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L402
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L407
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L412
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L417
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::thai-buddhist-date)

(extend-type ThaiBuddhistDate
  chrono-local-date-impl/IChronoLocalDateImpl
  (plus-years [this years] (-plus-years this years))
  (plus-months [this months] (-plus-months this months))
  (plus-weeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plus-days [this days] (-plus-days this days))
  (minus-years [this years-to-subtract] (-minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (-minus-months this months-to-subtract))
  (minus-weeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minus-days [this days-to-subtract] (-minus-days this days-to-subtract)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java
(s/def ::until-temporal-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until-temporal [this until--unknown-param-name-1 until--unknown-param-name-2] (wip ::-until))
(s/fdef -until-temporal :args ::until-temporal-args :ret ::j/long)

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L346
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L311
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L356
  ([this amount] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L392
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::chrono-local-date/chrono-local-date)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L366
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L397
  ([this amount-to-add unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::temporal/temporal)

(extend-type ThaiBuddhistDate
  temporal/ITemporal
  (until [this until--unknown-param-name-1 until--unknown-param-name-2] (-until-temporal this until--unknown-param-name-1 until--unknown-param-name-2))
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount] (-plus this amount))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount] (-minus this amount))
    ([this amount-to-add unit] (-minus this amount-to-add unit))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L259
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L282
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

(extend-type ThaiBuddhistDate
  temporal-accessor/ITemporalAccessor
  (range [this field] (-range this field))
  (get-long [this field] (-get-long this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L132
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L148
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L181
(s/def ::of-args (args ::j/int ::j/int ::j/int))
(defn of [proleptic-year month day-of-month] (wip ::of))
(s/fdef of :args ::of-args :ret ::thai-buddhist-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistDate.java#L202
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::thai-buddhist-date)
