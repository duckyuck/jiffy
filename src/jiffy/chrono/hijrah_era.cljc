(ns jiffy.chrono.hijrah-era
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.era :as era]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.value-range :as value-range]))

(defrecord HijrahEra [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::hijrah-era (j/constructor-spec HijrahEra create ::create-args))
(s/fdef create :args ::create-args :ret ::hijrah-era)

(defmacro args [& x] `(s/tuple ::hijrah-era ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L82
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L168
(s/def ::get-display-name-args (args ::text-style/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

(extend-type HijrahEra
  era/IEra
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L154
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

(extend-type HijrahEra
  temporal-accessor/ITemporalAccessor
  (range [this field] (-range this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::hijrah-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L107
(s/def ::of-args (args ::j/int))
(defn of [hijrah-era] (wip ::of))
(s/fdef of :args ::of-args :ret ::hijrah-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(def AH ::AH--not-implemented)