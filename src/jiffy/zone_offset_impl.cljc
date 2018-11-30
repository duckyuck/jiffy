(ns jiffy.zone-offset-impl
  (:require [clojure.spec.alpha :as s]
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

#?(:clj
   (defmethod conversion/jiffy->java ZoneOffset [jiffy-object]
     (java.time.ZoneOffset/ofTotalSeconds (:total-seconds jiffy-object))))

#?(:clj
   (defmethod conversion/same? ZoneOffset
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:id :total-seconds])
        [(.getId java-object)
         (.getTotalSeconds java-object)])))
