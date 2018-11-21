(ns jiffy.year
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.local-date :as LocalDate]
            [jiffy.month :as Month]
            [jiffy.month-day :as MonthDay]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.year-impl :as impl]
            [jiffy.year-month :as YearMonth]
            [jiffy.zone-id :as ZoneId]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java
(defprotocol IYear
  (getValue [this])
  (isLeap [this])
  (isValidMonthDay [this month-day])
  (length [this])
  (plusYears [this years-to-add])
  (minusYears [this years-to-subtract])
  (format [this formatter])
  (atDay [this day-of-year])
  (atMonth [this at-month--overloaded-param])
  (atMonthDay [this month-day])
  (isAfter [this other])
  (isBefore [this other]))

(defrecord Year [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::year (j/constructor-spec Year create ::create-args))
(s/fdef create :args ::create-args :ret ::year)

(defmacro args [& x] `(s/tuple ::year ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L337
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L527
(s/def ::is-leap-args (args))
(defn -is-leap [this] (wip ::-is-leap))
(s/fdef -is-leap :args ::is-leap-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L540
(s/def ::is-valid-month-day-args (args ::MonthDay/month-day))
(defn -is-valid-month-day [this month-day] (wip ::-is-valid-month-day))
(s/fdef -is-valid-month-day :args ::is-valid-month-day-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L549
(s/def ::length-args (args))
(defn -length [this] (wip ::-length))
(s/fdef -length :args ::length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L731
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years-to-add] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L797
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L938
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L956
(s/def ::at-day-args (args ::j/int))
(defn -at-day [this day-of-year] (wip ::-at-day))
(s/fdef -at-day :args ::at-day-args :ret ::LocalDate/local-date)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L974
(s/def ::at-month-args (args ::Month/month))
(defn -at-month [this at-month--overloaded-param] (wip ::-at-month))
(s/fdef -at-month :args ::at-month-args :ret ::YearMonth/year-month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1008
(s/def ::at-month-day-args (args ::MonthDay/month-day))
(defn -at-month-day [this month-day] (wip ::-at-month-day))
(s/fdef -at-month-day :args ::at-month-day-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1033
(s/def ::is-after-args (args ::year))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1043
(s/def ::is-before-args (args ::year))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

(extend-type Year
  IYear
  (getValue [this] (-get-value this))
  (isLeap [this] (-is-leap this))
  (isValidMonthDay [this month-day] (-is-valid-month-day this month-day))
  (length [this] (-length this))
  (plusYears [this years-to-add] (-plus-years this years-to-add))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (format [this formatter] (-format this formatter))
  (atDay [this day-of-year] (-at-day this day-of-year))
  (atMonth [this at-month--overloaded-param] (-at-month this at-month--overloaded-param))
  (atMonthDay [this month-day] (-at-month-day this month-day))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1023
(s/def ::compare-to-args (args ::year))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type Year
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L573
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L619
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L655
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L708
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::year)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L760
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L784
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L913
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type Year
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L368
(s/def ::is-supported-args (args ::TemporalUnit/temporal-unit))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L434
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L468
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L496
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L822
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Year
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L858
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type Year
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L175
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L191
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L225
(s/def ::of-args (args ::j/int))
(defn of [iso-year] (wip ::of))
(s/fdef of :args ::of-args :ret ::year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L249
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::year)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L276
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L290
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L315
;; TODO: figure out how to handle this (Java static) function. Replaces function in IYear protocol
;; (s/def ::is-leap-args (args ::j/long))
;; (defn isLeap [year] (wip ::isLeap))
;; (s/fdef isLeap :args ::is-leap-args :ret ::j/boolean)

(def MIN_VALUE impl/MIN_VALUE)
(def MAX_VALUE impl/MAX_VALUE)
