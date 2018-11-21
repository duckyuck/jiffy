(ns jiffy.chrono.iso-chronology-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

(defrecord IsoChronology [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::iso-chronology (j/constructor-spec IsoChronology create ::create-args))
(s/fdef create :args ::create-args :ret ::iso-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L131
(def INSTANCE ::INSTANCE--not-implemented)
