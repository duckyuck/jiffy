(ns jiffy.temporal.chrono-field
  (:require [clojure.spec.alpha :as s]
            [jiffy.temporal.chrono-unit :as unit]
            #?(:clj [jiffy.conversion :refer [jiffy->java same?]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.math :as math]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.year-impl :as Year]
            [jiffy.enum #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defprotocol IChronoField
  (checkValidValue [this value])
  (checkValidIntValue [this value]))

(defrecord ChronoField [ordinal enum-name name base-unit range-unit range display-name-key])

(defn chrono-field? [o]
  (instance? ChronoField o))

(s/def ::create-args ::j/wip)
(defn create
  ([ordinal enum-name name base-unit range-unit range]
   (create ordinal enum-name name base-unit range-unit range nil))
  ([ordinal enum-name name base-unit range-unit range display-name-key]
   (->ChronoField ordinal enum-name name base-unit range-unit range display-name-key)))
(s/def ::chrono-field (j/constructor-spec ChronoField create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-field)

(defmacro args [& x] `(s/tuple ::chrono-field ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L716
(s/def ::check-valid-value-args (args ::j/long))
(defn -check-valid-value [this value]
  (ValueRange/checkValidValue (TemporalField/range this) value this))
(s/fdef -check-valid-value :args ::check-valid-value-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L735
(s/def ::check-valid-int-value-args (args ::j/long))
(defn -check-valid-int-value [this value]
  (ValueRange/checkValidIntValue (TemporalField/range this) value this))
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
(defn -range [this] (:range this))
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

(defenum create
  {NANO_OF_SECOND ["NanoOfSecond" unit/NANOS unit/SECONDS (ValueRange/of 0 999999999)]
   NANO_OF_DAY ["NanoOfDay" unit/NANOS unit/DAYS (ValueRange/of 0 (- (* 86400 1000000000) 1))]
   MICRO_OF_SECOND ["MicroOfSecond" unit/MICROS unit/SECONDS (ValueRange/of 0 999999)]
   MICRO_OF_DAY ["MicroOfDay" unit/MICROS unit/DAYS (ValueRange/of 0 (- (* 86400 1000000) 1))]
   MILLI_OF_SECOND ["MilliOfSecond" unit/MILLIS unit/SECONDS (ValueRange/of 0 999)]
   MILLI_OF_DAY ["MilliOfDay" unit/MILLIS unit/DAYS (ValueRange/of 0 (- (* 86400 1000) 1))]
   SECOND_OF_MINUTE ["SecondOfMinute" unit/SECONDS unit/MINUTES (ValueRange/of 0 59) "second"]
   SECOND_OF_DAY ["SecondOfDay" unit/SECONDS unit/DAYS (ValueRange/of 0 (- 86400 1))]
   MINUTE_OF_HOUR ["MinuteOfHour" unit/MINUTES unit/HOURS (ValueRange/of 0 59) "minute"]
   MINUTE_OF_DAY ["MinuteOfDay" unit/MINUTES unit/DAYS (ValueRange/of 0 (- (* 24 60) 1))]
   HOUR_OF_AMPM ["HourOfAmPm" unit/HOURS unit/HALF_DAYS (ValueRange/of 0 11)]
   CLOCK_HOUR_OF_AMPM ["ClockHourOfAmPm" unit/HOURS unit/HALF_DAYS (ValueRange/of 1 12)]
   HOUR_OF_DAY ["HourOfDay" unit/HOURS unit/DAYS (ValueRange/of 0 23) "hour"]
   CLOCK_HOUR_OF_DAY ["ClockHourOfDay" unit/HOURS unit/DAYS (ValueRange/of 1 24)]
   AMPM_OF_DAY ["AmPmOfDay" unit/HALF_DAYS unit/DAYS (ValueRange/of 0 1) "dayperiod"]
   DAY_OF_WEEK ["DayOfWeek" unit/DAYS unit/WEEKS (ValueRange/of 1 7) "weekday"]
   ALIGNED_DAY_OF_WEEK_IN_MONTH ["AlignedDayOfWeekInMonth" unit/DAYS unit/WEEKS (ValueRange/of 1 7)]
   ALIGNED_DAY_OF_WEEK_IN_YEAR ["AlignedDayOfWeekInYear" unit/DAYS unit/WEEKS (ValueRange/of 1 7)]
   DAY_OF_MONTH ["DayOfMonth" unit/DAYS unit/MONTHS (ValueRange/of 1 28 31) "day"]
   DAY_OF_YEAR ["DayOfYear" unit/DAYS unit/YEARS (ValueRange/of 1 365 366)]
   EPOCH_DAY ["EpochDay" unit/DAYS unit/FOREVER (ValueRange/of -365243219162 365241780471)]
   ALIGNED_WEEK_OF_MONTH ["AlignedWeekOfMonth" unit/WEEKS unit/MONTHS (ValueRange/of 1 4 5)]
   ALIGNED_WEEK_OF_YEAR ["AlignedWeekOfYear" unit/WEEKS unit/YEARS (ValueRange/of 1 53)]
   MONTH_OF_YEAR ["MonthOfYear" unit/MONTHS unit/YEARS (ValueRange/of 1 12) "month"]
   PROLEPTIC_MONTH ["ProlepticMonth" unit/MONTHS unit/FOREVER (ValueRange/of (* Year/MIN_VALUE 12) (+ (* Year/MAX_VALUE 12) 11))]
   YEAR_OF_ERA ["YearOfEra" unit/YEARS unit/FOREVER (ValueRange/of 1 Year/MAX_VALUE (+ Year/MAX_VALUE 1))]
   YEAR ["Year" unit/YEARS unit/FOREVER (ValueRange/of Year/MIN_VALUE Year/MAX_VALUE) "year"]
   ERA ["Era" unit/ERAS unit/FOREVER (ValueRange/of 0 1) "era"]
   INSTANT_SECONDS ["InstantSeconds" unit/SECONDS unit/FOREVER (ValueRange/of math/long-min-value math/long-max-value)]
   OFFSET_SECONDS ["OffsetSeconds" unit/SECONDS unit/FOREVER (ValueRange/of (* -18 3600) (* 18 3600))]})

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defn values [] (vals @enums))
(s/fdef values :ret (s/coll-of ::chrono-field))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(s/def ::value-of-args (s/tuple string?))
(defn valueOf [enum-name] (@enums enum-name))
(s/fdef valueOf :args ::value-of-args :ret ::chrono-field)

#?(:clj
   (defmethod jiffy->java ChronoField [chrono-field]
     (java.time.temporal.ChronoField/valueOf (:enum-name chrono-field))))

#?(:clj
   (defmethod same? ChronoField
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:ordinal
                                :enum-name
                                :name
                                :base-unit
                                :range-unit
                                :range
                                :display-name-key])
        (map #(% java-object) [(memfn ordinal)
                               (memfn name)
                               (memfn getName)
                               (memfn getBaseUnit)
                               (memfn getRangeUnit)
                               (memfn getRange)
                               (memfn getDisplayNameKey)]))))
