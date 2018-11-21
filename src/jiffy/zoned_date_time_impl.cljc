(ns jiffy.zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord ZonedDateTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zoned-date-time (j/constructor-spec ZonedDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::zoned-date-time)
