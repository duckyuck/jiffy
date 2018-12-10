(ns jiffy.protocols.format.date-time-print-context
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java
(defprotocol IDateTimePrintContext
  (get-temporal [this])
  (get-locale [this])
  (get-decimal-style [this])
  (start-optional [this])
  (end-optional [this])
  (get-value [this get-value--overloaded-param]))

(s/def ::date-time-print-context #(satisfies? IDateTimePrintContext %))