(ns jiffy.temporal.temporal-adjusters
  (:refer-clojure :exclude [next ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L139
(s/def ::of-date-adjuster-args (s/tuple ::j/wip))
(defn of-date-adjuster [date-based-adjuster] (wip ::of-date-adjuster))
(s/fdef of-date-adjuster :args ::of-date-adjuster-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L165
(defn first-day-of-month [] (wip ::first-day-of-month))
(s/fdef first-day-of-month :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L188
(defn last-day-of-month [] (wip ::last-day-of-month))
(s/fdef last-day-of-month :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L208
(defn first-day-of-next-month [] (wip ::first-day-of-next-month))
(s/fdef first-day-of-next-month :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L229
(defn first-day-of-year [] (wip ::first-day-of-year))
(s/fdef first-day-of-year :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L250
(defn last-day-of-year [] (wip ::last-day-of-year))
(s/fdef last-day-of-year :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L269
(defn first-day-of-next-year [] (wip ::first-day-of-next-year))
(s/fdef first-day-of-next-year :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L290
(s/def ::first-in-month-args (s/tuple ::day-of-week/day-of-week))
(defn first-in-month [day-of-week] (wip ::first-in-month))
(s/fdef first-in-month :args ::first-in-month-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L310
(s/def ::last-in-month-args (s/tuple ::day-of-week/day-of-week))
(defn last-in-month [day-of-week] (wip ::last-in-month))
(s/fdef last-in-month :args ::last-in-month-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L346
(s/def ::day-of-week-in-month-args (s/tuple ::j/int ::day-of-week/day-of-week))
(defn day-of-week-in-month [ordinal day-of-week] (wip ::day-of-week-in-month))
(s/fdef day-of-week-in-month :args ::day-of-week-in-month-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L386
(s/def ::next-args (s/tuple ::day-of-week/day-of-week))
(defn next [day-of-week] (wip ::next))
(s/fdef next :args ::next-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L412
(s/def ::next-or-same-args (s/tuple ::day-of-week/day-of-week))
(defn next-or-same [day-of-week] (wip ::next-or-same))
(s/fdef next-or-same :args ::next-or-same-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L440
(s/def ::previous-args (s/tuple ::day-of-week/day-of-week))
(defn previous [day-of-week] (wip ::previous))
(s/fdef previous :args ::previous-args :ret ::temporal-adjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L466
(s/def ::previous-or-same-args (s/tuple ::day-of-week/day-of-week))
(defn previous-or-same [day-of-week] (wip ::previous-or-same))
(s/fdef previous-or-same :args ::previous-or-same-args :ret ::temporal-adjuster/temporal-adjuster)

;; TODO: Implement specs / constructors for methods in TemporalAdjusters
;; Used by `jiffy.parity-tests.test-specs`.
(def temporal-adjusters-specs [])
