(ns jiffy.local-time-impl
  (:require [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.specs :as j]
            [clojure.spec.alpha :as s]
            [jiffy.local-time-constants :as consts]))

(def HOURS_PER_DAY consts/HOURS_PER_DAY)
(def MINUTES_PER_HOUR consts/MINUTES_PER_HOUR)
(def MINUTES_PER_DAY consts/MINUTES_PER_DAY)
(def SECONDS_PER_MINUTE consts/SECONDS_PER_MINUTE)
(def SECONDS_PER_HOUR consts/SECONDS_PER_HOUR)
(def SECONDS_PER_DAY consts/SECONDS_PER_DAY)
(def MILLIS_PER_DAY consts/MILLIS_PER_DAY)
(def MICROS_PER_DAY consts/MICROS_PER_DAY)
(def NANOS_PER_MILLI consts/NANOS_PER_MILLI)
(def NANOS_PER_SECOND consts/NANOS_PER_SECOND)
(def NANOS_PER_MINUTE consts/NANOS_PER_MINUTE)
(def NANOS_PER_HOUR consts/NANOS_PER_HOUR)
(def NANOS_PER_DAY consts/NANOS_PER_DAY)

(defrecord LocalTime [hour minute second nano])

(s/def ::create-args (s/tuple ::j/hour-of-day
                              ::j/minute-of-hour
                              ::j/second-of-minute
                              ::j/nano-of-second))
(defn create [hour minute second nano]
  (->LocalTime hour minute second nano))
(s/def ::local-time (j/constructor-spec LocalTime create ::create-args))
(s/fdef create :args ::create-args :ret ::local-time)

(defmacro args [& x] `(s/tuple ::local-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L397
(s/def ::of-nano-of-day-args (args ::j/long))
(defn of-nano-of-day [nano-of-day]
  (chrono-field/check-valid-value chrono-field/NANO_OF_DAY nano-of-day)
  (let [hours (int (/ nano-of-day NANOS_PER_HOUR))
        nanos (- nano-of-day (* hours NANOS_PER_HOUR))
        minutes (int (/ nanos NANOS_PER_MINUTE))
        nanos (- nanos (* minutes NANOS_PER_MINUTE))
        seconds (int (/ nanos NANOS_PER_SECOND))
        nanos (- nanos (* seconds NANOS_PER_SECOND))]
    (create hours minutes seconds nanos)))
(s/fdef of-nano-of-day :args ::of-nano-of-day-args :ret ::local-time)
