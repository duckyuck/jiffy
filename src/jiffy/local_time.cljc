(ns jiffy.local-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.asserts :refer [require-non-nil]]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaNullPointerException UnsupportedTemporalTypeException]]
            [jiffy.clock :as clock-impl]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.local-time-impl :refer [create #?@(:cljs [LocalTime])] :as impl]
            [jiffy.offset-time-impl :as offset-time-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math])
  #?(:clj (:import [jiffy.local_time_impl LocalTime])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L132
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L137
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L141
(def MIDNIGHT ::MIDNIGHT--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L145
(def NOON ::NOON--not-implemented)

(def HOURS_PER_DAY impl/HOURS_PER_DAY)
(def MINUTES_PER_HOUR impl/MINUTES_PER_HOUR)
(def MINUTES_PER_DAY impl/MINUTES_PER_DAY)
(def SECONDS_PER_MINUTE impl/SECONDS_PER_MINUTE)
(def SECONDS_PER_HOUR impl/SECONDS_PER_HOUR)
(def SECONDS_PER_DAY impl/SECONDS_PER_DAY)
(def MILLIS_PER_DAY impl/MILLIS_PER_DAY)
(def MICROS_PER_DAY impl/MICROS_PER_DAY)
(def NANOS_PER_MILLI impl/NANOS_PER_MILLI)
(def NANOS_PER_SECOND impl/NANOS_PER_SECOND)
(def NANOS_PER_MINUTE impl/NANOS_PER_MINUTE)
(def NANOS_PER_HOUR impl/NANOS_PER_HOUR)
(def NANOS_PER_DAY impl/NANOS_PER_DAY)

(s/def ::local-time ::impl/local-time)

(defmacro args [& x] `(s/tuple ::local-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L673
(s/def ::to-nano-of-day-args (args))
(defn -to-nano-of-day [this]
  (+ (* (:hour this) NANOS_PER_HOUR)
     (* (:minute this) NANOS_PER_MINUTE)
     (* (:second this) NANOS_PER_SECOND)
     (:nano this)))
(s/fdef -to-nano-of-day :args ::to-nano-of-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L692
(s/def ::to-second-of-day-args (args))
(defn -to-second-of-day [this]
  (math/add-exact
   (math/add-exact (math/multiply-exact (:hour this) SECONDS_PER_HOUR)
                   (math/multiply-exact (:minute this) SECONDS_PER_MINUTE))
   (:second this)))
(s/fdef -to-second-of-day :args ::to-second-of-day-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L710
(s/def ::get-hour-args (args))
(defn -get-hour [this]
  (:hour this))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L719
(s/def ::get-minute-args (args))
(defn -get-minute [this]
  (:minute this))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L728
(s/def ::get-second-args (args))
(defn -get-second [this]
  (:second this))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L737
(s/def ::get-nano-args (args))
(defn -get-nano [this]
  (:nano this))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L891
(s/def ::with-hour-args (args ::j/hour-of-day))
(defn -with-hour [this hour]
  (if (= hour (:hour this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/HOUR_OF_DAY hour)
      (create hour (:minute this) (:second this) (:nano this)))))
(s/fdef -with-hour :args ::with-hour-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L908
(s/def ::with-minute-args (args ::j/minute-of-hour))
(defn -with-minute [this minute]
  (if (= minute (:minute this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/MINUTE_OF_HOUR minute)
      (create (:hour this) minute (:second this) (:nano this)))))
(s/fdef -with-minute :args ::with-minute-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L925
(s/def ::with-second-args (args ::j/second-of-minute))
(defn -with-second [this second]
  (if (= second (:second this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/SECOND_OF_MINUTE second)
      (create (:hour this) (:minute this) second (:nano this)))))
(s/fdef -with-second :args ::with-second-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L942
(s/def ::with-nano-args (args ::j/nano-of-second))
(defn -with-nano [this nano-of-second]
  (if (= nano-of-second (:nano this))
    this
    (do
      (chrono-field/check-valid-value chrono-field/NANO_OF_SECOND nano-of-second)
      (create (:hour this) (:minute this) (:second this) nano-of-second))))
(s/fdef -with-nano :args ::with-nano-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L397
(s/def ::of-nano-of-day-args ::impl/of-nano-of-day-args)
(def of-nano-of-day #'impl/of-nano-of-day)
(s/fdef of-nano-of-day :args ::of-nano-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L971
(s/def ::truncated-to-args (args ::temporal-unit/temporal-unit))
(defn -truncated-to [this unit]
  (if (= unit chrono-unit/NANOS)
    this
    (let [unit-dur (temporal-unit/get-duration unit)]
      (when (> (duration/get-seconds unit-dur) SECONDS_PER_DAY)
        (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:local-time this :unit unit})))
      (let [dur (duration/to-nanos unit-dur)]
        (when (not= 0 (mod NANOS_PER_DAY dur))
          (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:local-time this :unit unit})))
        (of-nano-of-day (* dur (long (/ (-to-nano-of-day this) dur))))))))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1094
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours-to-add]
  (if (= 0 hours-to-add)
    this
    (create (mod (+ (int (mod hours-to-add HOURS_PER_DAY))
                    (:hour this)
                    HOURS_PER_DAY)
                 HOURS_PER_DAY)
            (:minute this)
            (:second this)
            (:nano this))))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1113
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes-to-add]
  (if (= 0 minutes-to-add)
    this
    (let [mofd (+ (:minute this)
                  (* (:hour this) MINUTES_PER_HOUR))
          new-mofd (mod (+ (int (mod minutes-to-add MINUTES_PER_DAY))
                           mofd
                           MINUTES_PER_DAY)
                        MINUTES_PER_DAY)]
      (if (= mofd new-mofd)
        this
        (create (int (/ new-mofd MINUTES_PER_HOUR))
                (mod new-mofd MINUTES_PER_HOUR)
                (:second this)
                (:nano this))))))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1138
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds-to-add]
  (if (= 0 seconds-to-add)
    this
    (let [sofd (+ (:second this)
                  (* (:minute this) SECONDS_PER_MINUTE)
                  (* (:hour this) SECONDS_PER_HOUR))
          new-sofd (mod (+ (int (mod seconds-to-add SECONDS_PER_DAY))
                           sofd
                           SECONDS_PER_DAY)
                        SECONDS_PER_DAY)]
      (if (= sofd new-sofd)
        this
        (create (int (/ new-sofd SECONDS_PER_HOUR))
                (mod (int (/ new-sofd SECONDS_PER_MINUTE)) MINUTES_PER_HOUR)
                (mod new-sofd SECONDS_PER_MINUTE)
                (:nano this))))))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1165
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos-to-add]
  (if (= 0 nanos-to-add)
    this
    (let [nofd (-to-nano-of-day this)
          new-nofd (mod (+ (mod nanos-to-add NANOS_PER_DAY)
                           nofd
                           NANOS_PER_DAY)
                        NANOS_PER_DAY)]
      (if (= nofd new-nofd)
        this
        (create (int (/ new-nofd NANOS_PER_HOUR))
                (mod (int (/ new-nofd NANOS_PER_MINUTE)) MINUTES_PER_HOUR)
                (mod (int (/ new-nofd NANOS_PER_SECOND)) SECONDS_PER_MINUTE)
                (mod new-nofd NANOS_PER_SECOND))))))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1243
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours-to-subtract]
  (-plus-hours this (- (mod hours-to-subtract HOURS_PER_DAY))))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1258
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes-to-subtract]
  (-plus-minutes this (- (mod minutes-to-subtract MINUTES_PER_DAY))))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1273
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds-to-subtract]
  (-plus-seconds this (- (mod seconds-to-subtract SECONDS_PER_DAY))))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1288
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos-to-subtract]
  (-plus-nanos this (- (mod nanos-to-subtract NANOS_PER_DAY))))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1433
(s/def ::format-args (args ::date-time-formatter/date-time-formatter))
(defn -format [this formatter]
  (require-non-nil formatter "formatter")
  (date-time-formatter/format formatter this))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1448
(s/def ::at-date-args (args ::local-date/local-date))
(defn -at-date [this date]
  (local-date-time-impl/of date this))
(s/fdef -at-date :args ::at-date-args :ret ::local-date-time/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1461
(s/def ::at-offset-args (args ::zone-offset/zone-offset))
(defn -at-offset [this offset]
  (offset-time-impl/of this offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::offset-time/offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1508
(s/def ::to-epoch-second-args (args ::local-date/local-date ::zone-offset/zone-offset))
(defn -to-epoch-second [this date offset]
  (require-non-nil date "date")
  (require-non-nil offset "offset")
  (+ (math/multiply-exact (chrono-local-date/to-epoch-day date) 86400)
     (-to-second-of-day this)
     (math/subtract-exact (zone-offset/get-total-seconds offset))))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1550
(s/def ::is-after-args (args ::local-time))
(defn -is-after [this other]
  (> (time-comparable/compare-to this other) 0))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1562
(s/def ::is-before-args (args ::local-time))
(defn -is-before [this other]
  (< (time-comparable/compare-to this other) 0))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

(extend-type LocalTime
  local-time/ILocalTime
  (to-nano-of-day [this] (-to-nano-of-day this))
  (to-second-of-day [this] (-to-second-of-day this))
  (get-hour [this] (-get-hour this))
  (get-minute [this] (-get-minute this))
  (get-second [this] (-get-second this))
  (get-nano [this] (-get-nano this))
  (with-hour [this hour] (-with-hour this hour))
  (with-minute [this minute] (-with-minute this minute))
  (with-second [this second] (-with-second this second))
  (with-nano [this nano-of-second] (-with-nano this nano-of-second))
  (truncated-to [this unit] (-truncated-to this unit))
  (plus-hours [this hours-to-add] (-plus-hours this hours-to-add))
  (plus-minutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plus-seconds [this secondsto-add] (-plus-seconds this secondsto-add))
  (plus-nanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minus-hours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minus-minutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minus-seconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minus-nanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (format [this formatter] (-format this formatter))
  (at-date [this date] (-at-date this date))
  (at-offset [this offset] (-at-offset this offset))
  (to-epoch-second [this date offset] (-to-epoch-second this date offset))
  (is-after [this other] (-is-after this other))
  (is-before [this other] (-is-before this other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1528
(s/def ::compare-to-args (args ::local-time))
(defn -compare-to [this other]
  (compare (mapv #(% this)  [:hour :minute :second :nano])
           (mapv #(% other) [:hour :minute :second :nano])))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalTime
  time-comparable/ITimeComparable
  (compare-to [this other] (-compare-to this other)))

(declare -plus-seconds
         -plus-minutes
         -plus-hours)

(s/def ::with-args (s/or :arity-2 (s/tuple ::local-time ::temporal-adjuster/temporal-adjuster)
                         :arity-3 (s/tuple ::local-time ::temporal-field/temporal-field ::j/long)))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L764
  ([this adjuster]
   (if (instance? LocalTime adjuster)
     adjuster
     (temporal-adjuster/adjust-into adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L855
  ([this field new-value]
   (if (chrono-field/chrono-field? field)
     (do (chrono-field/check-valid-value field new-value)
         (cond
           (= field chrono-field/NANO_OF_SECOND) (-with-nano this (int new-value))
           (= field chrono-field/NANO_OF_DAY) (of-nano-of-day new-value)
           (= field chrono-field/MICRO_OF_SECOND) (-with-nano this (* (int new-value) 1000))
           (= field chrono-field/MICRO_OF_DAY) (of-nano-of-day (* new-value 1000))
           (= field chrono-field/MILLI_OF_SECOND) (-with-nano this (* (int new-value) 1000000))
           (= field chrono-field/MILLI_OF_DAY) (of-nano-of-day (* new-value 1000000))
           (= field chrono-field/SECOND_OF_MINUTE) (-with-second this (int new-value))
           (= field chrono-field/SECOND_OF_DAY) (-plus-seconds this (- new-value (-to-second-of-day this)))
           (= field chrono-field/MINUTE_OF_HOUR) (-with-minute this (int new-value))
           (= field chrono-field/MINUTE_OF_DAY) (-plus-minutes this (- new-value (+ (* (:hour this) 60) (:minute this))))
           (= field chrono-field/HOUR_OF_AMPM) (-plus-hours this (- new-value (mod (:hour this) 12)))
           (= field chrono-field/CLOCK_HOUR_OF_AMPM) (-plus-hours this (- (if (= 12 new-value) 0 new-value) (mod (:hour this) 12)))
           (= field chrono-field/HOUR_OF_DAY) (-with-hour this (int new-value))
           (= field chrono-field/CLOCK_HOUR_OF_DAY) (-with-hour this (int (if (= 24 new-value) 0 new-value)))
           (= field chrono-field/AMPM_OF_DAY) (-plus-hours this (* 12 (- new-value (/ (:hour this) 12))))
           :else (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:local-time this :field field :new-value new-value}))))
     (temporal-field/adjust-into field this new-value))))
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1009
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1066
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1203
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1227
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1406
(s/def ::until-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type LocalTime
  temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L539
(s/def ::is-supported-args (args ::temporal-unit/temporal-unit))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L607
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L639
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L670
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1313
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1354
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type LocalTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L359
(s/def ::of-instant-args (args ::instant/instant ::zone-id/zone-id))
(defn of-instant [instant zone] (wip ::of-instant))
(s/fdef of-instant :args ::of-instant-args :ret ::local-time)

(s/def ::now-args (args ::j/wip))
(defn now
  ([] (now (clock-impl/system-default-zone)))

  ;; NB! This method is overloaded!
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L263
  ([zone-or-clock]
   (case (type zone-or-clock)
     ZoneId (now (clock-impl/system zone-or-clock))
     Clock (of-instant (clock/instant zone-or-clock)
                       (clock/get-zone zone-or-clock)))))
(s/fdef now :ret ::local-time)

(def-constructor of ::local-time
  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (of hour minute 0 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (of hour minute second 0))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (chrono-field/check-valid-value chrono-field/HOUR_OF_DAY hour)
   (chrono-field/check-valid-value chrono-field/MINUTE_OF_HOUR minute)
   (chrono-field/check-valid-value chrono-field/SECOND_OF_MINUTE second)
   (chrono-field/check-valid-value chrono-field/NANO_OF_SECOND nano-of-second)
   (impl/create hour minute second nano-of-second)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L379
(s/def ::of-second-of-day-args (args ::j/long))
(defn of-second-of-day [second-of-day] (wip ::of-second-of-day))
(s/fdef of-second-of-day :args ::of-second-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L426
(s/def ::from-args (args ::temporal-accessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L447
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L461
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-time)
