(ns jiffy.protocols.chrono.chronology
  (:refer-clojure :exclude [range ])
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronology
  (get-id [this])
  (get-calendar-type [this])
  (date [this temporal] [this proleptic-year month day-of-month] [this era year-of-era month day-of-month])
  (date-year-day [this proleptic-year day-of-year] [this era year-of-era day-of-year])
  (date-epoch-day [this epoch-day])
  (date-now [this] [this date-now--overloaded-param])
  (local-date-time [this temporal])
  (zoned-date-time [this temporal] [this instant zone])
  (is-leap-year [this proleptic-year])
  (proleptic-year [this era year-of-era])
  (era-of [this era-value])
  (eras [this])
  (range [this field])
  (get-display-name [this style locale])
  (resolve-date [this field-values resolver-style])
  (period [this years months days])
  (epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] [this era year-of-era month day-of-month hour minute second zone-offset]))

(s/def ::chronology #(satisfies? IChronology %))
