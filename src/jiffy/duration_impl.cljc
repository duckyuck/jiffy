(ns jiffy.duration-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.math.big-decimal :as big-decimal]
            [jiffy.math.big-integer :as big-integer]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException]]
            [jiffy.local-time-constants :refer [MINUTES_PER_HOUR NANOS_PER_DAY NANOS_PER_MILLI NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(def-record Duration ::duration
  [seconds ::j/pos-long
   nanos ::j/nano-of-second])

(def ZERO (->Duration 0 0))
(def BI_NANOS_PER_SECOND (big-integer/value-of NANOS_PER_SECOND))

(declare of-seconds negated)

(defn create
  ([big-decimal-seconds]
   (let [nanos (-> big-decimal-seconds
                   (big-decimal/move-point-right 9)
                   (big-decimal/to-big-integer-exact))
         [div rem] (big-integer/divide-and-reminder nanos BI_NANOS_PER_SECOND)]
     (when (> (big-integer/bit-length div) 63)
       (throw (ex JavaArithmeticException (str "Exceeds capacity of Duration: " nanos)
                  {:big-decimal-seconds big-decimal-seconds :nanos nanos})))
     (of-seconds (big-integer/long-value div)
                 (big-integer/int-value rem))))
  ([seconds nano-adjustment]
   (if (zero? (bit-or seconds nano-adjustment))
     ZERO
     (->Duration seconds nano-adjustment)))
  ([negate days-as-secs hours-as-secs mins-as-secs secs nanos]
   (let [seconds (math/add-exact days-as-secs (math/add-exact hours-as-secs (math/add-exact mins-as-secs secs)))]
     (if negate
       (negated (of-seconds seconds nanos))
       (of-seconds seconds nanos)))))

(defn to-big-decimal-seconds [this]
  (big-decimal/add (big-decimal/value-of (:seconds this))
                   (big-decimal/value-of (:nanos this) 9)))

(def-method multiplied-by ::duration
  [this ::duration
   multiplicand ::j/long]
  (condp = multiplicand
    0 ZERO
    1 this
    (create (big-decimal/multiply (to-big-decimal-seconds this) (big-decimal/value-of multiplicand)))))

(def-method negated ::duration
  [this ::duration]
  (multiplied-by this -1))

(def-constructor of-seconds ::duration
  ([seconds ::j/long]
   (create seconds 0))

  ([seconds ::j/long
    nano-adjustment ::j/long]
   (create (math/add-exact seconds (math/floor-div nano-adjustment NANOS_PER_SECOND))
           (int (math/floor-mod nano-adjustment NANOS_PER_SECOND)))))

(def-constructor of-nanos ::duration
  [nanos ::j/long]
  (let [nos (int (rem nanos NANOS_PER_SECOND))
        secs (cond-> (long (/ nanos NANOS_PER_SECOND)) (neg? nos) dec)
        nos (cond-> nos (neg? nos) (+ NANOS_PER_SECOND))]
    (create secs nos)))
