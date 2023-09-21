(ns jiffy.clock
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.protocols.zone-id :as ZoneId]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.specs :as j])
  #?(:clj (:import [jiffy.zone_offset_impl ZoneOffset])))

(def-record SystemClock ::system-clock [zone ::ZoneId/zone-id])

(s/def ::clock ::clock/clock)

(def-constructor create-system-clock ::clock
  [zone-id ::ZoneId/zone-id]
  (->SystemClock zone-id))

(def-method -get-zone-system-clock  ::ZoneId/zone-id
  [this ::system-clock]
  (:zone this))

(def-method -with-zone-system-clock ::system-clock
  [this ::system-clock
   zone ::ZoneId/zone-id]
  (if (= (:zone this) zone)
    this
    (->SystemClock zone)))

(def-method -millis-system-clock ::j/long
  [this ::system-clock]
  #?(:clj (java.lang.System/currentTimeMillis)
     :cljs (.getTime (js/Date.))))

(def-method -instant-system-clock ::instant/instant
  [this ::system-clock]
  #?(:clj (let [java-instant (.instant (java.time.Clock/systemUTC))]
            (instant-impl/create (.getEpochSecond java-instant)
                                 (.getNano java-instant)))
     :cljs (instant-impl/of-epoch-milli (-millis-system-clock this))))

(extend-type SystemClock
  clock/IClock
  (get-zone [this] (-get-zone-system-clock this))
  (with-zone [this zone] (-with-zone-system-clock this zone))
  (millis [this] (-millis-system-clock this))
  (instant [this] (-instant-system-clock this)))

(def UTC (->SystemClock zone-offset/UTC))

(def-constructor system-utc ::clock
  []
  UTC)


(def-record FixedClock ::fixed-clock [instant ::instant/instant zone ::ZoneId/zone-id])

(def-constructor create-fixed-clock ::clock
  [instant ::instant/instant
   zone ::ZoneId/zone-id]
  (->FixedClock instant zone))

(defmacro fc-args [& x] `(s/tuple ::fixed-clock ~@x))

(def-method -get-zone-fixed-clock ::ZoneId/zone-id
  [this ::fixed-clock]
  (:zone this))

(def-method -with-zone-fixed-clock ::fixed-clock
  [this ::fixed-clock
   zone ::ZoneId/zone-id]
  (if (= (:zone this) zone)
    this
    (->FixedClock (:instant this) zone)))

(def-method -millis-fixed-clock ::j/long
  [this ::fixed-clock]
  (instant/to-epoch-milli this))

(def-method -instant-fixed-clock ::instant/instant
  [this ::fixed-clock]
  (:instant this))

(extend-type FixedClock
  clock/IClock
  (get-zone [this] (-get-zone-fixed-clock this))
  (with-zone [this zone] (-with-zone-fixed-clock this zone))
  (millis [this] (-millis-fixed-clock this))
  (instant [this] (-instant-fixed-clock this)))


(def-constructor system-default-zone ::clock
  []
  (create-system-clock (zone-id/system-default)))

(def-constructor system ::clock
  [zone ::ZoneId/zone-id]
  (create-system-clock zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L231
(def-constructor tick-millis ::clock
  [zone ::ZoneId/zone-id]
  (wip ::tick-millis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L255
(def-constructor tick-seconds ::clock
  [zone ::ZoneId/zone-id]
  (wip ::tick-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L278
(def-constructor tick-minutes ::clock
  [zone ::ZoneId/zone-id]
  (wip ::tick-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L313
(def-constructor tick ::clock
  [base-clock ::clock
   tick-duration ::duration/duration]
  (wip ::tick))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L348
(def-constructor fixed ::clock
  [fixed-instant ::instant/instant
   zone ::ZoneId/zone-id]
  (create-fixed-clock fixed-instant zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Clock.java#L374
(def-constructor offset ::clock
  [base-clock ::clock
   offset-duration ::duration/duration]
  (wip ::offset))
