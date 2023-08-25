(ns jiffy.clock
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.instant-2-impl :as instant-impl]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.specs :as j])
  #?(:clj (:import [jiffy.zone_offset_impl ZoneOffset])))

(s/def ::clock ::clock/clock)


;; Clock.SystemClock

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L481

(defrecord SystemClock [zone])

(s/def ::create-system-clock-args (s/tuple ::zone-id/zone-id))
(defn create-system-clock [zone-id] (->SystemClock zone-id))
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
            (instant-impl/create (.getEpochSecond java-instant)
                                 (.getNano java-instant)))
     :cljs (instant-impl/of-epoch-milli (-millis-system-clock this))))
(s/fdef -instant-system-clock :args ::instant-system-clock-args :ret ::instant/instant)

(extend-type SystemClock
  clock/IClock
  (get-zone [this] (-get-zone-system-clock this))
  (with-zone [this zone] (-with-zone-system-clock this zone))
  (millis [this] (-millis-system-clock this))
  (instant [this] (-instant-system-clock this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L485
;; TODO: use ZoneOffset/UTC
(def UTC (->SystemClock zone-offset/UTC))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L160
(def system-utc (constantly UTC))
(s/fdef system-utc :ret ::clock)


;; Clock.FixedClock

;; https://github.com/unofficial-openjdk/openjdk/blob/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L588

(defrecord FixedClock [instant zone])

(s/def ::create-fixed-clock-args (s/tuple ::instant/instant ::zone-id/zone-id))
(defn create-fixed-clock [instant zone] (->FixedClock instant zone))
(s/def ::fixed-clock (j/constructor-spec FixedClock create-fixed-clock ::create-fixed-clock-args))
(s/fdef create-fixed-clock :args ::create-fixed-clock-args :ret ::clock)

(defmacro fc-args [& x] `(s/tuple ::fixed-clock ~@x))

(s/def ::get-zone-fixed-clock-args (fc-args))
(defn -get-zone-fixed-clock [this] (:zone this))
(s/fdef -get-zone-fixed-clock :args ::get-zone-fixed-clock-args :ret ::zone-id/zone-id)

(s/def ::with-zone-fixed-clock-args (fc-args ::zone-id/zone-id))
(defn -with-zone-fixed-clock [this zone]
  (if (= (:zone this) zone)
    this
    (->FixedClock (:instant this) zone)))
(s/fdef -with-zone-fixed-clock :args ::with-zone-fixed-clock-args :ret ::fixed-clock)

(s/def ::millis-fixed-clock-args (fc-args))
(defn -millis-fixed-clock [this] (instant/to-epoch-milli this))
(s/fdef -millis-fixed-clock :args ::millis-fixed-clock-args :ret ::j/long)

(s/def ::instant-fixed-clock-args (fc-args))
(defn -instant-fixed-clock [this] (:instant this))
(s/fdef -instant-fixed-clock :args ::instant-fixed-clock-args :ret ::instant/instant)

(extend-type FixedClock
  clock/IClock
  (get-zone [this] (-get-zone-fixed-clock this))
  (with-zone [this zone] (-with-zone-fixed-clock this zone))
  (millis [this] (-millis-fixed-clock this))
  (instant [this] (-instant-fixed-clock this)))


;; (abstract) Clock


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
(defn fixed [fixed-instant zone] (create-fixed-clock fixed-instant zone))
(s/fdef fixed :args ::fixed-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L374
(s/def ::offset-args (s/tuple ::clock ::duration/duration))
(defn offset [base-clock offset-duration] (wip ::offset))
(s/fdef offset :args ::offset-args :ret ::clock)
