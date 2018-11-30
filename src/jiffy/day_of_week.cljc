(ns jiffy.day-of-week
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.value-range :as value-range]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(defprotocol IDayOfWeek
  (get-value [this])
  (get-display-name [this style locale])
  (plus [this days])
  (minus [this days]))

(defrecord DayOfWeek [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::day-of-week (j/constructor-spec DayOfWeek create ::create-args))
(s/fdef create :args ::create-args :ret ::day-of-week)

(defmacro args [& x] `(s/tuple ::day-of-week ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L98
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L226
(s/def ::get-display-name-args (args ::text-style/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L365
(s/def ::plus-args (args ::j/long))
(defn -plus [this days] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L381
(s/def ::minus-args (args ::j/long))
(defn -minus [this days] (wip ::-minus))
(s/fdef -minus :args ::minus-args :ret ::day-of-week)

(extend-type DayOfWeek
  IDayOfWeek
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale))
  (plus [this days] (-plus this days))
  (minus [this days] (-minus this days)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L251
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L281
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L314
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L344
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L406
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type DayOfWeek
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L453
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type DayOfWeek
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L163
(s/def ::of-args (args ::j/int))
(defn of [day-of-week] (wip ::of))
(s/fdef of :args ::of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L187
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def SATURDAY ::SATURDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def THURSDAY ::THURSDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def FRIDAY ::FRIDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def WEDNESDAY ::WEDNESDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def SUNDAY ::SUNDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def MONDAY ::MONDAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(def TUESDAY ::TUESDAY--not-implemented)