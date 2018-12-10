(ns jiffy.local-date
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :refer [create #?@(:cljs [LocalDate])] :as impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.chrono.iso-chronology :as iso-chronology]
            [jiffy.protocols.chrono.iso-era :as iso-era]
            [jiffy.protocols.clock :as clock]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.period :as period]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query])
  #?(:clj (:import [jiffy.local_date_impl LocalDate])))

(def DAYS_PER_CYCLE impl/DAYS_PER_CYCLE)
(def DAYS_0000_TO_1970 impl/DAYS_0000_TO_1970)

(s/def ::local-date ::impl/local-date)

(defmacro args [& x] `(s/tuple ::local-date ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L694
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::day-of-week/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L698
(s/def ::get-day-of-year-args (args))
(defn -get-day-of-year [this] (wip ::-get-day-of-year))
(s/fdef -get-day-of-year :args ::get-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L761
(s/def ::get-year-args (args))
(defn -get-year [this] (wip ::-get-year))
(s/fdef -get-year :args ::get-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L775
(s/def ::get-month-value-args (args))
(defn -get-month-value [this] (wip ::-get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L801
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this] (wip ::-get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1081
(s/def ::with-year-args (args ::j/int))
(defn -with-year [this year] (wip ::-with-year))
(s/fdef -with-year :args ::with-year-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1100
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month] (wip ::-with-month))
(s/fdef -with-month :args ::with-month-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1120
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1139
(s/def ::with-day-of-year-args (args ::j/int))
(defn -with-day-of-year [this day-of-year] (wip ::-with-day-of-year))
(s/fdef -with-day-of-year :args ::with-day-of-year-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1298
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years-to-add] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1326
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months-to-add] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1352
(s/def ::plus-weeks-args (args ::j/long))
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))
(s/fdef -plus-weeks :args ::plus-weeks-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1371
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days-to-add] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1472
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1496
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1515
(s/def ::minus-weeks-args (args ::j/long))
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))
(s/fdef -minus-weeks :args ::minus-weeks-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1534
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1661
(s/def ::days-until-args (args ::local-date))
(defn -days-until [this end] (wip ::-days-until))
(s/fdef -days-until :args ::days-until-args :ret ::j/long)

(s/def ::dates-until-args (args ::j/wip))
(defn -dates-until
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1732
  ([this end-exclusive] (wip ::-dates-until))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1759
  ([this end-exclusive step] (wip ::-dates-until)))
(s/fdef -dates-until :args ::dates-until-args :ret ::j/wip)

(s/def ::at-time-args (args ::j/wip))
(defn -at-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1848
  ([this hour minute] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1867
  ([this hour minute second] (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1886
  ([this hour minute second nano-of-second] (wip ::-at-time)))
(s/fdef -at-time :args ::at-time-args :ret ::local-date-time/local-date-time)

(s/def ::at-start-of-day-args (args ::j/wip))
(defn -at-start-of-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1912
  ([this] (wip ::-at-start-of-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1936
  ([this zone] (wip ::-at-start-of-day)))
(s/fdef -at-start-of-day :args ::at-start-of-day-args :ret ::zoned-date-time/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1989
(s/def ::to-epoch-second-args (args ::local-time/local-time ::zone-offset/zone-offset))
(defn -to-epoch-second [this time offset] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2020
(s/def ::compare-to0-args (args ::local-date))
(defn -compare-to0 [this other-date] (wip ::-compare-to0))
(s/fdef -compare-to0 :args ::compare-to0-args :ret ::j/int)

(extend-type LocalDate
  local-date/ILocalDate
  (get-month [this] (-get-month this))
  (get-day-of-week [this] (-get-day-of-week this))
  (get-day-of-year [this] (-get-day-of-year this))
  (get-year [this] (-get-year this))
  (get-month-value [this] (-get-month-value this))
  (get-day-of-month [this] (-get-day-of-month this))
  (with-year [this year] (-with-year this year))
  (with-month [this month] (-with-month this month))
  (with-day-of-month [this day-of-month] (-with-day-of-month this day-of-month))
  (with-day-of-year [this day-of-year] (-with-day-of-year this day-of-year))
  (plus-years [this years-to-add] (-plus-years this years-to-add))
  (plus-months [this months-to-add] (-plus-months this months-to-add))
  (plus-weeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plus-days [this days-to-add] (-plus-days this days-to-add))
  (minus-years [this years-to-subtract] (-minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (-minus-months this months-to-subtract))
  (minus-weeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minus-days [this days-to-subtract] (-minus-days this days-to-subtract))
  (days-until [this end] (-days-until this end))
  (dates-until
    ([this end-exclusive] (-dates-until this end-exclusive))
    ([this end-exclusive step] (-dates-until this end-exclusive step)))
  (at-time
    ([this hour minute] (-at-time this hour minute))
    ([this hour minute second] (-at-time this hour minute second))
    ([this hour minute second nano-of-second] (-at-time this hour minute second nano-of-second)))
  (at-start-of-day
    ([this] (-at-start-of-day this))
    ([this zone] (-at-start-of-day this zone)))
  (to-epoch-second [this time offset] (-to-epoch-second this time offset))
  (compare-to0 [this other-date] (-compare-to0 this other-date)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2013
(s/def ::compare-to-args (args ::chrono-local-date/chrono-local-date))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalDate
  time-comparable/ITimeComparable
  (compare-to [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L607
(s/def ::length-of-month-args (args))
(defn -length-of-month [this] (wip ::-length-of-month))
(s/fdef -length-of-month :args ::length-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L608
(s/def ::length-of-year-args (args))
(defn -length-of-year [this] (wip ::-length-of-year))
(s/fdef -length-of-year :args ::length-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
(s/def ::is-leap-year-args (args))
(defn -is-leap-year [this] (wip ::-is-leap-year))
(s/fdef -is-leap-year :args ::is-leap-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L682
(s/def ::to-epoch-day-args (args))
(defn -to-epoch-day [this] (wip ::-to-epoch-day))
(s/fdef -to-epoch-day :args ::to-epoch-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L727
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L747
(s/def ::get-era-args (args))
(defn -get-era [this] (wip ::-get-era))
(s/fdef -get-era :args ::get-era-args :ret ::era/era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1704
(s/def ::until-args (args ::chrono-local-date/chrono-local-date))
(defn -until [this end-date-exclusive] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1814
(s/def ::format-args (args ::date-time-formatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1830
(s/def ::at-time--chrono-args (args ::offset-time/offset-time))
(defn -at-time--chrono [this at-time--chrono--overloaded-param] (wip ::-at-time--chrono))
(s/fdef -at-time--chrono :args ::at-time--chrono-args :ret ::offset-date-time/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2053
(s/def ::is-after-args (args ::chrono-local-date/chrono-local-date))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2082
(s/def ::is-before-args (args ::chrono-local-date/chrono-local-date))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2111
(s/def ::is-equal-args (args ::chrono-local-date/chrono-local-date))
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)

(extend-type LocalDate
  chrono-local-date/IChronoLocalDate
  (length-of-month [this] (-length-of-month this))
  (length-of-year [this] (-length-of-year this))
  (is-leap-year [this] (-is-leap-year this))
  (to-epoch-day [this] (-to-epoch-day this))
  (get-chronology [this] (-get-chronology this))
  (get-era [this] (-get-era this))
  (until [this end-date-exclusive] (-until this end-date-exclusive))
  (format [this formatter] (-format this formatter))
  (at-time [this at-time--chrono--overloaded-param] (-at-time--chrono this at-time--chrono--overloaded-param))
  (is-after [this other] (-is-after this other))
  (is-before [this other] (-is-before this other))
  (is-equal [this other] (-is-equal this other)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L932
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1045
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::chrono-local-date/chrono-local-date)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1168
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1259
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1418
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1447
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1643
(s/def ::until--temporal---args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until--temporal [this end-exclusive unit] (wip ::-until--temporal))
(s/fdef -until--temporal :args ::until--temporal---args :ret ::j/long)

(extend-type LocalDate
  temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until--temporal this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L539
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L602
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L648
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L679
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1559
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalDate
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1591
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type LocalDate
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L197
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L213
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::local-date)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L247
(s/def ::of-args (args ::j/int ::month/month ::j/int))
(defn of [of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3] (wip ::of))
(s/fdef of :args ::of-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L287
(s/def ::of-year-day-args (args ::j/int ::j/int))
(defn of-year-day [year day-of-year] (wip ::of-year-day))
(s/fdef of-year-day :args ::of-year-day-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L318
(s/def ::of-instant-args (args ::instant/instant ::zone-id/zone-id))
(defn of-instant [instant zone] (wip ::of-instant))
(s/fdef of-instant :args ::of-instant-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L340
(s/def ::of-epoch-day-args ::impl/of-epoch-day-args)
(def of-epoch-day #'impl/of-epoch-day)
(s/fdef of-epoch-day :args ::of-epoch-day-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L391
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-date)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L412
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L426
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L146
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L151
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L155
(def EPOCH ::EPOCH--not-implemented)
