(ns jiffy.chrono.hijrah-era
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.chrono.era :as Era]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

(defrecord HijrahEra [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L82
(defn -get-value [this] (wip ::-get-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L168
(defn -get-display-name [this style locale] (wip ::-get-display-name))

(extend-type HijrahEra
  Era/IEra
  (getValue [this] (-get-value this))
  (getDisplayName [this style locale] (-get-display-name this style locale)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L154
(defn -range [this field] (wip ::-range))

(extend-type HijrahEra
  TemporalAccessor/ITemporalAccessor
  (range [this field] (-range this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(defn values [] (wip ::values))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java#L107
(defn of [hijrah-era] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahEra.java
(def AH ::AH--not-implemented)