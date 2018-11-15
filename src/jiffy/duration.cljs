(ns jiffy.duration
  (:require [jiffy.big-decimal :as big-decimal]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time :refer [NANOS_PER_SECOND NANOS_PER_DAY SECONDS_PER_DAY SECONDS_PER_MINUTE SECONDS_PER_HOUR SECONDS_PER_MINUTE NANOS_PER_MILLI MINUTES_PER_HOUR]]
            [jiffy.math :as math]
            [jiffy.temporal.chrono-field :as ChronoField :refer [NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as ChronoUnit :refer [DAYS MICROS MILLIS NANOS SECONDS]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.unsupported-temporal-type-exception :refer [unsupported-temporal-type-exception]]
            [jiffy.time-comparable :as TimeComparable]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java
(defprotocol IDuration
  (isZero [this])
  (isNegative [this])
  (getSeconds [this])
  (getNano [this])
  (withSeconds [this seconds])
  (withNanos [this nano-of-second])
  (plus [this duration] [this amount-to-add unit])
  (plusDays [this days-to-add])
  (plusHours [this hours-to-add])
  (plusMinutes [this minutes-to-add])
  (plusSeconds [this seconds-to-add])
  (plusMillis [this millis-to-add])
  (plusNanos [this nanos-to-add])
  (minus [this duration] [this amount-to-subtract unit])
  (minusDays [this days-to-subtract])
  (minusHours [this hours-to-subtract])
  (minusMinutes [this minutes-to-subtract])
  (minusSeconds [this seconds-to-subtract])
  (minusMillis [this millis-to-subtract])
  (minusNanos [this nanos-to-subtract])
  (multipliedBy [this multiplicand])
  (dividedBy [this divisor])
  (negated [this])
  (abs [this])
  (toDays [this])
  (toHours [this])
  (toMinutes [this])
  (toSeconds [this])
  (toMillis [this])
  (toNanos [this])
  (toDaysPart [this])
  (toHoursPart [this])
  (toMinutesPart [this])
  (toSecondsPart [this])
  (toMillisPart [this])
  (toNanosPart [this])
  (truncatedTo [this unit]))

(defrecord Duration [seconds nano-of-second])

(def ZERO (->Duration 0 0))

(def -get-seconds :seconds)
(def -get-nano :nanos)

(defn- create
  ([big-decimal-seconds] (wip ::create--not-implemented))
  ([seconds nano-adjustment]
   (if (zero? (bit-or seconds nano-adjustment))
     ZERO
     (->Duration seconds nano-adjustment))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L592
(defn -is-zero [this]
  (bit-or (:seconds this) (:nanos this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L605
(defn -is-negative [this]
  (neg? (:seconds this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L658
(defn -with-seconds [this seconds]
  (create seconds (:nanos this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L674
(defn -with-nanos [this nano-of-second]
  (ChronoField/checkValidIntValue NANO_OF_SECOND nano-of-second)
  (create (:seconds this) nano-of-second))

(defn ofSeconds
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L223
  ([seconds]
   (create seconds 0))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L246
  ([seconds nano-adjustment]
   (create (math/add-exact seconds (math/floor-div nano-adjustment NANOS_PER_SECOND))
           (int (math/floor-mod nano-adjustment NANOS_PER_SECOND)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L825
(defn- --plus [this seconds-to-add nanos-to-add]
  (if (zero? (bit-or seconds-to-add nanos-to-add))
    this
    (let [epoch-sec (-> (:seconds this)
                        (math/add-exact seconds-to-add)
                        (math/add-exact (/ nanos-to-add NANOS_PER_SECOND)))
          nano-adjustment (-> (mod nanos-to-add NANOS_PER_SECOND)
                              (+ (:nanos this)))]
      (ofSeconds epoch-sec nano-adjustment))))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L689
  ([this duration]
   (--plus this (:seconds duration) (:nano duration)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L709
  ([this amount-to-add unit]
   (cond
     (= unit DAYS)
     (--plus this (math/multiply-exact amount-to-add SECONDS_PER_DAY) 0)

     (TemporalUnit/isDurationEstimated unit)
     (throw (unsupported-temporal-type-exception "Unit must not have an estimated duration" {:duration this :unit unit}))

     (zero? amount-to-add)
     this

     (instance? ChronoUnit/IChronoUnit unit)
     (condp = unit
       NANOS (plusNanos this amount-to-add)
       MICROS (-> this
                  (plusSeconds (-> amount-to-add (/ (* 1000000 1000)) (* 1000)))
                  (plusNanos (-> amount-to-add (mod (* 1000000 1000)) (* 1000))))
       MILLIS (plusMillis this amount-to-add)
       SECONDS (plusSeconds this amount-to-add)
       (plusSeconds this (-> unit TemporalUnit/getDuration :seconds (math/multiply-exact amount-to-add))))

     :else
     (let [duration (-> unit TemporalUnit/getDuration (multipliedBy amount-to-add))]
       (-> this
           (plusSeconds (:seconds duration))
           (plusNanos (:nano duration)))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L746
(defn -plus-days [this days-to-add]
  (--plus this (math/multiply-exact days-to-add SECONDS_PER_DAY) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L759
(defn -plus-hours [this hours-to-add]
  (--plus this (math/multiply-exact hours-to-add SECONDS_PER_HOUR) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L772
(defn -plus-minutes [this minutes-to-add]
  (--plus this (math/multiply-exact minutes-to-add SECONDS_PER_MINUTE) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L785
(defn -plus-seconds [this seconds-to-add]
  (--plus this seconds-to-add 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L798
(defn -plus-millis [this millis-to-add]
  (--plus this (/ millis-to-add 1000) (* (mod millis-to-add 1000) 1000000)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L811
(defn -plus-nanos [this nanos-to-add]
  (--plus this 0 nanos-to-add))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L846
  ([this duration]
   (if (= (:seconds duration) math/long-min-value)
     (-> this
         (--plus math/long-max-value (- (:nano duration)))
         (--plus 1 0))
     (--plus this (- (:second duration)) (- (:nano duration)))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L870
  ([this amount-to-subtract unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L887
(defn -minus-days [this days-to-subtract]
  (if (= days-to-subtract math/long-min-value)
    (-> this
        (plusDays math/long-max-value)
        (plusDays 1))
    (plusDays this (- days-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L902
(defn -minus-hours [this hours-to-subtract]
  (if (= hours-to-subtract math/long-min-value)
    (-> this
        (plusHours math/long-max-value)
        (plusHours 1))
    (plusHours this (- hours-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L917
(defn -minus-minutes [this minutes-to-subtract]
  (if (= minutes-to-subtract math/long-min-value)
    (-> this
        (plusMinutes math/long-max-value)
        (plusMinutes 1))
    (plusMinutes this (- minutes-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L930
(defn -minus-seconds [this seconds-to-subtract]
  (if (= seconds-to-subtract math/long-min-value)
    (-> this
        (plusSeconds math/long-max-value)
        (plusSeconds 1))
    (plusSeconds this (- seconds-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L943
(defn -minus-millis [this millis-to-subtract]
  (if (= millis-to-subtract math/long-min-value)
    (-> this
        (plusMillis math/long-max-value)
        (plusMillis 1))
    (plusMillis this (- millis-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L956
(defn -minus-nanos [this nanos-to-subtract]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this
        (plusNanos math/long-max-value)
        (plusNanos 1))
    (plusNanos this (- nanos-to-subtract))))

(defn- to-big-decimal-seconds [this]
  (big-decimal/add (big-decimal/value-of (:seconds this))
                   (big-decimal/value-of (:nanos this) 9)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L970
(defn -multiplied-by [this multiplicand]
  (condp multiplicand
    0 ZERO
    1 this
    :else (create (big-decimal/multiply (to-big-decimal-seconds this) (big-decimal/value-of multiplicand)))))

(defn --divided-by-long [this divisor]
  (condp = divisor
    0 (throw (ex-info "Cannot divide by zero" {:duration this :divisor divisor}))
    1 this
    (create (big-decimal/divide (to-big-decimal-seconds this) (big-decimal/value-of divisor) :rounding.mode/down))))

(defn --divided-by-duration [this duration]
  (-> (to-big-decimal-seconds this)
      (big-decimal/divide-to-integral-value (to-big-decimal-seconds duration))
      big-decimal/long-value-exact))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L989
(defn -divided-by [this divisor]
  (if (instance? IDuration divisor)
    (--divided-by-duration this divisor)
    (--divided-by-long this divisor)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1055
(defn -negated [this]
  (multipliedBy this -1))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1070
(defn -abs [this]
  (if (isNegative this)
    (negated this)
    this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1157
(defn -to-days [this]
  (/ (:seconds this) SECONDS_PER_DAY))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1171
(defn -to-hours [this]
  (/ (:seconds this) SECONDS_PER_HOUR))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1185
(defn -to-minutes [this]
  (/ (:seconds this) SECONDS_PER_MINUTE))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1199
(defn -to-seconds [this]
  (:seconds this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1216
(defn -to-millis [this]
  (let [[seconds nanos] (if (neg? (:seconds this))
                          ;; change the seconds and nano value to
                          ;; handle Long.MIN_VALUE case
                          [(inc (:seconds this))
                           (- (:nanos this) NANOS_PER_SECOND)]
                          [(:seconds this)
                           (:nanos this)])]
    (-> seconds
        (math/multiply-exact 1000)
        (math/add-exact (/ nanos NANOS_PER_MILLI)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1239
(defn -to-nanos [this]
  (let [[seconds nanos] (if (neg? (:seconds this))
                          ;; change the seconds and nano value to
                          ;; handle Long.MIN_VALUE case
                          [(inc (:seconds this))
                           (- (:nanos this) NANOS_PER_SECOND)]
                          [(:seconds this)
                           (:nanos this)])]
    (-> seconds
        (math/multiply-exact NANOS_PER_SECOND)
        (math/add-exact nanos))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1265
(defn -to-days-part [this]
  (/ (:seconds this) SECONDS_PER_DAY))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1281
(defn -to-hours-part [this]
  (int (mod (toHours this) 24)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1297
(defn -to-minutes-part [this]
  (int (mod (toMinutes this) MINUTES_PER_HOUR)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1313
(defn -to-seconds-part [this]
  (int (mod (:seconds this) SECONDS_PER_MINUTE)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1331
(defn -to-millis-part [this]
  (/ (:nanos this) 1000000))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1348
(defn -to-nanos-part [this]
  (:nanos this))


;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1377
(defn -truncated-to [this unit]
  (let [unit-dur (TemporalUnit/getDuration unit)
        dur (toNanos this)]
    (cond
      (and (= unit ChronoUnit/SECONDS)
           (or (>= (:seconds this) 0)
               (== (:nanos this) 0)))
      (->Duration (:seconds this) 0)

      (= unit ChronoUnit/NANOS)
      this

      (> (getSeconds unit-dur) SECONDS_PER_DAY)
      (throw (unsupported-temporal-type-exception "Unit is too large to be used for truncation" {:duration this :unit unit}))

      (-> NANOS_PER_DAY (mod dur) zero? not)
      (throw (unsupported-temporal-type-exception "Unit must divide into a standard day without remainder" {:duration this :unit unit}))

      :else
      (let [nod (-> (:seconds this)
                    (mod SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result  (-> nod
                        (/ dur)
                        (* dur))]
        (plusNanos this (- result nod))))))

(extend-type Duration
  IDuration
  (isZero [this] (-is-zero this))
  (isNegative [this] (-is-negative this))
  (getSeconds [this] (-get-seconds this))
  (getNano [this] (-get-nano this))
  (withSeconds [this seconds] (-with-seconds this seconds))
  (withNanos [this nano-of-second] (-with-nanos this nano-of-second))
  (plus
    ([this duration] (-plus this duration))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (plusDays [this days-to-add] (-plus-days this days-to-add))
  (plusHours [this hours-to-add] (-plus-hours this hours-to-add))
  (plusMinutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plusSeconds [this seconds-to-add] (-plus-seconds this seconds-to-add))
  (plusMillis [this millis-to-add] (-plus-millis this millis-to-add))
  (plusNanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minus
    ([this duration] (-minus this duration))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract))
  (minusHours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minusMinutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minusSeconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minusMillis [this millis-to-subtract] (-minus-millis this millis-to-subtract))
  (minusNanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (multipliedBy [this multiplicand] (-multiplied-by this multiplicand))
  (dividedBy [this divisor] (-divided-by this divisor))
  (negated [this] (-negated this))
  (abs [this] (-abs this))
  (toDays [this] (-to-days this))
  (toHours [this] (-to-hours this))
  (toMinutes [this] (-to-minutes this))
  (toSeconds [this] (-to-seconds this))
  (toMillis [this] (-to-millis this))
  (toNanos [this] (-to-nanos this))
  (toDaysPart [this] (-to-days-part this))
  (toHoursPart [this] (-to-hours-part this))
  (toMinutesPart [this] (-to-minutes-part this))
  (toSecondsPart [this] (-to-seconds-part this))
  (toMillisPart [this] (-to-millis-part this))
  (toNanosPart [this] (-to-nanos-part this))
  (truncatedTo [this unit] (-truncated-to this unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1408
(defn -compare-to [this other-duration]
  (let [cmp (compare (:seconds this) (:seconds other-duration))]
    (if-not (zero? cmp)
      cmp
      (- (:nanos this) (:nanos other-duration)))))

(extend-type Duration
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L546
(defn -get [this unit]
  (condp = unit
    SECONDS (:seconds this)
    NANOS (:nanos this)
    (throw (unsupported-temporal-type-exception (str "Unsupported unit: " unit) {:duration this :unit unit}))))

(def UNITS (delay [SECONDS NANOS]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L569
(defn -get-units [this]
  @UNITS)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1100
(defn -add-to [this temporal]
  (cond-> temporal
    (not (zero? (:seconds this)))
    (plus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (plus (:seconds this) NANOS)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1135
(defn -subtract-from [this temporal]
  (cond-> temporal
    (not (zero? (:seconds this)))
    (minus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (minus (:seconds this) NANOS)))

(extend-type Duration
  TemporalAmount/ITemporalAmount
  (get [this unit] (-get this unit))
  (getUnits [this] (-get-units this))
  (addTo [this temporal] (-add-to this temporal))
  (subtractFrom [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L180
(defn ofDays [days]
  (create (math/multiply-exact days SECONDS_PER_DAY) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L195
(defn ofHours [hours]
  (create (math/multiply-exact hours SECONDS_PER_HOUR) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L210
(defn ofMinutes [minutes]
  (create (math/multiply-exact minutes SECONDS_PER_MINUTE) 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L261
(defn ofMillis [millis]
  (let [mos (int (mod millis 1000))
        secs (cond-> (/ millis 1000) (neg? mos) dec)
        mos (cond-> mos neg? (+ 1000))]
    (create secs (* mos 1000000))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L280
(defn ofNanos [nanos]
  (let [nos (int (mod nanos NANOS_PER_SECOND))
        secs (cond-> (/ nanos NANOS_PER_SECOND) (neg? nos) dec)
        nos (cond-> nos neg? (+ NANOS_PER_SECOND))]
    (create secs nos)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L309
(defn of [amount unit]
  (plus ZERO amount unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L334
(defn from [amount]
  (reduce #(plus %1 (TemporalAmount/get amount %2) %2)
          ZERO
          (TemporalAmount/getUnits amount)))

(def PATTERN (delay (re-pattern (str "(?i)([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?"))));

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L388
(defn parse [text]
  (wip ::parse--not-implemented)
  (let [matches (re-matches @PATTERN text)]
    (if-not matches
      (throw (ex-info "Text cannot be parsed to a Duration" {:text text}))
      matches)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L486
(defn between [start-inclusive end-exclusive]
  (try
    (-> start-inclusive (Temporal/until end-exclusive NANOS) ofNanos)
    (catch :default e
      (let [secs (-> start-inclusive (Temporal/until end-exclusive SECONDS))
            [secs nanos] (try
                           (let [n (- (TemporalAccessor/getLong end-exclusive NANO_OF_SECOND)
                                      (TemporalAccessor/getLong start-inclusive NANO_OF_SECOND))
                                 s (cond-> secs
                                     (and (pos? secs) (neg? n))
                                     inc
                                     (and (neg? secs) (pos? n))
                                     dec)]
                             [s n])
                           (catch :default e [secs 0]))]
        (ofSeconds secs nanos)))))
