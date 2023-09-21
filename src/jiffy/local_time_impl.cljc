(ns jiffy.local-time-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.temporal.chrono-field :as chrono-field]
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

(def-record LocalTime ::local-time
  [hour ::j/hour-of-day
   minute ::j/minute-of-hour
   second ::j/second-of-minute
   nano ::j/nano-of-second])

(def-constructor create ::local-time
  [hour ::j/hour-of-day
   minute ::j/minute-of-hour
   second ::j/second-of-minute
   nano ::j/nano-of-second]
  (->LocalTime hour minute second nano))

(def MIDNIGHT (create 0 0 0 0))

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

(def-constructor of ::local-time
  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (of hour minute 0 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (of hour minute second 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (chrono-field/check-valid-value chrono-field/HOUR_OF_DAY hour)
   (chrono-field/check-valid-value chrono-field/MINUTE_OF_HOUR minute)
   (chrono-field/check-valid-value chrono-field/SECOND_OF_MINUTE second)
   (chrono-field/check-valid-value chrono-field/NANO_OF_SECOND nano-of-second)
   (create hour minute second nano-of-second)))
