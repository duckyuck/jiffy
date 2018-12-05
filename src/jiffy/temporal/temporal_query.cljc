(ns jiffy.temporal.temporal-query
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.conversion :as conversion])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalQuery.java
(defprotocol ITemporalQuery
  (query-from [this temporal]))

(s/def ::temporal-query #(satisfies? ITemporalQuery %))
(s/def ::result any?)

(defrecord TemporalQuery [name query-from-fn]
  ITemporalQuery
  (query-from [this temporal] (query-from-fn temporal)))

(defmacro defquery [sym name & query-from]
  `(def ~sym (->TemporalQuery ~name (fn ~@query-from))))

#?(:clj
   (defmethod conversion/jiffy->java TemporalQuery [{:keys [name]}]
     (case name
       "ZoneId" (java.time.temporal.TemporalQueries/zoneId)
       "Chronology" (java.time.temporal.TemporalQueries/chronology)
       "Precision" (java.time.temporal.TemporalQueries/precision)
       "ZoneOffset" (java.time.temporal.TemporalQueries/offset)
       "Zone" (java.time.temporal.TemporalQueries/zone)
       "LocalDate" (java.time.temporal.TemporalQueries/localDate)
       "LocalTime" (java.time.temporal.TemporalQueries/localTime))))

#?(:clj
   (defmethod conversion/same? TemporalQuery
     [jiffy-object java-object]
     (= (:name jiffy-object) (.toString java-object))))
