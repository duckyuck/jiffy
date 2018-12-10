(ns jiffy.protocols.temporal.week-fields
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java
(defprotocol IWeekFields
  (get-first-day-of-week [this])
  (get-minimal-days-in-first-week [this])
  (day-of-week [this])
  (week-of-month [this])
  (week-of-year [this])
  (week-of-week-based-year [this])
  (week-based-year [this]))

(s/def ::week-fields #(satisfies? IWeekFields %))