(ns jiffy.parity-tests.test-specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.chrono.chrono-period-impl :as chrono-period-impl]
            [jiffy.clock :as clock]
            [jiffy.duration :as duration]
            [jiffy.period :as period]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-unit :as temporal-unit]))

(s/def ::clock/clock
  (s/with-gen #(satisfies? clock/IClock %)
    (fn [] (gen/one-of [(s/gen ::system-clock)]))))

(s/def ::temporal-field/temporal-field
  (s/with-gen #(satisfies? temporal-field/ITemporalField %)
    (fn [] (gen/one-of (map gen/return (chrono-field/values))))))

(s/def ::chrono-unit/chrono-unit
  (s/with-gen #(satisfies? chrono-unit/IChronoUnit %)
    (fn [] (gen/one-of (map gen/return (chrono-unit/values))))))

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


