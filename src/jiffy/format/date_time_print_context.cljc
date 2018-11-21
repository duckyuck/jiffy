(ns jiffy.format.date-time-print-context
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.decimal-style :as DecimalStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java
(defprotocol IDateTimePrintContext
  (getTemporal [this])
  (getLocale [this])
  (getDecimalStyle [this])
  (startOptional [this])
  (endOptional [this])
  (getValue [this get-value--overloaded-param]))

(defrecord DateTimePrintContext [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-print-context (j/constructor-spec DateTimePrintContext create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-print-context)

(defmacro args [& x] `(s/tuple ::date-time-print-context ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L237
(s/def ::get-temporal-args (args))
(defn -get-temporal [this] (wip ::-get-temporal))
(s/fdef -get-temporal :args ::get-temporal-args :ret ::TemporalAccessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L249
(s/def ::get-locale-args (args))
(defn -get-locale [this] (wip ::-get-locale))
(s/fdef -get-locale :args ::get-locale-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L260
(s/def ::get-decimal-style-args (args))
(defn -get-decimal-style [this] (wip ::-get-decimal-style))
(s/fdef -get-decimal-style :args ::get-decimal-style-args :ret ::DecimalStyle/decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L268
(s/def ::start-optional-args (args))
(defn -start-optional [this] (wip ::-start-optional))
(s/fdef -start-optional :args ::start-optional-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L275
(s/def ::end-optional-args (args))
(defn -end-optional [this] (wip ::-end-optional))
(s/fdef -end-optional :args ::end-optional-args :ret ::j/void)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L286
(s/def ::get-value-args (args ::TemporalField/temporal-field))
(defn -get-value [this get-value--overloaded-param] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/wip)

(extend-type DateTimePrintContext
  IDateTimePrintContext
  (getTemporal [this] (-get-temporal this))
  (getLocale [this] (-get-locale this))
  (getDecimalStyle [this] (-get-decimal-style this))
  (startOptional [this] (-start-optional this))
  (endOptional [this] (-end-optional this))
  (getValue [this get-value--overloaded-param] (-get-value this get-value--overloaded-param)))

