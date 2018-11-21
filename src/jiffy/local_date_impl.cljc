(ns jiffy.local-date-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord LocalDate [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::local-date (j/constructor-spec LocalDate create ::create-args))
(s/fdef create :args ::create-args :ret ::local-date)
