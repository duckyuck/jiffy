(ns jiffy.temporal.chrono-unit
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :as Duration]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.math :as math]
            [jiffy.enum #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [name duration]
  IChronoUnit)

(s/def ::create-args (s/tuple string? ::Duration/duration))
(defn create [ordinal enum-name name estimated-duration] (->ChronoUnit name estimated-duration))
(s/def ::chrono-unit (j/constructor-spec ChronoUnit create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-unit)

(defmacro args [& x] `(s/tuple ::chrono-unit ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L210
(s/def ::get-duration-args (args))
(defn -get-duration [this] (:duration this))
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

(defenum create
  {NANOS ["Nanos" (Duration/ofNanos 1)]
   MICROS ["Micros" (Duration/ofNanos 1000)]
   MILLIS ["Millis" (Duration/ofNanos 1000000)]
   SECONDS ["Seconds" (Duration/ofSeconds 1)]
   MINUTES ["Minutes" (Duration/ofSeconds 60)]
   HOURS ["Hours" (Duration/ofSeconds 3600)]
   HALF_DAYS ["HalfDays" (Duration/ofSeconds 43200)]
   DAYS ["Days" (Duration/ofSeconds 86400)]
   WEEKS ["Weeks" (Duration/ofSeconds (* 7 86400))]
   MONTHS ["Months" (Duration/ofSeconds (/ 31556952 12))]
   YEARS ["Years" (Duration/ofSeconds 31556952)]
   DECADES ["Decades" (Duration/ofSeconds (* 31556952 10))]
   CENTURIES ["Centuries" (Duration/ofSeconds (* 31556952 100))]
   MILLENNIA ["Millennia" (Duration/ofSeconds (* 31556952 1000))]
   ERAS ["Eras" (Duration/ofSeconds (* 31556952 1000000000))]
   FOREVER ["Forever" (Duration/ofSeconds math/long-max-value 999999999)]})

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(defn values [] (vals @enums))
(s/fdef values :ret (s/coll-of ::chrono-unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(s/def ::value-of-args (s/tuple string?))
(defn valueOf [enum-name] (@enum enum-name))
(s/fdef valueOf :args ::value-of-args :ret ::chrono-unit)
