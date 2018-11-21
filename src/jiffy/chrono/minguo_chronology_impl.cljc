(ns jiffy.chrono.minguo-chronology-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord MinguoChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::minguo-chronology (j/constructor-spec MinguoChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::minguo-chronology)
