(ns jiffy.temporal.chrono-field
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.math :as math]
            [jiffy.temporal.chrono-unit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS WEEKS FOREVER MONTHS YEARS ERAS]]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.year-impl :as Year]))

(defprotocol IChronoField
  (checkValidValue [this value])
  (checkValidIntValue [this value]))

(defrecord ChronoField [name base-unit range-unit range display-name-key])

(defn create [name base-unit range-unit range & [display-name-key]]
  (->ChronoField name base-unit range-unit range display-name-key))

(def NANO_OF_SECOND (create "NanoOfSecond" NANOS SECONDS (ValueRange/of 0 999999999)))
(def NANO_OF_DAY (create "NanoOfDay" NANOS DAYS (ValueRange/of 0 (-> 86400 (* 1000000000) (- 1)))))
(def MICRO_OF_SECOND (create "MicroOfSecond" MICROS SECONDS (ValueRange/of 0 999999)))
(def MICRO_OF_DAY (create "MicroOfDay" MICROS DAYS (ValueRange/of 0 (-> 86400 (* 1000000) (- 1)))))
(def MILLI_OF_SECOND (create "MilliOfSecond" MILLIS SECONDS (ValueRange/of 0 999)))
(def MILLI_OF_DAY (create "MilliOfDay" MILLIS DAYS (ValueRange/of 0 (-> 86400 (* 1000) (- 1)))))
(def SECOND_OF_MINUTE (create "SecondOfMinute" SECONDS MINUTES (ValueRange/of 0 59) "second"))
(def SECOND_OF_DAY (create "SecondOfDay" SECONDS DAYS (ValueRange/of 0 (- 86400 1))))
(def MINUTE_OF_HOUR (create "MinuteOfHour" MINUTES HOURS (ValueRange/of 0 59) "minute"))
(def MINUTE_OF_DAY (create "MinuteOfDay" MINUTES DAYS (ValueRange/of 0 (-> 24 (* 60) (- 1)))))
(def HOUR_OF_AMPM (create "HourOfAmPm" HOURS HALF_DAYS (ValueRange/of 0 11)))
(def CLOCK_HOUR_OF_AMPM (create "ClockHourOfAmPm" HOURS HALF_DAYS (ValueRange/of 1 12)))
(def HOUR_OF_DAY (create "HourOfDay" HOURS DAYS (ValueRange/of 0 23) "hour"))
(def CLOCK_HOUR_OF_DAY (create "ClockHourOfDay" HOURS DAYS (ValueRange/of 1 24)))
(def AMPM_OF_DAY (create "AmPmOfDay" HALF_DAYS DAYS (ValueRange/of 0 1) "dayperiod"))
(def DAY_OF_WEEK (create "DayOfWeek" DAYS WEEKS (ValueRange/of 1 7) "weekday"))
(def ALIGNED_DAY_OF_WEEK_IN_MONTH (create "AlignedDayOfWeekInMonth" DAYS WEEKS (ValueRange/of 1 7)))
(def ALIGNED_DAY_OF_WEEK_IN_YEAR (create "AlignedDayOfWeekInYear" DAYS WEEKS (ValueRange/of 1 7)))
(def DAY_OF_MONTH (create "DayOfMonth" DAYS MONTHS (ValueRange/of 1 28 31) "day"))
(def DAY_OF_YEAR (create "DayOfYear" DAYS YEARS (ValueRange/of 1 365 366)))
(def EPOCH_DAY (create "EpochDay" DAYS FOREVER (ValueRange/of (long (* Year/MIN_VALUE 365.25)) (long (* Year/MAX_VALUE 365.25)))))
(def ALIGNED_WEEK_OF_MONTH (create "AlignedWeekOfMonth" WEEKS MONTHS (ValueRange/of 1 4 5)))
(def ALIGNED_WEEK_OF_YEAR (create "AlignedWeekOfYear" WEEKS YEARS (ValueRange/of 1 53)))
(def MONTH_OF_YEAR (create "MonthOfYear" MONTHS YEARS (ValueRange/of 1 12) "month"))
(def PROLEPTIC_MONTH (create "ProlepticMonth" MONTHS FOREVER (ValueRange/of (* Year/MIN_VALUE 12) (-> Year/MAX_VALUE (* 12) (+ 11)))))
(def YEAR_OF_ERA (create "YearOfEra" YEARS FOREVER (ValueRange/of 1 Year/MAX_VALUE (+ Year/MAX_VALUE 1))))
(def YEAR (create "Year" YEARS FOREVER (ValueRange/of Year/MIN_VALUE Year/MAX_VALUE) "year"))
(def ERA (create "Era" ERAS FOREVER (ValueRange/of 0 1) "era"))
(def INSTANT_SECONDS (create "InstantSeconds" SECONDS FOREVER (ValueRange/of math/long-min-value math/long-max-value)))
(def OFFSET_SECONDS (create "OffsetSeconds" SECONDS FOREVER (ValueRange/of (* -18 3600) (* 18 3600))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L716
(defn -check-valid-value [this value] (wip ::-check-valid-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L735
(defn -check-valid-int-value [this value] (wip ::-check-valid-int-value))

(extend-type ChronoField
  IChronoField
  (checkValidValue [this value] (-check-valid-value this value))
  (checkValidIntValue [this value] (-check-valid-int-value this value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L629
(defn -get-display-name [this locale] (wip ::-get-display-name))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L645
(defn -get-base-unit [this] (wip ::-get-base-unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L650
(defn -get-range-unit [this] (wip ::-get-range-unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L672
(defn -range [this] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L685
(defn -is-date-based [this] (wip ::-is-date-based))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L697
(defn -is-time-based [this] (wip ::-is-time-based))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L741
(defn -is-supported-by [this temporal] (wip ::-is-supported-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L746
(defn -range-refined-by [this temporal] (wip ::-range-refined-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L751
(defn -get-from [this temporal] (wip ::-get-from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java#L757
(defn -adjust-into [this temporal new-value] (wip ::-adjust-into))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoField.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))
