(ns jiffy.temporal.temporal-adjusters
  (:refer-clojure :exclude [next ])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-adjusters :as temporal-adjusters]
            [jiffy.specs :as j]
            [jiffy.math :as math]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.temporal.chrono-unit :as chrono-unit]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L139
(def-constructor of-date-adjuster ::temporal-adjuster/temporal-adjuster
  [date-based-adjuster any?]
  (wip ::of-date-adjuster))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L165
(def-constructor first-day-of-month ::temporal-adjuster/temporal-adjuster
  []
  (wip ::first-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L188
(def-constructor last-day-of-month ::temporal-adjuster/temporal-adjuster
  []
  (wip ::last-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L208
(def-constructor first-day-of-next-month ::temporal-adjuster/temporal-adjuster
  []
  (wip ::first-day-of-next-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L229
(def-constructor first-day-of-year ::temporal-adjuster/temporal-adjuster
  []
  (wip ::first-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L250
(def-constructor last-day-of-year ::temporal-adjuster/temporal-adjuster
  []
  (wip ::last-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L269
(def-constructor first-day-of-next-year ::temporal-adjuster/temporal-adjuster
  []
  (wip ::first-day-of-next-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L290
(def-constructor first-in-month ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (wip ::first-in-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L310
(def-constructor last-in-month ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (wip ::last-in-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L346
(def-constructor day-of-week-in-month ::temporal-adjuster/temporal-adjuster
  [ordinal ::j/int
   day-of-week ::day-of-week/day-of-week]
  (wip ::day-of-week-in-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L386
(def-constructor next ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (wip ::next))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L412
(def-constructor next-or-same ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (let [dow-value (day-of-week/get-value day-of-week)]
    (reify temporal-adjuster/ITemporalAdjuster
      (adjust-into [_ temporal]
        (let [cal-dow (temporal-accessor/get temporal chrono-field/DAY_OF_WEEK)
              days-diff (math/subtract-exact cal-dow dow-value)]
          (if (= cal-dow dow-value)
            temporal
            (temporal/plus temporal
                           (if (>= days-diff 0)
                             (- 7 days-diff)
                             (- days-diff))
                           chrono-unit/DAYS)))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L440
(def-constructor previous ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (wip ::previous))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L466
(def-constructor previous-or-same ::temporal-adjuster/temporal-adjuster
  [day-of-week ::day-of-week/day-of-week]
  (let [dow-value (day-of-week/get-value day-of-week)]
    (reify temporal-adjuster/ITemporalAdjuster
      (adjust-into [_ temporal]
        (let [cal-dow (temporal-accessor/get temporal chrono-field/DAY_OF_WEEK)
              days-diff (math/subtract-exact dow-value cal-dow)]
          (if (= cal-dow dow-value)
            temporal
            (temporal/minus temporal
                            (if (>= days-diff 0)
                              (- 7 days-diff)
                              (- days-diff))
                            chrono-unit/DAYS)))))))

;; TODO: Implement specs / constructors for methods in TemporalAdjusters
;; Used by `jiffy.parity-tests.test-specs`.
(def temporal-adjusters-specs [])
