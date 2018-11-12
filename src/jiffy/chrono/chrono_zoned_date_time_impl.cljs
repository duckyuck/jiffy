(ns jiffy.chrono.chrono-zoned-date-time-impl
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

(defrecord ChronoZonedDateTimeImpl [])

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L226
(defn -get-offset [this] (wip ::-get-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L231
(defn -with-earlier-offset-at-overlap [this] (wip ::-with-earlier-offset-at-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L232
(defn -get-zone [this] (wip ::-get-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L243
(defn -with-later-offset-at-overlap [this] (wip ::-with-later-offset-at-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L256
(defn -to-local-date-time [this] (wip ::-to-local-date-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L266
(defn -with-zone-same-local [this zone] (wip ::-with-zone-same-local))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L271
(defn -with-zone-same-instant [this zone] (wip ::-with-zone-same-instant))

(extend-type ChronoZonedDateTimeImpl
  ChronoZonedDateTime/IChronoZonedDateTime
  (getOffset [this] (-get-offset this))
  (withEarlierOffsetAtOverlap [this] (-with-earlier-offset-at-overlap this))
  (getZone [this] (-get-zone this))
  (withLaterOffsetAtOverlap [this] (-with-later-offset-at-overlap this))
  (toLocalDateTime [this] (-to-local-date-time this))
  (withZoneSameLocal [this zone] (-with-zone-same-local this zone))
  (withZoneSameInstant [this zone] (-with-zone-same-instant this zone)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L284
(defn -with [this field new-value] (wip ::-with))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L301
(defn -plus [this amount-to-add unit] (wip ::-plus))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L310
(defn -until [this end-exclusive unit] (wip ::-until))

(extend-type ChronoZonedDateTimeImpl
  Temporal/ITemporal
  (with [this field new-value] (-with this field new-value))
  (plus [this amount-to-add unit] (-plus this amount-to-add unit))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L278
(defn -is-supported [this field] (wip ::-is-supported))

(extend-type ChronoZonedDateTimeImpl
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L134
(defn ofBest [local-date-time zone preferred-offset] (wip ::ofBest))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L170
(defn ofInstant [chrono instant zone] (wip ::ofInstant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L200
(defn ensureValid [chrono temporal] (wip ::ensureValid))
