(ns jiffy.temporal.temporal-queries
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-query :as temporal-query]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L167
(defn zone-id [] (wip ::zone-id))
(s/fdef zone-id :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L206
(defn chronology [] (wip ::chronology))
(s/fdef chronology :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L243
(defn precision [] (wip ::precision))
(s/fdef precision :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L267
(defn zone [] (wip ::zone))
(s/fdef zone :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L290
(defn offset [] (wip ::offset))
(s/fdef offset :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L313
(defn local-date [] (wip ::local-date))
(s/fdef local-date :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L336
(defn local-time [] (wip ::local-time))
(s/fdef local-time :ret ::temporal-query/temporal-query)

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
