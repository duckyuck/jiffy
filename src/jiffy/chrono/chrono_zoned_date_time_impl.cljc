(ns jiffy.chrono.chrono-zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date-time :as ChronoLocalDateTime]
            [jiffy.chrono.chrono-local-date-time-impl :as ChronoLocalDateTimeImpl]
            [jiffy.chrono.chrono-zoned-date-time :as ChronoZonedDateTime]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.instant :as Instant]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]))

(defrecord ChronoZonedDateTimeImpl [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::chrono-zoned-date-time-impl (j/constructor-spec ChronoZonedDateTimeImpl create ::create-args))
(s/fdef create :args ::create-args :ret ::chrono-zoned-date-time-impl)

(defmacro args [& x] `(s/tuple ::chrono-zoned-date-time-impl ~@x))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L226
(s/def ::get-offset-args (args))
(defn -get-offset [this] (wip ::-get-offset))
(s/fdef -get-offset :args ::get-offset-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L231
(s/def ::with-earlier-offset-at-overlap-args (args))
(defn -with-earlier-offset-at-overlap [this] (wip ::-with-earlier-offset-at-overlap))
(s/fdef -with-earlier-offset-at-overlap :args ::with-earlier-offset-at-overlap-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L232
(s/def ::get-zone-args (args))
(defn -get-zone [this] (wip ::-get-zone))
(s/fdef -get-zone :args ::get-zone-args :ret ::ZoneId/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L243
(s/def ::with-later-offset-at-overlap-args (args))
(defn -with-later-offset-at-overlap [this] (wip ::-with-later-offset-at-overlap))
(s/fdef -with-later-offset-at-overlap :args ::with-later-offset-at-overlap-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L256
(s/def ::to-local-date-time-args (args))
(defn -to-local-date-time [this] (wip ::-to-local-date-time))
(s/fdef -to-local-date-time :args ::to-local-date-time-args :ret ::ChronoLocalDateTime/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L266
(s/def ::with-zone-same-local-args (args ::ZoneId/zone-id))
(defn -with-zone-same-local [this zone] (wip ::-with-zone-same-local))
(s/fdef -with-zone-same-local :args ::with-zone-same-local-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L271
(s/def ::with-zone-same-instant-args (args ::ZoneId/zone-id))
(defn -with-zone-same-instant [this zone] (wip ::-with-zone-same-instant))
(s/fdef -with-zone-same-instant :args ::with-zone-same-instant-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

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
(s/def ::with-args (args ::TemporalField/temporal-field ::j/long))
(defn -with [this field new-value] (wip ::-with))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L301
(s/def ::plus-args (args ::j/long ::TemporalUnit/temporal-unit))
(defn -plus [this amount-to-add unit] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L310
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type ChronoZonedDateTimeImpl
  Temporal/ITemporal
  (with [this field new-value] (-with this field new-value))
  (plus [this amount-to-add unit] (-plus this amount-to-add unit))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L278
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

(extend-type ChronoZonedDateTimeImpl
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L134
(s/def ::of-best-args (args ::ChronoLocalDateTimeImpl/chrono-local-date-time-impl ::ZoneId/zone-id ::ZoneOffset/zone-offset))
(defn ofBest [local-date-time zone preferred-offset] (wip ::ofBest))
(s/fdef ofBest :args ::of-best-args :ret ::ChronoZonedDateTime/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L170
(s/def ::of-instant-args (args ::Chronology/chronology ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [chrono instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::chrono-zoned-date-time-impl)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L200
(s/def ::ensure-valid-args (args ::Chronology/chronology ::Temporal/temporal))
(defn ensureValid [chrono temporal] (wip ::ensureValid))
(s/fdef ensureValid :args ::ensure-valid-args :ret ::chrono-zoned-date-time-impl)
