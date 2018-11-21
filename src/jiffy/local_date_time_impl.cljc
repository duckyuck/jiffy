(ns jiffy.local-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord LocalDateTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::local-date-time (j/constructor-spec LocalDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::local-date-time)
