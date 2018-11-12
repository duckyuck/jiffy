(ns jiffy.period
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.temporal.temporal-amount :as TemporalAmount]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java
(defprotocol IPeriod
  (getYears [this])
  (getMonths [this])
  (getDays [this])
  (withYears [this years])
  (withMonths [this months])
  (withDays [this days])
  (plusYears [this years-to-add])
  (plusMonths [this months-to-add])
  (plusDays [this days-to-add])
  (minusYears [this years-to-subtract])
  (minusMonths [this months-to-subtract])
  (minusDays [this days-to-subtract])
  (toTotalMonths [this]))

(defrecord Period [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L435
(defn -get-years [this] (wip ::-get-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L437
(defn -get-months [this] (wip ::-get-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L439
(defn -get-days [this] (wip ::-get-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L558
(defn -with-years [this years] (wip ::-with-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L580
(defn -with-months [this months] (wip ::-with-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L598
(defn -with-days [this days] (wip ::-with-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L647
(defn -plus-years [this years-to-add] (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L667
(defn -plus-months [this months-to-add] (wip ::-plus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L687
(defn -plus-days [this days-to-add] (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L736
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L753
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L770
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L835
(defn -to-total-months [this] (wip ::-to-total-months))

(extend-type Period
  IPeriod
  (getYears [this] (-get-years this))
  (getMonths [this] (-get-months this))
  (getDays [this] (-get-days this))
  (withYears [this years] (-with-years this years))
  (withMonths [this months] (-with-months this months))
  (withDays [this days] (-with-days this days))
  (plusYears [this years-to-add] (-plus-years this years-to-add))
  (plusMonths [this months-to-add] (-plus-months this months-to-add))
  (plusDays [this days-to-add] (-plus-days this days-to-add))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (minusMonths [this months-to-subtract] (-minus-months this months-to-subtract))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract))
  (toTotalMonths [this] (-to-total-months this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L473
(defn -get-chronology [this] (wip ::-get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L485
(defn -is-zero [this] (wip ::-is-zero))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L496
(defn -is-negative [this] (wip ::-is-negative))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L626
(defn -plus [this amount-to-add] (wip ::-plus))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L715
(defn -minus [this amount-to-subtract] (wip ::-minus))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L789
(defn -multiplied-by [this scalar] (wip ::-multiplied-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L812
(defn -negated [this] (wip ::-negated))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L834
(defn -normalized [this] (wip ::-normalized))

(extend-type Period
  ChronoPeriod/IChronoPeriod
  (getChronology [this] (-get-chronology this))
  (isZero [this] (-is-zero this))
  (isNegative [this] (-is-negative this))
  (plus [this amount-to-add] (-plus this amount-to-add))
  (minus [this amount-to-subtract] (-minus this amount-to-subtract))
  (multipliedBy [this scalar] (-multiplied-by this scalar))
  (negated [this] (-negated this))
  (normalized [this] (-normalized this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L433
(defn -get [this unit] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L458
(defn -get-units [this] (wip ::-get-units))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L894
(defn -add-to [this temporal] (wip ::-add-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L947
(defn -subtract-from [this temporal] (wip ::-subtract-from))

(extend-type Period
  TemporalAmount/ITemporalAmount
  (get [this unit] (-get this unit))
  (getUnits [this] (-get-units this))
  (addTo [this temporal] (-add-to this temporal))
  (subtractFrom [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L178
(defn ofYears [years] (wip ::ofYears))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L191
(defn ofMonths [months] (wip ::ofMonths))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L205
(defn ofWeeks [weeks] (wip ::ofWeeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L218
(defn ofDays [days] (wip ::ofDays))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L233
(defn of [years months days] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L257
(defn from [amount] (wip ::from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L325
(defn parse [text] (wip ::parse))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L386
(defn between [start-date-inclusive end-date-exclusive] (wip ::between))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L139
(def ZERO ::ZERO--not-implemented)