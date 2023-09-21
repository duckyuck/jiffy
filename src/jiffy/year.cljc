(ns jiffy.year
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.month :as month]
            [jiffy.protocols.month-day :as month-day]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.year :as year]
            [jiffy.protocols.year-month :as year-month]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.year-impl :as impl]))

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
(s/def ::is-valid-month-day-args (args ::month-day/month-day))
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
(s/def ::format-args (args ::date-time-formatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L956
(s/def ::at-day-args (args ::j/int))
(defn -at-day [this day-of-year] (wip ::-at-day))
(s/fdef -at-day :args ::at-day-args :ret ::local-date/local-date)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L974
(s/def ::at-month-args (args ::month/month))
(defn -at-month [this at-month--overloaded-param] (wip ::-at-month))
(s/fdef -at-month :args ::at-month-args :ret ::year-month/year-month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1008
(s/def ::at-month-day-args (args ::month-day/month-day))
(defn -at-month-day [this month-day] (wip ::-at-month-day))
(s/fdef -at-month-day :args ::at-month-day-args :ret ::local-date/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1033
(s/def ::is-after-args (args ::year))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1043
(s/def ::is-before-args (args ::year))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

(extend-type Year
  year/IYear
  (get-value [this] (-get-value this))
  (is-leap [this] (-is-leap this))
  (is-valid-month-day [this month-day] (-is-valid-month-day this month-day))
  (length [this] (-length this))
  (plus-years [this years-to-add] (-plus-years this years-to-add))
  (minus-years [this years-to-subtract] (-minus-years this years-to-subtract))
  (format [this formatter] (-format this formatter))
  (at-day [this day-of-year] (-at-day this day-of-year))
  (at-month [this at-month--overloaded-param] (-at-month this at-month--overloaded-param))
  (at-month-day [this month-day] (-at-month-day this month-day))
  (is-after [this other] (-is-after this other))
  (is-before [this other] (-is-before this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1023
(s/def ::compare-to-args (args ::year))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type Year
  time-comparable/ITimeComparable
  (compare-to [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L573
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L619
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

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
(s/fdef -minus :args ::minus-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L913
(s/def ::until-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type Year
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
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L368
(s/def ::is-supported-args (args ::temporal-unit/temporal-unit))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L434
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L468
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L496
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L822
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Year
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L858
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type Year
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

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
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
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
(s/def ::is-leap-args (args ::j/long))
(defn is-leap [year] (wip ::is-leap))
(s/fdef is-leap :args ::is-leap-args :ret ::j/boolean)

(def MIN_VALUE impl/MIN_VALUE)
(def MAX_VALUE impl/MAX_VALUE)
