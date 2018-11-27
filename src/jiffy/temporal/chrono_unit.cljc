(ns jiffy.temporal.chrono-unit
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :as Duration]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.math :as math]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [name duration]
  IChronoUnit)

(s/def ::create-args (s/tuple string? ::Duration/duration))
(defn create [name estimated-duration] (->ChronoUnit name estimated-duration))
(s/def ::chrono-unit (j/constructor-spec ChronoUnit create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-unit)

(defmacro args [& x] `(s/tuple ::chrono-unit ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L210
(s/def ::get-duration-args (args))
(defn -get-duration [this] (wip ::-get-duration))
(s/fdef -get-duration :args ::get-duration-args :ret ::Duration/duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L226
(s/def ::is-duration-estimated-args (args))
(defn -is-duration-estimated [this] (wip ::-is-duration-estimated))
(s/fdef -is-duration-estimated :args ::is-duration-estimated-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L240
(s/def ::is-date-based-args (args))
(defn -is-date-based [this] (wip ::-is-date-based))
(s/fdef -is-date-based :args ::is-date-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L253
(s/def ::is-time-based-args (args))
(defn -is-time-based [this] (wip ::-is-time-based))
(s/fdef -is-time-based :args ::is-time-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L254
(s/def ::add-to-args (args ::Temporal/temporal ::j/long))
(defn -add-to [this temporal amount] (wip ::-add-to))
(s/fdef -add-to :args ::add-to-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L259
(s/def ::is-supported-by-args (args ::Temporal/temporal))
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))
(s/fdef -is-supported-by :args ::is-supported-by-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L271
(s/def ::between-args (args ::Temporal/temporal ::Temporal/temporal))
(defn -between [this temporal-1-inclusive temporal-2-exclusive] (wip ::-between))
(s/fdef -between :args ::between-args :ret ::j/long)

(extend-type ChronoUnit
  TemporalUnit/ITemporalUnit
  (getDuration [this] (-get-duration this))
  (isDurationEstimated [this] (-is-duration-estimated this))
  (isDateBased [this] (-is-date-based this))
  (isTimeBased [this] (-is-time-based this))
  (addTo [this temporal amount] (-add-to this temporal amount))
  (isSupportedBy [this temporal] (-is-supported-by this temporal))
  (between [this temporal-1-inclusive temporal-2-exclusive] (-between this temporal-1-inclusive temporal-2-exclusive)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(s/def ::value-of-args (args string?))
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))
(s/fdef valueOf :args ::value-of-args :ret ::chrono-unit)

(def NANOS (->ChronoUnit "Nanos" (Duration/ofNanos 1)))
(def MICROS (->ChronoUnit "Micros" (Duration/ofNanos 1000)))
(def MILLIS (->ChronoUnit "Millis" (Duration/ofNanos 1000000)))
(def SECONDS (->ChronoUnit "Seconds" (Duration/ofSeconds 1)))
(def MINUTES (->ChronoUnit "Minutes" (Duration/ofSeconds 60)))
(def HOURS (->ChronoUnit "Hours" (Duration/ofSeconds 3600)))
(def HALF_DAYS (->ChronoUnit "HalfDays" (Duration/ofSeconds 43200)))
(def DAYS (->ChronoUnit "Days" (Duration/ofSeconds 86400)))
(def WEEKS (->ChronoUnit "Weeks" (Duration/ofSeconds (* 7 86400))))
(def MONTHS (->ChronoUnit "Months" (Duration/ofSeconds (/ 31556952 12))))
(def YEARS (->ChronoUnit "Years" (Duration/ofSeconds 31556952)))
(def DECADES (->ChronoUnit "Decades" (Duration/ofSeconds (* 31556952 10))))
(def CENTURIES (->ChronoUnit "Centuries" (Duration/ofSeconds (* 31556952 100))))
(def MILLENNIA (->ChronoUnit "Millennia" (Duration/ofSeconds (* 31556952 1000))))
(def ERAS (->ChronoUnit "Eras" (Duration/ofSeconds (* 31556952 1000000000))))
(def FOREVER (->ChronoUnit "Forever" (Duration/ofSeconds math/long-max-value 999999999)))
