(ns jiffy.temporal.chrono-field
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.math :as math]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-unit :as unit]
            [jiffy.temporal.value-range :as value-range-impl]
            [jiffy.year-impl :as year]))

(defprotocol IChronoField
  (check-valid-value [this value])
  (check-valid-int-value [this value]))

(defrecord ChronoField [ordinal enum-name name base-unit range-unit range display-name-key])

(def chrono-field? (partial instance? ChronoField))

(s/def ::create-args (s/tuple ::j/long string? string? ::unit/chrono-unit ::unit/chrono-unit ::value-range/value-range (s/? ::j/string)))
(defn create
  ([ordinal enum-name name base-unit range-unit range]
   (create ordinal enum-name name base-unit range-unit range nil))
  ([ordinal enum-name name base-unit range-unit range display-name-key]
   (->ChronoField ordinal enum-name name base-unit range-unit range display-name-key)))
(s/def ::chrono-field (j/constructor-spec ChronoField create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-field)

(defenum create
  [NANO_OF_SECOND ["NanoOfSecond" unit/NANOS unit/SECONDS (value-range-impl/of 0 999999999)]
   NANO_OF_DAY ["NanoOfDay" unit/NANOS unit/DAYS (value-range-impl/of 0 (- (* 86400 1000000000) 1))]
   MICRO_OF_SECOND ["MicroOfSecond" unit/MICROS unit/SECONDS (value-range-impl/of 0 999999)]
   MICRO_OF_DAY ["MicroOfDay" unit/MICROS unit/DAYS (value-range-impl/of 0 (- (* 86400 1000000) 1))]
   MILLI_OF_SECOND ["MilliOfSecond" unit/MILLIS unit/SECONDS (value-range-impl/of 0 999)]
   MILLI_OF_DAY ["MilliOfDay" unit/MILLIS unit/DAYS (value-range-impl/of 0 (- (* 86400 1000) 1))]
   SECOND_OF_MINUTE ["SecondOfMinute" unit/SECONDS unit/MINUTES (value-range-impl/of 0 59) "second"]
   SECOND_OF_DAY ["SecondOfDay" unit/SECONDS unit/DAYS (value-range-impl/of 0 (- 86400 1))]
   MINUTE_OF_HOUR ["MinuteOfHour" unit/MINUTES unit/HOURS (value-range-impl/of 0 59) "minute"]
   MINUTE_OF_DAY ["MinuteOfDay" unit/MINUTES unit/DAYS (value-range-impl/of 0 (- (* 24 60) 1))]
   HOUR_OF_AMPM ["HourOfAmPm" unit/HOURS unit/HALF_DAYS (value-range-impl/of 0 11)]
   CLOCK_HOUR_OF_AMPM ["ClockHourOfAmPm" unit/HOURS unit/HALF_DAYS (value-range-impl/of 1 12)]
   HOUR_OF_DAY ["HourOfDay" unit/HOURS unit/DAYS (value-range-impl/of 0 23) "hour"]
   CLOCK_HOUR_OF_DAY ["ClockHourOfDay" unit/HOURS unit/DAYS (value-range-impl/of 1 24)]
   AMPM_OF_DAY ["AmPmOfDay" unit/HALF_DAYS unit/DAYS (value-range-impl/of 0 1) "dayperiod"]
   DAY_OF_WEEK ["DayOfWeek" unit/DAYS unit/WEEKS (value-range-impl/of 1 7) "weekday"]
   ALIGNED_DAY_OF_WEEK_IN_MONTH ["AlignedDayOfWeekInMonth" unit/DAYS unit/WEEKS (value-range-impl/of 1 7)]
   ALIGNED_DAY_OF_WEEK_IN_YEAR ["AlignedDayOfWeekInYear" unit/DAYS unit/WEEKS (value-range-impl/of 1 7)]
   DAY_OF_MONTH ["DayOfMonth" unit/DAYS unit/MONTHS (value-range-impl/of 1 28 31) "day"]
   DAY_OF_YEAR ["DayOfYear" unit/DAYS unit/YEARS (value-range-impl/of 1 365 366)]
   EPOCH_DAY ["EpochDay" unit/DAYS unit/FOREVER (value-range-impl/of -365243219162 365241780471)]
   ALIGNED_WEEK_OF_MONTH ["AlignedWeekOfMonth" unit/WEEKS unit/MONTHS (value-range-impl/of 1 4 5)]
   ALIGNED_WEEK_OF_YEAR ["AlignedWeekOfYear" unit/WEEKS unit/YEARS (value-range-impl/of 1 53)]
   MONTH_OF_YEAR ["MonthOfYear" unit/MONTHS unit/YEARS (value-range-impl/of 1 12) "month"]
   PROLEPTIC_MONTH ["ProlepticMonth" unit/MONTHS unit/FOREVER (value-range-impl/of (* year/MIN_VALUE 12) (+ (* year/MAX_VALUE 12) 11))]
   YEAR_OF_ERA ["YearOfEra" unit/YEARS unit/FOREVER (value-range-impl/of 1 year/MAX_VALUE (+ year/MAX_VALUE 1))]
   YEAR ["Year" unit/YEARS unit/FOREVER (value-range-impl/of year/MIN_VALUE year/MAX_VALUE) "year"]
   ERA ["Era" unit/ERAS unit/FOREVER (value-range-impl/of 0 1) "era"]
   INSTANT_SECONDS ["InstantSeconds" unit/SECONDS unit/FOREVER (value-range-impl/of math/long-min-value math/long-max-value)]
   OFFSET_SECONDS ["OffsetSeconds" unit/SECONDS unit/FOREVER (value-range-impl/of (* -18 3600) (* 18 3600))]])

(defmacro args [& x] `(s/tuple ::chrono-field ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L716
(s/def ::check-valid-value-args (args ::j/long))
(defn -check-valid-value [this value]
  (value-range/check-valid-value (temporal-field/range this) value this))
(s/fdef -check-valid-value :args ::check-valid-value-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L735
(s/def ::check-valid-int-value-args (args ::j/long))
(defn -check-valid-int-value [this value]
  (value-range/check-valid-int-value (temporal-field/range this) value this))
(s/fdef -check-valid-int-value :args ::check-valid-int-value-args :ret ::j/int)

(extend-type ChronoField
  IChronoField
  (check-valid-value [this value] (-check-valid-value this value))
  (check-valid-int-value [this value] (-check-valid-int-value this value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L629
(s/def ::get-display-name-args (args ::j/wip))
(defn -get-display-name [this locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L645
(s/def ::get-base-unit-args (args))
(defn -get-base-unit [this] (wip ::-get-base-unit))
(s/fdef -get-base-unit :args ::get-base-unit-args :ret ::temporal-unit/temporal-unit)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L650
(s/def ::get-range-unit-args (args))
(defn -get-range-unit [this] (wip ::-get-range-unit))
(s/fdef -get-range-unit :args ::get-range-unit-args :ret ::temporal-unit/temporal-unit)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L672
(s/def ::range-args (args))
(defn -range [this] (:range this))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L685
(def-method -is-date-based ::j/boolean
  [this ::chrono-field]
  (>= (:ordinal ERA) (:ordinal this) (:ordinal DAY_OF_WEEK)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L697
(def-method -is-time-based ::j/boolean
  [this ::chrono-field]
  (< (:ordinal this) (:ordinal DAY_OF_WEEK)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L741
(s/def ::is-supported-by-args (args ::temporal-accessor/temporal-accessor))
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))
(s/fdef -is-supported-by :args ::is-supported-by-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L746
(s/def ::range-refined-by-args (args ::temporal-accessor/temporal-accessor))
(defn -range-refined-by [this temporal] (wip ::-range-refined-by))
(s/fdef -range-refined-by :args ::range-refined-by-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L751
(s/def ::get-from-args (args ::temporal-accessor/temporal-accessor))
(defn -get-from [this temporal] (wip ::-get-from))
(s/fdef -get-from :args ::get-from-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L757
(s/def ::adjust-into-args (args ::temporal/temporal ::j/long))
(defn -adjust-into [this temporal new-value] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type ChronoField
  temporal-field/ITemporalField
  (get-display-name [this locale] (-get-display-name this locale))
  (get-base-unit [this] (-get-base-unit this))
  (get-range-unit [this] (-get-range-unit this))
  (range [this] (-range this))
  (is-date-based [this] (-is-date-based this))
  (is-time-based [this] (-is-time-based this))
  (is-supported-by [this temporal] (-is-supported-by this temporal))
  (range-refined-by [this temporal] (-range-refined-by this temporal))
  (get-from [this temporal] (-get-from this temporal))
  (adjust-into [this temporal new-value] (-adjust-into this temporal new-value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defn values [] (vals @enums))
(s/fdef values :ret (s/coll-of ::chrono-field))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(s/def ::value-of-args (s/tuple string?))
(defn valueOf [enum-name] (@enums enum-name))
(s/fdef valueOf :args ::value-of-args :ret ::chrono-field)
