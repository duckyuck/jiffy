(ns jiffy.instant-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.conversion :refer [jiffy->java same?]]
            [jiffy.exception :refer [DateTimeException ex]]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(defrecord Instant [seconds nanos])

(def EPOCH (->Instant 0 0))
(def MAX_SECOND 31556889864403199)
(def MIN_SECOND -31557014167219200)

(s/def ::create-params (s/tuple ::j/second ::j/nano))
(defn create [seconds nano-of-second]
  (cond
    (zero? (bit-or seconds nano-of-second))
    EPOCH

    (or (< seconds MIN_SECOND) (> seconds MAX_SECOND))
    (throw (ex DateTimeException
               "Instant exceeds minimum or maximum instant"
               {:max-second MAX_SECOND
                :min-second MIN_SECOND
                :seconds seconds}))

    :else
    (->Instant seconds nano-of-second)))

(s/def ::instant (j/constructor-spec Instant create ::create-params))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L343
(defn ofEpochMilli [epoch-milli]
  (create (math/floor-div epoch-milli 1000)
          (int (* (math/floor-mod epoch-milli 1000)
                  1000000))))

(defmethod jiffy->java Instant [{:keys [seconds nanos]}]
  (.plusNanos (java.time.Instant/ofEpochSecond seconds) nanos))

(defmethod same? Instant
  [jiffy-object java-object]
  (= (map #(% jiffy-object) [:seconds :nanos])
     (map #(% java-object) [(memfn getEpochSecond) (memfn getNano)])))
