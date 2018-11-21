(ns jiffy.offset-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord OffsetDateTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::offset-date-time (j/constructor-spec OffsetDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::offset-date-time)
