(ns jiffy.protocols.chrono.hijrah-chronology
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahChronology.java
(defprotocol IHijrahChronology
  (get-minimum-year [this])
  (get-maximum-year [this])
  (get-maximum-month-length [this])
  (get-minimum-month-length [this])
  (get-maximum-day-of-year [this])
  (check-valid-year [this proleptic-year])
  (check-valid-day-of-year [this day-of-year])
  (check-valid-month [this month])
  (get-hijrah-date-info [this epoch-day])
  (get-epoch-day [this proleptic-year month-of-year day-of-month])
  (get-day-of-year [this proleptic-year month])
  (get-month-length [this proleptic-year month-of-year])
  (get-year-length [this proleptic-year])
  (get-smallest-maximum-day-of-year [this]))

(s/def ::hijrah-chronology #(satisfies? IHijrahChronology %))