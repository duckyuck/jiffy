(ns jiffy.clock
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.instant-impl :as Instant]
            [jiffy.zone-offset :as ZoneOffset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java
(defprotocol IClock
  (getZone [this])
  (withZone [this zone])
  (millis [this])
  (instant [this]))

(defrecord Clock [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L399
(defn -get-zone-clock [this] (wip ::-get-zone-clock))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L411
(defn -with-zone-clock [this zone] (wip ::-with-zone-clock))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L431
(defn -millis-clock [this] (wip ::-millis-clock))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L444
(defn -instant-clock [this] (wip ::-instant-clock))

(extend-type Clock
  IClock
  (getZone [this] (-get-zone-clock this))
  (withZone [this zone] (-with-zone-clock this zone))
  (millis [this] (-millis-clock this))
  (instant [this] (-instant-clock this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L183
(defn systemDefaultZone [] (wip ::systemDefaultZone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L202
(defn system [zone] (wip ::system))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L231
(defn tickMillis [zone] (wip ::tickMillis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L255
(defn tickSeconds [zone] (wip ::tickSeconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L278
(defn tickMinutes [zone] (wip ::tickMinutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L313
(defn tick [base-clock tick-duration] (wip ::tick))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L348
(defn fixed [fixed-instant-clock zone] (wip ::fixed))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L374
(defn offset [base-clock offset-duration] (wip ::offset))


;; Clock.SystemClock

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L481

(defrecord SystemClock [zone])

(def -get-zone-system-clock :zone)

(defn -with-zone-system-clock [this zone]
  (if (= (:zone this) zone)
    this
    (->SystemClock zone)))

(defn -millis-system-clock [this]
  #?(:clj (java.lang.System/currentTimeMillis)
     :cljs (.getTime (js/Date.))))

(defn -instant-system-clock [this]
  (Instant/ofEpochMilli (-millis-system-clock this)))

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
