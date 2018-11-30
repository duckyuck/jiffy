(ns jiffy.chrono.chrono-zoned-date-time
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as date-time-formatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-time :as LocalTime]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java
(defprotocol IChronoZonedDateTime
  (to-epoch-second [this])
  (to-local-date [this])
  (to-local-time [this])
  (to-local-date-time [this])
  (get-chronology [this])
  (get-offset [this])
  (get-zone [this])
  (with-earlier-offset-at-overlap [this])
  (with-later-offset-at-overlap [this])
  (with-zone-same-local [this zone])
  (with-zone-same-instant [this zone])
  (format [this formatter])
  (to-instant [this])
  (is-before [this other])
  (is-after [this other])
  (is-equal [this other]))

(s/def ::chrono-zoned-date-time #(satisfies? IChronoZonedDateTime %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L140
(defn time-line-order [] (wip ::time-line-order))
(s/fdef time-line-order :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L172
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chrono-zoned-date-time)
