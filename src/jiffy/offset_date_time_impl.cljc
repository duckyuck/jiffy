(ns jiffy.offset-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-time-impl :as local-date-time]
            [jiffy.zone-offset-impl :as zone-offset]))

(defrecord OffsetDateTime [date-time offset])

(s/def ::create-args (s/tuple ::local-date-time/local-date-time ::zone-offset/zone-offset))
(defn create [date-time offset] (->OffsetDateTime date-time offset))
(s/def ::offset-date-time (j/constructor-spec OffsetDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::offset-date-time)

(defn of [local-date-time zone-offset]
  (wip ::-at-start-of-day))
