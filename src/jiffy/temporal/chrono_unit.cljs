(ns jiffy.temporal.chrono-unit
  (:require [jiffy.duration :as Duration]
            [jiffy.math :as math]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [name estimated-duration])

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
