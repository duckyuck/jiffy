(ns jiffy.chrono.chrono-zoned-date-time-defaults
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.math :as math]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.instant-impl :as instant-impl]))

(s/def ::chrono-zoned-date-time ::chrono-zoned-date-time/chrono-zoned-date-time)

(def-method get ::j/int
  [this ::chrono-zoned-date-time
   field ::temporal-field/temporal-field]
  (wip ::get))

(def-method to-local-date ::chrono-local-date/chrono-local-date
  [this ::chrono-zoned-date-time]
  (-> this
      chrono-zoned-date-time/to-local-date-time
      chrono-local-date-time/to-local-date))

(def-method to-local-time ::local-time/local-time
  [this ::chrono-zoned-date-time]
  (-> this
      chrono-zoned-date-time/to-local-date-time
      chrono-local-date-time/to-local-time))

(def-method to-epoch-second ::j/long
  [this ::chrono-zoned-date-time]
  (let [epoch-day (-> this to-local-date chrono-local-date/to-epoch-day)
        secs (-> epoch-day
                 (math/multiply-exact 86400)
                 (math/add-exact (-> this to-local-time local-time/to-second-of-day)))]
    (math/subtract-exact secs
                         (-> this
                             chrono-zoned-date-time/get-offset
                             zone-offset/get-total-seconds))))

(def-method get-chronology ::chronology/chronology
  [this ::chrono-zoned-date-time]
  (-> this
      to-local-date
      chrono-local-date/get-chronology))

(def-method is-supported ::j/boolean
  [this ::chrono-zoned-date-time
   unit ::temporal-unit/temporal-unit]
  (if (chrono-unit/chrono-unit? unit)
    (not= unit chrono-unit/FOREVER)
    (and unit (temporal-unit/is-supported-by unit this))))

(def-method query ::temporal-query/result
  [this ::chrono-zoned-date-time
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/zone)
       (temporal-queries/zone-id)} query)
    (chrono-zoned-date-time/get-zone this)

    (= query (temporal-queries/offset))
    (chrono-zoned-date-time/get-offset this)

    (= query (temporal-queries/local-time))
    (to-local-time this)

    (= query (temporal-queries/chronology))
    (get-chronology this)

    (= query (temporal-queries/precision))
    chrono-unit/NANOS

    :else
    (temporal-query/query-from query this)))

(def-method to-instant ::instant/instant
  [this ::chrono-zoned-date-time]
  (instant-impl/of-epoch-second
   (chrono-zoned-date-time/to-epoch-second this)
   (-> this chrono-zoned-date-time/to-local-time local-time/get-nano)))

(def-method compare-to ::j/int
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (let [cmp (math/compare (chrono-zoned-date-time/to-epoch-second this)
                          (chrono-zoned-date-time/to-epoch-second other))]
    (if-not (zero? cmp)
      cmp
      (let [cmp (math/subtract-exact (-> this
                                         chrono-zoned-date-time/to-local-time
                                         local-time/get-nano)
                                     (-> other
                                         chrono-zoned-date-time/to-local-time
                                         local-time/get-nano))]
        (if-not (zero? cmp)
          cmp
          (let [cmp (-> this
                        chrono-zoned-date-time/to-local-date-time
                        (time-comparable/compare-to
                         (chrono-zoned-date-time/to-local-date-time other)))]
            (if-not (zero? cmp)
              cmp
              (let [cmp (-> this
                            chrono-zoned-date-time/get-zone
                            (time-comparable/compare-to
                             (chrono-zoned-date-time/get-zone other)))]
                (if-not (zero? cmp)
                  cmp
                  (-> this
                      chrono-zoned-date-time/get-chronology
                      (time-comparable/compare-to
                       (chrono-zoned-date-time/get-chronology other))))))))))))

(def-method is-before ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (let [this-epoch-sec (chrono-zoned-date-time/to-epoch-second this)
        other-epoch-sec (chrono-zoned-date-time/to-epoch-second other)]
    (or (< this-epoch-sec other-epoch-sec)
        (and (= this-epoch-sec other-epoch-sec)
             (< (-> this
                    chrono-zoned-date-time/to-local-time
                    local-time/get-nano)
                (-> other
                    chrono-zoned-date-time/to-local-time
                    local-time/get-nano))))))

(def-method is-after ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (let [this-epoch-sec (chrono-zoned-date-time/to-epoch-second this)
        other-epoch-sec (chrono-zoned-date-time/to-epoch-second other)]
    (or (> this-epoch-sec other-epoch-sec)
        (and (= this-epoch-sec other-epoch-sec)
             (> (-> this
                    chrono-zoned-date-time/to-local-time
                    local-time/get-nano)
                (-> other
                    chrono-zoned-date-time/to-local-time
                    local-time/get-nano))))))

(def-method is-equal ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (and (= (chrono-zoned-date-time/to-epoch-second this)
          (chrono-zoned-date-time/to-epoch-second other))
       (= (-> this
              chrono-zoned-date-time/to-local-time
              local-time/get-nano)
          (-> other
              chrono-zoned-date-time/to-local-time
              local-time/get-nano))))
