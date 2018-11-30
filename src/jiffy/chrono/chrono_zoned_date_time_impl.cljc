(ns jiffy.chrono.chrono-zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.chrono.chrono-local-date-time-impl :as chrono-local-date-time-impl]
            [jiffy.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.instant :as instant]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]))

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
(s/fdef -get-offset :args ::get-offset-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L231
(s/def ::with-earlier-offset-at-overlap-args (args))
(defn -with-earlier-offset-at-overlap [this] (wip ::-with-earlier-offset-at-overlap))
(s/fdef -with-earlier-offset-at-overlap :args ::with-earlier-offset-at-overlap-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L232
(s/def ::get-zone-args (args))
(defn -get-zone [this] (wip ::-get-zone))
(s/fdef -get-zone :args ::get-zone-args :ret ::zone-id/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L243
(s/def ::with-later-offset-at-overlap-args (args))
(defn -with-later-offset-at-overlap [this] (wip ::-with-later-offset-at-overlap))
(s/fdef -with-later-offset-at-overlap :args ::with-later-offset-at-overlap-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L256
(s/def ::to-local-date-time-args (args))
(defn -to-local-date-time [this] (wip ::-to-local-date-time))
(s/fdef -to-local-date-time :args ::to-local-date-time-args :ret ::chrono-local-date-time/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L266
(s/def ::with-zone-same-local-args (args ::zone-id/zone-id))
(defn -with-zone-same-local [this zone] (wip ::-with-zone-same-local))
(s/fdef -with-zone-same-local :args ::with-zone-same-local-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L271
(s/def ::with-zone-same-instant-args (args ::zone-id/zone-id))
(defn -with-zone-same-instant [this zone] (wip ::-with-zone-same-instant))
(s/fdef -with-zone-same-instant :args ::with-zone-same-instant-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

(extend-type ChronoZonedDateTimeImpl
  chrono-zoned-date-time/IChronoZonedDateTime
  (get-offset [this] (-get-offset this))
  (with-earlier-offset-at-overlap [this] (-with-earlier-offset-at-overlap this))
  (get-zone [this] (-get-zone this))
  (with-later-offset-at-overlap [this] (-with-later-offset-at-overlap this))
  (to-local-date-time [this] (-to-local-date-time this))
  (with-zone-same-local [this zone] (-with-zone-same-local this zone))
  (with-zone-same-instant [this zone] (-with-zone-same-instant this zone)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L284
(s/def ::with-args (args ::temporal-field/temporal-field ::j/long))
(defn -with [this field new-value] (wip ::-with))
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L301
(s/def ::plus-args (args ::j/long ::temporal-unit/temporal-unit))
(defn -plus [this amount-to-add unit] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L310
(s/def ::until-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type ChronoZonedDateTimeImpl
  temporal/ITemporal
  (with [this field new-value] (-with this field new-value))
  (plus [this amount-to-add unit] (-plus this amount-to-add unit))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L278
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

(extend-type ChronoZonedDateTimeImpl
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (-is-supported this field)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L134
(s/def ::of-best-args (args ::chrono-local-date-time-impl/chrono-local-date-time-impl ::zone-id/zone-id ::zone-offset/zone-offset))
(defn of-best [local-date-time zone preferred-offset] (wip ::of-best))
(s/fdef of-best :args ::of-best-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L170
(s/def ::of-instant-args (args ::chronology/chronology ::instant/instant ::zone-id/zone-id))
(defn of-instant [chrono instant zone] (wip ::of-instant))
(s/fdef of-instant :args ::of-instant-args :ret ::chrono-zoned-date-time-impl)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTimeImpl.java#L200
(s/def ::ensure-valid-args (args ::chronology/chronology ::temporal/temporal))
(defn ensure-valid [chrono temporal] (wip ::ensure-valid))
(s/fdef ensure-valid :args ::ensure-valid-args :ret ::chrono-zoned-date-time-impl)
