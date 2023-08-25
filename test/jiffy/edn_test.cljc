(ns jiffy.edn-test
  (:require [clojure.test :refer [deftest is testing]]
            [jiffy.edn-clj]
            [jiffy.instant-2-impl :as instant-2-impl]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.duration-impl :as duration-impl]
            [jiffy.temporal.temporal-query :as temporal-query #?@(:clj [:refer [defquery]] #?@(:cljs [:refer-macros [defquery]]))]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]))

:jiffy.edn-clj/keep

(deftest print-edn-test

  ;; (is (= (pr-str (instant-impl/create 1692982727 544376000))
  ;;        "#jiffy/instant{:seconds 1692982727, :nanos 544376000}"))

  (is (= (pr-str (instant-2-impl/create 1692982727 544376000))
         "#jiffy/instant-2{:seconds 1692982727, :nanos 544376000}"))

  (is (= (pr-str (duration-impl/create 1692982727 544376000))
         "#jiffy/duration{:seconds 1692982727, :nanos 544376000}"))

  (testing "TemporalQuery"
    (is (= (pr-str temporal-queries/ZONE_ID)
           "#jiffy/query \"ZoneId\""))
    (is (= (pr-str temporal-queries/CHRONO)
           "#jiffy/query \"Chronology\""))
    (is (= (pr-str temporal-queries/PRECISION)
           "#jiffy/query \"Precision\""))
    (is (= (pr-str temporal-queries/OFFSET)
           "#jiffy/query \"ZoneOffset\""))
    (is (= (pr-str temporal-queries/ZONE)
           "#jiffy/query \"Zone\""))
    (is (= (pr-str temporal-queries/LOCAL_DATE)
           "#jiffy/query \"LocalDate\""))
    (is (= (pr-str temporal-queries/LOCAL_TIME)
           "#jiffy/query \"LocalTime\"")))

  (testing "ChronoField"
    (is (= (pr-str chrono-field/NANO_OF_SECOND)
           "#jiffy/field \"NANO_OF_SECOND\""))
    (is (= (pr-str chrono-field/NANO_OF_DAY)
           "#jiffy/field \"NANO_OF_DAY\""))
    (is (= (pr-str chrono-field/MICRO_OF_SECOND)
           "#jiffy/field \"MICRO_OF_SECOND\""))
    (is (= (pr-str chrono-field/MICRO_OF_DAY)
           "#jiffy/field \"MICRO_OF_DAY\""))
    (is (= (pr-str chrono-field/MILLI_OF_SECOND)
           "#jiffy/field \"MILLI_OF_SECOND\""))
    (is (= (pr-str chrono-field/MILLI_OF_DAY)
           "#jiffy/field \"MILLI_OF_DAY\""))
    (is (= (pr-str chrono-field/SECOND_OF_MINUTE)
           "#jiffy/field \"SECOND_OF_MINUTE\""))
    (is (= (pr-str chrono-field/SECOND_OF_DAY)
           "#jiffy/field \"SECOND_OF_DAY\""))
    (is (= (pr-str chrono-field/MINUTE_OF_HOUR)
           "#jiffy/field \"MINUTE_OF_HOUR\""))
    (is (= (pr-str chrono-field/MINUTE_OF_DAY)
           "#jiffy/field \"MINUTE_OF_DAY\""))
    (is (= (pr-str chrono-field/HOUR_OF_AMPM)
           "#jiffy/field \"HOUR_OF_AMPM\""))
    (is (= (pr-str chrono-field/CLOCK_HOUR_OF_AMPM)
           "#jiffy/field \"CLOCK_HOUR_OF_AMPM\""))
    (is (= (pr-str chrono-field/HOUR_OF_DAY)
           "#jiffy/field \"HOUR_OF_DAY\""))
    (is (= (pr-str chrono-field/CLOCK_HOUR_OF_DAY)
           "#jiffy/field \"CLOCK_HOUR_OF_DAY\""))
    (is (= (pr-str chrono-field/AMPM_OF_DAY)
           "#jiffy/field \"AMPM_OF_DAY\""))
    (is (= (pr-str chrono-field/DAY_OF_WEEK)
           "#jiffy/field \"DAY_OF_WEEK\""))
    (is (= (pr-str chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH)
           "#jiffy/field \"ALIGNED_DAY_OF_WEEK_IN_MONTH\""))
    (is (= (pr-str chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR)
           "#jiffy/field \"ALIGNED_DAY_OF_WEEK_IN_YEAR\""))
    (is (= (pr-str chrono-field/DAY_OF_MONTH)
           "#jiffy/field \"DAY_OF_MONTH\""))
    (is (= (pr-str chrono-field/DAY_OF_YEAR)
           "#jiffy/field \"DAY_OF_YEAR\""))
    (is (= (pr-str chrono-field/EPOCH_DAY)
           "#jiffy/field \"EPOCH_DAY\""))
    (is (= (pr-str chrono-field/ALIGNED_WEEK_OF_MONTH)
           "#jiffy/field \"ALIGNED_WEEK_OF_MONTH\""))
    (is (= (pr-str chrono-field/ALIGNED_WEEK_OF_YEAR)
           "#jiffy/field \"ALIGNED_WEEK_OF_YEAR\""))
    (is (= (pr-str chrono-field/MONTH_OF_YEAR)
           "#jiffy/field \"MONTH_OF_YEAR\""))
    (is (= (pr-str chrono-field/PROLEPTIC_MONTH)
           "#jiffy/field \"PROLEPTIC_MONTH\""))
    (is (= (pr-str chrono-field/YEAR_OF_ERA)
           "#jiffy/field \"YEAR_OF_ERA\""))
    (is (= (pr-str chrono-field/YEAR)
           "#jiffy/field \"YEAR\""))
    (is (= (pr-str chrono-field/ERA)
           "#jiffy/field \"ERA\""))
    (is (= (pr-str chrono-field/INSTANT_SECONDS)
           "#jiffy/field \"INSTANT_SECONDS\""))
    (is (= (pr-str chrono-field/OFFSET_SECONDS)
           "#jiffy/field \"OFFSET_SECONDS\"")))

  (testing "ChronoUnit"
    (is (= (pr-str chrono-unit/NANOS)
           "#jiffy/unit \"NANOS\""))
    (is (= (pr-str chrono-unit/MICROS)
           "#jiffy/unit \"MICROS\""))
    (is (= (pr-str chrono-unit/MILLIS)
           "#jiffy/unit \"MILLIS\""))
    (is (= (pr-str chrono-unit/SECONDS)
           "#jiffy/unit \"SECONDS\""))
    (is (= (pr-str chrono-unit/MINUTES)
           "#jiffy/unit \"MINUTES\""))
    (is (= (pr-str chrono-unit/HOURS)
           "#jiffy/unit \"HOURS\""))
    (is (= (pr-str chrono-unit/HALF_DAYS)
           "#jiffy/unit \"HALF_DAYS\""))
    (is (= (pr-str chrono-unit/DAYS)
           "#jiffy/unit \"DAYS\""))
    (is (= (pr-str chrono-unit/WEEKS)
           "#jiffy/unit \"WEEKS\""))
    (is (= (pr-str chrono-unit/MONTHS)
           "#jiffy/unit \"MONTHS\""))
    (is (= (pr-str chrono-unit/YEARS)
           "#jiffy/unit \"YEARS\""))
    (is (= (pr-str chrono-unit/DECADES)
           "#jiffy/unit \"DECADES\""))
    (is (= (pr-str chrono-unit/CENTURIES)
           "#jiffy/unit \"CENTURIES\""))
    (is (= (pr-str chrono-unit/MILLENNIA)
           "#jiffy/unit \"MILLENNIA\""))
    (is (= (pr-str chrono-unit/ERAS)
           "#jiffy/unit \"ERAS\""))
    (is (= (pr-str chrono-unit/FOREVER)
           "#jiffy/unit \"FOREVER\""))

    )
  )

(deftest read-literal-test

  ;; (is (= #jiffy/instant{:seconds 1692982727, :nanos 544376000}
  ;;        (instant-impl/create 1692982727 544376000)))

  (is (= #jiffy/instant-2{:seconds 1692982727, :nanos 544376000}
         (instant-2-impl/create 1692982727 544376000)))

  (is (= #jiffy/duration{:seconds 1692982727, :nanos 544376000}
         (duration-impl/create 1692982727 544376000)))

  (testing "TemporalQuery"
    (is (= #jiffy/query "ZoneId"
           temporal-queries/ZONE_ID))
    (is (= #jiffy/query "Chronology"
           temporal-queries/CHRONO))
    (is (= #jiffy/query "Precision"
           temporal-queries/PRECISION))
    (is (= #jiffy/query "ZoneOffset"
           temporal-queries/OFFSET))
    (is (= #jiffy/query "Zone"
           temporal-queries/ZONE))
    (is (= #jiffy/query "LocalDate"
           temporal-queries/LOCAL_DATE))
    (is (= #jiffy/query "LocalTime"
           temporal-queries/LOCAL_TIME)))

  (testing "ChronoField"
    (is (= #jiffy/field "NANO_OF_SECOND"
           chrono-field/NANO_OF_SECOND))
    (is (= #jiffy/field "NANO_OF_DAY"
           chrono-field/NANO_OF_DAY))
    (is (= #jiffy/field "MICRO_OF_SECOND"
           chrono-field/MICRO_OF_SECOND))
    (is (= #jiffy/field "MICRO_OF_DAY"
           chrono-field/MICRO_OF_DAY))
    (is (= #jiffy/field "MILLI_OF_SECOND"
           chrono-field/MILLI_OF_SECOND))
    (is (= #jiffy/field "MILLI_OF_DAY"
           chrono-field/MILLI_OF_DAY))
    (is (= #jiffy/field "SECOND_OF_MINUTE"
           chrono-field/SECOND_OF_MINUTE))
    (is (= #jiffy/field "SECOND_OF_DAY"
           chrono-field/SECOND_OF_DAY))
    (is (= #jiffy/field "MINUTE_OF_HOUR"
           chrono-field/MINUTE_OF_HOUR))
    (is (= #jiffy/field "MINUTE_OF_DAY"
           chrono-field/MINUTE_OF_DAY))
    (is (= #jiffy/field "HOUR_OF_AMPM"
           chrono-field/HOUR_OF_AMPM))
    (is (= #jiffy/field "CLOCK_HOUR_OF_AMPM"
           chrono-field/CLOCK_HOUR_OF_AMPM))
    (is (= #jiffy/field "HOUR_OF_DAY"
           chrono-field/HOUR_OF_DAY))
    (is (= #jiffy/field "CLOCK_HOUR_OF_DAY"
           chrono-field/CLOCK_HOUR_OF_DAY))
    (is (= #jiffy/field "AMPM_OF_DAY"
           chrono-field/AMPM_OF_DAY))
    (is (= #jiffy/field "DAY_OF_WEEK"
           chrono-field/DAY_OF_WEEK))
    (is (= #jiffy/field "ALIGNED_DAY_OF_WEEK_IN_MONTH"
           chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH))
    (is (= #jiffy/field "ALIGNED_DAY_OF_WEEK_IN_YEAR"
           chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR))
    (is (= #jiffy/field "DAY_OF_MONTH"
           chrono-field/DAY_OF_MONTH))
    (is (= #jiffy/field "DAY_OF_YEAR"
           chrono-field/DAY_OF_YEAR))
    (is (= #jiffy/field "EPOCH_DAY"
           chrono-field/EPOCH_DAY))
    (is (= #jiffy/field "ALIGNED_WEEK_OF_MONTH"
           chrono-field/ALIGNED_WEEK_OF_MONTH))
    (is (= #jiffy/field "ALIGNED_WEEK_OF_YEAR"
           chrono-field/ALIGNED_WEEK_OF_YEAR))
    (is (= #jiffy/field "MONTH_OF_YEAR"
           chrono-field/MONTH_OF_YEAR))
    (is (= #jiffy/field "PROLEPTIC_MONTH"
           chrono-field/PROLEPTIC_MONTH))
    (is (= #jiffy/field "YEAR_OF_ERA"
           chrono-field/YEAR_OF_ERA))
    (is (= #jiffy/field "YEAR"
           chrono-field/YEAR))
    (is (= #jiffy/field "ERA"
           chrono-field/ERA))
    (is (= #jiffy/field "INSTANT_SECONDS"
           chrono-field/INSTANT_SECONDS))
    (is (= #jiffy/field "OFFSET_SECONDS"
           chrono-field/OFFSET_SECONDS)))

  (testing "ChronoUnit"
    (is (= #jiffy/unit "NANOS"
           chrono-unit/NANOS))
    (is (= #jiffy/unit "MICROS"
           chrono-unit/MICROS))
    (is (= #jiffy/unit "MILLIS"
           chrono-unit/MILLIS))
    (is (= #jiffy/unit "SECONDS"
           chrono-unit/SECONDS))
    (is (= #jiffy/unit "MINUTES"
           chrono-unit/MINUTES))
    (is (= #jiffy/unit "HOURS"
           chrono-unit/HOURS))
    (is (= #jiffy/unit "HALF_DAYS"
           chrono-unit/HALF_DAYS))
    (is (= #jiffy/unit "DAYS"
           chrono-unit/DAYS))
    (is (= #jiffy/unit "WEEKS"
           chrono-unit/WEEKS))
    (is (= #jiffy/unit "MONTHS"
           chrono-unit/MONTHS))
    (is (= #jiffy/unit "YEARS"
           chrono-unit/YEARS))
    (is (= #jiffy/unit "DECADES"
           chrono-unit/DECADES))
    (is (= #jiffy/unit "CENTURIES"
           chrono-unit/CENTURIES))
    (is (= #jiffy/unit "MILLENNIA"
           chrono-unit/MILLENNIA))
    (is (= #jiffy/unit "ERAS"
           chrono-unit/ERAS))
    (is (= #jiffy/unit "FOREVER"
           chrono-unit/FOREVER)))

  )
