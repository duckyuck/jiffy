(ns jiffy.day-of-week
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as TextStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.value-range :as ValueRange]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(defprotocol IDayOfWeek
  (getValue [this])
  (getDisplayName [this style locale])
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
(s/def ::get-display-name-args (args ::TextStyle/text-style ::j/wip))
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
  (getValue [this] (-get-value this))
  (getDisplayName [this style locale] (-get-display-name this style locale))
  (plus [this days] (-plus this days))
  (minus [this days] (-minus this days)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L251
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L281
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L314
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L344
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L406
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type DayOfWeek
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L453
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type DayOfWeek
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(s/def ::value-of-args (args string?))
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))
(s/fdef valueOf :args ::value-of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L163
(s/def ::of-args (args ::j/int))
(defn of [day-of-week] (wip ::of))
(s/fdef of :args ::of-args :ret ::day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/DayOfWeek.java#L187
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
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
