(ns jiffy.chrono.chrono-zoned-date-time-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.chrono.chronology :as chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.instant-impl :as instant]
            [jiffy.local-time :as local-time]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L214
(s/def ::to-epoch-second-args ::j/wip)
(defn -to-epoch-second [this] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L230
(s/def ::to-local-date-args ::j/wip)
(defn -to-local-date [this] (wip ::-to-local-date))
(s/fdef -to-local-date :args ::to-local-date-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L242
(s/def ::to-local-time-args ::j/wip)
(defn -to-local-time [this] (wip ::-to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::local-time/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L264
(s/def ::get-chronology-args ::j/wip)
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L521
(s/def ::format-args ::j/wip)
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L537
(s/def ::to-instant-args ::j/wip)
(defn -to-instant [this] (wip ::-to-instant))
(s/fdef -to-instant :args ::to-instant-args :ret ::instant/instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L606
(s/def ::is-before-args ::j/wip)
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L626
(s/def ::is-after-args ::j/wip)
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoZonedDateTime.java#L646
(s/def ::is-equal-args ::j/wip)
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)
