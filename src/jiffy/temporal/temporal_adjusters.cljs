(ns jiffy.temporal.temporal-adjusters
  (:refer-clojure :exclude [next ])
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L139
(defn ofDateAdjuster [date-based-adjuster] (wip ::ofDateAdjuster))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L165
(defn firstDayOfMonth [] (wip ::firstDayOfMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L188
(defn lastDayOfMonth [] (wip ::lastDayOfMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L208
(defn firstDayOfNextMonth [] (wip ::firstDayOfNextMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L229
(defn firstDayOfYear [] (wip ::firstDayOfYear))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L250
(defn lastDayOfYear [] (wip ::lastDayOfYear))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L269
(defn firstDayOfNextYear [] (wip ::firstDayOfNextYear))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L290
(defn firstInMonth [day-of-week] (wip ::firstInMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L310
(defn lastInMonth [day-of-week] (wip ::lastInMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L346
(defn dayOfWeekInMonth [ordinal day-of-week] (wip ::dayOfWeekInMonth))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L386
(defn next [day-of-week] (wip ::next))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L412
(defn nextOrSame [day-of-week] (wip ::nextOrSame))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L440
(defn previous [day-of-week] (wip ::previous))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAdjusters.java#L466
(defn previousOrSame [day-of-week] (wip ::previousOrSame))
