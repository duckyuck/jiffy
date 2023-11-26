(ns jiffy.edn.writers
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
            [jiffy.zone-region-impl #?@(:cljs [:refer [ZoneRegion]])]
            [jiffy.month #?@(:cljs [:refer [Month]])]
            [jiffy.month-day #?@(:cljs [:refer [MonthDay]])]
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
            [jiffy.zone.zone-offset-transition-rule #?@(:cljs [:refer [ZoneOffsetTransitionRule TimeDefinition]])]
            [jiffy.zone.zone-rules-impl #?@(:cljs [:refer [ZoneRules]])]
            [jiffy.chrono.iso-era #?@(:cljs [:refer [IsoEra]])]
            [jiffy.chrono.iso-chronology #?@(:cljs [:refer [IsoChronology]])]
            [jiffy.zone.zone-offset-transition-impl #?@(:cljs [:refer [ZoneOffsetTransition]])]
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
                   [jiffy.month_day MonthDay]
                   [jiffy.year_month YearMonth]
                   [jiffy.year Year]
                   [jiffy.period Period]
                   [jiffy.temporal.value_range ValueRange]
                   [jiffy.temporal.temporal_query TemporalQuery]
                   [jiffy.temporal.chrono_field ChronoField]
                   [jiffy.temporal.chrono_unit ChronoUnit]
                   [jiffy.zone_offset_impl ZoneOffset]
                   [jiffy.zone.zone_offset_transition_rule ZoneOffsetTransitionRule TimeDefinition]
                   [jiffy.zone.zone_rules_impl ZoneRules]
                   [jiffy.chrono.iso_era IsoEra]
                   [jiffy.chrono.iso_chronology IsoChronology]
                   [jiffy.zone.zone_offset_transition_impl ZoneOffsetTransition])))

(defn to-string [tag f obj]
  (let [value (f obj)]
    (str "#jiffy/" tag " " (pr-str value))))

(defn ->map [x] (into {} x))

(defn vectorize [x]
  (if x
    (vec x)
    x))

(defn serialize-zone-rules [zone-rules]
  (-> zone-rules
      (update :standard-transitions vectorize)
      (update :standard-offsets vectorize)
      (update :savings-instant-transitions vectorize)
      (update :savings-local-transitions vectorize)
      (update :wall-offsets vectorize)
      (update :last-rules vectorize)
      ->map))

(defn write-enum [enum]
  (-> enum :enum-name str/lower-case (str/replace #"_" "-") keyword))

(defn tag-writer [tag serialize]
  (fn [x] (to-string tag serialize x)))

(def write-instant (tag-writer "instant" ->map))
(def write-duration (tag-writer "duration" jiffy.protocols.string/to-string))
(def write-temporal-query (tag-writer "query" :id))
(def write-chrono-field (tag-writer "field" write-enum))
(def write-chrono-unit (tag-writer "unit" write-enum))
(def write-value-range (tag-writer "range" (comp vec (juxt :min-smallest :min-largest :max-smallest :max-largest))))
(def write-period (tag-writer "period" jiffy.protocols.string/to-string))
(def write-day-of-week (tag-writer "day" write-enum))
(def write-local-date (tag-writer "ld" jiffy.protocols.string/to-string))
(def write-local-date-time (tag-writer "ldt" jiffy.protocols.string/to-string))
(def write-local-time (tag-writer "lt" jiffy.protocols.string/to-string))
(def write-month (tag-writer "month" write-enum))
(def write-month-day (tag-writer "month-day" jiffy.protocols.string/to-string))
(def write-year-month (tag-writer "year-month" jiffy.protocols.string/to-string))
(def write-year (tag-writer "year" jiffy.protocols.string/to-string))
(def write-zone-offset (tag-writer "zone" jiffy.protocols.string/to-string))
(def write-fixed-clock (tag-writer "fixed-clock" ->map))
(def write-zone-rules (tag-writer "zone-rules" serialize-zone-rules))
(def write-zone-offset-transition-rule (tag-writer "zone-rule" ->map))
(def write-zone-region (tag-writer "zone" :id))
(def write-zoned-date-time (tag-writer "zdt" jiffy.protocols.string/to-string))
(def write-offset-date-time (tag-writer "odt" jiffy.protocols.string/to-string))
(def write-offset-time (tag-writer "ot" jiffy.protocols.string/to-string))
(def write-iso-era (tag-writer "era" write-enum))
(def write-time-definition (tag-writer "time" write-enum))
(def write-iso-chronology (tag-writer "chronology" (constantly :iso)))
(def write-zone-offset-transition (tag-writer "transition" ->map))

(defn install-writers! []
  #?@(:clj
      [
       (defmethod print-method Instant [x ^Writer w] (.write w ^String (write-instant x)))
       (defmethod print-method Duration [x ^Writer w] (.write w ^String (write-duration x)))
       (defmethod print-method TemporalQuery [x ^Writer w] (.write w ^String (write-temporal-query x)))
       (defmethod print-method ChronoField [x ^Writer w] (.write w ^String (write-chrono-field x)))
       (defmethod print-method ChronoUnit [x ^Writer w] (.write w ^String (write-chrono-unit x)))
       (defmethod print-method ValueRange [x ^Writer w] (.write w ^String (write-value-range x)))
       (defmethod print-method Period [x ^Writer w] (.write w ^String (write-period x)))
       (defmethod print-method DayOfWeek [x ^Writer w] (.write w ^String (write-day-of-week x)))
       (defmethod print-method LocalDate [x ^Writer w] (.write w ^String (write-local-date x)))
       (defmethod print-method LocalDateTime [x ^Writer w] (.write w ^String (write-local-date-time x)))
       (defmethod print-method LocalTime [x ^Writer w] (.write w ^String (write-local-time x)))
       (defmethod print-method Month [x ^Writer w] (.write w ^String (write-month x)))
       (defmethod print-method MonthDay [x ^Writer w] (.write w ^String (write-month-day x)))
       (defmethod print-method YearMonth [x ^Writer w] (.write w ^String (write-year-month x)))
       (defmethod print-method Year [x ^Writer w] (.write w ^String (write-year x)))
       (defmethod print-method ZoneOffset [x ^Writer w] (.write w ^String (write-zone-offset x)))
       (defmethod print-method FixedClock [x ^Writer w] (.write w ^String (write-fixed-clock x)))
       (defmethod print-method ZoneRules [x ^Writer w] (.write w ^String (write-zone-rules x)))
       (defmethod print-method ZoneOffsetTransitionRule [x ^Writer w] (.write w ^String (write-zone-offset-transition-rule x)))
       (defmethod print-method ZoneRegion [x ^Writer w] (.write w ^String (write-zone-region x)))
       (defmethod print-method ZonedDateTime [x ^Writer w] (.write w ^String (write-zoned-date-time x)))
       (defmethod print-method OffsetDateTime [x ^Writer w] (.write w ^String (write-offset-date-time x)))
       (defmethod print-method OffsetTime [x ^Writer w] (.write w ^String (write-offset-time x)))
       (defmethod print-method IsoEra [x ^Writer w] (.write w ^String (write-iso-era x)))
       (defmethod print-method TimeDefinition [x ^Writer w] (.write w ^String (write-time-definition x)))
       (defmethod print-method IsoChronology [x ^Writer w] (.write w ^String (write-iso-chronology x)))
       (defmethod print-method ZoneOffsetTransition [x ^Writer w] (.write w ^String (write-zone-offset-transition x)))

       (defmethod print-dup Instant [x ^Writer w] (.write w ^String (write-instant x)))
       (defmethod print-dup Duration [x ^Writer w] (.write w ^String (write-duration x)))
       (defmethod print-dup TemporalQuery [x ^Writer w] (.write w ^String (write-temporal-query x)))
       (defmethod print-dup ChronoField [x ^Writer w] (.write w ^String (write-chrono-field x)))
       (defmethod print-dup ChronoUnit [x ^Writer w] (.write w ^String (write-chrono-unit x)))
       (defmethod print-dup ValueRange [x ^Writer w] (.write w ^String (write-value-range x)))
       (defmethod print-dup Period [x ^Writer w] (.write w ^String (write-period x)))
       (defmethod print-dup DayOfWeek [x ^Writer w] (.write w ^String (write-day-of-week x)))
       (defmethod print-dup LocalDate [x ^Writer w] (.write w ^String (write-local-date x)))
       (defmethod print-dup LocalDateTime [x ^Writer w] (.write w ^String (write-local-date-time x)))
       (defmethod print-dup LocalTime [x ^Writer w] (.write w ^String (write-local-time x)))
       (defmethod print-dup Month [x ^Writer w] (.write w ^String (write-month x)))
       (defmethod print-dup MonthDay [x ^Writer w] (.write w ^String (write-month-day x)))
       (defmethod print-dup YearMonth [x ^Writer w] (.write w ^String (write-year-month x)))
       (defmethod print-dup Year [x ^Writer w] (.write w ^String (write-year x)))
       (defmethod print-dup ZoneOffset [x ^Writer w] (.write w ^String (write-zone-offset x)))
       (defmethod print-dup FixedClock [x ^Writer w] (.write w ^String (write-fixed-clock x)))
       (defmethod print-dup ZoneRules [x ^Writer w] (.write w ^String (write-zone-rules x)))
       (defmethod print-dup ZoneOffsetTransitionRule [x ^Writer w] (.write w ^String (write-zone-offset-transition-rule x)))
       (defmethod print-dup ZoneRegion [x ^Writer w] (.write w ^String (write-zone-region x)))
       (defmethod print-dup ZonedDateTime [x ^Writer w] (.write w ^String (write-zoned-date-time x)))
       (defmethod print-dup OffsetDateTime [x ^Writer w] (.write w ^String (write-offset-date-time x)))
       (defmethod print-dup OffsetTime [x ^Writer w] (.write w ^String (write-offset-time x)))
       (defmethod print-dup IsoEra [x ^Writer w] (.write w ^String (write-iso-era x)))
       (defmethod print-dup TimeDefinition [x ^Writer w] (.write w ^String (write-time-definition x)))
       (defmethod print-dup IsoChronology [x ^Writer w] (.write w ^String (write-iso-chronology x)))
       (defmethod print-dup ZoneOffsetTransition [x ^Writer w] (.write w ^String (write-zone-offset-transition x)))

       ]
      )
  #?(:cljs
     (extend-protocol IPrintWithWriter
       Instant
       (-pr-writer [x w _] (-write w (write-instant x)))

       Duration
       (-pr-writer [x w _] (-write w (write-duration x)))

       TemporalQuery
       (-pr-writer [x w _] (-write w (write-temporal-query x)))

       ChronoField
       (-pr-writer [x w _] (-write w (write-chrono-field x)))

       ChronoUnit
       (-pr-writer [x w _] (-write w (write-chrono-unit x)))

       ValueRange
       (-pr-writer [x w _] (-write w (write-value-range x)))

       Period
       (-pr-writer [x w _] (-write w (write-period x)))

       DayOfWeek
       (-pr-writer [x w _] (-write w (write-day-of-week x)))

       LocalDate
       (-pr-writer [x w _] (-write w (write-local-date x)))

       LocalDateTime
       (-pr-writer [x w _] (-write w (write-local-date-time x)))

       LocalTime
       (-pr-writer [x w _] (-write w (write-local-time x)))

       Month
       (-pr-writer [x w _] (-write w (write-month x)))

       MonthDay
       (-pr-writer [x w _] (-write w (write-month-day x)))

       YearMonth
       (-pr-writer [x w _] (-write w (write-year-month x)))

       Year
       (-pr-writer [x w _] (-write w (write-year x)))

       ZoneOffset
       (-pr-writer [x w _] (-write w (write-zone-offset x)))

       FixedClock
       (-pr-writer [x w _] (-write w (write-fixed-clock x)))

       ZoneRules
       (-pr-writer [x w _] (-write w (write-zone-rules x)))

       ZoneOffsetTransitionRule
       (-pr-writer [x w _] (-write w (write-zone-offset-transition-rule x)))

       ZoneRegion
       (-pr-writer [x w _] (-write w (write-zone-region x)))

       ZonedDateTime
       (-pr-writer [x w _] (-write w (write-zoned-date-time x)))

       OffsetDateTime
       (-pr-writer [x w _] (-write w (write-offset-date-time x)))

       OffsetTime
       (-pr-writer [x w _] (-write w (write-offset-time x)))

       IsoEra
       (-pr-writer [x w _] (-write w (write-iso-era x)))

       TimeDefinition
       (-pr-writer [x w _] (-write w (write-time-definition x)))

       IsoChronology
       (-pr-writer [x w _] (-write w (write-iso-chronology x)))

       ZoneOffsetTransition
       (-pr-writer [x w _] (-write w (write-zone-offset-transition x)))
       )))

(install-writers!)
