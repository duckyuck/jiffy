(ns jiffy.chrono.hijrah-chronology-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord HijrahChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::hijrah-chronology (j/constructor-spec HijrahChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::hijrah-chronology)
