(ns jiffy.parity-tests.test-specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.chrono.chrono-local-date-time-impl :as chrono-local-date-time-impl]
            [jiffy.chrono.chrono-period-impl :as chrono-period-impl]
            [jiffy.chrono.hijrah-date :as hijrah-date]
            [jiffy.chrono.hijrah-era :as hijrah-era]
            [jiffy.chrono.japanese-date :as japanese-date]
            [jiffy.chrono.japanese-era :as japanese-era]
            [jiffy.chrono.minguo-date :as minguo-date]
            [jiffy.chrono.thai-buddhist-date :as thai-buddhist-date]
            [jiffy.clock :as clock]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.duration :as duration]
            [jiffy.format.parsed :as parsed]
            [jiffy.instant :as instant]
            [jiffy.local-date :as local-date]
            [jiffy.local-date-time :as local-date-time]
            [jiffy.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.month-day :as month-day]
            [jiffy.offset-date-time :as offset-date-time]
            [jiffy.offset-time :as offset-time]
            [jiffy.period :as period]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-adjusters :as temporal-adjusters]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.year-impl :as year]
            [jiffy.year-month :as year-month]
            [jiffy.zoned-date-time :as zoned-date-time]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]))

(s/def ::clock/clock
  (s/with-gen #(satisfies? clock/IClock %)
    (fn [] (gen/one-of [(s/gen ::clock/system-clock)]))))

(s/def ::temporal-field/temporal-field
  (s/with-gen #(satisfies? temporal-field/ITemporalField %)
    (fn [] (gen/one-of (map gen/return (chrono-field/values))))))

(s/def ::chrono-unit/chrono-unit
  (s/with-gen #(satisfies? chrono-unit/IChronoUnit %)
    (fn [] (gen/one-of (map gen/return (chrono-unit/values))))))

(s/def ::month/month
  (s/with-gen #(satisfies? month/IMonth %)
    (fn [] (gen/one-of (map gen/return (month/values))))))

(s/def ::temporal-unit/temporal-unit ::chrono-unit/chrono-unit)

(s/def ::temporal-amount/temporal-amount
  (s/with-gen #(satisfies? temporal-amount/ITemporalAmount %)
    (fn [] (gen/one-of (map s/gen [ ;; ::chrono-period-impl/chrono-period-impl
                                   ::duration/duration
                                   ;; ::period/period
                                   ])))))

(s/def ::temporal/temporal
  (s/with-gen #(satisfies? temporal/ITemporal %)
    (fn [] (gen/one-of (map s/gen [;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                   ;; :jiffy.chrono.chrono-local-date-time-impl/chrono-local-date-time-impl
                                   ;; :jiffy.chrono.chrono-zoned-date-time-impl/chrono-zoned-date-time-impl
                                   ;; :jiffy.chrono.hijrah-date/hijrah-date
                                   ;; :jiffy.chrono.japanese-date/japanese-date
                                   ;; :jiffy.chrono.minguo-date/minguo-date
                                   ;; :jiffy.chrono.thai-buddhist-date/thai-buddhist-date
                                   :jiffy.instant/instant
                                   ;; :jiffy.local-date/local-date
                                   ;; :jiffy.local-date-time/local-date-time
                                   ;; :jiffy.local-time/local-time
                                   ;; :jiffy.offset-date-time/offset-date-time
                                   ;; :jiffy.offset-time/offset-time
                                   ;; :jiffy.year/year
                                   ;; :jiffy.year-month/year-month
                                   ;; :jiffy.zoned-date-time/zoned-date-time
                                   ])))))

(s/def ::temporal-query/temporal-query
  (s/with-gen #(satisfies? temporal-query/ITemporalQuery %)
    (fn [] (gen/one-of (map gen/return [temporal-queries/ZONE_ID
                                        temporal-queries/CHRONO
                                        temporal-queries/PRECISION
                                        temporal-queries/ZONE
                                        temporal-queries/OFFSET
                                        temporal-queries/LOCAL_DATE
                                        temporal-queries/LOCAL_TIME])))))

(s/def ::temporal-adjuster/temporal-adjuster
  (s/with-gen #(satisfies? temporal-adjuster/ITemporalAdjuster %)
    (fn [] (gen/one-of (map s/gen (concat
                                   [;; ::day-of-week/day-of-week
                                    ::instant/instant
                                    ;; ::local-date/local-date
                                    ;; ::local-date-time/local-date-time
                                    ;; ::local-time/local-time
                                    ;; ::month/month
                                    ;; ::month-day/month-day
                                    ;; ::offset-date-time/offset-date-time
                                    ;; ::offset-time/offset-time
                                    ;; ::year/year
                                    ;; ::year-month/year-month
                                    ;; ::zone-offset/zone-offset
                                    ]
                                   temporal-adjusters/temporal-adjusters-specs))))))

(s/def ::temporal-accessor/temporal-accessor
  (s/with-gen #(satisfies? temporal-accessor/ITemporalAccessor %)
    (fn [] (gen/one-of (map s/gen [;; ::local-date-time/local-date-time
                                   ::instant/instant
                                   ;; ::day-of-week/day-of-week
                                   ;; ::hijrah-era/hijrah-era
                                   ;; ::hijrah-date/hijrah-date
                                   ;; ::japanese-era/japanese-era
                                   ;; ::japanese-date/japanese-date
                                   ;; ::chrono-zoned-date-time-impl/chrono-zoned-date-time-impl
                                   ;; ::minguo-date/minguo-date
                                   ;; ::thai-buddhist-date/thai-buddhist-date
                                   ;; ::chrono-local-date-time-impl/chrono-local-date-time-impl
                                   ;; ::local-date/local-date
                                   ;; ::zone-offset/zone-offset
                                   ;; ::offset-time/offset-time
                                   ;; ::year/year
                                   ;; ::parsed/parsed
                                   ;; ::month/month
                                   ;; ::local-time/local-time
                                   ;; ::zoned-date-time/zoned-date-time
                                   ;; ::offset-date-time/offset-date-time
                                   ;; ::year-month/year-month
                                   ;; ::month-day/month-day
                                   ])))))

(s/def ::zone-id/zone-id
  (s/with-gen #(satisfies? zone-id/IZoneId %)
    (fn [] (gen/one-of (map s/gen [::zone-offset/zone-offset
                                   ;; ::zone-region/zone-region
                                   ])))))
