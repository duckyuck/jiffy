(ns jiffy.chrono.minguo-era
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.era :as Era]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as TextStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

(defrecord MinguoEra [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::minguo-era (j/constructor-spec MinguoEra create ::create-args))
(s/fdef create :args ::create-args :ret ::minguo-era)

(defmacro args [& x] `(s/tuple ::minguo-era ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java#L106
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java#L168
(s/def ::get-display-name-args (args ::TextStyle/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

(extend-type MinguoEra
  Era/IEra
  (getValue [this] (-get-value this))
  (getDisplayName [this style locale] (-get-display-name this style locale)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAccessor

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java
(s/def ::value-of-args (args string?))
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))
(s/fdef valueOf :args ::value-of-args :ret ::minguo-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java#L137
(s/def ::of-args (args ::j/int))
(defn of [minguo-era] (wip ::of))
(s/fdef of :args ::of-args :ret ::minguo-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java
(def BEFORE_ROC ::BEFORE_ROC--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/MinguoEra.java
(def ROC ::ROC--not-implemented)
