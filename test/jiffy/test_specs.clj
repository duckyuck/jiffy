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
            [jiffy.month :as month-impl]
            [jiffy.month-day :as month-day-impl]
            [jiffy.offset-date-time :as offset-date-time-impl]
            [jiffy.offset-time :as offset-time-impl]
            [jiffy.period :as period-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-local-date-time-impl
             :as chrono-local-date-time-impl]
            [jiffy.protocols.chrono.chrono-period-impl :as chrono-period-impl]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.chrono.era :as era]
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
            [jiffy.protocols.month :as month]
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
            [jiffy.protocols.zone-region :as zone-region]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-offset-transition-rule :as zone-offset-transition-rule]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-adjusters :as temporal-adjusters-impl]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.year :as year-impl]
            [jiffy.year-month :as year-month-impl]
            [jiffy.zoned-date-time :as zoned-date-time-impl]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.zone-region :as zone-region-impl]
            [jiffy.zone.zone-offset-transition-impl :as zone-offset-transition-impl]
            [jiffy.zone.zone-offset-transition-rule :as transition-rule-impl]
            [jiffy.zone.zone-rules :as zone-rules-impl]
            [jiffy.zone.zone-rules-conversion]
            [jiffy.zone.zone-rules-store :as zone-rules-store]))

:jiffy.zone.zone-rules-conversion/keep

(s/def ::duration/duration ::duration-impl/duration)
(s/def ::period/period ::period-impl/period)
(s/def ::instant/instant ::instant-impl/instant)
(s/def ::local-date/local-date ::local-date-impl/local-date)
(s/def ::local-date-time/local-date-time ::local-date-time-impl/local-date-time)
(s/def ::local-time/local-time ::local-time-impl/local-time)
(s/def ::zone-offset/zone-offset ::zone-offset-impl/zone-offset)
(s/def ::offset-time/offset-time ::offset-time-impl/offset-time)
(s/def ::zoned-date-time/zoned-date-time ::zoned-date-time-impl/zoned-date-time)
(s/def ::offset-date-time/offset-date-time ::offset-date-time-impl/offset-date-time)
(s/def ::zone-offset-transition/zone-offset-transition ::zone-offset-transition-impl/zone-offset-transition)
(s/def ::zone-offset-transition-rule/zone-offset-transition-rule (->> @zone-rules-store/zone-id->rules
                                                                      vals
                                                                      (mapcat :last-rules)
                                                                      set))
(s/def ::zone-rules-impl/zone-rules (set (vals @zone-rules-store/zone-id->rules)))
(s/def ::zone-rules/zone-rules ::zone-rules-impl/zone-rules)
(s/def ::month-day/month-day ::month-day-impl/month-day)
(s/def ::j/zone-id (set (keys @zone-rules-store/zone-id->rules)))

(s/def ::transition-rule-impl/time-definition (set (vals @transition-rule-impl/enums)))

(s/def ::zone-region/zone-region
  (s/with-gen any?
    #(->> #'jiffy.zone-region-impl/of-id
          s/get-spec
          :args
          s/gen
          (gen/fmap (fn [[id]]
                      (zone-region-impl/of-id id true))))))

(s/def ::zone-offset-transition-impl/zone-offset-transition
  (s/with-gen any?
    #(->> 'jiffy.zone.zone-offset-transition-impl/of
          s/get-spec
          :args
          s/gen
          (gen/such-that (fn [[transition offset-before offset-after]]
                           (try (jiffy.zone.zone-offset-transition-impl/of
                                 (local-date-time/with-nano transition 0)
                                 offset-before
                                 offset-after)
                                true
                                (catch Exception e
                                  false))))
          (gen/fmap (fn [[transition offset-before offset-after]]
                      (jiffy.zone.zone-offset-transition-impl/of
                       (local-date-time/with-nano transition 0)
                       offset-before
                       offset-after))))))

(defn to-string-gen [spec]
  #(->> spec
        s/gen
        (gen/fmap (fn [jiffy-object]
                    (.toString (conversion/jiffy->java jiffy-object))))))

(s/def ::zoned-date-time-impl/string
  (s/with-gen string? (to-string-gen ::zoned-date-time/zoned-date-time)))

(s/def ::offset-date-time-impl/string
  (s/with-gen string? (to-string-gen ::offset-date-time/offset-date-time)))

(s/def ::offset-time-impl/string
  (s/with-gen string? (to-string-gen ::offset-time/offset-time)))

(s/def ::local-date-time-impl/string
  (s/with-gen string? (to-string-gen ::local-date-time/local-date-time)))

(s/def ::local-time-impl/string
  (s/with-gen string? (to-string-gen ::local-time/local-time)))

(s/def ::local-date-impl/string
  (s/with-gen string? (to-string-gen ::local-date/local-date)))

(s/def ::duration-impl/string
  (s/with-gen string? (to-string-gen ::duration/duration)))

(s/def ::period-impl/string
  (s/with-gen string? (to-string-gen ::period/period)))

(defn formatter-gen [spec formatter-builder]
  #(->> spec
        s/gen
        (gen/fmap (fn [jiffy-object]
                    (.format (conversion/jiffy->java jiffy-object)
                             (.toFormatter formatter-builder))))))

(s/def ::year-month-impl/string
  (s/with-gen string?
    (formatter-gen ::year-month-impl/year-month
                   (-> (java.time.format.DateTimeFormatterBuilder.)
                       (.appendValue java.time.temporal.ChronoField/YEAR 4 10 java.time.format.SignStyle/EXCEEDS_PAD)
                       (.appendLiteral "-")
                       (.appendValue java.time.temporal.ChronoField/MONTH_OF_YEAR 2)))))

(s/def ::year-impl/string
  (s/with-gen string?
    (formatter-gen ::year-impl/year
                   (-> (java.time.format.DateTimeFormatterBuilder.)
                       (.appendValue java.time.temporal.ChronoField/YEAR 4 10 java.time.format.SignStyle/EXCEEDS_PAD)))))


(s/def ::transition-rule-impl/zone-offset-transition-rule
  (->> @zone-rules-store/zone-id->rules
       vals
       (mapcat :last-rules)
       set))

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
  (s/with-gen month-impl/month?
    (fn [] (gen/one-of (map gen/return (month-impl/values))))))

(s/def ::month-impl/month ::month/month)

(s/def ::day-of-week/day-of-week
  (s/with-gen day-of-week/day-of-week?
    (fn [] (gen/one-of (map gen/return (day-of-week/values))))))

(s/def ::temporal-unit/temporal-unit ::chrono-unit/chrono-unit)

(s/def ::temporal-amount/temporal-amount
  (s/with-gen #(satisfies? temporal-amount/ITemporalAmount %)
    (fn [] (gen/one-of (map s/gen [
                                   ;; ::chrono-period-impl/chrono-period-impl
                                   ::duration/duration
                                   ::period-impl/period
                                   ])))))

(s/def ::chrono-local-date/chrono-local-date
  (s/with-gen #(satisfies? chrono-local-date/IChronoLocalDate %)
    (fn [] (gen/one-of (map s/gen [
                                   :jiffy.local-date/local-date
                                   ;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                   ;; :jiffy.chrono.hijrah-date/hijrah-date
                                   ;; :jiffy.chrono.japanese-date/japanese-date
                                   ;; :jiffy.chrono.minguo-date/minguo-date
                                   ;; :jiffy.chrono.thai-buddhist-date/thai-buddhist-date
                                   ])))))

(s/def ::chrono-local-date-time/chrono-local-date-time
  (s/with-gen #(satisfies? chrono-local-date-time/IChronoLocalDateTime %)
    (fn [] (gen/one-of (map s/gen [:jiffy.local-date-time/local-date-time])))))

(s/def ::temporal/temporal
  (s/with-gen #(satisfies? temporal/ITemporal %)
    (fn [] (gen/one-of (map s/gen [
                                   :jiffy.instant-impl/instant
                                   :jiffy.local-date/local-date
                                   :jiffy.local-date-time/local-date-time
                                   :jiffy.local-time/local-time
                                   ;; :jiffy.offset-date-time/offset-date-time
                                   ;; :jiffy.offset-time/offset-time
                                   ;; :jiffy.year/year
                                   ;; :jiffy.year-month/year-month
                                   ;; :jiffy.zoned-date-time/zoned-date-time

                                   ::chrono-local-date/chrono-local-date
                                   ::chrono-local-date-time/chrono-local-date-time
                                   ;; ::chrono-zoned-date-time/chrono-zoned-date-time
                                   ;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                   ;; :jiffy.chrono.chrono-local-date-time-impl/chrono-local-date-time-impl
                                   ;; :jiffy.chrono.chrono-zoned-date-time-impl/chrono-zoned-date-time-impl

                                   ;; :jiffy.chrono.hijrah-date/hijrah-date
                                   ;; :jiffy.chrono.japanese-date/japanese-date
                                   ;; :jiffy.chrono.minguo-date/minguo-date
                                   ;; :jiffy.chrono.thai-buddhist-date/thai-buddhist-date
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
                                   [
                                    ;; ::day-of-week/day-of-week
                                    ::instant/instant
                                    ::local-date/local-date
                                    ::local-date-time/local-date-time
                                    ::local-time/local-time
                                    ::month/month
                                    ;; ::month-day/month-day
                                    ;; ::offset-date-time/offset-date-time
                                    ;; ::offset-time/offset-time
                                    ;; ::year/year
                                    ;; ::year-month/year-month
                                    ;; ::zone-offset/zone-offset
                                    ;; ::iso-era/iso-era

                                    ::chrono-local-date/chrono-local-date
                                    ::chrono-local-date-time/chrono-local-date-time
                                    ;; ::era/era

                                    ;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                    ;; :jiffy.chrono.chrono-local-date-time-impl/chrono-local-date-time-impl

                                    ;; ::hijrah-date/hijrah-date
                                    ;; ::hijrah-era/hijrah-era
                                    ;; ::japanese-date/japanese-date
                                    ;; ::japanese-era/japanese-era
                                    ;; ::minguo-date/minguo-date
                                    ;; ::minguo-era/minguo-era
                                    ;; ::thai-buddhist-date/thai-buddhist-date
                                    ;; ::thai-buddhist-era/thai-buddhist-era
                                    ]
                                   temporal-adjusters-impl/temporal-adjusters-specs))))))

(s/def ::temporal-accessor/temporal-accessor
  (s/with-gen any? ;; #(satisfies? temporal-accessor/ITemporalAccessor %)
    (fn [] (gen/one-of (map s/gen [
                                   ;; ::day-of-week/day-of-week
                                   ::instant/instant
                                   ::local-date/local-date
                                   ::local-date-time/local-date-time
                                   ::local-time/local-time
                                   ::month/month
                                   ;; ::month-day/month-day
                                   ;; ::offset-date-time/offset-date-time
                                   ;; ::offset-time/offset-time
                                   ;; ::year/year
                                   ;; ::year-month/year-month
                                   ;; ::zoned-date-time/zoned-date-time
                                   ;; TODO: See method ZoneId#toTemporal
                                   ;; ::zone-offset/zone-offset

                                   ::chrono-local-date/chrono-local-date
                                   ;; :jiffy.chrono.chrono-local-date-impl/chrono-local-date-impl
                                   ::chrono-local-date-time/chrono-local-date-time
                                   ;; :jiffy.chrono.chrono-local-date-time-impl/chrono-local-date-time-impl
                                   ;; ::chronology/chronology
                                   ;; ::chrono-zoned-date-time/chrono-zoned-date-time
                                   ;; :jiffy.chrono.chrono-zoned-date-time-impl/chrono-zoned-date-time-impl

                                   ;; ::era/era
                                   ;; ::iso-era/iso-era

                                   ;; ::hijrah-era/hijrah-era
                                   ;; ::hijrah-date/hijrah-date
                                   ;; ::japanese-era/japanese-era
                                   ;; ::japanese-date/japanese-date
                                   ;; ::minguo-date/minguo-date
                                   ;; ::minguo-era/minguo-era
                                   ;; ::thai-buddhist-date/thai-buddhist-date
                                   ;; ::thai-buddhist-era/thai-buddhist-era

                                   ;; ::parsed/parsed
                                   ])))))

(s/def ::zone-id/zone-id
  (s/with-gen #(satisfies? zone-id/IZoneId %)
    (fn [] (gen/one-of (map s/gen [
                                   ::zone-offset/zone-offset
                                   ::zone-region/zone-region
                                   ])))))

(comment

  (-> #'jiffy.duration/parse
      s/get-spec
      :args
      s/gen
      gen/sample)

  (-> ::offset-date-time/offset-date-time
      s/gen
      gen/sample)

  (-> ::zone-id/zone-id
      s/gen
      gen/sample)

  (-> #'jiffy.local-date/of-instant
      s/get-spec
      :args
      s/gen
      gen/sample)

  (-> :jiffy.zoned-date-time/zoned-date-time
      s/gen
      gen/sample
      first
      :date-time
      conversion/jiffy->java)

  ;; (gen/sample
  ;;  (gen'/string-from-regex
  ;;   (re-pattern
  ;;    (str
  ;;     "([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?"))))
  )
