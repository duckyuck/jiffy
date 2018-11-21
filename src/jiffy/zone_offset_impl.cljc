(ns jiffy.zone-offset-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord ZoneOffset [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-offset (j/constructor-spec ZoneOffset create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-offset)
