(ns jiffy.chrono.chrono-period-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.specs :as j]))

;; TODO: remove this ns and protocol. Only used indirectly to support Dates for other chronologies

(defrecord ChronoPeriodImpl [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::chrono-period-impl (j/constructor-spec ChronoPeriodImpl create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-period-impl)

(defmacro args [& x] `(s/tuple ::chrono-period-impl ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L156
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L162
(s/def ::is-zero-args (args))
(defn -is-zero [this] (wip ::-is-zero))
(s/fdef -is-zero :args ::is-zero-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L167
(s/def ::is-negative-args (args))
(defn -is-negative [this] (wip ::-is-negative))
(s/fdef -is-negative :args ::is-negative-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L173
(s/def ::plus-args (args ::temporal-amount/temporal-amount))
(defn -plus [this amount-to-add] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L183
(s/def ::minus-args (args ::temporal-amount/temporal-amount))
(defn -minus [this amount-to-subtract] (wip ::-minus))
(s/fdef -minus :args ::minus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L212
(s/def ::multiplied-by-args (args ::j/int))
(defn -multiplied-by [this scalar] (wip ::-multiplied-by))
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L225
(s/def ::normalized-args (args))
(defn -normalized [this] (wip ::-normalized))
(s/fdef -normalized :args ::normalized-args :ret ::chrono-period/chrono-period)

(extend-type ChronoPeriodImpl
  chrono-period/IChronoPeriod
  (get-chronology [this] (-get-chronology this))
  (is-zero [this] (-is-zero this))
  (is-negative [this] (-is-negative this))
  (plus [this amount-to-add] (-plus this amount-to-add))
  (minus [this amount-to-subtract] (-minus this amount-to-subtract))
  (multiplied-by [this scalar] (-multiplied-by this scalar))
  (normalized [this] (-normalized this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L138
(s/def ::get-args (args ::temporal-unit/temporal-unit))
(defn -get [this unit] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L151
(s/def ::get-units-args (args))
(defn -get-units [this] (wip ::-get-units))
(s/fdef -get-units :args ::get-units-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L255
(s/def ::add-to-args (args ::temporal/temporal))
(defn -add-to [this temporal] (wip ::-add-to))
(s/fdef -add-to :args ::add-to-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriodImpl.java#L281
(s/def ::subtract-from-args (args ::temporal/temporal))
(defn -subtract-from [this temporal] (wip ::-subtract-from))
(s/fdef -subtract-from :args ::subtract-from-args :ret ::temporal/temporal)

(extend-type ChronoPeriodImpl
  temporal-amount/ITemporalAmount
  (get [this unit] (-get this unit))
  (get-units [this] (-get-units this))
  (add-to [this temporal] (-add-to this temporal))
  (subtract-from [this temporal] (-subtract-from this temporal)))

