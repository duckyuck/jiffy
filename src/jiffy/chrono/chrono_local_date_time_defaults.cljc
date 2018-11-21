(ns jiffy.chrono.chrono-local-date-time-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.instant-impl :as Instant]))

(s/def ::chrono-local-date-time #(= % ::wip))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L192
(s/def ::get-chronology-args ::j/wip)
(defn -get-chronology [this] (wip ::-get-chronology))
;; TODO: fix cyclic dependency
;; (s/fdef -get-chronology :args ::get-chronology-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L404
(s/def ::format-args ::j/wip)
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L452
(s/def ::to-instant-args ::j/wip)
(defn -to-instant [this offset] (wip ::-to-instant))
;; TODO: fix cyclic dependency
;; (s/fdef -to-instant :args ::to-instant-args :ret ::Instant/instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L470
(s/def ::to-epoch-second-args ::j/wip)
(defn -to-epoch-second [this offset] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L531
(s/def ::is-after-args ::j/wip)
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L552
(s/def ::is-before-args ::j/wip)
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L573
(s/def ::is-equal-args ::j/wip)
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)
