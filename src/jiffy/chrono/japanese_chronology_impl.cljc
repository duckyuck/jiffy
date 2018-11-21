(ns jiffy.chrono.japanese-chronology-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord JapaneseChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::japanese-chronology (j/constructor-spec JapaneseChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::japanese-chronology)
