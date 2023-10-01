(ns jiffy.temporal.julian-fields
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

(defrecord JulianFields [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::julian-fields (j/constructor-spec JulianFields create ::create-args))
(s/fdef create :args ::create-args :ret ::julian-fields)

(defmacro args [& x] `(s/tuple ::julian-fields ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/JulianFields.java#L147
(def JULIAN_DAY ::JULIAN_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/JulianFields.java#L188
(def MODIFIED_JULIAN_DAY ::MODIFIED_JULIAN_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/JulianFields.java#L208
(def RATA_DIE ::RATA_DIE--not-implemented)
