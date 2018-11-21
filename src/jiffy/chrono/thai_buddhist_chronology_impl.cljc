(ns jiffy.chrono.thai-buddhist-chronology-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord ThaiBuddhistChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::thai-buddhist-chronology (j/constructor-spec ThaiBuddhistChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::thai-buddhist-chronology)
