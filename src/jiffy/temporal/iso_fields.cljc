(ns jiffy.temporal.iso-fields
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

(defn IsoFields [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::iso-fields (j/constructor-spec IsoFields create ::create-args))
(s/fdef create :args ::create-args :ret ::iso-fields)

(defmacro args [& x] `(s/tuple ::iso-fields ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L740
(s/def ::is-iso-args (args ::TemporalAccessor/temporal-accessor))
(defn isIso [temporal] (wip ::isIso))
(s/fdef isIso :args ::is-iso-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L200
(def DAY_OF_QUARTER ::DAY_OF_QUARTER--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L215
(def QUARTER_OF_YEAR ::QUARTER_OF_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L245
(def WEEK_OF_WEEK_BASED_YEAR ::WEEK_OF_WEEK_BASED_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L259
(def WEEK_BASED_YEAR ::WEEK_BASED_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L274
(def WEEK_BASED_YEARS ::WEEK_BASED_YEARS--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/IsoFields.java#L282
(def QUARTER_YEARS ::QUARTER_YEARS--not-implemented)
