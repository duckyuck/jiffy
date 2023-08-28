(ns jiffy.edn
  (:require [clojure.edn :as edn]
            #?(:cljs [cljs.reader :as reader])
            [jiffy.clock #?@(:cljs [:refer [FixedClock]])]
            [jiffy.day-of-week #?@(:cljs [:refer [DayOfWeek]])]
            [jiffy.duration-impl #?@(:cljs [:refer [Duration]])]
            [jiffy.instant-2-impl #?@(:cljs [:refer [Instant]])]
            [jiffy.local-time-impl #?@(:cljs [:refer [LocalTime]])]
            [jiffy.month #?@(:cljs [:refer [Month]])]
            [jiffy.period #?@(:cljs [:refer [Period]])]
            [jiffy.temporal.temporal-queries]
            [jiffy.temporal.value-range #?@(:cljs [:refer [ValueRange]])]
            [jiffy.temporal.temporal-query #?@(:cljs [:refer [TemporalQuery]])]
            [jiffy.temporal.chrono-field #?@(:cljs [:refer [ChronoField]])]
            [jiffy.temporal.chrono-unit #?@(:cljs [:refer [ChronoUnit]])]
            [jiffy.zone-offset]
            [jiffy.zone-offset-impl #?@(:cljs [:refer [ZoneOffset]])]
            )
  #?(:clj (:import [java.io Writer]
                   [jiffy.clock FixedClock]
                   [jiffy.day_of_week DayOfWeek]
                   [jiffy.duration_impl Duration]
                   [jiffy.instant_2_impl Instant]
                   [jiffy.local_time_impl LocalTime]
                   [jiffy.month Month]
                   [jiffy.period Period]
                   [jiffy.temporal.value_range ValueRange]
                   [jiffy.temporal.temporal_query TemporalQuery]
                   [jiffy.temporal.chrono_field ChronoField]
                   [jiffy.temporal.chrono_unit ChronoUnit]
                   [jiffy.zone_offset_impl ZoneOffset])))

(defn to-string [tag f obj]
  (let [value (f obj)]
    (str "#jiffy/" tag " " (pr-str value))))

(defn ->map [x] (into {} x))

(def tags
  `{:instant-2
    {:record Instant
     :read-fn 'jiffy.instant-2-impl/map->Instant
     :write-fn '->map}

    :duration
    {:record Duration
     :read-fn 'jiffy.duration-impl/map->Duration
     :write-fn '->map}

    :query
    {:record TemporalQuery
     :read-fn 'jiffy.temporal.temporal-queries/name->query
     :write-fn :name}

    :field
    {:record ChronoField
     :read-fn 'jiffy.temporal.chrono-field/valueOf
     :write-fn :enum-name}

    :unit
    {:record ChronoUnit
     :read-fn 'jiffy.temporal.chrono-unit/value-of
     :write-fn :enum-name}

    :value-range
    {:record ValueRange
     :read-fn 'jiffy.temporal.value-range/map->ValueRange
     :write-fn '->map}

    :period
    {:record Period
     :read-fn 'jiffy.period/map->Period
     :write-fn '->map}

    :day-of-week
    {:record DayOfWeek
     :read-fn 'jiffy.day-of-week/value-of
     :write-fn :enum-name}

    :local-time
    {:record LocalTime
     :read-fn 'jiffy.local-time-impl/map->LocalTime
     :write-fn '->map}

    :month
    {:record Month
     :read-fn 'jiffy.month/value-of
     :write-fn :enum-name}

    :zone-offset
    {:record ZoneOffset
     :read-fn 'jiffy.zone-offset/of
     :write-fn :id}

    :fixed-clock
    {:record FixedClock
     :read-fn 'jiffy.clock/map->FixedClock
     :write-fn '->map}})
