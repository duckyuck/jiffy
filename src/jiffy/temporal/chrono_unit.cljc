(ns jiffy.temporal.chrono-unit
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :as Duration]
            [jiffy.math :as math]
            [jiffy.temporal.temporal-unit :as TemporalUnit]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [name estimated-duration]
  IChronoUnit)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L210
(defn -get-duration [this] (wip ::-get-duration))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L226
(defn -is-duration-estimated [this] (wip ::-is-duration-estimated))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L240
(defn -is-date-based [this] (wip ::-is-date-based))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L253
(defn -is-time-based [this] (wip ::-is-time-based))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L254
(defn -add-to [this temporal amount] (wip ::-add-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L259
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L271
(defn -between [this temporal-1-inclusive temporal-2-exclusive] (wip ::-between))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))

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
