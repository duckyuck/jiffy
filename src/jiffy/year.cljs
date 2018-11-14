(ns jiffy.year
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.time-comparable :as TimeComparable]))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L337
(defn -get-value [this] (wip ::-get-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L527
(defn -is-leap [this] (wip ::-is-leap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L540
(defn -is-valid-month-day [this month-day] (wip ::-is-valid-month-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L549
(defn -length [this] (wip ::-length))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L731
(defn -plus-years [this years-to-add] (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L797
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L938
(defn -format [this formatter] (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L956
(defn -at-day [this day-of-year] (wip ::-at-day))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L974
(defn -at-month [this at-month--overloaded-param] (wip ::-at-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1008
(defn -at-month-day [this month-day] (wip ::-at-month-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1033
(defn -is-after [this other] (wip ::-is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L1043
(defn -is-before [this other] (wip ::-is-before))

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
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type Year
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L573
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L619
  ([this field new-value] (wip ::-with)))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L655
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L708
  ([this amount-to-add unit] (wip ::-plus)))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L760
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L784
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L913
(defn -until [this end-exclusive unit] (wip ::-until))

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
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L434
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L468
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L496
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L822
(defn -query [this query] (wip ::-query))

(extend-type Year
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L858
(defn -adjust-into [this temporal] (wip ::-adjust-into))

(extend-type Year
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L175
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L191
  ([now--overloaded-param] (wip ::now)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L225
(defn of [iso-year] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L249
(defn from [temporal] (wip ::from))

(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L276
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L290
  ([text formatter] (wip ::parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java#L315
(defn isLeap [year] (wip ::isLeap))

(def MIN_VALUE -999999999)
(def MAX_VALUE 999999999)
