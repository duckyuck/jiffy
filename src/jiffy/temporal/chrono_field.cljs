(ns jiffy.temporal.chrono-field
  (:require [jiffy.math :as math]
            [jiffy.temporal.chrono-unit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS WEEKS FOREVER MONTHS YEARS ERAS]]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.year :as Year]))

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
