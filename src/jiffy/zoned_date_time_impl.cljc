(ns jiffy.zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.local-date-time-impl :as local-date-time]
            [jiffy.zone-offset-impl :as zone-offset]
            [jiffy.zone-id :as zone-id]
            [jiffy.specs :as j]))

(defrecord ZonedDateTime [date-time offset zone])

(s/def ::create-args (s/tuple ::local-date-time/local-date-time
                              ::zone-offset/zone-offset
                              ::zone-id/zone-id))
(defn create [date-time offset zone]
  (->ZonedDateTime date-time offset zone))
(s/def ::zoned-date-time (j/constructor-spec ZonedDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::zoned-date-time)
