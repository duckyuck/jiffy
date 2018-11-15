(ns jiffy.zone.zone-offset-transition-rule
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java
(defprotocol IZoneOffsetTransitionRule
  (getMonth [this])
  (getDayOfMonthIndicator [this])
  (getDayOfWeek [this])
  (getLocalTime [this])
  (isMidnightEndOfDay [this])
  (getTimeDefinition [this])
  (getStandardOffset [this])
  (getOffsetBefore [this])
  (getOffsetAfter [this])
  (createTransition [this year]))

(defrecord ZoneOffsetTransitionRule [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L374
(defn -get-month [this] (wip ::-get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L396
(defn -get-day-of-month-indicator [this] (wip ::-get-day-of-month-indicator))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L412
(defn -get-day-of-week [this] (wip ::-get-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L424
(defn -get-local-time [this] (wip ::-get-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L435
(defn -is-midnight-end-of-day [this] (wip ::-is-midnight-end-of-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L447
(defn -get-time-definition [this] (wip ::-get-time-definition))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L456
(defn -get-standard-offset [this] (wip ::-get-standard-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L465
(defn -get-offset-before [this] (wip ::-get-offset-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L474
(defn -get-offset-after [this] (wip ::-get-offset-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L487
(defn -create-transition [this year] (wip ::-create-transition))

(extend-type ZoneOffsetTransitionRule
  IZoneOffsetTransitionRule
  (getMonth [this] (-get-month this))
  (getDayOfMonthIndicator [this] (-get-day-of-month-indicator this))
  (getDayOfWeek [this] (-get-day-of-week this))
  (getLocalTime [this] (-get-local-time this))
  (isMidnightEndOfDay [this] (-is-midnight-end-of-day this))
  (getTimeDefinition [this] (-get-time-definition this))
  (getStandardOffset [this] (-get-standard-offset this))
  (getOffsetBefore [this] (-get-offset-before this))
  (getOffsetAfter [this] (-get-offset-after this))
  (createTransition [this year] (-create-transition this year)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L172
(defn of [month day-of-month-indicator day-of-week time time-end-of-day time-defnition standard-offset offset-before offset-after] (wip ::of))
