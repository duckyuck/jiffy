(ns jiffy.chrono.chrono-period-impl
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.temporal.temporal-amount :as TemporalAmount]))

(defrecord ChronoPeriodImpl [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L156
(defn -get-chronology [this] (wip ::-get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L162
(defn -is-zero [this] (wip ::-is-zero))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L167
(defn -is-negative [this] (wip ::-is-negative))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L173
(defn -plus [this amount-to-add] (wip ::-plus))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L183
(defn -minus [this amount-to-subtract] (wip ::-minus))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L212
(defn -multiplied-by [this scalar] (wip ::-multiplied-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L225
(defn -normalized [this] (wip ::-normalized))

(extend-type ChronoPeriodImpl
  ChronoPeriod/IChronoPeriod
  (getChronology [this] (-get-chronology this))
  (isZero [this] (-is-zero this))
  (isNegative [this] (-is-negative this))
  (plus [this amount-to-add] (-plus this amount-to-add))
  (minus [this amount-to-subtract] (-minus this amount-to-subtract))
  (multipliedBy [this scalar] (-multiplied-by this scalar))
  (normalized [this] (-normalized this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L138
(defn -get [this unit] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L151
(defn -get-units [this] (wip ::-get-units))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L255
(defn -add-to [this temporal] (wip ::-add-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L281
(defn -subtract-from [this temporal] (wip ::-subtract-from))

(extend-type ChronoPeriodImpl
  TemporalAmount/ITemporalAmount
  (get [this unit] (-get this unit))
  (getUnits [this] (-get-units this))
  (addTo [this temporal] (-add-to this temporal))
  (subtractFrom [this temporal] (-subtract-from this temporal)))

