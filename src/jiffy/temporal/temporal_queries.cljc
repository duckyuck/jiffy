(ns jiffy.temporal.temporal-queries
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-query :as temporal-query #?@(:clj [:refer [defquery]] #?@(:cljs [:refer-macros [defquery]]))]
            [jiffy.zone-offset-impl :as zone-offset]
            [jiffy.local-time-impl :as local-time]
            [jiffy.local-date-impl :as local-date]))

;; TODO: the following objects also overrides Object#toString in Java implementation
;; figure out if these are used (other than in error messages etc)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L344
;; (defquery ZONE_ID "ZoneId" [temporal]
;;   (temporal-accessor/query temporal ZONE_ID))

(def ZONE_ID
  (temporal-query/->TemporalQuery
   "ZoneId" :zone-id
   (fn [temporal] (temporal-accessor/query temporal ZONE_ID))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L359
;; (defquery CHRONO "Chronology" [temporal]
;;   (temporal-accessor/query temporal CHRONO))

(def CHRONO
  (temporal-query/->TemporalQuery
   "Chronology" :chronology
   (fn [temporal] (temporal-accessor/query temporal CHRONO))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L375
;; (defquery PRECISION "Precision" [temporal]
;;   (temporal-accessor/query temporal PRECISION))

(def PRECISION
  (temporal-query/->TemporalQuery
   "Precision" :precision
   (fn [temporal] (temporal-accessor/query temporal PRECISION))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L391
;; (defquery OFFSET "ZoneOffset" [temporal]
;;   (when (temporal-accessor/is-supported temporal chrono-field/OFFSET_SECONDS)
;;     (zone-offset/of-total-seconds (temporal-accessor/get temporal chrono-field/OFFSET_SECONDS))))

(def OFFSET
  (temporal-query/->TemporalQuery
   "ZoneOffset" :zone-offset
   (fn [temporal]
     (when (temporal-accessor/is-supported
            temporal
            chrono-field/OFFSET_SECONDS)
       (zone-offset/of-total-seconds
        (temporal-accessor/get
         temporal
         chrono-field/OFFSET_SECONDS))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L409
;; (defquery ZONE "Zone" [temporal]
;;   (or (temporal-accessor/query temporal ZONE_ID)
;;       (temporal-accessor/query temporal OFFSET)))

(def ZONE
  (temporal-query/->TemporalQuery
   "Zone" :zone
   (fn [temporal]
     (or
      (temporal-accessor/query temporal ZONE_ID)
      (temporal-accessor/query temporal OFFSET)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L425
;; (defquery LOCAL_DATE "LocalDate" [temporal]
;;   (when (temporal-accessor/is-supported temporal chrono-field/EPOCH_DAY)
;;     (local-date/of-epoch-day (temporal-accessor/get temporal chrono-field/EPOCH_DAY))))

(def LOCAL_DATE
  (temporal-query/->TemporalQuery
   "LocalDate" :local-date
   (fn [temporal]
     (when (temporal-accessor/is-supported
            temporal
            chrono-field/EPOCH_DAY)
       (local-date/of-epoch-day
        (temporal-accessor/get temporal chrono-field/EPOCH_DAY))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L443
;; (defquery LOCAL_TIME "LocalTime" [temporal]
;;   (when (temporal-accessor/is-supported temporal chrono-field/NANO_OF_DAY)
;;     (local-time/of-nano-of-day (temporal-accessor/get temporal chrono-field/NANO_OF_DAY))))

(def LOCAL_TIME
  (temporal-query/->TemporalQuery
   "LocalTime" :local-time
   (fn [temporal]
     (when (temporal-accessor/is-supported
            temporal
            chrono-field/NANO_OF_DAY)
       (local-time/of-nano-of-day
        (temporal-accessor/get
         temporal
         chrono-field/NANO_OF_DAY))))))

(defn name->query [name]
  (or (get {"Chronology" CHRONO
            "Precision" PRECISION
            "ZoneOffset" OFFSET
            "Zone" ZONE
            "LocalDate" LOCAL_DATE
            "LocalTime" LOCAL_TIME
            "ZoneId" ZONE_ID}
           name)
      (throw (ex-info (str "Unknown TemporalQuery with name: '" name "'") {:name name}))))

(defn id->query [id]
  (or (get {:chronology CHRONO
            :precision PRECISION
            :zone-offset OFFSET
            :zone ZONE
            :local-date LOCAL_DATE
            :local-time LOCAL_TIME
            :zone-id ZONE_ID}
           id)
      (throw (ex-info (str "Unknown TemporalQuery with id: '" id "'") {:id id}))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L167
(defn zone-id [] ZONE_ID)
(s/fdef zone-id :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L206
(defn chronology [] CHRONO)
(s/fdef chronology :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L243
(defn precision [] PRECISION)
(s/fdef precision :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L267
(defn zone [] ZONE)
(s/fdef zone :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L290
(defn offset [] OFFSET)
(s/fdef offset :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L313
(defn local-date [] LOCAL_DATE)
(s/fdef local-date :ret ::temporal-query/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQueries.java#L336
(defn local-time [] LOCAL_TIME)
(s/fdef local-time :ret ::temporal-query/temporal-query)

(comment


  (str (java.time.temporal.TemporalQueries/zone))
  )

