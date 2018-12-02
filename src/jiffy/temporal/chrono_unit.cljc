(ns jiffy.temporal.chrono-unit
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.conversion :as conversion])
            [jiffy.duration-impl :as duration]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.math :as math]
            [jiffy.enum #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]))

(defprotocol IChronoUnit)

(defrecord ChronoUnit [ordinal enum-name name duration]
  IChronoUnit)

(s/def ::create-args (s/tuple ::j/int string? string? ::duration/duration))
(def create ->ChronoUnit)
(def chrono-unit-spec (j/constructor-spec ChronoUnit create ::create-args))
(s/def ::chrono-unit chrono-unit-spec)
(s/fdef create :args ::create-args :ret ::chrono-unit)

(defenum create
  [NANOS ["Nanos" (duration/of-nanos 1)]
   MICROS ["Micros" (duration/of-nanos 1000)]
   MILLIS ["Millis" (duration/of-nanos 1000000)]
   SECONDS ["Seconds" (duration/of-seconds 1)]
   MINUTES ["Minutes" (duration/of-seconds 60)]
   HOURS ["Hours" (duration/of-seconds 3600)]
   HALF_DAYS ["HalfDays" (duration/of-seconds 43200)]
   DAYS ["Days" (duration/of-seconds 86400)]
   WEEKS ["Weeks" (duration/of-seconds (* 7 86400))]
   MONTHS ["Months" (duration/of-seconds (/ 31556952 12))]
   YEARS ["Years" (duration/of-seconds 31556952)]
   DECADES ["Decades" (duration/of-seconds (* 31556952 10))]
   CENTURIES ["Centuries" (duration/of-seconds (* 31556952 100))]
   MILLENNIA ["Millennia" (duration/of-seconds (* 31556952 1000))]
   ERAS ["Eras" (duration/of-seconds (* 31556952 1000000000))]
   FOREVER ["Forever" (duration/of-seconds math/long-max-value 999999999)]])

(defmacro args [& x] `(s/tuple ::chrono-unit ~@x))

(defn --compare-to [this other]
  (- (:ordinal this) (:ordinal other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L210
(s/def ::get-duration-args (args))
(defn -get-duration [this]
  (:duration this))
(s/fdef -get-duration :args ::get-duration-args :ret ::duration/duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L226
(s/def ::is-duration-estimated-args (args))
(defn -is-duration-estimated [this]
  (not (neg? (--compare-to this DAYS))))
(s/fdef -is-duration-estimated :args ::is-duration-estimated-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L240
(s/def ::is-date-based-args (args))
(defn -is-date-based [this]
  (and (not (neg? (--compare-to this DAYS)))
       (not= this FOREVER)))
(s/fdef -is-date-based :args ::is-date-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L253
(s/def ::is-time-based-args (args))
(defn -is-time-based [this]
  (neg? (--compare-to this DAYS)))
(s/fdef -is-time-based :args ::is-time-based-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L254
(s/def ::add-to-args (args ::temporal/temporal ::j/long))
(defn -add-to [this temporal amount]
  (temporal/plus temporal amount this))
(s/fdef -add-to :args ::add-to-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L259
(s/def ::is-supported-by-args (args ::temporal/temporal))
(defn -is-supported-by [this temporal]
  (temporal-accessor/is-supported temporal this))
(s/fdef -is-supported-by :args ::is-supported-by-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java#L271
(s/def ::between-args (args ::temporal/temporal ::temporal/temporal))
(defn -between [this temporal-1-inclusive temporal-2-exclusive]
  (temporal/until temporal-1-inclusive temporal-2-exclusive this))
(s/fdef -between :args ::between-args :ret ::j/long)

(extend-type ChronoUnit
  temporal-unit/ITemporalUnit
  (get-duration [this] (-get-duration this))
  (is-duration-estimated [this] (-is-duration-estimated this))
  (is-date-based [this] (-is-date-based this))
  (is-time-based [this] (-is-time-based this))
  (add-to [this temporal amount] (-add-to this temporal amount))
  (is-supported-by [this temporal] (-is-supported-by this temporal))
  (between [this temporal-1-inclusive temporal-2-exclusive] (-between this temporal-1-inclusive temporal-2-exclusive)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(defn values [] (vals @enums))
(s/fdef values :ret (s/coll-of ::chrono-unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ChronoUnit.java
(s/def ::value-of-args (s/tuple string?))
(defn value-of [enum-name] (@enums enum-name))
(s/fdef value-of :args ::value-of-args :ret ::chrono-unit)

#?(:clj
   (defmethod conversion/jiffy->java ChronoUnit [chrono-unit]
     (java.time.temporal.ChronoUnit/valueOf (:enum-name chrono-unit))))

#?(:clj
   (defmethod conversion/same? ChronoUnit [jiffy-object java-object]
     ;; TODO compare all values of enum
     (= (map #(% jiffy-object) [:ordinal
                                ;; :enum-name
                                :name
                                ;; :duration
                                ])
        (map #(% java-object) [(memfn ordinal)
                               (memfn toString)]))))
