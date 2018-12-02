(ns jiffy.zone-offset-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.exception :refer [DateTimeException ex]]
            #?(:clj [jiffy.conversion :as conversion])
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

(s/def ::total-seconds (s/int-in -64800 64801))
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
(s/fdef of-total-seconds :args ::of-total-seconds-args :ret ::zone-offset)

#?(:clj
   (defmethod conversion/jiffy->java ZoneOffset [jiffy-object]
     (java.time.ZoneOffset/ofTotalSeconds (:total-seconds jiffy-object))))

#?(:clj
   (defmethod conversion/same? ZoneOffset
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:id :total-seconds])
        [(.getId java-object)
         (.getTotalSeconds java-object)])))
