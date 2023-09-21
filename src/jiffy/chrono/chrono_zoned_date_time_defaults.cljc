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
            [jiffy.temporal.chrono-unit :as chrono-unit]))

(s/def ::chrono-zoned-date-time ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L186
(def-method range ::value-range/value-range
  [this ::chrono-zoned-date-time
   field ::temporal-field/temporal-field]
  (wip ::range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L197
(def-method get ::j/int
  [this ::chrono-zoned-date-time
   field ::temporal-field/temporal-field]
  (wip ::get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L211
(def-method get-long ::j/long
  [this ::chrono-zoned-date-time
   field ::temporal-field/temporal-field]
  (wip ::get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L230
(def-method to-local-date ::chrono-local-date/chrono-local-date
  [this ::chrono-zoned-date-time]
  (-> this
      chrono-zoned-date-time/to-local-date-time
      chrono-local-date-time/to-local-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L242
(def-method to-local-time ::local-time/local-time
  [this ::chrono-zoned-date-time]
  (-> this
      chrono-zoned-date-time/to-local-date-time
      chrono-local-date-time/to-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L214
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L264
(def-method get-chronology ::chronology/chronology
  [this ::chrono-zoned-date-time]
  (-> this
      to-local-date
      chrono-local-date/get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L383
(def-method is-supported ::j/boolean
  [this ::chrono-zoned-date-time
   unit ::temporal-unit/temporal-unit]
  (if (chrono-unit/chrono-unit? unit)
    (not= unit chrono-unit/FOREVER)
    (and unit (temporal-unit/is-supported-by unit this))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L419
(def-method with ::chrono-zoned-date-time
  [this ::chrono-zoned-date-time
   adjuster ::temporal-adjuster/temporal-adjuster]
  (wip ::with))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L437
(def-method plus ::chrono-zoned-date-time
  [this ::chrono-zoned-date-time
   amount ::temporal-amount/temporal-amount]
  (wip ::plus))

(s/def ::minus-args ::j/wip)
(def-method minus ::chrono-zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L455
  ([this ::chrono-zoned-date-time
    amount ::temporal-amount/temporal-amount]
   (wip ::minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L465
  ([this ::chrono-zoned-date-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L490
(def-method query ::j/wip
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L521
(def-method format string?
  [this ::chrono-zoned-date-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L537
(def-method to-instant ::instant/instant
  [this ::chrono-zoned-date-time]
  (wip ::to-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L576
(def-method compare-to ::j/int
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (wip ::compare-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L606
(def-method is-before ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (wip ::is-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L626
(def-method is-after ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (wip ::is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L646
(def-method is-equal ::j/boolean
  [this ::chrono-zoned-date-time
   other ::chrono-zoned-date-time]
  (wip ::is-equal))
