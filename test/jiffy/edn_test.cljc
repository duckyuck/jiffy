(ns jiffy.edn-test
  (:require [clojure.test :refer [deftest is testing]]
            #?(:clj [jiffy.edn-clj] :cljs [jiffy.edn-cljs])
            [jiffy.instant-impl :as instant-impl]
            [jiffy.duration-impl :as duration-impl]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.local-date :as local-date]
            [jiffy.local-time :as local-time]
            [jiffy.local-date-time :as local-date-time]
            [jiffy.month :as month]
            [jiffy.year-month :as year-month]
            [jiffy.year :as year]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.clock :as clock]
            [jiffy.zone-id :as zone-id]
            [jiffy.zoned-date-time :as zoned-date-time]
            [jiffy.offset-date-time :as offset-date-time]
            [jiffy.offset-time :as offset-time]
            [jiffy.temporal.temporal-query :as temporal-query #?@(:clj [:refer [defquery]] #?@(:cljs [:refer-macros [defquery]]))]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.period :as period]))

:jiffy.edn-clj/keep

(deftest print-edn-test

  (testing "Instant"
    (is (= (pr-str (instant-impl/create 1692982727 544376000))
           "#jiffy/instant {:seconds 1692982727, :nanos 544376000}")))

  (testing "Duration"
    (is (= (pr-str (duration-impl/create 1692982727 544376000))
           "#jiffy/duration \"PT470272H58M47.544376S\"")))

  (testing "TemporalQuery"
    (is (= (pr-str temporal-queries/ZONE_ID)
           "#jiffy/query :zone-id"))
    (is (= (pr-str temporal-queries/CHRONO)
           "#jiffy/query :chronology"))
    (is (= (pr-str temporal-queries/PRECISION)
           "#jiffy/query :precision"))
    (is (= (pr-str temporal-queries/OFFSET)
           "#jiffy/query :zone-offset"))
    (is (= (pr-str temporal-queries/ZONE)
           "#jiffy/query :zone"))
    (is (= (pr-str temporal-queries/LOCAL_DATE)
           "#jiffy/query :local-date"))
    (is (= (pr-str temporal-queries/LOCAL_TIME)
           "#jiffy/query :local-time")))

  (testing "ChronoField"
    (is (= (pr-str chrono-field/NANO_OF_SECOND)
           "#jiffy/field :nano-of-second"))
    (is (= (pr-str chrono-field/NANO_OF_DAY)
           "#jiffy/field :nano-of-day"))
    (is (= (pr-str chrono-field/MICRO_OF_SECOND)
           "#jiffy/field :micro-of-second"))
    (is (= (pr-str chrono-field/MICRO_OF_DAY)
           "#jiffy/field :micro-of-day"))
    (is (= (pr-str chrono-field/MILLI_OF_SECOND)
           "#jiffy/field :milli-of-second"))
    (is (= (pr-str chrono-field/MILLI_OF_DAY)
           "#jiffy/field :milli-of-day"))
    (is (= (pr-str chrono-field/SECOND_OF_MINUTE)
           "#jiffy/field :second-of-minute"))
    (is (= (pr-str chrono-field/SECOND_OF_DAY)
           "#jiffy/field :second-of-day"))
    (is (= (pr-str chrono-field/MINUTE_OF_HOUR)
           "#jiffy/field :minute-of-hour"))
    (is (= (pr-str chrono-field/MINUTE_OF_DAY)
           "#jiffy/field :minute-of-day"))
    (is (= (pr-str chrono-field/HOUR_OF_AMPM)
           "#jiffy/field :hour-of-ampm"))
    (is (= (pr-str chrono-field/CLOCK_HOUR_OF_AMPM)
           "#jiffy/field :clock-hour-of-ampm"))
    (is (= (pr-str chrono-field/HOUR_OF_DAY)
           "#jiffy/field :hour-of-day"))
    (is (= (pr-str chrono-field/CLOCK_HOUR_OF_DAY)
           "#jiffy/field :clock-hour-of-day"))
    (is (= (pr-str chrono-field/AMPM_OF_DAY)
           "#jiffy/field :ampm-of-day"))
    (is (= (pr-str chrono-field/DAY_OF_WEEK)
           "#jiffy/field :day-of-week"))
    (is (= (pr-str chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH)
           "#jiffy/field :aligned-day-of-week-in-month"))
    (is (= (pr-str chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR)
           "#jiffy/field :aligned-day-of-week-in-year"))
    (is (= (pr-str chrono-field/DAY_OF_MONTH)
           "#jiffy/field :day-of-month"))
    (is (= (pr-str chrono-field/DAY_OF_YEAR)
           "#jiffy/field :day-of-year"))
    (is (= (pr-str chrono-field/EPOCH_DAY)
           "#jiffy/field :epoch-day"))
    (is (= (pr-str chrono-field/ALIGNED_WEEK_OF_MONTH)
           "#jiffy/field :aligned-week-of-month"))
    (is (= (pr-str chrono-field/ALIGNED_WEEK_OF_YEAR)
           "#jiffy/field :aligned-week-of-year"))
    (is (= (pr-str chrono-field/MONTH_OF_YEAR)
           "#jiffy/field :month-of-year"))
    (is (= (pr-str chrono-field/PROLEPTIC_MONTH)
           "#jiffy/field :proleptic-month"))
    (is (= (pr-str chrono-field/YEAR_OF_ERA)
           "#jiffy/field :year-of-era"))
    (is (= (pr-str chrono-field/YEAR)
           "#jiffy/field :year"))
    (is (= (pr-str chrono-field/ERA)
           "#jiffy/field :era"))
    (is (= (pr-str chrono-field/INSTANT_SECONDS)
           "#jiffy/field :instant-seconds"))
    (is (= (pr-str chrono-field/OFFSET_SECONDS)
           "#jiffy/field :offset-seconds")))

  (testing "ChronoUnit"
    (is (= (pr-str chrono-unit/NANOS)
           "#jiffy/unit :nanos"))
    (is (= (pr-str chrono-unit/MICROS)
           "#jiffy/unit :micros"))
    (is (= (pr-str chrono-unit/MILLIS)
           "#jiffy/unit :millis"))
    (is (= (pr-str chrono-unit/SECONDS)
           "#jiffy/unit :seconds"))
    (is (= (pr-str chrono-unit/MINUTES)
           "#jiffy/unit :minutes"))
    (is (= (pr-str chrono-unit/HOURS)
           "#jiffy/unit :hours"))
    (is (= (pr-str chrono-unit/HALF_DAYS)
           "#jiffy/unit :half-days"))
    (is (= (pr-str chrono-unit/DAYS)
           "#jiffy/unit :days"))
    (is (= (pr-str chrono-unit/WEEKS)
           "#jiffy/unit :weeks"))
    (is (= (pr-str chrono-unit/MONTHS)
           "#jiffy/unit :months"))
    (is (= (pr-str chrono-unit/YEARS)
           "#jiffy/unit :years"))
    (is (= (pr-str chrono-unit/DECADES)
           "#jiffy/unit :decades"))
    (is (= (pr-str chrono-unit/CENTURIES)
           "#jiffy/unit :centuries"))
    (is (= (pr-str chrono-unit/MILLENNIA)
           "#jiffy/unit :millennia"))
    (is (= (pr-str chrono-unit/ERAS)
           "#jiffy/unit :eras"))
    (is (= (pr-str chrono-unit/FOREVER)
           "#jiffy/unit :forever")))

  (testing "ValueRange"
    (is (= (pr-str (value-range/create 100 200 300 400))
           "#jiffy/range [100 200 300 400]")))

  (testing "Period"
    (is (= (pr-str (period/of 1111 11 11))
           "#jiffy/period \"P1111Y11M11D\"")))

  (testing "DayOfWeek"
    (is (= (pr-str day-of-week/MONDAY)
           "#jiffy/day :monday"))
    (is (= (pr-str day-of-week/TUESDAY)
           "#jiffy/day :tuesday"))
    (is (= (pr-str day-of-week/WEDNESDAY)
           "#jiffy/day :wednesday"))
    (is (= (pr-str day-of-week/THURSDAY)
           "#jiffy/day :thursday"))
    (is (= (pr-str day-of-week/FRIDAY)
           "#jiffy/day :friday"))
    (is (= (pr-str day-of-week/SATURDAY)
           "#jiffy/day :saturday"))
    (is (= (pr-str day-of-week/SUNDAY)
           "#jiffy/day :sunday")))

  (testing "LocalDate"
    (is (= (pr-str (local-date/of 1111 11 11))
           "#jiffy/ld \"1111-11-11\"")))

  (testing "LocalDate"
    (is (= (pr-str (local-time/of 11 11 11 111111111))
           "#jiffy/lt \"11:11:11.111111111\"")))

  (testing "LocalDateTime"
    (is (= (pr-str (local-date-time/of (local-date/of 1111 11 11) (local-time/of 11 11 11 111111111)))
           "#jiffy/ldt \"1111-11-11T11:11:11.111111111\"")))

  (testing "Month"
    (is (= (pr-str month/JANUARY)
           "#jiffy/month :january"))
    (is (= (pr-str month/FEBRUARY)
           "#jiffy/month :february"))
    (is (= (pr-str month/MARCH)
           "#jiffy/month :march"))
    (is (= (pr-str month/APRIL)
           "#jiffy/month :april"))
    (is (= (pr-str month/MAY)
           "#jiffy/month :may"))
    (is (= (pr-str month/JUNE)
           "#jiffy/month :june"))
    (is (= (pr-str month/JULY)
           "#jiffy/month :july"))
    (is (= (pr-str month/AUGUST)
           "#jiffy/month :august"))
    (is (= (pr-str month/SEPTEMBER)
           "#jiffy/month :september"))
    (is (= (pr-str month/OCTOBER)
           "#jiffy/month :october"))
    (is (= (pr-str month/NOVEMBER)
           "#jiffy/month :november"))
    (is (= (pr-str month/DECEMBER)
           "#jiffy/month :december")))

  (testing "YearMonth"
    (is (= (pr-str (year-month/of 1111 11))
           "#jiffy/year-month \"1111-11\"")))

  (testing "Year"
    (is (= (pr-str (year/of 1111))
           "#jiffy/year \"1111\"")))

  (testing "ZoneOffset"
    (is (= (pr-str (zone-offset/of "+11:11"))
           "#jiffy/zone \"+11:11\"")))

  (testing "FixedClock"
    (is (= (pr-str (clock/fixed (instant-impl/create 1692982727 544376000)
                                (zone-id/of "Europe/Oslo")))
           (str "#jiffy/fixed-clock {:instant #jiffy/instant {:seconds 1692982727, :nanos 544376000}, "
                ":zone #jiffy/zone \"Europe/Oslo\"}"))))

  (testing "ZonedDateTime"
    (is (= (pr-str (zoned-date-time/of (local-date-time/of (local-date/of 1111 11 11)
                                                           (local-time/of 11 11 11 111111111))
                                       (zone-id/of "Europe/Oslo")))
           "#jiffy/zdt \"1111-11-11T11:11:11.111111111+00:53:28[Europe/Oslo]\"")))

  (testing "OffsetDateTime"
    (is (= (pr-str (offset-date-time/of (local-date-time/of (local-date/of 1111 11 11)
                                                            (local-time/of 11 11 11 111111111))
                                        (zone-offset/of "+11:11")))
           "#jiffy/odt \"1111-11-11T11:11:11.111111111+11:11\"")))

  (testing "OffsetTime"
    (is (= (pr-str (offset-time/of (local-time/of 11 11 11 111111111)
                                   (zone-offset/of "+11:11")))
           "#jiffy/ot \"11:11:11.111111111+11:11\"")))

  )

;; TODO - get these tests running cljs too
#?(:clj
   (deftest read-literal-test

     (is (= #jiffy/instant {:seconds 1692982727, :nanos 544376000}
            (instant-impl/create 1692982727 544376000)))

     (is (= #jiffy/duration "PT470272H58M47.544376S"
            (duration-impl/create 1692982727 544376000)))

     (testing "TemporalQuery"
       (is (= #jiffy/query :zone-id
              temporal-queries/ZONE_ID))
       (is (= #jiffy/query :chronology
              temporal-queries/CHRONO))
       (is (= #jiffy/query :precision
              temporal-queries/PRECISION))
       (is (= #jiffy/query :zone-offset
              temporal-queries/OFFSET))
       (is (= #jiffy/query :zone
              temporal-queries/ZONE))
       (is (= #jiffy/query :local-date
              temporal-queries/LOCAL_DATE))
       (is (= #jiffy/query :local-time
              temporal-queries/LOCAL_TIME)))

     (testing "ChronoField"
       (is (= #jiffy/field :nano_of_second
              chrono-field/NANO_OF_SECOND))
       (is (= #jiffy/field :nano_of_day
              chrono-field/NANO_OF_DAY))
       (is (= #jiffy/field :micro_of_second
              chrono-field/MICRO_OF_SECOND))
       (is (= #jiffy/field :micro_of_day
              chrono-field/MICRO_OF_DAY))
       (is (= #jiffy/field :milli_of_second
              chrono-field/MILLI_OF_SECOND))
       (is (= #jiffy/field :milli_of_day
              chrono-field/MILLI_OF_DAY))
       (is (= #jiffy/field :second_of_minute
              chrono-field/SECOND_OF_MINUTE))
       (is (= #jiffy/field :second_of_day
              chrono-field/SECOND_OF_DAY))
       (is (= #jiffy/field :minute_of_hour
              chrono-field/MINUTE_OF_HOUR))
       (is (= #jiffy/field :minute_of_day
              chrono-field/MINUTE_OF_DAY))
       (is (= #jiffy/field :hour_of_ampm
              chrono-field/HOUR_OF_AMPM))
       (is (= #jiffy/field :clock_hour_of_ampm
              chrono-field/CLOCK_HOUR_OF_AMPM))
       (is (= #jiffy/field :hour_of_day
              chrono-field/HOUR_OF_DAY))
       (is (= #jiffy/field :clock_hour_of_day
              chrono-field/CLOCK_HOUR_OF_DAY))
       (is (= #jiffy/field :ampm_of_day
              chrono-field/AMPM_OF_DAY))
       (is (= #jiffy/field :day_of_week
              chrono-field/DAY_OF_WEEK))
       (is (= #jiffy/field :aligned_day_of_week_in_month
              chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH))
       (is (= #jiffy/field :aligned_day_of_week_in_year
              chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR))
       (is (= #jiffy/field :day_of_month
              chrono-field/DAY_OF_MONTH))
       (is (= #jiffy/field :day_of_year
              chrono-field/DAY_OF_YEAR))
       (is (= #jiffy/field :epoch_day
              chrono-field/EPOCH_DAY))
       (is (= #jiffy/field :aligned_week_of_month
              chrono-field/ALIGNED_WEEK_OF_MONTH))
       (is (= #jiffy/field :aligned_week_of_year
              chrono-field/ALIGNED_WEEK_OF_YEAR))
       (is (= #jiffy/field :month_of_year
              chrono-field/MONTH_OF_YEAR))
       (is (= #jiffy/field :proleptic_month
              chrono-field/PROLEPTIC_MONTH))
       (is (= #jiffy/field :year_of_era
              chrono-field/YEAR_OF_ERA))
       (is (= #jiffy/field :year
              chrono-field/YEAR))
       (is (= #jiffy/field :era
              chrono-field/ERA))
       (is (= #jiffy/field :instant_seconds
              chrono-field/INSTANT_SECONDS))
       (is (= #jiffy/field :offset_seconds
              chrono-field/OFFSET_SECONDS)))

     (testing "ChronoUnit"
       (is (= #jiffy/unit :nanos
              chrono-unit/NANOS))
       (is (= #jiffy/unit :micros
              chrono-unit/MICROS))
       (is (= #jiffy/unit :millis
              chrono-unit/MILLIS))
       (is (= #jiffy/unit :seconds
              chrono-unit/SECONDS))
       (is (= #jiffy/unit :minutes
              chrono-unit/MINUTES))
       (is (= #jiffy/unit :hours
              chrono-unit/HOURS))
       (is (= #jiffy/unit :half-days
              chrono-unit/HALF_DAYS))
       (is (= #jiffy/unit :days
              chrono-unit/DAYS))
       (is (= #jiffy/unit :weeks
              chrono-unit/WEEKS))
       (is (= #jiffy/unit :months
              chrono-unit/MONTHS))
       (is (= #jiffy/unit :years
              chrono-unit/YEARS))
       (is (= #jiffy/unit :decades
              chrono-unit/DECADES))
       (is (= #jiffy/unit :centuries
              chrono-unit/CENTURIES))
       (is (= #jiffy/unit :millennia
              chrono-unit/MILLENNIA))
       (is (= #jiffy/unit :eras
              chrono-unit/ERAS))
       (is (= #jiffy/unit :forever
              chrono-unit/FOREVER)))

     (testing "ValueRange"
       (is (= #jiffy/range [100 200 300 400]
              (value-range/create 100 200 300 400))))

     (testing "Period"
       (is (= #jiffy/period "P1111Y11M11D"
              (period/of 1111 11 11))))

     (testing "DayOfWeek"
       (is (= #jiffy/day :monday
              day-of-week/MONDAY))
       (is (= #jiffy/day :tuesday
              day-of-week/TUESDAY))
       (is (= #jiffy/day :wednesday
              day-of-week/WEDNESDAY))
       (is (= #jiffy/day :thursday
              day-of-week/THURSDAY))
       (is (= #jiffy/day :friday
              day-of-week/FRIDAY))
       (is (= #jiffy/day :saturday
              day-of-week/SATURDAY))
       (is (= #jiffy/day :sunday
              day-of-week/SUNDAY)))

     (testing "LocalDate"
       (is (= #jiffy/ld "1111-11-11"
              (local-date/of 1111 11 11))))

     (testing "LocalTime"
       (is (= #jiffy/lt "11:11:11.111111111"
              (local-time/of 11 11 11 111111111))))

     (testing "LocalDateTime"
       (is (= #jiffy/ldt "1111-11-11T11:11:11.111111111"
              (local-date-time/of (local-date/of 1111 11 11) (local-time/of 11 11 11 111111111)))))

     (testing "Month"
       (is (= #jiffy/month :january
              month/JANUARY))
       (is (= #jiffy/month :february
              month/FEBRUARY))
       (is (= #jiffy/month :march
              month/MARCH))
       (is (= #jiffy/month :april
              month/APRIL))
       (is (= #jiffy/month :may
              month/MAY))
       (is (= #jiffy/month :june
              month/JUNE))
       (is (= #jiffy/month :july
              month/JULY))
       (is (= #jiffy/month :august
              month/AUGUST))
       (is (= #jiffy/month :september
              month/SEPTEMBER))
       (is (= #jiffy/month :october
              month/OCTOBER))
       (is (= #jiffy/month :november
              month/NOVEMBER))
       (is (= #jiffy/month :december
              month/DECEMBER)))

     (testing "YearMonth"
       (is (= #jiffy/year-month "1111-11"
              (year-month/of 1111 11))))

     (testing "Year"
       (is (= #jiffy/year "1111"
              (year/of 1111))))

     (testing "ZoneOffset"
       (is (= #jiffy/zone "+11:11"
              (zone-offset/of "+11:11"))))

     (testing "FixedClock"
       (is (= #jiffy/fixed-clock {:instant #jiffy/instant {:seconds 1692982727, :nanos 544376000}, :zone #jiffy/zone "Europe/Oslo"}
              (clock/fixed (instant-impl/create 1692982727 544376000)
                           (zone-id/of "Europe/Oslo")))))

     (testing "ZonedDateTime"
       (is (= #jiffy/zdt "1111-11-11T11:11:11.111111111+00:53:28[Europe/Oslo]"
              (zoned-date-time/of (local-date-time/of (local-date/of 1111 11 11)
                                                      (local-time/of 11 11 11 111111111))
                                  (zone-id/of "Europe/Oslo")))))

     (testing "OffsetDateTime"
       (is (= #jiffy/odt "1111-11-11T11:11:11.111111111+11:11"
              (offset-date-time/of (local-date-time/of (local-date/of 1111 11 11)
                                                       (local-time/of 11 11 11 111111111))
                                   (zone-offset/of "+11:11")))))

     (testing "OffsetTime"
       (is (= #jiffy/ot "11:11:11.111111111+11:11"
              (offset-time/of (local-time/of 11 11 11 111111111)
                              (zone-offset/of "+11:11")))))

     ))
