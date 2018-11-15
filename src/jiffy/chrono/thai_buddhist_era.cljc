(ns jiffy.chrono.thai-buddhist-era
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.chrono.era :as Era]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

(defrecord ThaiBuddhistEra [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L106
(defn -get-value [this] (wip ::-get-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L168
(defn -get-display-name [this style locale] (wip ::-get-display-name))

(extend-type ThaiBuddhistEra
  Era/IEra
  (getValue [this] (-get-value this))
  (getDisplayName [this style locale] (-get-display-name this style locale)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAccessor

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(defn values [] (wip ::values))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java#L137
(defn of [thai-buddhist-era] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(def BEFORE_BE ::BEFORE_BE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ThaiBuddhistEra.java
(def BE ::BE--not-implemented)