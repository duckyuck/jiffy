(ns jiffy.edn
  (:require [clojure.edn :as edn]
            #?(:cljs [cljs.reader :as reader])
            [jiffy.clock #?@(:cljs [:refer [FixedClock]])]
            [jiffy.day-of-week #?@(:cljs [:refer [DayOfWeek]])]
            [jiffy.duration-impl #?@(:cljs [:refer [Duration]])]
            [jiffy.instant-impl #?@(:cljs [:refer [Instant]])]
            [jiffy.local-date-impl #?@(:cljs [:refer [LocalDate]])]
            [jiffy.local-date]
            [jiffy.local-time-impl #?@(:cljs [:refer [LocalTime]])]
            [jiffy.local-time]
            [jiffy.local-date-time-impl #?@(:cljs [:refer [LocalDateTime]])]
            [jiffy.local-date-time]
            [jiffy.zoned-date-time-impl #?@(:cljs [:refer [ZonedDateTime]])]
            [jiffy.offset-date-time-impl #?@(:cljs [:refer [OffsetDateTime]])]
            [jiffy.offset-date-time]
            [jiffy.offset-time-impl #?@(:cljs [:refer [OffsetTime]])]
            [jiffy.offset-time]
            [jiffy.zone-region-impl #?@(:cljs [:refer [ZonedRegion]])]
            [jiffy.month #?@(:cljs [:refer [Month]])]
            [jiffy.year-month #?@(:cljs [:refer [YearMonth]])]
            [jiffy.year #?@(:cljs [:refer [Year]])]
            [jiffy.period #?@(:cljs [:refer [Period]])]
            [jiffy.temporal.temporal-queries]
            [jiffy.temporal.value-range #?@(:cljs [:refer [ValueRange]])]
            [jiffy.temporal.temporal-query #?@(:cljs [:refer [TemporalQuery]])]
            [jiffy.temporal.chrono-field #?@(:cljs [:refer [ChronoField]])]
            [jiffy.temporal.chrono-unit #?@(:cljs [:refer [ChronoUnit]])]
            [jiffy.zone-offset]
            [jiffy.zone-id]
            [jiffy.zone-offset-impl #?@(:cljs [:refer [ZoneOffset]])]
            [jiffy.zone.zone-offset-transition-rule #?@(:cljs [:refer [ZoneOffsetTransitionRule]])]
            [jiffy.zone.zone-rules-impl #?@(:cljs [:refer [ZoneRules]])]
            [jiffy.protocols.string]
            [clojure.string :as str])
  #?(:clj (:import [java.io Writer]
                   [jiffy.clock FixedClock]
                   [jiffy.day_of_week DayOfWeek]
                   [jiffy.duration_impl Duration]
                   [jiffy.instant_impl Instant]
                   [jiffy.local_date_impl LocalDate]
                   [jiffy.local_time_impl LocalTime]
                   [jiffy.local_date_time_impl LocalDateTime]
                   [jiffy.zoned_date_time_impl ZonedDateTime]
                   [jiffy.offset_date_time_impl OffsetDateTime]
                   [jiffy.offset_time_impl OffsetTime]
                   [jiffy.zone_region_impl ZoneRegion]
                   [jiffy.month Month]
                   [jiffy.year_month YearMonth]
                   [jiffy.year Year]
                   [jiffy.period Period]
                   [jiffy.temporal.value_range ValueRange]
                   [jiffy.temporal.temporal_query TemporalQuery]
                   [jiffy.temporal.chrono_field ChronoField]
                   [jiffy.temporal.chrono_unit ChronoUnit]
                   [jiffy.zone_offset_impl ZoneOffset]
                   [jiffy.zone.zone_offset_transition_rule ZoneOffsetTransitionRule]
                   [jiffy.zone.zone_rules_impl ZoneRules])))

(defn to-string [tag f obj]
  (let [value (f obj)]
    (str "#jiffy/" tag " " (pr-str value))))

(defn ->map [x] (into {} x))

(defn write-value-range [{:keys [min-smallest min-largest max-smallest max-largest]}]
  [min-smallest min-largest max-smallest max-largest])

(defn read-value-range [args]
  (apply jiffy.temporal.value-range/->ValueRange args))

(defn write-zone-rules [zone-rules]
  (-> zone-rules
      (update :standard-transitions vec)
      (update :standard-offsets vec)
      (update :savings-instant-transitions vec)
      (update :savings-local-transitions vec)
      (update :wall-offsets vec)
      (update :last-rules vec)
      ->map))

(defn write-enum [enum]
  (-> enum :enum-name str/lower-case (str/replace #"_" "-") keyword))

(defn read-enum [value-fn kw]
  (-> kw name str/upper-case (str/replace #"-" "_") value-fn))

(def read-chrono-unit (partial read-enum jiffy.temporal.chrono-unit/value-of))
(def read-day-of-week (partial read-enum jiffy.day-of-week/value-of))
(def read-month (partial read-enum jiffy.month/value-of))
(def read-chrono-field (partial read-enum jiffy.temporal.chrono-field/valueOf))

(def
  tags
  `[{:tag :instant
     :record Instant
     :read-fn 'jiffy.instant-impl/map->Instant
     :write-fn '->map}

    {:tag :duration
     :record Duration
     :read-fn 'jiffy.duration/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :query
     :record TemporalQuery
     :read-fn 'jiffy.temporal.temporal-queries/id->query
     :write-fn :id}

    {:tag :field
     :record ChronoField
     :read-fn 'read-chrono-field
     :write-fn 'write-enum}

    {:tag :unit
     :record ChronoUnit
     :read-fn 'read-chrono-unit
     :write-fn 'write-enum}

    {:tag :range
     :record ValueRange
     :read-fn 'read-value-range
     :write-fn 'write-value-range}

    {:tag :period
     :record Period
     :read-fn 'jiffy.period/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :day
     :record DayOfWeek
     :read-fn 'read-day-of-week
     :write-fn 'write-enum}

    {:tag :ld
     :record LocalDate
     :read-fn 'jiffy.local-date/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :lt
     :record LocalTime
     :read-fn 'jiffy.local-time/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :ldt
     :record LocalDateTime
     :read-fn 'jiffy.local-date-time/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :month
     :record Month
     :read-fn 'read-month
     :write-fn 'write-enum}

    {:tag :year-month
     :record YearMonth
     :read-fn 'jiffy.year-month/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :year
     :record Year
     :read-fn 'jiffy.year/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :zone
     :record ZoneOffset
     :read-fn 'jiffy.zone-id/of
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :fixed-clock
     :record FixedClock
     :read-fn 'jiffy.clock/map->FixedClock
     :write-fn '->map}

    {:tag :zone-rule
     :record ZoneOffsetTransitionRule
     :read-fn 'jiffy.zone.zone-offset-transition-rule/map->ZoneOffsetTransitionRule
     :write-fn '->map}

    {:tag :zone-rules
     :record ZoneRules
     :read-fn 'jiffy.zone.zone-rules-impl/map->ZoneRules
     :write-fn 'write-zone-rules}

    {:tag :zone
     :record ZoneRegion
     :read-fn 'jiffy.zone-id/of
     :write-fn :id}

    {:tag :zdt
     :record ZonedDateTime
     :read-fn 'jiffy.zoned-date-time/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :odt
     :record OffsetDateTime
     :read-fn 'jiffy.offset-date-time/parse
     :write-fn 'jiffy.protocols.string/to-string}

    {:tag :ot
     :record OffsetTime
     :read-fn 'jiffy.offset-time/parse
     :write-fn 'jiffy.protocols.string/to-string}

    ])
