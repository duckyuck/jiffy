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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L325
(def-method -get-era ::era/era
  [this ::chrono-local-date]
  (wip ::-get-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L340
(def-method -is-leap-year ::j/boolean
  [this ::chrono-local-date]
  (wip ::-is-leap-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L362
(def-method -length-of-year ::j/int
  [this ::chrono-local-date]
  (wip ::-length-of-year))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L386
(def-method -is-supported ::j/boolean
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

(def-method -with ::chrono-local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L427
  ([this ::chrono-local-date
    adjuster ::temporal-adjuster/temporal-adjuster]
   (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L438
  ([this ::chrono-local-date
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (wip ::-with)))

(def-method -plus ::chrono-local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L451
  ([this ::chrono-local-date
    amount ::temporal-amount/temporal-amount]
   (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L461
  ([this ::chrono-local-date
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-plus)))

(def-method -minus ::chrono-local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L474
  ([this ::chrono-local-date
    amount ::temporal-amount/temporal-amount]
   (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L485
  ([this ::chrono-local-date
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L510
(def-method -query ::j/wip
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L551
(def-method -to-epoch-day ::j/long
  [this ::chrono-local-date]
  (temporal-accessor/get-long this chrono-field/EPOCH_DAY))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L550
(def-method -adjust-into ::temporal/temporal
  [this ::chrono-local-date
   temporal ::temporal/temporal]
  (temporal/with temporal chrono-field/EPOCH_DAY (-to-epoch-day this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L640
(def-method -format string?
  [this ::chrono-local-date
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L656
(def-method -at-time ::chrono-local-date-time/chrono-local-date-time
  [this ::chrono-local-date
   local-time ::local-time/local-time]
  (wip ::-at-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L706
(def-method -compare-to ::j/int
  [this ::chrono-local-date
   other ::chrono-local-date]
  (let [cmp (math/compare (-to-epoch-day this) (-to-epoch-day other))]
    (if (zero? cmp)
      (time-comparable/compare-to (chrono-local-date/get-chronology this)
                                  (chrono-local-date/get-chronology other))
      cmp)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L728
(def-method -is-after ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (> (-to-epoch-day this) (-to-epoch-day other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L746
(def-method -is-before ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (< (-to-epoch-day this) (-to-epoch-day other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDate.java#L764
(def-method -is-equal ::j/boolean
  [this ::chrono-local-date
   other ::chrono-local-date]
  (= (-to-epoch-day this) (-to-epoch-day other)))
