(ns jiffy.zone-offset-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.exception :refer [DateTimeException ex]]
            [jiffy.math :as math]
            [jiffy.local-time-impl :as local-time]
            [jiffy.specs :as j]))

(defrecord ZoneOffset [id total-seconds])

(defn build-id [total-seconds]
  (if (zero? total-seconds)
    "Z"
    (let [abs-total-seconds (Math/abs total-seconds)
          abs-hours (long (/ abs-total-seconds local-time/SECONDS_PER_HOUR))
          abs-minutes (long (mod (/ abs-total-seconds local-time/SECONDS_PER_MINUTE)
                                 local-time/MINUTES_PER_HOUR))
          abs-seconds (long (mod abs-total-seconds local-time/SECONDS_PER_MINUTE))]
      (str (if (neg? total-seconds) "-" "+")
           (when (< abs-hours 10) "0")
           abs-hours
           (if (< abs-minutes 10) ":0" ":")
           abs-minutes
           (when (not (zero? abs-seconds))
             (str (if (< abs-seconds 10) ":0" ":")
                  abs-seconds))))))

(s/def ::total-seconds (j/int-in -64800 64801))
(s/def ::create-args (s/tuple ::total-seconds))
(defn create [total-seconds]
  (->ZoneOffset (build-id total-seconds) total-seconds))
(s/def ::zone-offset (j/constructor-spec ZoneOffset create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-offset)

(def MAX_SECONDS (* 18 local-time/SECONDS_PER_HOUR))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L413
(s/def ::of-total-seconds-args (s/tuple ::total-seconds))
(defn of-total-seconds [total-seconds]
  (when (not (<= (- MAX_SECONDS) total-seconds MAX_SECONDS))
    (throw (ex DateTimeException "Zone offset not in valid range: -18:00 to +18:00")))
  (create total-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L151
(def UTC (of-total-seconds 0))

(defn --validate [hours minutes seconds]
  (cond
    (not (<= -18 hours 18))
    (throw (ex DateTimeException (str "Zone offset hours not in valid range: value " hours " is not in the range -18 to 18")))

    (and (pos? hours) (or (neg? minutes) (neg? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must be positive because hours is positive"))

    (and (neg? hours) (or (pos? minutes) (pos? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must be negative because hours is negative"))

    (or (and (pos? minutes) (neg? seconds))
        (and (neg? minutes) (pos? seconds)))
    (throw (ex DateTimeException "Zone offset minutes and seconds must have the same sign"))

    (not (<= -59 minutes 59))
    (throw (ex DateTimeException (str "Zone offset minutes not in valid range: value " minutes  " is not in the range -59 to 59")))

    (not (<= -59 seconds 59))
    (throw (ex DateTimeException (str "Zone offset seconds not in valid range: value " seconds " is not in the range -59 to 59")))

    (and (= (math/abs hours) 18)
         (not (zero? (bit-or minutes seconds))))
    (throw (ex DateTimeException "Zone offset not in valid range: -18:00 to +18:00"))))

(defn --total-seconds [hours minutes seconds]
  (+ (* hours local-time/SECONDS_PER_HOUR)
     (* minutes local-time/SECONDS_PER_MINUTE)
     seconds))

(defn of-hours-minutes-seconds [hours minutes seconds]
  (--validate hours minutes seconds)
  (of-total-seconds (--total-seconds hours minutes seconds)))

(defn- --parse-number [offset-id pos preceded-by-colon]
  )

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L202
(defn of [offset-id]
  (if (= offset-id "Z")
    UTC
    (let [[hours minutes seconds new-offset-id]
          (case (count offset-id)
            2 (let [offset-id (str (first offset-id) \0 (second offset-id))]
                [(--parse-number offset-id 1 false) 0 0 offset-id])
            3 [(--parse-number offset-id 1 false) 0 0]
            5 [(--parse-number offset-id 1 false) (--parse-number offset-id 3 false) 0]
            6 [(--parse-number offset-id 1 false) (--parse-number offset-id 4 true) 0]
            7 [(--parse-number offset-id 1 false) (--parse-number offset-id 3 false) (--parse-number offset-id 5 false)]
            9 [(--parse-number offset-id 1 false) (--parse-number offset-id 4 true) (--parse-number offset-id 7 true)]
            (throw (ex DateTimeException (str "Invalid ID for ZoneOffset, invalid format: " offset-id) {:offset-id offset-id})))
          offset-id-prefix (first (or new-offset-id offset-id))]
      (when (and (not= offset-id-prefix \+)
                 (not= offset-id-prefix \-))
        (throw (ex DateTimeException (str "Invalid ID for ZoneOffset, plus/minus not found when expected: " offset-id))))
      (if (= offset-id-prefix \-)
        (of-hours-minutes-seconds (- hours) (- minutes) (- seconds))
        (of-hours-minutes-seconds hours minutes seconds)))))
