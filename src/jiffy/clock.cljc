(ns jiffy.clock
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :as duration]
            [jiffy.instant-impl :as instant]
            [jiffy.specs :as j]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java
(defprotocol IClock
  (get-zone [this])
  (with-zone [this zone])
  (millis [this])
  (instant [this]))

(s/def ::clock #(satisfies? IClock %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L183
(defn system-default-zone [] (wip ::system-default-zone))
(s/fdef system-default-zone :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L202
(s/def ::system-args (s/tuple ::zone-id/zone-id))
(defn system [zone] (wip ::system))
(s/fdef system :args ::system-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L231
(s/def ::tick-millis-args (s/tuple ::zone-id/zone-id))
(defn tick-millis [zone] (wip ::tick-millis))
(s/fdef tick-millis :args ::tick-millis-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L255
(s/def ::tick-seconds-args (s/tuple ::zone-id/zone-id))
(defn tick-seconds [zone] (wip ::tick-seconds))
(s/fdef tick-seconds :args ::tick-seconds-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L278
(s/def ::tick-minutes-args (s/tuple ::zone-id/zone-id))
(defn tick-minutes [zone] (wip ::tick-minutes))
(s/fdef tick-minutes :args ::tick-minutes-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L313
(s/def ::tick-args (s/tuple ::clock ::duration/duration))
(defn tick [base-clock tick-duration] (wip ::tick))
(s/fdef tick :args ::tick-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L348
(s/def ::fixed-args (s/tuple ::instant/instant ::zone-id/zone-id))
(defn fixed [fixed-instant zone] (wip ::fixed))
(s/fdef fixed :args ::fixed-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L374
(s/def ::offset-args (s/tuple ::clock ::duration/duration))
(defn offset [base-clock offset-duration] (wip ::offset))
(s/fdef offset :args ::offset-args :ret ::clock)


;; Clock.SystemClock

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L481

(defrecord SystemClock [zone])

(s/def ::create-system-clock-args empty?)
(defn create-system-clock [] (->SystemClock nil))
(s/def ::system-clock (j/constructor-spec SystemClock create-system-clock ::create-system-clock-args))
(s/fdef create-system-clock :args ::create-system-clock-args :ret ::clock)

(defmacro sc-args [& x] `(s/tuple ::system-clock ~@x))

(s/def ::get-zone-system-clock-args (sc-args))
(defn -get-zone-system-clock [this] (:zone this))
(s/fdef -get-zone-system-clock :args ::get-zone-system-clock-args :ret ::zone-id/zone-id)

(s/def ::with-zone-system-clock-args (sc-args ::zone-id/zone-id))
(defn -with-zone-system-clock [this zone]
  (if (= (:zone this) zone)
    this
    (->SystemClock zone)))
(s/fdef -with-zone-system-clock :args ::with-zone-system-clock-args :ret ::system-clock)

(s/def ::millis-system-clock-args (sc-args))
(defn -millis-system-clock [this]
  #?(:clj (java.lang.System/currentTimeMillis)
     :cljs (.getTime (js/Date.))))
(s/fdef -millis-system-clock :args ::millis-system-clock-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L444
(s/def ::instant-system-clock-args (sc-args))
(defn -instant-system-clock [this]
  #?(:clj (let [java-instant (.instant (java.time.Clock/systemUTC))]
            (instant/create (.getEpochSecond java-instant)
                            (.getNano java-instant)))
     :cljs (instant/of-epoch-milli (-millis-system-clock this))))
(s/fdef -instant-system-clock :args ::instant-system-clock-args :ret ::instant/instant)

(extend-type SystemClock
  IClock
  (get-zone [this] (-get-zone-system-clock this))
  (with-zone [this zone] (-with-zone-system-clock this zone))
  (millis [this] (-millis-system-clock this))
  (instant [this] (-instant-system-clock this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L485
;; TODO: use ZoneOffset/UTC
(def UTC (->SystemClock nil
                        ;; (ZoneOffset/UTC)
                        ))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L160
(def system-utc (constantly UTC))
(s/fdef system-utc :ret ::clock)
