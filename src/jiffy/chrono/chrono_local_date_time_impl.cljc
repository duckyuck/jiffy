(ns jiffy.chrono.chrono-local-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time :as LocalTime]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java
(defprotocol IChronoLocalDateTimeImpl
  (plusSeconds [this seconds]))

(defrecord ChronoLocalDateTimeImpl [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::chrono-local-date-time-impl (j/constructor-spec ChronoLocalDateTimeImpl create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-local-date-time-impl)

(defmacro args [& x] `(s/tuple ::chrono-local-date-time-impl ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L333
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::chrono-local-date-time-impl)

(extend-type ChronoLocalDateTimeImpl
  IChronoLocalDateTimeImpl
  (plusSeconds [this seconds] (-plus-seconds this seconds)))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L228
(s/def ::to-local-date-args (args))
(defn -to-local-date [this] (wip ::-to-local-date))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::ChronoLocalDate/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L233
(s/def ::to-local-time-args (args))
(defn -to-local-time [this] (wip ::-to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L365
(s/def ::at-zone-args (args ::ZoneId/zone-id))
(defn -at-zone [this zone] (wip ::-at-zone))
(s/fdef -at-zone :args ::at-zone-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

(extend-type ChronoLocalDateTimeImpl
  ChronoLocalDateTime/IChronoLocalDateTime
  (toLocalDate [this] (-to-local-date this))
  (toLocalTime [this] (-to-local-time this))
  (atZone [this zone] (-at-zone this zone)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L277
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L217
  ([this new-date new-time] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L304
(s/def ::plus-args (args ::j/long ::TemporalUnit/temporal-unit))
(defn -plus [this amount-to-add unit] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::chrono-local-date-time-impl)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L371
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type ChronoLocalDateTimeImpl
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this new-date new-time] (-with this new-date new-time)))
  (plus [this amount-to-add unit] (-plus this amount-to-add unit))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L239
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L248
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L257
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L266
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

(extend-type ChronoLocalDateTimeImpl
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L173
(s/def ::of-args (args ::ChronoLocalDate/chrono-local-date ::LocalTime/local-time))
(defn of [date time] (wip ::of))
(s/fdef of :args ::of-args :ret ::chrono-local-date-time-impl)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L186
(s/def ::ensure-valid-args (args ::Chronology/chronology ::Temporal/temporal))
(defn ensureValid [chrono temporal] (wip ::ensureValid))
(s/fdef ensureValid :args ::ensure-valid-args :ret ::chrono-local-date-time-impl)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L110
(def HOURS_PER_DAY ::HOURS_PER_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L114
(def MINUTES_PER_HOUR ::MINUTES_PER_HOUR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L118
(def MINUTES_PER_DAY ::MINUTES_PER_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L122
(def SECONDS_PER_MINUTE ::SECONDS_PER_MINUTE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L126
(def SECONDS_PER_HOUR ::SECONDS_PER_HOUR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L130
(def SECONDS_PER_DAY ::SECONDS_PER_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L134
(def MILLIS_PER_DAY ::MILLIS_PER_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L138
(def MICROS_PER_DAY ::MICROS_PER_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L142
(def NANOS_PER_SECOND ::NANOS_PER_SECOND--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L146
(def NANOS_PER_MINUTE ::NANOS_PER_MINUTE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L150
(def NANOS_PER_HOUR ::NANOS_PER_HOUR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java#L154
(def NANOS_PER_DAY ::NANOS_PER_DAY--not-implemented)
