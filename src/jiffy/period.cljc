(ns jiffy.period
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-period :as chrono-period]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.chrono.iso-chronology-impl :as iso-chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :as local-date]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal-unit :as temporal-unit]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java
(defprotocol IPeriod
  (get-years [this])
  (get-months [this])
  (get-days [this])
  (with-years [this years])
  (with-months [this months])
  (with-days [this days])
  (plus-years [this years-to-add])
  (plus-months [this months-to-add])
  (plus-days [this days-to-add])
  (minus-years [this years-to-subtract])
  (minus-months [this months-to-subtract])
  (minus-days [this days-to-subtract])
  (to-total-months [this]))

(defrecord Period [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::period (j/constructor-spec Period create ::create-args))
(s/fdef create :args ::create-args :ret ::period)

(defmacro args [& x] `(s/tuple ::period ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L435
(s/def ::get-years-args (args))
(defn -get-years [this] (wip ::-get-years))
(s/fdef -get-years :args ::get-years-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L437
(s/def ::get-months-args (args))
(defn -get-months [this] (wip ::-get-months))
(s/fdef -get-months :args ::get-months-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L439
(s/def ::get-days-args (args))
(defn -get-days [this] (wip ::-get-days))
(s/fdef -get-days :args ::get-days-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L558
(s/def ::with-years-args (args ::j/int))
(defn -with-years [this years] (wip ::-with-years))
(s/fdef -with-years :args ::with-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L580
(s/def ::with-months-args (args ::j/int))
(defn -with-months [this months] (wip ::-with-months))
(s/fdef -with-months :args ::with-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L598
(s/def ::with-days-args (args ::j/int))
(defn -with-days [this days] (wip ::-with-days))
(s/fdef -with-days :args ::with-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L647
(s/def ::plus-years-args (args ::j/long))
(defn -plus-years [this years-to-add] (wip ::-plus-years))
(s/fdef -plus-years :args ::plus-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L667
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months-to-add] (wip ::-plus-months))
(s/fdef -plus-months :args ::plus-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L687
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days-to-add] (wip ::-plus-days))
(s/fdef -plus-days :args ::plus-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L736
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))
(s/fdef -minus-years :args ::minus-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L753
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))
(s/fdef -minus-months :args ::minus-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L770
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))
(s/fdef -minus-days :args ::minus-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L835
(s/def ::to-total-months-args (args))
(defn -to-total-months [this] (wip ::-to-total-months))
(s/fdef -to-total-months :args ::to-total-months-args :ret ::j/long)

(extend-type Period
  IPeriod
  (get-years [this] (-get-years this))
  (get-months [this] (-get-months this))
  (get-days [this] (-get-days this))
  (with-years [this years] (-with-years this years))
  (with-months [this months] (-with-months this months))
  (with-days [this days] (-with-days this days))
  (plus-years [this years-to-add] (-plus-years this years-to-add))
  (plus-months [this months-to-add] (-plus-months this months-to-add))
  (plus-days [this days-to-add] (-plus-days this days-to-add))
  (minus-years [this years-to-subtract] (-minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (-minus-months this months-to-subtract))
  (minus-days [this days-to-subtract] (-minus-days this days-to-subtract))
  (to-total-months [this] (-to-total-months this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L473
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::iso-chronology/iso-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L485
(s/def ::is-zero-args (args))
(defn -is-zero [this] (wip ::-is-zero))
(s/fdef -is-zero :args ::is-zero-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L496
(s/def ::is-negative-args (args))
(defn -is-negative [this] (wip ::-is-negative))
(s/fdef -is-negative :args ::is-negative-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L626
(s/def ::plus-args (args ::temporal-amount/temporal-amount))
(defn -plus [this amount-to-add] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L715
(s/def ::minus-args (args ::temporal-amount/temporal-amount))
(defn -minus [this amount-to-subtract] (wip ::-minus))
(s/fdef -minus :args ::minus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L789
(s/def ::multiplied-by-args (args ::j/int))
(defn -multiplied-by [this scalar] (wip ::-multiplied-by))
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L812
(s/def ::negated-args (args))
(defn -negated [this] (wip ::-negated))
(s/fdef -negated :args ::negated-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L834
(s/def ::normalized-args (args))
(defn -normalized [this] (wip ::-normalized))
(s/fdef -normalized :args ::normalized-args :ret ::chrono-period/chrono-period)

(extend-type Period
  chrono-period/IChronoPeriod
  (get-chronology [this] (-get-chronology this))
  (is-zero [this] (-is-zero this))
  (is-negative [this] (-is-negative this))
  (plus [this amount-to-add] (-plus this amount-to-add))
  (minus [this amount-to-subtract] (-minus this amount-to-subtract))
  (multiplied-by [this scalar] (-multiplied-by this scalar))
  (negated [this] (-negated this))
  (normalized [this] (-normalized this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L433
(s/def ::get-args (args ::temporal-unit/temporal-unit))
(defn -get [this unit] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L458
(s/def ::get-units-args (args))
(defn -get-units [this] (wip ::-get-units))
(s/fdef -get-units :args ::get-units-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L894
(s/def ::add-to-args (args ::temporal/temporal))
(defn -add-to [this temporal] (wip ::-add-to))
(s/fdef -add-to :args ::add-to-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L947
(s/def ::subtract-from-args (args ::temporal/temporal))
(defn -subtract-from [this temporal] (wip ::-subtract-from))
(s/fdef -subtract-from :args ::subtract-from-args :ret ::temporal/temporal)

(extend-type Period
  temporal-amount/ITemporalAmount
  (get [this unit] (-get this unit))
  (get-units [this] (-get-units this))
  (add-to [this temporal] (-add-to this temporal))
  (subtract-from [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L178
(s/def ::of-years-args (args ::j/int))
(defn of-years [years] (wip ::of-years))
(s/fdef of-years :args ::of-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L191
(s/def ::of-months-args (args ::j/int))
(defn of-months [months] (wip ::of-months))
(s/fdef of-months :args ::of-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L205
(s/def ::of-weeks-args (args ::j/int))
(defn of-weeks [weeks] (wip ::of-weeks))
(s/fdef of-weeks :args ::of-weeks-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L218
(s/def ::of-days-args (args ::j/int))
(defn of-days [days] (wip ::of-days))
(s/fdef of-days :args ::of-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L233
(s/def ::of-args (args ::j/int ::j/int ::j/int))
(defn of [years months days] (wip ::of))
(s/fdef of :args ::of-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L257
(s/def ::from-args (args ::temporal-amount/temporal-amount))
(defn from [amount] (wip ::from))
(s/fdef from :args ::from-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L325
(s/def ::parse-args (args ::j/wip))
(defn parse [text] (wip ::parse))
(s/fdef parse :args ::parse-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L386
(s/def ::between-args (args ::local-date/local-date ::local-date/local-date))
(defn between [start-date-inclusive end-date-exclusive] (wip ::between))
(s/fdef between :args ::between-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L139
(def ZERO ::ZERO--not-implemented)
