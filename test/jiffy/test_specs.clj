(ns jiffy.test-specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.clock :as clock-impl]
            [jiffy.conversion :as conversion]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.duration :as duration-impl]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.local-date-impl :as local-date-impl]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.month :as month]
            [jiffy.period :as period-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time-impl
             :as chrono-local-date-time-impl]
            [jiffy.protocols.chrono.chrono-period-impl :as chrono-period-impl]
            [jiffy.protocols.chrono.hijrah-date :as hijrah-date]
            [jiffy.protocols.chrono.hijrah-era :as hijrah-era]
            [jiffy.protocols.chrono.japanese-date :as japanese-date]
            [jiffy.protocols.chrono.japanese-era :as japanese-era]
            [jiffy.protocols.chrono.minguo-date :as minguo-date]
            [jiffy.protocols.chrono.thai-buddhist-date :as thai-buddhist-date]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.format.parsed :as parsed]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.month-day :as month-day]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.period :as period]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.year :as year]
            [jiffy.protocols.year-month :as year-month]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as ZoneOffsetTransition]
            [jiffy.protocols.zone.zone-offset-transition-rule
             :as ZoneOffsetTransitionRule]
            [jiffy.protocols.zone.zone-rules :as ZoneRules]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-adjusters :as temporal-adjusters-impl]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.zoned-date-time :as zoned-date-time-impl]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.zone.zone-offset-transition-rule :as transition-rule]
            [jiffy.zone.zone-rules :as zone-rules]
            [jiffy.zone.zone-rules-conversion]
            [jiffy.zone.zone-rules-store :as zone-rules-store]))

:jiffy.zone.zone-rules-conversion/keep

(s/def ::duration/duration ::duration-impl/duration)
(s/def ::period/period ::period-impl/period)
(s/def ::instant/instant ::instant-impl/instant)
(s/def ::local-date-time/local-date-time ::local-date-time-impl/local-date-time)
(s/def ::local-time/local-time ::local-time-impl/local-time)
(s/def ::zone-offset/zone-offset ::zone-offset-impl/zone-offset)
(s/def ::zoned-date-time/zoned-date-time ::zoned-date-time-impl/zoned-date-time)
(s/def ::ZoneOffsetTransition/zone-offset-transition ::zone-offset-transition/zone-offset-transition)
(s/def ::ZoneOffsetTransitionRule/zone-offset-transition-rule ::transition-rule/zone-offset-transition-rule)
(s/def ::zone-rules/zone-rules (set (vals @zone-rules-store/zone-id->rules)))

(s/def ::local-date/local-date ::local-date-impl/local-date)

;; (->> (s/gen (s/and ::local-date-impl/local-date
;;                    local-date-impl/valid?
;;                    ))
;;      (gen/sample ))

;; (->> (s/tuple ~@fields-spec)
;;      s/gen
;;      (gen/such-that (fn [~(vec field-names)]
;;                       (if (seq ~args)
;;                         (do
;;                           ~@args)
;;                         true)))
;;      (gen/fmap #(apply constructor# %)))

(s/def ::clock/clock
  (s/with-gen #(satisfies? clock/IClock %)
    (fn [] (gen/one-of [(s/gen ::clock-impl/fixed-clock)]))))

(s/def ::temporal-field/temporal-field
  (s/with-gen #(satisfies? temporal-field/ITemporalField %)
    (fn [] (gen/one-of (map gen/return (chrono-field/values))))))

(s/def ::chrono-unit/chrono-unit
  (s/with-gen chrono-unit/chrono-unit?
    (fn [] (gen/one-of (map gen/return (chrono-unit/values))))))

(s/def ::month/month
  (s/with-gen month/month?
    (fn [] (gen/one-of (map gen/return (month/values))))))

(s/def ::day-of-week/day-of-week
  (s/with-gen day-of-week/day-of-week?
    (fn [] (gen/one-of (map gen/return (day-of-week/values))))))

(s/def ::temporal-unit/temporal-unit ::chrono-unit/chrono-unit)

(s/def ::temporal-amount/temporal-amount
  (s/with-gen #(satisfies? temporal-amount/ITemporalAmount %)
    (fn [] (gen/one-of (map s/gen [;; ::chrono-period-impl/chrono-period-impl
                                   ::duration/duration
                                   ;; ::period-impl/period
                                   ])))))

(s/def ::chrono-local-date/chrono-local-date
  (s/with-gen #(satisfies? chrono-local-date/IChronoLocalDate %)
    (fn [] (gen/one-of (map s/gen [;; :jiffy.chrono.hijrah-date/hijrah-date
                                   ;; :jiffy.chrono.japanese-date/japanese-date
                                   ;; :jiffy.chrono.minguo-date/minguo-date
                                   ;; :jiffy.chrono.thai-buddhist-date/thai-buddhist-date
                                   :jiffy.local-date/local-date])))))

(s/def ::temporal/temporal
  (s/with-gen #(satisfies? temporal/ITemporal %)
    (fn [] (gen/one-of (map s/gen [ ;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                   ;; :jiffy.chrono.chrono-local-date-time-impl/chrono-local-date-time-impl
                                   ;; :jiffy.chrono.chrono-zoned-date-time-impl/chrono-zoned-date-time-impl
                                   ;; :jiffy.chrono.hijrah-date/hijrah-date
                                   ;; :jiffy.chrono.japanese-date/japanese-date
                                   ;; :jiffy.chrono.minguo-date/minguo-date
                                   ;; :jiffy.chrono.thai-buddhist-date/thai-buddhist-date
                                   :jiffy.instant-impl/instant
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
                                   temporal-adjusters-impl/temporal-adjusters-specs))))))

(s/def ::temporal-accessor/temporal-accessor
  (s/with-gen any? ;; #(satisfies? temporal-accessor/ITemporalAccessor %)
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

(comment

  ;; (satisfies? temporal/ITemporal
  ;;               (first (gen/sample (s/gen :jiffy.instant/instant))))

  (gen/sample (s/gen (:args (s/get-spec #'instant-impl/with))))

  (gen/sample (s/gen (:args (s/get-spec #'instant-impl/of-epoch-milli))))

  (gen/sample (s/gen :jiffy.instant/instant))

  (gen/sample (s/gen ::temporal-amount/temporal-amount))

  (gen/sample (s/gen :jiffy.protocols.temporal.temporal-unit/temporal-unit))


  (first
   (gen/sample
    (s/gen (s/cat
            :this
            (s/spec :jiffy.instant/instant)
            :amount-to-subtract
            (s/spec
             :jiffy.protocols.temporal.temporal-amount/temporal-amount)))))

  (gen/sample (s/gen ::temporal-field/temporal-field))

  (gen/sample (s/gen :jiffy.protocols.temporal.temporal-field/temporal-field))

  (first (gen/sample (s/gen :jiffy.zoned-date-time/zoned-date-time)))

  (conversion/jiffy->java (:date-time (first (gen/sample (s/gen :jiffy.zoned-date-time/zoned-date-time)))))

  (gen/sample (s/gen :jiffy.zone.zone-offset-transition-rule/zone-offset-transition-rule))

  (gen/sample (s/gen :jiffy.protocols.zone.zone-rules/zone-rules))

  (-> :jiffy.zone.zone-rules/zone-rules
      s/gen
      gen/sample)

  zone-offset-transition-rule



  (satisfies? temporal/ITemporal
              (first (gen/sample (s/gen :jiffy.zoned-date-time/zoned-date-time))))



  ;; (gen/sample
  ;;  (gen'/string-from-regex
  ;;   (re-pattern
  ;;    (str
  ;;     "([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?"))))
  )
