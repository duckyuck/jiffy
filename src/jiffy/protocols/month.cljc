(ns jiffy.protocols.month
  (:require [clojure.spec.alpha :as s]))

(defprotocol IMonth
  (get-value [this])
  (get-display-name [this style locale])
  (plus [this months])
  (minus [this months])
  (length [this leap-year])
  (min-length [this])
  (max-length [this])
  (first-day-of-year [this leap-year])
  (first-month-of-quarter [this]))

(s/def ::month #(satisfies? IMonth %))
