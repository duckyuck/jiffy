(ns jiffy.chrono.chrono-period-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-period :as ChronoPeriod]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-unit :as TemporalUnit]))

(defrecord ChronoPeriodImpl [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::chrono-period-impl (j/constructor-spec ChronoPeriodImpl create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-period-impl)

(defmacro args [& x] `(s/tuple ::chrono-period-impl ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L156
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L162
(s/def ::is-zero-args (args))
(defn -is-zero [this] (wip ::-is-zero))
(s/fdef -is-zero :args ::is-zero-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L167
(s/def ::is-negative-args (args))
(defn -is-negative [this] (wip ::-is-negative))
(s/fdef -is-negative :args ::is-negative-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L173
(s/def ::plus-args (args ::TemporalAmount/temporal-amount))
(defn -plus [this amount-to-add] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::ChronoPeriod/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L183
(s/def ::minus-args (args ::TemporalAmount/temporal-amount))
(defn -minus [this amount-to-subtract] (wip ::-minus))
(s/fdef -minus :args ::minus-args :ret ::ChronoPeriod/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L212
(s/def ::multiplied-by-args (args ::j/int))
(defn -multiplied-by [this scalar] (wip ::-multiplied-by))
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::ChronoPeriod/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L225
(s/def ::normalized-args (args))
(defn -normalized [this] (wip ::-normalized))
(s/fdef -normalized :args ::normalized-args :ret ::ChronoPeriod/chrono-period)

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
(s/def ::get-args (args ::TemporalUnit/temporal-unit))
(defn -get [this unit] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L151
(s/def ::get-units-args (args))
(defn -get-units [this] (wip ::-get-units))
(s/fdef -get-units :args ::get-units-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L255
(s/def ::add-to-args (args ::Temporal/temporal))
(defn -add-to [this temporal] (wip ::-add-to))
(s/fdef -add-to :args ::add-to-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L281
(s/def ::subtract-from-args (args ::Temporal/temporal))
(defn -subtract-from [this temporal] (wip ::-subtract-from))
(s/fdef -subtract-from :args ::subtract-from-args :ret ::Temporal/temporal)

(extend-type ChronoPeriodImpl
  TemporalAmount/ITemporalAmount
  (get [this unit] (-get this unit))
  (getUnits [this] (-get-units this))
  (addTo [this temporal] (-add-to this temporal))
  (subtractFrom [this temporal] (-subtract-from this temporal)))

