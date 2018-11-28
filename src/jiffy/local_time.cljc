(ns jiffy.local-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.conversion :refer [jiffy->java same?]])
            [jiffy.asserts :refer [require-non-nil]]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as Duration]
            [jiffy.exception :refer [ex JavaNullPointerException UnsupportedTemporalTypeException]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date-impl :as LocalDate]
            [jiffy.local-date-time-impl :as LocalDateTime]
            [jiffy.local-time-impl :as impl]
            [jiffy.offset-time-impl :as OffsetTime]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.chrono-unit :as ChronoUnit]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java
(defprotocol ILocalTime
  (toNanoOfDay [this])
  (toSecondOfDay [this])
  (getHour [this])
  (getMinute [this])
  (getSecond [this])
  (getNano [this])
  (withHour [this hour])
  (withMinute [this minute])
  (withSecond [this second])
  (withNano [this nano-of-second])
  (truncatedTo [this unit])
  (plusHours [this hours-to-add])
  (plusMinutes [this minutes-to-add])
  (plusSeconds [this secondsto-add])
  (plusNanos [this nanos-to-add])
  (minusHours [this hours-to-subtract])
  (minusMinutes [this minutes-to-subtract])
  (minusSeconds [this seconds-to-subtract])
  (minusNanos [this nanos-to-subtract])
  (format [this formatter])
  (atDate [this date])
  (atOffset [this offset])
  (toEpochSecond [this date offset])
  (isAfter [this other])
  (isBefore [this other]))

(defrecord LocalTime [hour minute second nano])

(s/def ::create-args (s/tuple ::j/hour-of-day
                              ::j/minute-of-hour
                              ::j/second-of-minute
                              ::j/nano-of-second))
(defn create [hour minute second nano]
  (->LocalTime hour minute second nano))
(s/def ::local-time (j/constructor-spec LocalTime create ::create-args))
(s/fdef create :args ::create-args :ret ::local-time)

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
  (+ (* (:hour this) SECONDS_PER_HOUR)
     (* (:minute this) SECONDS_PER_MINUTE)
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
      (ChronoField/checkValidValue ChronoField/HOUR_OF_DAY hour)
      (create hour (:minute this) (:second this) (:nano this)))))
(s/fdef -with-hour :args ::with-hour-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L908
(s/def ::with-minute-args (args ::j/minute-of-hour))
(defn -with-minute [this minute]
  (if (= minute (:minute this))
    this
    (do
      (ChronoField/checkValidValue ChronoField/MINUTE_OF_HOUR minute)
      (create (:hour this) minute (:second this) (:nano this)))))
(s/fdef -with-minute :args ::with-minute-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L925
(s/def ::with-second-args (args ::j/second-of-minute))
(defn -with-second [this second]
  (if (= second (:second this))
    this
    (do
      (ChronoField/checkValidValue ChronoField/SECOND_OF_MINUTE second)
      (create (:hour this) (:minute this) second (:nano this)))))
(s/fdef -with-second :args ::with-second-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L942
(s/def ::with-nano-args (args ::j/nano-of-second))
(defn -with-nano [this nano-of-second]
  (if (= nano-of-second (:nano this))
    this
    (do
      (ChronoField/checkValidValue ChronoField/NANO_OF_SECOND nano-of-second)
      (create (:hour this) (:minute this) (:second this) nano-of-second))))
(s/fdef -with-nano :args ::with-nano-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L397
(s/def ::of-nano-of-day-args (args ::j/long))
(defn ofNanoOfDay [nano-of-day]
  (ChronoField/checkValidValue ChronoField/NANO_OF_DAY nano-of-day)
  (let [hours (int (/ nano-of-day NANOS_PER_HOUR))
        nanos (- nano-of-day (* hours NANOS_PER_HOUR))
        minutes (int (/ nanos NANOS_PER_MINUTE))
        nanos (- nanos (* minutes NANOS_PER_MINUTE))
        seconds (int (/ nanos NANOS_PER_SECOND))
        nanos (- nanos (* seconds NANOS_PER_SECOND))]
    (create hours minutes seconds nanos)))
(s/fdef ofNanoOfDay :args ::of-nano-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L971
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit]
  (if (= unit ChronoUnit/NANOS)
    this
    (let [unit-dur (TemporalUnit/getDuration unit)
          dur (Duration/toNanos unit-dur)]
      (when (> (Duration/getSeconds unit-dur) SECONDS_PER_DAY)
        (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:local-time this :unit unit})))
      (when (not= 0 (mod NANOS_PER_DAY dur))
        (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:local-time this :unit unit})))
      (ofNanoOfDay (* dur (int (/ (-to-nano-of-day this) dur)))))))
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
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter]
  (require-non-nil formatter "formatter")
  (DateTimeFormatter/format formatter this))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1448
(s/def ::at-date-args (args ::LocalDate/local-date))
(defn -at-date [this date]
  (LocalDateTime/of date this))
(s/fdef -at-date :args ::at-date-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1461
(s/def ::at-offset-args (args ::ZoneOffset/zone-offset))
(defn -at-offset [this offset]
  (OffsetTime/of this offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::OffsetTime/offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1508
(s/def ::to-epoch-second-args (args ::LocalDate/local-date ::ZoneOffset/zone-offset))
(defn -to-epoch-second [this date offset]
  (require-non-nil date "date")
  (require-non-nil offset "offset")
  (+ (* (ChronoLocalDate/toEpochDay date) 86400)
     (-to-second-of-day this)
     (- (ZoneOffset/getTotalSeconds offset))))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1550
(s/def ::is-after-args (args ::local-time))
(defn -is-after [this other]
  (> (TimeComparable/compareTo this other) 0))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1562
(s/def ::is-before-args (args ::local-time))
(defn -is-before [this other]
  (< (TimeComparable/compareTo this other) 0))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

(extend-type LocalTime
  ILocalTime
  (toNanoOfDay [this] (-to-nano-of-day this))
  (toSecondOfDay [this] (-to-second-of-day this))
  (getHour [this] (-get-hour this))
  (getMinute [this] (-get-minute this))
  (getSecond [this] (-get-second this))
  (getNano [this] (-get-nano this))
  (withHour [this hour] (-with-hour this hour))
  (withMinute [this minute] (-with-minute this minute))
  (withSecond [this second] (-with-second this second))
  (withNano [this nano-of-second] (-with-nano this nano-of-second))
  (truncatedTo [this unit] (-truncated-to this unit))
  (plusHours [this hours-to-add] (-plus-hours this hours-to-add))
  (plusMinutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plusSeconds [this secondsto-add] (-plus-seconds this secondsto-add))
  (plusNanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minusHours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minusMinutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minusSeconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minusNanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (format [this formatter] (-format this formatter))
  (atDate [this date] (-at-date this date))
  (atOffset [this offset] (-at-offset this offset))
  (toEpochSecond [this date offset] (-to-epoch-second this date offset))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1528
(s/def ::compare-to-args (args ::local-time))
(defn -compare-to [this other]
  (compare (mapv #(% this)  [:hour :minute :second :nano])
           (mapv #(% other) [:hour :minute :second :nano])))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalTime
  TimeComparable/ITimeComparable
  (compareTo [this other] (-compare-to this other)))

(s/def ::with-args (s/or :arity-2 (s/tuple ::local-time ::TemporalAdjuster/temporal-adjuster)
                         :arity-3 (s/tuple ::local-time ::TemporalField/temporal-field ::j/long)))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L764
  ([this adjuster]
   (if (instance? LocalTime adjuster)
     adjuster
     (TemporalAdjuster/adjustInto adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L855
  ([this field new-value]
   (if (ChronoField/chrono-field? field)
     (do (ChronoField/checkValidValue field new-value)
         (cond
           (= field ChronoField/NANO_OF_SECOND) (-with-nano this (int new-value))
           (= field ChronoField/NANO_OF_DAY) (ofNanoOfDay new-value)
           (= field ChronoField/MICRO_OF_SECOND) (-with-nano this (* (int new-value) 1000))
           (= field ChronoField/MICRO_OF_DAY) (ofNanoOfDay (* new-value 1000))
           (= field ChronoField/MILLI_OF_SECOND) (-with-nano this (* (int new-value) 1000000))
           (= field ChronoField/MILLI_OF_DAY) (ofNanoOfDay (* new-value 1000000))
           (= field ChronoField/SECOND_OF_MINUTE) (-with-second this (int new-value))
           (= field ChronoField/SECOND_OF_DAY) (plusSeconds this (- new-value (-to-second-of-day this)))
           (= field ChronoField/MINUTE_OF_HOUR) (-with-minute this (int new-value))
           (= field ChronoField/MINUTE_OF_DAY) (plusMinutes this (- new-value (+ (* (:hour this) 60) (:minute this))))
           (= field ChronoField/HOUR_OF_AMPM) (plusHours this (- new-value (mod (:hour this) 12)))
           (= field ChronoField/CLOCK_HOUR_OF_AMPM) (plusHours this (- (if (= 12 new-value) 0 new-value) (mod (:hour this) 12)))
           (= field ChronoField/HOUR_OF_DAY) (-with-hour this (int new-value))
           (= field ChronoField/CLOCK_HOUR_OF_DAY) (-with-hour this (int (if (= 24 new-value) 0 new-value)))
           (= field ChronoField/AMPM_OF_DAY) (plusHours this (* 12 (- new-value (/ (:hour this) 12))))
           :else (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:local-time this :field field :new-value new-value}))))
     (TemporalField/adjustInto field this new-value))))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1009
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1066
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1203
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1227
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1406
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type LocalTime
  Temporal/ITemporal
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
(s/def ::is-supported-args (args ::TemporalUnit/temporal-unit))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L607
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L639
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L670
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1313
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1354
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type LocalTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L359
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::local-time)

(s/def ::now-args (args ::j/wip))
(defn now
  ([] (now (Clock/systemDefaultZone)))

  ;; NB! This method is overloaded!
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L263
  ([zone-or-clock]
   (case (type zone-or-clock)
     ZoneId (now (Clock/system zone-or-clock))
     Clock (ofInstant (Clock/instant zone-or-clock)
                      (Clock/getZone zone-or-clock)))))
(s/fdef now :ret ::local-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L295
  ([hour minute] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L316
  ([hour minute second] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L338
  ([hour minute second nano-of-second] (wip ::of)))
(s/fdef of :args ::of-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L379
(s/def ::of-second-of-day-args (args ::j/long))
(defn ofSecondOfDay [second-of-day] (wip ::ofSecondOfDay))
(s/fdef ofSecondOfDay :args ::of-second-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L426
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L447
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L461
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-time)

#?(:clj
   (defmethod jiffy->java LocalTime [this]
     (java.time.LocalTime/of (:hour this)
                             (:minute this)
                             (:second this)
                             (:nano this))))

#?(:clj
   (defmethod same? LocalTime
     [jiffy-object java-object]
     (= (mapv #(% jiffy-object) [:hour :minute :second :nano])
        [(.getHour java-object)
         (.getMinute java-object)
         (.getSecond java-object)
         (.getNano java-object)])))
