(ns jiffy.chrono.chrono-local-date-defaults
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.math :as math]))

(s/def ::chrono-local-date ::chrono-local-date/chrono-local-date)

(def-method is-supported ::j/boolean
  [this ::chrono-local-date/chrono-local-date
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (if (satisfies? temporal-field/ITemporalField field-or-unit)
    (if (satisfies? chrono-field/IChronoField field-or-unit)
      (temporal-field/is-date-based field-or-unit)
      (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))
    (if (chrono-unit/chrono-unit? field-or-unit)
      (temporal-unit/is-date-based field-or-unit)
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))))

(def-method query ::j/wip
  [this ::chrono-local-date
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/zone-id)
       (temporal-queries/zone)
       (temporal-queries/offset)
       (temporal-queries/local-time)} query)
    nil

    (= query (temporal-queries/chronology))
    (chrono-local-date/get-chronology this)

    (= query (temporal-queries/precision))
    chrono-unit/DAYS

    :else
    (temporal-query/query-from query this)))

(def-method to-epoch-day ::j/long
  [this ::chrono-local-date]
  (temporal-accessor/get-long this chrono-field/EPOCH_DAY))

(def-method adjust-into ::temporal/temporal
  [this ::chrono-local-date
   temporal ::temporal/temporal]
  (temporal/with temporal chrono-field/EPOCH_DAY (to-epoch-day this)))

(def-method compare-to ::j/int
  [this ::chrono-local-date
   other ::chrono-local-date]
  (let [cmp (math/compare (to-epoch-day this) (to-epoch-day other))]
    (if (zero? cmp)
      (time-comparable/compare-to (chrono-local-date/get-chronology this)
                                  (chrono-local-date/get-chronology other))
      cmp)))

(def-method is-after ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (> (to-epoch-day this) (to-epoch-day other)))

(def-method is-before ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (< (to-epoch-day this) (to-epoch-day other)))

(def-method is-equal ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (= (to-epoch-day this) (to-epoch-day other)))
