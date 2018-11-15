(ns jiffy.chrono.chrono-local-date-impl
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java
(defprotocol IChronoLocalDateImpl
  (plusYears [this years-to-add])
  (plusMonths [this months-to-add])
  (plusWeeks [this weeks-to-add])
  (plusDays [this days-to-add])
  (minusYears [this years-to-subtract])
  (minusMonths [this months-to-subtract])
  (minusWeeks [this weeks-to-subtract])
  (minusDays [this days-to-subtract]))

(defrecord ChronoLocalDateImpl [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L243
(defn -plus-years [this years-to-add] (wip ::-plus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L259
(defn -plus-months [this months-to-add] (wip ::-plus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L276
(defn -plus-weeks [this weeks-to-add] (wip ::-plus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L291
(defn -plus-days [this days-to-add] (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L311
(defn -minus-years [this years-to-subtract] (wip ::-minus-years))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L332
(defn -minus-months [this months-to-subtract] (wip ::-minus-months))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L352
(defn -minus-weeks [this weeks-to-subtract] (wip ::-minus-weeks))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L370
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))

(extend-type ChronoLocalDateImpl
  IChronoLocalDateImpl
  (plusYears [this years-to-add] (-plus-years this years-to-add))
  (plusMonths [this months-to-add] (-plus-months this months-to-add))
  (plusWeeks [this weeks-to-add] (-plus-weeks this weeks-to-add))
  (plusDays [this days-to-add] (-plus-days this days-to-add))
  (minusYears [this years-to-subtract] (-minus-years this years-to-subtract))
  (minusMonths [this months-to-subtract] (-minus-months this months-to-subtract))
  (minusWeeks [this weeks-to-subtract] (-minus-weeks this weeks-to-subtract))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; FIXME: no implementation found from inherited class interface java.time.chrono.ChronoLocalDate

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L178
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L184
  ([this field value] (wip ::-with)))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L191
  ([this amount] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L198
  ([this amount-to-add unit] (wip ::-plus)))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L218
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L224
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L376
(defn -until [this end-exclusive unit] (wip ::-until))

(extend-type ChronoLocalDateImpl
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field value] (-with this field value)))
  (plus
    ([this amount] (-plus this amount))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount] (-minus this amount))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAccessor

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java#L160
(defn ensureValid [chrono temporal] (wip ::ensureValid))
