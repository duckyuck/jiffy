(ns jiffy.format.date-time-print-context
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.format.date-time-print-context :as date-time-print-context]
            [jiffy.protocols.format.decimal-style :as decimal-style]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.specs :as j]))

(defrecord DateTimePrintContext [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-print-context (j/constructor-spec DateTimePrintContext create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-print-context)

(defmacro args [& x] `(s/tuple ::date-time-print-context ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L237
(s/def ::get-temporal-args (args))
(defn -get-temporal [this] (wip ::-get-temporal))
(s/fdef -get-temporal :args ::get-temporal-args :ret ::temporal-accessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L249
(s/def ::get-locale-args (args))
(defn -get-locale [this] (wip ::-get-locale))
(s/fdef -get-locale :args ::get-locale-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimePrintContext.java#L260
(s/def ::get-decimal-style-args (args))
(defn -get-decimal-style [this] (wip ::-get-decimal-style))
(s/fdef -get-decimal-style :args ::get-decimal-style-args :ret ::decimal-style/decimal-style)

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
(s/def ::get-value-args (args ::temporal-field/temporal-field))
(defn -get-value [this get-value--overloaded-param] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/wip)

(extend-type DateTimePrintContext
  date-time-print-context/IDateTimePrintContext
  (get-temporal [this] (-get-temporal this))
  (get-locale [this] (-get-locale this))
  (get-decimal-style [this] (-get-decimal-style this))
  (start-optional [this] (-start-optional this))
  (end-optional [this] (-end-optional this))
  (get-value [this get-value--overloaded-param] (-get-value this get-value--overloaded-param)))

