(ns ^:figwheel-hooks jiffy.dev.main
  (:require jiffy.math.big-decimal
            jiffy.chrono.chronology
            jiffy.chrono.iso-chronology
            jiffy.chrono.iso-era
            jiffy.clock
            jiffy.clock
            jiffy.protocols.clock
            jiffy.day-of-week
            jiffy.dev.wip
            [jiffy.duration :as duration]
            [jiffy.exception :refer [ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            jiffy.format.date-time-formatter
            jiffy.format.date-time-formatter-builder
            jiffy.format.date-time-parse-context
            jiffy.format.date-time-print-context
            jiffy.format.date-time-text-provider
            jiffy.format.decimal-style
            jiffy.format.format-style
            jiffy.format.parsed
            jiffy.format.resolver-style
            jiffy.format.sign-style
            jiffy.format.text-style
            jiffy.instant-impl
            [jiffy.instant :as instant]
            [jiffy.protocols.instant :as Instant]
            jiffy.local-date
            jiffy.local-date-time
            jiffy.local-time
            jiffy.math
            jiffy.month
            jiffy.month-day
            jiffy.offset-date-time
            jiffy.offset-time
            jiffy.period
            [jiffy.temporal.chrono-field :as chrono-field]
            jiffy.temporal.chrono-unit
            jiffy.temporal.iso-fields
            jiffy.temporal.julian-fields
            jiffy.temporal.temporal-adjusters
            jiffy.temporal.temporal-queries
            jiffy.temporal.temporal-query
            jiffy.temporal.week-fields
            jiffy.year
            jiffy.year-impl
            jiffy.year-month
            jiffy.zoned-date-time
            jiffy.zone-id
            jiffy.zone-offset
            jiffy.zone-region-impl
            jiffy.zone-region
            jiffy.zone.zone-offset-transition
            jiffy.zone.zone-offset-transition-rule
            jiffy.zone.zone-rules
            jiffy.zone.zone-rules-provider
            [jiffy.edn-cljs :include-macros true]))

(comment
  jiffy.math.big-decimal/keep-me
  jiffy.chrono.chrono-local-date-time-impl/keep-me
  jiffy.chrono.chrono-period-impl/keep-me
  jiffy.chrono.chronology/keep-me
  jiffy.chrono.era/keep-me
  jiffy.chrono.iso-chronology/keep-me
  jiffy.chrono.iso-era/keep-me
  jiffy.clock/keep-me
  jiffy.day-of-week/keep-me
  jiffy.dev.wip/keep-me
  jiffy.edn-cljs/keep-me
  jiffy.duration/keep-me
  jiffy.exception/keep-me
  jiffy.format.date-time-formatter
  jiffy.format.date-time-formatter-builder/keep-me
  jiffy.format.date-time-parse-context/keep-me
  jiffy.format.date-time-print-context/keep-me
  jiffy.format.date-time-text-provider/keep-me
  jiffy.format.decimal-style/keep-me
  jiffy.format.format-style/keep-me
  jiffy.format.parsed/keep-me
  jiffy.format.resolver-style/keep-me
  jiffy.format.sign-style/keep-me
  jiffy.format.text-style/keep-me
  jiffy.instant/keep-me
  jiffy.local-date/keep-me
  jiffy.local-date-time/keep-me
  jiffy.local-time/keep-me
  jiffy.math/keep-me
  jiffy.month/keep-me
  jiffy.month-day/keep-me
  jiffy.offset-date-time/keep-me
  jiffy.offset-time/keep-me
  jiffy.period/keep-me
  jiffy.temporal.chrono-field/keep-me
  jiffy.temporal.chrono-unit/keep-me
  jiffy.temporal.iso-fields/keep-me
  jiffy.temporal.julian-fields/keep-me
  jiffy.temporal.temporal/keep-me
  jiffy.temporal.temporal-accessor/keep-me
  jiffy.temporal.temporal-adjuster/keep-me
  jiffy.temporal.temporal-adjusters/keep-me
  jiffy.temporal.temporal-amount/keep-me
  jiffy.temporal.temporal-field/keep-me
  jiffy.temporal.temporal-queries/keep-me
  jiffy.temporal.temporal-query/keep-me
  jiffy.temporal.temporal-unit/keep-me
  jiffy.temporal.value-range/keep-me
  jiffy.temporal.week-fields/keep-me
  jiffy.time-comparable/keep-me
  jiffy.year/keep-me
  jiffy.year-impl/keep-me
  jiffy.year-month/keep-me
  jiffy.zone.zone-offset-transition/keep-me
  jiffy.zone.zone-offset-transition-rule/keep-me
  jiffy.zone.zone-rules/keep-me
  jiffy.zone.zone-rules-provider/keep-me
  jiffy.zone-id/keep-me
  jiffy.zone-offset/keep-me
  jiffy.zone-region/keep-me
  jiffy.zoned-date-time/keep-me)

#?(:cljs (enable-console-print!))

(defn ^:after-load dummy [] ::ok)

(comment

  (do
    (require '[flow-storm.api :as fs-api])
    (fs-api/local-connect))

  )

