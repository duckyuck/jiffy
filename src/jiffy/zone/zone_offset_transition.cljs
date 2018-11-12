(ns jiffy.zone.zone-offset-transition
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]))

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L253
(defn -get-instant [this] (wip ::-get-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L262
(defn -to-epoch-second [this] (wip ::-to-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L279
(defn -get-date-time-before [this] (wip ::-get-date-time-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L293
(defn -get-date-time-after [this] (wip ::-get-date-time-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L304
(defn -get-offset-before [this] (wip ::-get-offset-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L315
(defn -get-offset-after [this] (wip ::-get-offset-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L328
(defn -get-duration [this] (wip ::-get-duration))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L350
(defn -is-gap [this] (wip ::-is-gap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L363
(defn -is-overlap [this] (wip ::-is-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L377
(defn -is-valid-offset [this offset] (wip ::-is-valid-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L388
(defn -get-valid-offsets [this] (wip ::-get-valid-offsets))

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
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type ZoneOffsetTransition
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L138
(defn of [transition offset-before offset-after] (wip ::of))
