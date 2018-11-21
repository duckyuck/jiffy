(ns jiffy.clock
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :as Duration]
            [jiffy.instant-impl :as Instant]
            [jiffy.specs :as j]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java
(defprotocol IClock
  (getZone [this])
  (withZone [this zone])
  (millis [this])
  (instant [this]))

(defrecord Clock [])

(s/def ::create-clock-args ::j/wip)
(defn create-clock [])
(s/def ::clock (j/constructor-spec Clock create-clock ::create-clock-args))
(s/fdef create-clock :args ::create-clock-args :ret ::clock)

(defmacro clock-args [& x] `(s/tuple ::clock ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L399
(s/def ::get-zone-clock-args (clock-args))
(defn -get-zone-clock [this] (wip ::-get-zone-clock))
(s/fdef -get-zone-clock :args ::get-zone-clock-args :ret ::ZoneId/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L411
(s/def ::with-zone-clock-args (clock-args ::ZoneId/zone-id))
(defn -with-zone-clock [this zone] (wip ::-with-zone-clock))
(s/fdef -with-zone-clock :args ::with-zone-clock-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L431
(s/def ::millis-clock-args (clock-args))
(defn -millis-clock [this] (wip ::-millis-clock))
(s/fdef -millis-clock :args ::millis-clock-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L444
(s/def ::instant-clock-args (clock-args))
(defn -instant-clock [this] (wip ::-instant-clock))
(s/fdef -instant-clock :args ::instant-clock-args :ret ::Instant/instant)

(extend-type Clock
  IClock
  (getZone [this] (-get-zone-clock this))
  (withZone [this zone] (-with-zone-clock this zone))
  (millis [this] (-millis-clock this))
  (instant [this] (-instant-clock this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L183
(defn systemDefaultZone [] (wip ::systemDefaultZone))
(s/fdef systemDefaultZone :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L202
(s/def ::system-args (s/tuple ::ZoneId/zone-id))
(defn system [zone] (wip ::system))
(s/fdef system :args ::system-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L231
(s/def ::tick-millis-args (s/tuple ::ZoneId/zone-id))
(defn tickMillis [zone] (wip ::tickMillis))
(s/fdef tickMillis :args ::tick-millis-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L255
(s/def ::tick-seconds-args (s/tuple ::ZoneId/zone-id))
(defn tickSeconds [zone] (wip ::tickSeconds))
(s/fdef tickSeconds :args ::tick-seconds-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L278
(s/def ::tick-minutes-args (s/tuple ::ZoneId/zone-id))
(defn tickMinutes [zone] (wip ::tickMinutes))
(s/fdef tickMinutes :args ::tick-minutes-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L313
(s/def ::tick-args (s/tuple ::clock ::Duration/duration))
(defn tick [base-clock tick-duration] (wip ::tick))
(s/fdef tick :args ::tick-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L348
(s/def ::fixed-args (s/tuple ::Instant/instant ::ZoneId/zone-id))
(defn fixed [fixed-instant zone] (wip ::fixed))
(s/fdef fixed :args ::fixed-args :ret ::clock)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L374
(s/def ::offset-args (s/tuple ::clock ::Duration/duration))
(defn offset [base-clock offset-duration] (wip ::offset))
(s/fdef offset :args ::offset-args :ret ::clock)


;; Clock.SystemClock

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L481

(defrecord SystemClock [zone])

(s/def ::create-system-clock-args ::j/wip)
(defn create-system-clock [])
(s/def ::system-clock (j/constructor-spec SystemClock create-system-clock ::create-system-clock-args))
(s/fdef create-system-clock :args ::create-system-clock-args :ret ::clock)

(defmacro sc-args [& x] `(s/tuple ::system-clock ~@x))

(s/def ::get-zone-system-clock-args (sc-args))
(defn -get-zone-system-clock [this] (:zone this))
(s/fdef -get-zone-system-clock :args ::get-zone-system-clock-args :ret ::ZoneId/zone-id)

(s/def ::with-zone-system-clock-args (sc-args ::ZoneId/zone-id))
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
  (Instant/ofEpochMilli (-millis-system-clock this)))
(s/fdef -instant-system-clock :args ::instant-system-clock-args :ret ::Instant/instant)

(extend-type SystemClock
  IClock
  (getZone [this] (-get-zone-system-clock this))
  (withZone [this zone] (-with-zone-system-clock this zone))
  (millis [this] (-millis-system-clock this))
  (instant [this] (-instant-system-clock this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L485
;; TODO: use ZoneOffset/UTC
(def UTC (->SystemClock nil
                        ;; (ZoneOffset/UTC)
                        ))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L160
(def systemUTC (constantly UTC))
(s/fdef systemUTC :ret ::clock)
