(ns jiffy.chrono.iso-era
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.chrono.iso-era :as iso-era]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.specs :as j]))

(defrecord IsoEra [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::iso-era (j/constructor-spec IsoEra create ::create-args))
(s/fdef create :args ::create-args :ret ::iso-era)

(defmacro args [& x] `(s/tuple ::iso-era ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java#L99
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

(extend-type IsoEra
  era/IEra
  (get-value [this] (-get-value this)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAccessor

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::iso-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java#L130
(s/def ::of-args (args ::j/int))
(defn of [iso-era] (wip ::of))
(s/fdef of :args ::of-args :ret ::iso-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java
(def CE ::CE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoEra.java
(def BCE ::BCE--not-implemented)