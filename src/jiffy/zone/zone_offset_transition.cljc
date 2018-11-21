(ns jiffy.zone.zone-offset-transition
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as Duration]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date-time :as LocalDateTime]
            [jiffy.specs :as j]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-offset :as ZoneOffset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java
(defprotocol IZoneOffsetTransition
  (getInstant [this])
  (toEpochSecond [this])
  (getDateTimeBefore [this])
  (getDateTimeAfter [this])
  (getOffsetBefore [this])
  (getOffsetAfter [this])
  (getDuration [this])
  (isGap [this])
  (isOverlap [this])
  (isValidOffset [this offset])
  (getValidOffsets [this]))

(defrecord ZoneOffsetTransition [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-offset-transition (j/constructor-spec ZoneOffsetTransition create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-offset-transition)

(defmacro args [& x] `(s/tuple ::zone-offset-transition ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L253
(s/def ::get-instant-args (args))
(defn -get-instant [this] (wip ::-get-instant))
(s/fdef -get-instant :args ::get-instant-args :ret ::Instant/instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L262
(s/def ::to-epoch-second-args (args))
(defn -to-epoch-second [this] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L279
(s/def ::get-date-time-before-args (args))
(defn -get-date-time-before [this] (wip ::-get-date-time-before))
(s/fdef -get-date-time-before :args ::get-date-time-before-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L293
(s/def ::get-date-time-after-args (args))
(defn -get-date-time-after [this] (wip ::-get-date-time-after))
(s/fdef -get-date-time-after :args ::get-date-time-after-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L304
(s/def ::get-offset-before-args (args))
(defn -get-offset-before [this] (wip ::-get-offset-before))
(s/fdef -get-offset-before :args ::get-offset-before-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L315
(s/def ::get-offset-after-args (args))
(defn -get-offset-after [this] (wip ::-get-offset-after))
(s/fdef -get-offset-after :args ::get-offset-after-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L328
(s/def ::get-duration-args (args))
(defn -get-duration [this] (wip ::-get-duration))
(s/fdef -get-duration :args ::get-duration-args :ret ::Duration/duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L350
(s/def ::is-gap-args (args))
(defn -is-gap [this] (wip ::-is-gap))
(s/fdef -is-gap :args ::is-gap-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L363
(s/def ::is-overlap-args (args))
(defn -is-overlap [this] (wip ::-is-overlap))
(s/fdef -is-overlap :args ::is-overlap-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L377
(s/def ::is-valid-offset-args (args ::ZoneOffset/zone-offset))
(defn -is-valid-offset [this offset] (wip ::-is-valid-offset))
(s/fdef -is-valid-offset :args ::is-valid-offset-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L388
(s/def ::get-valid-offsets-args (args))
(defn -get-valid-offsets [this] (wip ::-get-valid-offsets))
(s/fdef -get-valid-offsets :args ::get-valid-offsets-args :ret ::j/wip)

(extend-type ZoneOffsetTransition
  IZoneOffsetTransition
  (getInstant [this] (-get-instant this))
  (toEpochSecond [this] (-to-epoch-second this))
  (getDateTimeBefore [this] (-get-date-time-before this))
  (getDateTimeAfter [this] (-get-date-time-after this))
  (getOffsetBefore [this] (-get-offset-before this))
  (getOffsetAfter [this] (-get-offset-after this))
  (getDuration [this] (-get-duration this))
  (isGap [this] (-is-gap this))
  (isOverlap [this] (-is-overlap this))
  (isValidOffset [this offset] (-is-valid-offset this offset))
  (getValidOffsets [this] (-get-valid-offsets this)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L406
(s/def ::compare-to-args (args ::j/wip))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type ZoneOffsetTransition
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L138
(s/def ::of-args (args ::LocalDateTime/local-date-time ::ZoneOffset/zone-offset ::ZoneOffset/zone-offset))
(defn of [transition offset-before offset-after] (wip ::of))
(s/fdef of :args ::of-args :ret ::zone-offset-transition)
