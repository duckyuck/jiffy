(ns jiffy.chrono.thai-buddhist-era
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.era :as era]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]))

(defrecord ThaiBuddhistEra [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::thai-buddhist-era (j/constructor-spec ThaiBuddhistEra create ::create-args))
(s/fdef create :args ::create-args :ret ::thai-buddhist-era)

(defmacro args [& x] `(s/tuple ::thai-buddhist-era ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L106
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L168
(s/def ::get-display-name-args (args ::text-style/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

(extend-type ThaiBuddhistEra
  era/IEra
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAccessor

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::thai-buddhist-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L137
(s/def ::of-args (args ::j/int))
(defn of [thai-buddhist-era] (wip ::of))
(s/fdef of :args ::of-args :ret ::thai-buddhist-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(def BEFORE_BE ::BEFORE_BE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(def BE ::BE--not-implemented)