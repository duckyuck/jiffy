(ns jiffy.temporal.temporal-adjusters
  (:refer-clojure :exclude [next ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L139
(s/def ::of-date-adjuster-args (s/tuple ::j/wip))
(defn ofDateAdjuster [date-based-adjuster] (wip ::ofDateAdjuster))
(s/fdef ofDateAdjuster :args ::of-date-adjuster-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L165
(defn firstDayOfMonth [] (wip ::firstDayOfMonth))
(s/fdef firstDayOfMonth :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L188
(defn lastDayOfMonth [] (wip ::lastDayOfMonth))
(s/fdef lastDayOfMonth :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L208
(defn firstDayOfNextMonth [] (wip ::firstDayOfNextMonth))
(s/fdef firstDayOfNextMonth :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L229
(defn firstDayOfYear [] (wip ::firstDayOfYear))
(s/fdef firstDayOfYear :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L250
(defn lastDayOfYear [] (wip ::lastDayOfYear))
(s/fdef lastDayOfYear :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L269
(defn firstDayOfNextYear [] (wip ::firstDayOfNextYear))
(s/fdef firstDayOfNextYear :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L290
(s/def ::first-in-month-args (s/tuple ::DayOfWeek/day-of-week))
(defn firstInMonth [day-of-week] (wip ::firstInMonth))
(s/fdef firstInMonth :args ::first-in-month-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L310
(s/def ::last-in-month-args (s/tuple ::DayOfWeek/day-of-week))
(defn lastInMonth [day-of-week] (wip ::lastInMonth))
(s/fdef lastInMonth :args ::last-in-month-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L346
(s/def ::day-of-week-in-month-args (s/tuple ::j/int ::DayOfWeek/day-of-week))
(defn dayOfWeekInMonth [ordinal day-of-week] (wip ::dayOfWeekInMonth))
(s/fdef dayOfWeekInMonth :args ::day-of-week-in-month-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L386
(s/def ::next-args (s/tuple ::DayOfWeek/day-of-week))
(defn next [day-of-week] (wip ::next))
(s/fdef next :args ::next-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L412
(s/def ::next-or-same-args (s/tuple ::DayOfWeek/day-of-week))
(defn nextOrSame [day-of-week] (wip ::nextOrSame))
(s/fdef nextOrSame :args ::next-or-same-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L440
(s/def ::previous-args (s/tuple ::DayOfWeek/day-of-week))
(defn previous [day-of-week] (wip ::previous))
(s/fdef previous :args ::previous-args :ret ::TemporalAdjuster/temporal-adjuster)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L466
(s/def ::previous-or-same-args (s/tuple ::DayOfWeek/day-of-week))
(defn previousOrSame [day-of-week] (wip ::previousOrSame))
(s/fdef previousOrSame :args ::previous-or-same-args :ret ::TemporalAdjuster/temporal-adjuster)
