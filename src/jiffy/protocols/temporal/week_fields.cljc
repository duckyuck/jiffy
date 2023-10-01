(ns jiffy.protocols.temporal.week-fields
  (:require [clojure.spec.alpha :as s]))

(defprotocol IWeekFields
  (get-first-day-of-week [this])
  (get-minimal-days-in-first-week [this])
  (day-of-week [this])
  (week-of-month [this])
  (week-of-year [this])
  (week-of-week-based-year [this])
  (week-based-year [this]))

(s/def ::week-fields #(satisfies? IWeekFields %))
