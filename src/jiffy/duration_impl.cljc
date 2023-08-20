(ns jiffy.duration-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.math.big-decimal :as big-decimal]
            [jiffy.math.big-integer :as big-integer]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException]]
            [jiffy.local-time-constants :refer [MINUTES_PER_HOUR NANOS_PER_DAY NANOS_PER_MILLI NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(defrecord Duration [seconds nanos])

(def ZERO (->Duration 0 0))
(def BI_NANOS_PER_SECOND (big-integer/value-of NANOS_PER_SECOND))

(declare of-seconds -negated)

(s/def ::create-args (s/tuple pos-int? ::j/nano-of-second))
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
       (-negated (of-seconds seconds nanos))
       (of-seconds seconds nanos)))))
(s/def ::duration (j/constructor-spec Duration create ::create-args))
(s/fdef create :args ::create-args :ret ::duration)

(defmacro args [& x] `(s/tuple ::duration ~@x))

(defn to-big-decimal-seconds [this]
  (big-decimal/add (big-decimal/value-of (:seconds this))
                   (big-decimal/value-of (:nanos this) 9)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L970
(s/def ::multiplied-by-args (args ::j/long))
(defn -multiplied-by [this multiplicand]
  (condp = multiplicand
    0 ZERO
    1 this
    (create (big-decimal/multiply (to-big-decimal-seconds this) (big-decimal/value-of multiplicand)))))
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1055
(s/def ::negated-args (args))
(defn -negated [this]
  (-multiplied-by this -1))
(s/fdef -negated :args ::negated-args  :ret ::duration)

(s/def ::of-seconds-args (s/cat :seconds ::j/long :nano-adjustment (s/? ::j/long)))
(defn of-seconds
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L223
  ([seconds]
   (create seconds 0))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L246
  ([seconds nano-adjustment]
   (create (math/add-exact seconds (math/floor-div nano-adjustment NANOS_PER_SECOND))
           (int (math/floor-mod nano-adjustment NANOS_PER_SECOND)))))
(s/fdef of-seconds :args ::of-seconds-args :ret ::duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L280
(defn of-nanos [nanos]
  (let [nos (int (rem nanos NANOS_PER_SECOND))
        secs (cond-> (long (/ nanos NANOS_PER_SECOND)) (neg? nos) dec)
        nos (cond-> nos (neg? nos) (+ NANOS_PER_SECOND))]
    (create secs nos)))
