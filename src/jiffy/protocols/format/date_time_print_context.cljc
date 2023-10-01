(ns jiffy.protocols.format.date-time-print-context
  (:require [clojure.spec.alpha :as s]))

(defprotocol IDateTimePrintContext
  (get-temporal [this])
  (get-locale [this])
  (get-decimal-style [this])
  (start-optional [this])
  (end-optional [this])
  (get-value [this get-value--overloaded-param]))

(s/def ::date-time-print-context #(satisfies? IDateTimePrintContext %))
