(ns jiffy.temporal.temporal-queries
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-query :as TemporalQuery]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L167
(defn zoneId [] (wip ::zoneId))
(s/fdef zoneId :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L206
(defn chronology [] (wip ::chronology))
(s/fdef chronology :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L243
(defn precision [] (wip ::precision))
(s/fdef precision :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L267
(defn zone [] (wip ::zone))
(s/fdef zone :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L290
(defn offset [] (wip ::offset))
(s/fdef offset :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L313
(defn localDate [] (wip ::localDate))
(s/fdef localDate :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L336
(defn localTime [] (wip ::localTime))
(s/fdef localTime :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L344
(def ZONE_ID ::ZONE_ID--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L359
(def CHRONO ::CHRONO--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L375
(def PRECISION ::PRECISION--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L391
(def OFFSET ::OFFSET--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L409
(def ZONE ::ZONE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L425
(def LOCAL_DATE ::LOCAL_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L443
(def LOCAL_TIME ::LOCAL_TIME--not-implemented)
