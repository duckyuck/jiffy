(ns jiffy.chrono.chrono-zoned-date-time
  (:refer-clojure :exclude [format])
  (:require [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-time :as LocalTime]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]
            [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java
(defprotocol IChronoZonedDateTime
  (toEpochSecond [this])
  (toLocalDate [this])
  (toLocalTime [this])
  (toLocalDateTime [this])
  (getChronology [this])
  (getOffset [this])
  (getZone [this])
  (withEarlierOffsetAtOverlap [this])
  (withLaterOffsetAtOverlap [this])
  (withZoneSameLocal [this zone])
  (withZoneSameInstant [this zone])
  (format [this formatter])
  (toInstant [this])
  (isBefore [this other])
  (isAfter [this other])
  (isEqual [this other]))

(s/def ::chrono-zoned-date-time #(satisfies? IChronoZonedDateTime %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L140
(defn timeLineOrder [] (wip ::timeLineOrder))
(s/fdef timeLineOrder :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L172
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-zoned-date-time)
