(ns jiffy.protocols.temporal.temporal-unit
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITemporalUnit
  (get-duration [this])
  (is-duration-estimated [this])
  (is-date-based [this])
  (is-time-based [this])
  (is-supported-by [this temporal])
  (add-to [this temporal amount])
  (between [this temporal1inclusive temporal2exclusive]))

(s/def ::temporal-unit #(satisfies? ITemporalUnit %))
