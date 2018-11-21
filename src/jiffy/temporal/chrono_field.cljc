(ns jiffy.temporal.chrono-field
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            ;; [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defprotocol IChronoField
  (checkValidValue [this value])
  (checkValidIntValue [this value]))

(defrecord ChronoField [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::chrono-field (j/constructor-spec ChronoField create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-field)

(defmacro args [& x] `(s/tuple ::chrono-field ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L716
(s/def ::check-valid-value-args (args ::j/long))
(defn -check-valid-value [this value] (wip ::-check-valid-value))
(s/fdef -check-valid-value :args ::check-valid-value-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L735
(s/def ::check-valid-int-value-args (args ::j/long))
(defn -check-valid-int-value [this value] (wip ::-check-valid-int-value))
(s/fdef -check-valid-int-value :args ::check-valid-int-value-args :ret ::j/int)

(extend-type ChronoField
  IChronoField
  (checkValidValue [this value] (-check-valid-value this value))
  (checkValidIntValue [this value] (-check-valid-int-value this value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L629
(s/def ::get-display-name-args (args ::j/wip))
(defn -get-display-name [this locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L645
(s/def ::get-base-unit-args (args))
(defn -get-base-unit [this] (wip ::-get-base-unit))
(s/fdef -get-base-unit :args ::get-base-unit-args :ret ::TemporalUnit/temporal-unit)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L650
(s/def ::get-range-unit-args (args))
(defn -get-range-unit [this] (wip ::-get-range-unit))
(s/fdef -get-range-unit :args ::get-range-unit-args :ret ::TemporalUnit/temporal-unit)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L672
(s/def ::range-args (args))
(defn -range [this] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L685
(s/def ::is-date-based-args (args))
(defn -is-date-based [this] (wip ::-is-date-based))
(s/fdef -is-date-based :args ::is-date-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L697
(s/def ::is-time-based-args (args))
(defn -is-time-based [this] (wip ::-is-time-based))
(s/fdef -is-time-based :args ::is-time-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L741
(s/def ::is-supported-by-args (args ::TemporalAccessor/temporal-accessor))
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))
(s/fdef -is-supported-by :args ::is-supported-by-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L746
(s/def ::range-refined-by-args (args ::TemporalAccessor/temporal-accessor))
(defn -range-refined-by [this temporal] (wip ::-range-refined-by))
(s/fdef -range-refined-by :args ::range-refined-by-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L751
(s/def ::get-from-args (args ::TemporalAccessor/temporal-accessor))
(defn -get-from [this temporal] (wip ::-get-from))
(s/fdef -get-from :args ::get-from-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L757
;; (s/def ::adjust-into-args (args ::Temporal/temporal ::j/long))
(defn -adjust-into [this temporal new-value] (wip ::-adjust-into))
;; (s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type ChronoField
  TemporalField/ITemporalField
  (getDisplayName [this locale] (-get-display-name this locale))
  (getBaseUnit [this] (-get-base-unit this))
  (getRangeUnit [this] (-get-range-unit this))
  (range [this] (-range this))
  (isDateBased [this] (-is-date-based this))
  (isTimeBased [this] (-is-time-based this))
  (isSupportedBy [this temporal] (-is-supported-by this temporal))
  (rangeRefinedBy [this temporal] (-range-refined-by this temporal))
  (getFrom [this temporal] (-get-from this temporal))
  (adjustInto [this temporal new-value] (-adjust-into this temporal new-value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(s/def ::value-of-args (args string?))
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))
(s/fdef valueOf :args ::value-of-args :ret ::chrono-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MILLI_OF_SECOND ::MILLI_OF_SECOND--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def YEAR_OF_ERA ::YEAR_OF_ERA--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def CLOCK_HOUR_OF_DAY ::CLOCK_HOUR_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def ERA ::ERA--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def INSTANT_SECONDS ::INSTANT_SECONDS--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def AMPM_OF_DAY ::AMPM_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def OFFSET_SECONDS ::OFFSET_SECONDS--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def NANO_OF_SECOND ::NANO_OF_SECOND--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def NANO_OF_DAY ::NANO_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def ALIGNED_DAY_OF_WEEK_IN_MONTH ::ALIGNED_DAY_OF_WEEK_IN_MONTH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MONTH_OF_YEAR ::MONTH_OF_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def HOUR_OF_AMPM ::HOUR_OF_AMPM--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def YEAR ::YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MICRO_OF_SECOND ::MICRO_OF_SECOND--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def ALIGNED_WEEK_OF_YEAR ::ALIGNED_WEEK_OF_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def PROLEPTIC_MONTH ::PROLEPTIC_MONTH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def DAY_OF_MONTH ::DAY_OF_MONTH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def SECOND_OF_MINUTE ::SECOND_OF_MINUTE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def SECOND_OF_DAY ::SECOND_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def EPOCH_DAY ::EPOCH_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def DAY_OF_YEAR ::DAY_OF_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def ALIGNED_WEEK_OF_MONTH ::ALIGNED_WEEK_OF_MONTH--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def DAY_OF_WEEK ::DAY_OF_WEEK--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def CLOCK_HOUR_OF_AMPM ::CLOCK_HOUR_OF_AMPM--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MINUTE_OF_DAY ::MINUTE_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def ALIGNED_DAY_OF_WEEK_IN_YEAR ::ALIGNED_DAY_OF_WEEK_IN_YEAR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MINUTE_OF_HOUR ::MINUTE_OF_HOUR--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def HOUR_OF_DAY ::HOUR_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MILLI_OF_DAY ::MILLI_OF_DAY--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(def MICRO_OF_DAY ::MICRO_OF_DAY--not-implemented)
