(ns jiffy.duration
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators]
            [jiffy.asserts :refer [require-non-nil]]
            [jiffy.math.big-decimal :as big-decimal]
            #?(:clj [jiffy.conversion :as conversion])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :refer [create #?@(:cljs [Duration])] :as impl]
            [jiffy.exception :refer [DateTimeParseException JavaArithmeticException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.local-time-impl :refer [NANOS_PER_SECOND NANOS_PER_DAY SECONDS_PER_DAY SECONDS_PER_MINUTE SECONDS_PER_HOUR NANOS_PER_MILLI MINUTES_PER_HOUR]]
            [jiffy.math :as math]
            [jiffy.temporal.chrono-field :as chrono-field :refer [NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as chrono-unit :refer [DAYS MICROS MILLIS NANOS SECONDS]]
            [jiffy.specs :as j]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.duration :as duration])
  #?(:clj (:import [jiffy.duration_impl Duration])))

(def ZERO impl/ZERO)

(s/def ::duration ::impl/duration)

(defmacro args [& x] `(s/tuple ::duration ~@x))

(s/def ::get-seconds-args (args))
(defn -get-seconds [this] (:seconds this))
(s/fdef -get-seconds :ret ::j/long)

(s/def ::get-nano-args (args))
(defn -get-nano [this] (:nanos this))
(s/fdef -get-nano :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L592
(s/def ::is-zero-args (args))
(defn -is-zero [this]
  (zero? (bit-or (:seconds this) (:nanos this))))
(s/fdef -is-zero :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L605
(s/def ::is-negative-args (args))
(defn -is-negative [this]
  (neg? (:seconds this)))
(s/fdef -is-negative :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L658
(s/def ::with-seconds-args (args ::j/long))
(defn -with-seconds [this seconds]
  (create seconds (:nanos this)))
(s/fdef -with-seconds :args ::with-seconds-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L674
(s/def ::with-nanos-args (args ::j/nano-of-second))
(defn -with-nanos [this nano-of-second]
  (chrono-field/check-valid-int-value NANO_OF_SECOND nano-of-second)
  (create (:seconds this) nano-of-second))
(s/fdef -with-nanos :args ::with-nanos-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L223
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L246

(s/def ::of-seconds-args ::impl/of-seconds-args)
(def of-seconds #'impl/of-seconds)
(s/fdef of-seconds :args ::of-seconds-args :ret ::duration)

(declare -plus
         -plus-nanos
         -plus-millis
         -plus-seconds
         -plus-minutes
         -plus-hours
         -multiplied-by
         -negated
         -to-hours
         -to-minutes
         -to-nanos
         -get-seconds)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L825
(defn- --plus [this seconds-to-add nanos-to-add]
  (if (zero? (bit-or seconds-to-add nanos-to-add))
    this
    (do
      (let [epoch-sec (-> (:seconds this)
                          (math/add-exact seconds-to-add)
                          (math/add-exact (long (/ nanos-to-add NANOS_PER_SECOND))))
            nano-adjustment (-> (rem nanos-to-add NANOS_PER_SECOND)
                                (+ (:nanos this)))]
        (of-seconds epoch-sec nano-adjustment)))))

(s/def ::plus-args (s/or :arity-2 (args ::duration)
                         :arity-3 (args ::j/long ::temporal-unit/temporal-unit)))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L689
  ([this duration]
   (--plus this (:seconds duration) (:nanos duration)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L709
  ([this amount-to-add unit]
   (cond
     (= unit DAYS)
     (--plus this (math/multiply-exact amount-to-add SECONDS_PER_DAY) 0)

     (temporal-unit/is-duration-estimated unit)
     (throw (ex UnsupportedTemporalTypeException "Unit must not have an estimated duration" {:duration this :unit unit}))

     (zero? amount-to-add)
     this

     (chrono-unit/chrono-unit? unit)
     (condp = unit
       NANOS (duration/plus-nanos this amount-to-add)
       MICROS (-> this
                  (duration/plus-seconds (-> amount-to-add (/ (* 1000000 1000)) long (* 1000)))
                  (duration/plus-nanos (-> amount-to-add (rem (* 1000000 1000)) (* 1000))))
       MILLIS (duration/plus-millis this amount-to-add)
       SECONDS (duration/plus-seconds this amount-to-add)
       (duration/plus-seconds this (-> unit temporal-unit/get-duration :seconds (math/multiply-exact amount-to-add))))

     :else
     (let [duration (-> unit temporal-unit/get-duration (duration/multiplied-by amount-to-add))]
       (-> this
           (duration/plus-seconds (:seconds duration))
           (duration/plus-nanos (:nanos duration)))))))
(s/fdef -plus :args ::plus-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L746
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days-to-add]
  (--plus this (math/multiply-exact days-to-add SECONDS_PER_DAY) 0))
(s/fdef -plus-days :args ::plus-days-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L759
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours-to-add]
  (--plus this (math/multiply-exact hours-to-add SECONDS_PER_HOUR) 0))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L772
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes-to-add]
  (--plus this (math/multiply-exact minutes-to-add SECONDS_PER_MINUTE) 0))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L785
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds-to-add]
  (--plus this seconds-to-add 0))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L798
(s/def ::plus-millis-args (args ::j/long))
(defn -plus-millis [this millis-to-add]
  (--plus this (long (/ millis-to-add 1000)) (* (rem millis-to-add 1000) 1000000)))
(s/fdef -plus-millis :args ::plus-millis-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L811
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos-to-add]
  (--plus this 0 nanos-to-add))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::duration)

(s/def ::minus-args (s/or :arity-2 (args ::duration)
                          :arity-3 (args ::j/long ::temporal-unit/temporal-unit)))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L846
  ([this duration]
   (if (= (:seconds duration) math/long-min-value)
     (-> this
         (--plus math/long-max-value (- (:nanos duration)))
         (--plus 1 0))
     (--plus this (- (:seconds duration)) (- (:nanos duration)))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L870
  ([this amount-to-subtract unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (duration/plus math/long-max-value unit)
         (duration/plus 1 unit))
     (duration/plus this (- amount-to-subtract) unit))))
(s/fdef -minus :args ::minus-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L887
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract]
  (if (= days-to-subtract math/long-min-value)
    (-> this
        (duration/plus-days math/long-max-value)
        (duration/plus-days 1))
    (duration/plus-days this (- days-to-subtract))))
(s/fdef -minus-days :args ::minus-days-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L902
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours-to-subtract]
  (if (= hours-to-subtract math/long-min-value)
    (-> this
        (-plus-hours math/long-max-value)
        (-plus-hours 1))
    (-plus-hours this (- hours-to-subtract))))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L917
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes-to-subtract]
  (if (= minutes-to-subtract math/long-min-value)
    (-> this
        (-plus-minutes math/long-max-value)
        (-plus-minutes 1))
    (-plus-minutes this (- minutes-to-subtract))))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L930
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds-to-subtract]
  (if (= seconds-to-subtract math/long-min-value)
    (-> this
        (-plus-seconds math/long-max-value)
        (-plus-seconds 1))
    (-plus-seconds this (- seconds-to-subtract))))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L943
(s/def ::minus-millis-args (args ::j/long))
(defn -minus-millis [this millis-to-subtract]
  (if (= millis-to-subtract math/long-min-value)
    (-> this
        (-plus-millis math/long-max-value)
        (-plus-millis 1))
    (-plus-millis this (- millis-to-subtract))))
(s/fdef -minus-millis :args ::minus-millis-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L956
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos-to-subtract]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this
        (-plus-nanos math/long-max-value)
        (-plus-nanos 1))
    (-plus-nanos this (- nanos-to-subtract))))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L970
(s/def ::multiplied-by-args ::impl/multiplied-by-args)
(def -multiplied-by #'impl/-multiplied-by)
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::duration)

(defn --divided-by-long [this divisor]
  (condp = divisor
    0 (throw (ex JavaArithmeticException "Cannot divide by zero" {:duration this :divisor divisor}))
    1 this
    (create (big-decimal/divide (impl/to-big-decimal-seconds this) (big-decimal/value-of divisor) :rounding.mode/down))))

(defn --divided-by-duration [this duration]
  (-> (impl/to-big-decimal-seconds this)
      (big-decimal/divide-to-integral-value (impl/to-big-decimal-seconds duration))
      big-decimal/long-value-exact))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L989
(s/def ::divided-by-args (s/or :long (args ::j/long)
                               :duration (args ::duration)))
(defn -divided-by [this divisor]
  (if (satisfies? duration/IDuration divisor)
    (--divided-by-duration this divisor)
    (--divided-by-long this divisor)))
(s/fdef -divided-by :args ::divided-by-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1055
(s/def ::negated-args ::impl/negated-args)
(def -negated #'impl/-negated)
(s/fdef -negated :args ::negated-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1070
(s/def ::abs-args (args))
(defn -abs [this]
  (if (-is-negative this)
    (-negated this)
    this))
(s/fdef -abs :args ::abs-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1157
(s/def ::to-days-args (args))
(defn -to-days [this]
  (-> this :seconds (/ SECONDS_PER_DAY) long))
(s/fdef -to-days :args ::to-days-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1171
(s/def ::to-hours-args (args))
(defn -to-hours [this]
  (-> this :seconds (/ SECONDS_PER_HOUR) long))
(s/fdef -to-hours :args ::to-hours-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1185
(s/def ::to-minutes-args (args))
(defn -to-minutes [this]
  (-> this :seconds (/ SECONDS_PER_MINUTE) long))
(s/fdef -to-minutes :args ::to-minutes-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1199
(s/def ::to-seconds-args (args))
(defn -to-seconds [this]
  (:seconds this))
(s/fdef -to-seconds :args ::to-seconds-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1216
(s/def ::to-millis-args (args))
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
        (math/add-exact (long (/ nanos NANOS_PER_MILLI))))))
(s/fdef -to-millis :arg ::to-millis-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1239
(s/def ::to-nanos-args (args))
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
        (math/add-exact (long nanos)))))
(s/fdef -to-nanos :args ::to-nanos-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1265
(s/def ::to-days-part-args (args))
(defn -to-days-part [this]
  (-> this :seconds (/ SECONDS_PER_DAY) long))
(s/fdef -to-days-part :args ::to-days-part-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1281
(s/def ::to-hours-part-args (args))
(defn -to-hours-part [this]
  (int (rem (-to-hours this) 24)))
(s/fdef -to-hours-part :args ::to-hours-part-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1297
(s/def ::to-minutes-part-args (args))
(defn -to-minutes-part [this]
  (int (rem (-to-minutes this) MINUTES_PER_HOUR)))
(s/fdef -to-minutes-part :args ::to-minutes-part-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1313
(s/def ::to-seconds-part-args (args))
(defn -to-seconds-part [this]
  (int (rem (:seconds this) SECONDS_PER_MINUTE)))
(s/fdef -to-seconds-part :args ::to-seconds-part-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1331
(s/def ::to-millis-part-args (args))
(defn -to-millis-part [this]
  (-> this :nanos (/ 1000000) long))
(s/fdef -to-millis-part :args ::to-millis-part-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1348
(s/def ::to-nanos-part-args (args))
(defn -to-nanos-part [this]
  (:nanos this))
(s/fdef -to-nanos-part :args ::to-nanos-part-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1377
(s/def ::truncated-to-args (args ::temporal-unit/temporal-unit))
(defn -truncated-to [this unit]
  (let [unit-dur (temporal-unit/get-duration unit)
        dur (delay (-to-nanos unit-dur))]
    (cond
      (and (= unit chrono-unit/SECONDS)
           (or (>= (:seconds this) 0)
               (== (:nanos this) 0)))
      (impl/->Duration (:seconds this) 0)

      (= unit chrono-unit/NANOS)
      this

      (> (-get-seconds unit-dur) SECONDS_PER_DAY)
      (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:duration this :unit unit}))

      (-> NANOS_PER_DAY (rem @dur) zero? not)
      (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:duration this :unit unit}))

      :else
      (let [nod (-> (:seconds this)
                    (rem SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result  (-> nod
                        (/ @dur)
                        long
                        (* @dur))]
        (-plus-nanos this (- result nod))))))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::duration)

(extend-type Duration
  duration/IDuration
  (is-zero [this] (-is-zero this))
  (is-negative [this] (-is-negative this))
  (get-seconds [this] (-get-seconds this))
  (get-nano [this] (-get-nano this))
  (with-seconds [this seconds] (-with-seconds this seconds))
  (with-nanos [this nano-of-second] (-with-nanos this nano-of-second))
  (plus
    ([this duration] (-plus this duration))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (plus-days [this days-to-add] (-plus-days this days-to-add))
  (plus-hours [this hours-to-add] (-plus-hours this hours-to-add))
  (plus-minutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plus-seconds [this seconds-to-add] (-plus-seconds this seconds-to-add))
  (plus-millis [this millis-to-add] (-plus-millis this millis-to-add))
  (plus-nanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minus
    ([this duration] (-minus this duration))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (minus-days [this days-to-subtract] (-minus-days this days-to-subtract))
  (minus-hours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minus-minutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minus-seconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minus-millis [this millis-to-subtract] (-minus-millis this millis-to-subtract))
  (minus-nanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (multiplied-by [this multiplicand] (-multiplied-by this multiplicand))
  (divided-by [this divisor] (-divided-by this divisor))
  (negated [this] (-negated this))
  (abs [this] (-abs this))
  (to-days [this] (-to-days this))
  (to-hours [this] (-to-hours this))
  (to-minutes [this] (-to-minutes this))
  (to-seconds [this] (-to-seconds this))
  (to-millis [this] (-to-millis this))
  (to-nanos [this] (-to-nanos this))
  (to-days-part [this] (-to-days-part this))
  (to-hours-part [this] (-to-hours-part this))
  (to-minutes-part [this] (-to-minutes-part this))
  (to-seconds-part [this] (-to-seconds-part this))
  (to-millis-part [this] (-to-millis-part this))
  (to-nanos-part [this] (-to-nanos-part this))
  (truncated-to [this unit] (-truncated-to this unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1408
(s/def ::compare-to-args (args ::duration))
(defn -compare-to [this other-duration]
  (let [cmp (compare (:seconds this) (:seconds other-duration))]
    (if-not (zero? cmp)
      cmp
      (- (:nanos this) (:nanos other-duration)))))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type Duration
  time-comparable/ITimeComparable
  (compare-to [this other-duration] (-compare-to this other-duration)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L546
(s/def ::get-args (args ::temporal-unit/temporal-unit))
(defn -get [this unit]
  (condp = unit
    SECONDS (:seconds this)
    NANOS (:nanos this)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit) {:duration this :unit unit}))))
(s/fdef -get :args ::get-args :ret ::j/long)

(def UNITS (delay [SECONDS NANOS]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L569
(s/def ::get-units-args (args))
(defn -get-units [this]
  @UNITS)
(s/fdef -get-units :args ::get-units-args :ret (s/coll-of #(satisfies? temporal-unit/ITemporalUnit %)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1100
(s/def ::add-to-args (args ::temporal/temporal))
(defn -add-to [this temporal]
  (cond-> temporal
    (not (zero? (:seconds this)))
    (temporal/plus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (temporal/plus (:nanos this) NANOS)))
(s/fdef -add-to :args ::add-to-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1135
(s/def ::subtract-from-args (args ::temporal/temporal))
(defn -subtract-from [this temporal]
  (cond-> temporal
    (not (zero? (:seconds this)))
    (temporal/minus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (temporal/minus (:nanos this) NANOS)))
(s/fdef -subtract-from :args ::subtract-from-args :ret ::temporal/temporal)

(extend-type Duration
  temporal-amount/ITemporalAmount
  (get [this unit] (-get this unit))
  (get-units [this] (-get-units this))
  (add-to [this temporal] (-add-to this temporal))
  (subtract-from [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L180
(s/def ::of-days-args (s/tuple ::j/long))
(defn of-days [days]
  (create (math/multiply-exact days SECONDS_PER_DAY) 0))
(s/fdef of-days :args ::of-days-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L195
(s/def ::of-hours-args (s/tuple ::j/long))
(defn of-hours [hours]
  (create (math/multiply-exact hours SECONDS_PER_HOUR) 0))
(s/fdef of-hours :args ::of-hours-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L210
(s/def ::of-minutes-args (s/tuple ::j/long))
(defn of-minutes [minutes]
  (create (math/multiply-exact minutes SECONDS_PER_MINUTE) 0))
(s/fdef of-minutes :args ::of-minutes-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L261
(s/def ::of-millis-args (s/tuple ::j/long))
(defn of-millis [millis]
  (let [mos (int (rem millis 1000))
        secs (cond-> (long (/ millis 1000)) (neg? mos) dec)
        mos (cond-> mos (neg? mos) (+ 1000))]
    (create secs (* mos 1000000))))
(s/fdef of-millis :args ::of-millis-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L280
(s/def ::of-nanos-args (s/tuple ::j/long))
(def of-nanos impl/of-nanos)
(s/fdef of-nanos :args ::of-nanos-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L309
(s/def ::of-args (s/tuple ::j/long ::temporal-unit/temporal-unit))
(defn of [amount unit]
  (-plus ZERO amount unit))
(s/fdef of :args ::of-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L334
(s/def ::from-args (s/tuple ::temporal-amount/temporal-amount))
(defn from [amount]
  (reduce #(-plus %1 (temporal-amount/get amount %2) %2)
          ZERO
          (temporal-amount/get-units amount)))
(s/fdef from :args ::from-args :ret ::duration)

(def PATTERN (delay (re-pattern (str "(?i)([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?"))))

(defn- duration-part [[[sign] n] & [suffix]]
  (when (and n (not (zero? n)))
    (str (str sign n) suffix)))

(s/def ::iso8601-duration
  #?(:cljs (s/and string? #(re-find @PATTERN %))
     :clj (s/with-gen (s/and string? #(re-find @PATTERN %))
            (fn []
              (let [sign #{"" "-" "+"}
                    signed-long (s/nilable (s/tuple (s/? sign) ::j/pos-long))
                    ->str (fn [[[sign] n]] (str sign n))]
                (clojure.test.check.generators/let
                    [prefix (s/gen sign)
                     days (s/gen signed-long)
                     hours (s/gen signed-long)
                     minutes (s/gen signed-long)
                     seconds (s/gen signed-long)
                     second-fraction (s/gen (s/nilable ::j/pos-int))]
                  (str prefix
                       "P"
                       (duration-part days "D")
                       (when (some #(some-> % ->str) [hours minutes seconds])
                         "T")
                       (duration-part hours "H")
                       (duration-part minutes "M")
                       (when (ffirst seconds)
                         (str (duration-part seconds)
                              (when second-fraction
                                (str "." second-fraction))
                              "S")))))))))

(defn num-digits [n]
  (count (str (Math/abs n))))

(defn second-fraction [n]
  (let [trailing-zeros (- 9 (num-digits n))]
   (* n (reduce * (repeat trailing-zeros 10)))))

(defn parse-fraction [s]
  (let [n (or (math/parse-int s) 0)
        leading-zeros (- (count s) (num-digits n))
        trailing-zeros (- 9 leading-zeros (num-digits n))]
    (try*
      (math/multiply-exact n (math/to-int-exact (reduce * (repeat trailing-zeros 10))))
      (catch :default e
        (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text s}))))))

(defn parse-number [multiplier s]
  (try*
   (math/multiply-exact (or (math/parse-long s) 0) multiplier)
   (catch :default e
     (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text s :multiplier multiplier})))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L388
(s/def ::parse-args (s/tuple ::iso8601-duration))
(defn parse [text]
  (let [matches (re-matches @PATTERN text)]
    (if-not matches
      (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text text}))
      (let [[_ prefix days T & [hours minutes seconds fraction :as more]] matches
            nums (cons days more)
            factor (if (= "-" prefix) -1 1)
            multiplicands [(partial parse-number SECONDS_PER_DAY)
                           (partial parse-number SECONDS_PER_HOUR)
                           (partial parse-number SECONDS_PER_MINUTE)
                           (partial parse-number 1)
                           (partial parse-fraction)]
            [d h m s f :as parts] (map #(%1 %2) multiplicands nums)]
        (when (or (and T (not (some some? more)))
                  (= d h m s f 0))
          (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text text :error-index 0})))
        (create (neg? factor) d h m s (if (neg? s) (- f) f))))))
(s/fdef parse :args ::parse-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L486
(s/def ::between-args (s/tuple ::temporal/temporal ::temporal/temporal))
(defn between [start-inclusive end-exclusive]
  (try*
   (-> start-inclusive (temporal/until end-exclusive NANOS) of-nanos)
   (catch :default e
     (let [secs (-> start-inclusive (temporal/until end-exclusive SECONDS))
           [secs nanos] (try*
                         (let [n (- (temporal-accessor/get-long end-exclusive NANO_OF_SECOND)
                                    (temporal-accessor/get-long start-inclusive NANO_OF_SECOND))
                               s (cond-> secs
                                   (and (pos? secs) (neg? n))
                                   inc
                                   (and (neg? secs) (pos? n))
                                   dec)]
                           [s n])
                         (catch :default e [secs 0]))]
       (of-seconds secs nanos)))))
(s/fdef between :args ::between-args :ret ::duration)

#?(:clj
   (defmethod conversion/jiffy->java Duration [{:keys [seconds nanos]}]
     (.withNanos (java.time.Duration/ofSeconds seconds) nanos)))

#?(:clj
   (defmethod conversion/same? Duration
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:seconds :nanos])
        (map #(% java-object) [(memfn getSeconds) (memfn getNano)]))))
