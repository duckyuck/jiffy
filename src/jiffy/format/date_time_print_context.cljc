(ns jiffy.format.date-time-print-context
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java
(defprotocol IDateTimePrintContext
  (getTemporal [this])
  (getLocale [this])
  (getDecimalStyle [this])
  (startOptional [this])
  (endOptional [this])
  (getValue [this get-value--overloaded-param]))

(defrecord DateTimePrintContext [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L237
(defn -get-temporal [this] (wip ::-get-temporal))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L249
(defn -get-locale [this] (wip ::-get-locale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L260
(defn -get-decimal-style [this] (wip ::-get-decimal-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L268
(defn -start-optional [this] (wip ::-start-optional))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L275
(defn -end-optional [this] (wip ::-end-optional))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L286
(defn -get-value [this get-value--overloaded-param] (wip ::-get-value))

(extend-type DateTimePrintContext
  IDateTimePrintContext
  (getTemporal [this] (-get-temporal this))
  (getLocale [this] (-get-locale this))
  (getDecimalStyle [this] (-get-decimal-style this))
  (startOptional [this] (-start-optional this))
  (endOptional [this] (-end-optional this))
  (getValue [this get-value--overloaded-param] (-get-value this get-value--overloaded-param)))

