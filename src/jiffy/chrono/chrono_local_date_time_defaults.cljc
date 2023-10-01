(ns jiffy.chrono.chrono-local-date-time-defaults
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.instant-impl :as instant-impl]))

(s/def ::chrono-local-date-time ::chrono-local-date-time/chrono-local-date-time)

(def-method get-chronology ::chronology/chronology
  [this ::chrono-local-date-time]
  (-> this
      chrono-local-date-time/to-local-date
      chrono-local-date/get-chronology))

(def-method is-supported ::j/boolean
  [this ::chrono-local-date-time
   unit ::temporal-unit/temporal-unit]
  (if (chrono-unit/chrono-unit? unit)
    (not= unit chrono-unit/FOREVER)
    (and unit (temporal-unit/is-supported-by unit this))))

(def-method query ::j/wip
  [this ::chrono-local-date-time
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/zone-id)
       (temporal-queries/zone)
       (temporal-queries/offset)} query)
    nil

    (= query (temporal-queries/local-time))
    (chrono-local-date-time/to-local-time this)

    (= query (temporal-queries/chronology))
    (get-chronology this)

    (= query (temporal-queries/precision))
    chrono-unit/NANOS

    :else
    (temporal-query/query-from query this)))

(def-method adjust-into ::temporal/temporal
  [this ::chrono-local-date-time
   temporal ::temporal/temporal]
  (-> temporal
      (temporal/with chrono-field/EPOCH_DAY (-> this
                                                chrono-local-date-time/to-local-date
                                                chrono-local-date/to-epoch-day))
      (temporal/with chrono-field/NANO_OF_DAY (-> this
                                                  chrono-local-date-time/to-local-time
                                                  local-time/to-nano-of-day))))

(def-method to-epoch-second ::j/long
  [this ::chrono-local-date-time
   offset ::zone-offset/zone-offset]
  (let [epoch-day (-> this chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        secs (-> this
                 chrono-local-date-time/to-local-time
                 local-time/to-second-of-day
                 (math/add-exact (math/multiply-exact epoch-day 86400)))]
    (->> offset zone-offset/get-total-seconds (math/subtract-exact secs))))

(def-method to-instant ::instant/instant
  [this ::chrono-local-date-time
   offset ::zone-offset/zone-offset]
  (instant-impl/of-epoch-second (to-epoch-second this offset)
                                (-> this
                                    chrono-local-date-time/to-local-time
                                    local-time/get-nano)))

(def-method compare-to ::j/int
  [this ::chrono-local-date-time
   other ::chrono-local-date-time]
  (let [cmp (time-comparable/compare-to (chrono-local-date-time/to-local-date this)
                                        (chrono-local-date-time/to-local-date other))]
    (if (zero? cmp)
      (let [cmp (time-comparable/compare-to (chrono-local-date-time/to-local-time this)
                                            (chrono-local-date-time/to-local-time other))]
        (if (zero? cmp)
          (time-comparable/compare-to (get-chronology this)
                                      (get-chronology other))
          cmp))
      cmp)))

(def-method is-after ::j/boolean
  [this ::chrono-local-date-time
   other ::chrono-local-date-time]
  (let [this-ep-day (-> this chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        other-ep-day (-> other chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        this-nano-of-day (-> this chrono-local-date-time/to-local-time local-time/to-nano-of-day)
        other-nano-of-day (-> other chrono-local-date-time/to-local-time local-time/to-nano-of-day)]
    (or (> this-ep-day other-ep-day)
        (and (= this-ep-day other-ep-day)
             (> this-nano-of-day other-nano-of-day)))))

(def-method is-before  ::j/boolean
  [this ::chrono-local-date-time
   other ::chrono-local-date-time]
  (let [this-ep-day (-> this chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        other-ep-day (-> other chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        this-nano-of-day (-> this chrono-local-date-time/to-local-time local-time/to-nano-of-day)
        other-nano-of-day (-> other chrono-local-date-time/to-local-time local-time/to-nano-of-day)]
    (or (< this-ep-day other-ep-day)
        (and (= this-ep-day other-ep-day)
             (< this-nano-of-day other-nano-of-day)))))

(def-method is-equal ::j/boolean
  [this ::chrono-local-date-time
   other ::chrono-local-date-time]
  (and (= (-> this chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
          (-> other chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day))
       (= (-> this chrono-local-date-time/to-local-time local-time/to-nano-of-day)
          (-> other chrono-local-date-time/to-local-time local-time/to-nano-of-day))))
