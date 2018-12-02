(ns jiffy.duration-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.big-decimal :as big-decimal]
            [jiffy.big-integer :as big-integer]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException]]
            [jiffy.local-time-constants :refer [MINUTES_PER_HOUR NANOS_PER_DAY NANOS_PER_MILLI NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(defrecord Duration [seconds nanos])

(def ZERO (->Duration 0 0))
(def BI_NANOS_PER_SECOND (big-integer/value-of NANOS_PER_SECOND))

(declare of-seconds)

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
     (->Duration seconds nano-adjustment))))
(s/def ::duration (j/constructor-spec Duration create ::create-args))
(s/fdef create :args ::create-args :ret ::duration)

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
  (let [nos (int (mod nanos NANOS_PER_SECOND))
        secs (cond-> (long (/ nanos NANOS_PER_SECOND)) (neg? nos) dec)
        nos (cond-> nos (neg? nos) (+ NANOS_PER_SECOND))]
    (create secs nos)))
