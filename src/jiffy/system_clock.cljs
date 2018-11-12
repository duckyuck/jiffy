(ns jiffy.system-clock
  (:require [jiffy.clock :as Clock]
            [jiffy.instant :as Instant]
            [jiffy.zone-offset :as ZoneOffset]))

(defrecord SystemClock [zone])

(def -get-zone :zone)

(defn -with-zone [this zone]
  (if (= (:zone this) zone)
    this
    (->SystemClock zone)))

(defn -millis [this]
  (.getTime (js/Date.)))

(defn -instant [this]
  (Instant/ofEpochMilli (-millis this)))

(extend-type SystemClock
  Clock/IClock
  (getZone [this] (-get-zone this))
  (withZone [this zone] (-with-zone this zone))
  (millis [this] (-millis this))
  (instant [this] (-instant this)))

;; TODO: use ZoneOffset/UTC
(defmethod Clock/-system-utc :system-utc [_]
  (->SystemClock nil
                 ;; (ZoneOffset/UTC)
                 ))
