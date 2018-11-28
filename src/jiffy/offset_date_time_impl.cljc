(ns jiffy.offset-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.local-date-time-impl :as LocalDateTime]
            [jiffy.zone-offset-impl :as ZoneOffset]))

(defrecord OffsetDateTime [date-time offset])

(s/def ::create-args (s/tuple ::LocalDateTime/local-date-time ::ZoneOffset/zone-offset))
(defn create [date-time offset] (->OffsetDateTime date-time offset))
(s/def ::offset-date-time (j/constructor-spec OffsetDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::offset-date-time)
