(ns jiffy.offset-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord OffsetTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::offset-time (j/constructor-spec OffsetTime create ::create-args))
(s/fdef create :args ::create-args :ret ::offset-time)
