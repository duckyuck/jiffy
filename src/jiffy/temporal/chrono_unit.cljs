(ns jiffy.temporal.chrono-unit
  (:require ;; [jiffy.duration :as Duration]
            [jiffy.math :as math]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [name estimated-duration])

;; TODO: untangle circular dependency between chrono-unit and duration

;; strategy:
;; Create duration via multimethod defined alongside duration ns.  Need to make
;; sure the evaluation of chrono-unit namespace happens after multimethod
;; implementation in jiffy.duration has been registered

(def NANOS (->ChronoUnit "Nanos" nil ;; (Duration/ofNanos 1)
                         ))
(def MICROS (->ChronoUnit "Micros" nil ;; (Duration/ofNanos 1000)
                          ))
(def MILLIS (->ChronoUnit "Millis" nil ;; (Duration/ofNanos 1000000)
                          ))
(def SECONDS (->ChronoUnit "Seconds" nil ;; (Duration/ofSeconds 1)
                           ))
(def MINUTES (->ChronoUnit "Minutes" nil ;; (Duration/ofSeconds 60)
                           ))
(def HOURS (->ChronoUnit "Hours" nil ;; (Duration/ofSeconds 3600)
                         ))
(def HALF_DAYS (->ChronoUnit "HalfDays" nil ;; (Duration/ofSeconds 43200)
                             ))
(def DAYS (->ChronoUnit "Days" nil ;; (Duration/ofSeconds 86400)
                        ))
(def WEEKS (->ChronoUnit "Weeks" nil ;; (Duration/ofSeconds (* 7 86400))
                         ))
(def MONTHS (->ChronoUnit "Months" nil ;; (Duration/ofSeconds (/ 31556952 12))
                          ))
(def YEARS (->ChronoUnit "Years" nil ;; (Duration/ofSeconds 31556952)
                         ))
(def DECADES (->ChronoUnit "Decades" nil ;; (Duration/ofSeconds (* 31556952 10))
                           ))
(def CENTURIES (->ChronoUnit "Centuries" nil ;; (Duration/ofSeconds (* 31556952 100))
                             ))
(def MILLENNIA (->ChronoUnit "Millennia" nil ;; (Duration/ofSeconds (* 31556952 1000))
                             ))
(def ERAS (->ChronoUnit "Eras" nil ;; (Duration/ofSeconds (* 31556952 1000000000))
                        ))
(def FOREVER (->ChronoUnit "Forever" nil ;; (Duration/ofSeconds math/long-max-value 999999999)
                           ))
