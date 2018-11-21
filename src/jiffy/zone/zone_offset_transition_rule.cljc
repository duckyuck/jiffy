(ns jiffy.zone.zone-offset-transition-rule
  (:require [clojure.spec.alpha :as s]
            [jiffy.day-of-week :as DayOfWeek]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time :as LocalTime]
            [jiffy.month :as Month]
            [jiffy.specs :as j]
            [jiffy.zone-offset :as ZoneOffset]
            [jiffy.zone.zone-offset-transition :as ZoneOffsetTransition]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-offset-transition-rule (j/constructor-spec ZoneOffsetTransitionRule create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-offset-transition-rule)

(defmacro args [& x] `(s/tuple ::zone-offset-transition-rule ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L374
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L396
(s/def ::get-day-of-month-indicator-args (args))
(defn -get-day-of-month-indicator [this] (wip ::-get-day-of-month-indicator))
(s/fdef -get-day-of-month-indicator :args ::get-day-of-month-indicator-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L412
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::DayOfWeek/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L424
(s/def ::get-local-time-args (args))
(defn -get-local-time [this] (wip ::-get-local-time))
(s/fdef -get-local-time :args ::get-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L435
(s/def ::is-midnight-end-of-day-args (args))
(defn -is-midnight-end-of-day [this] (wip ::-is-midnight-end-of-day))
(s/fdef -is-midnight-end-of-day :args ::is-midnight-end-of-day-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L447
(s/def ::get-time-definition-args (args))
(defn -get-time-definition [this] (wip ::-get-time-definition))
(s/fdef -get-time-definition :args ::get-time-definition-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L456
(s/def ::get-standard-offset-args (args))
(defn -get-standard-offset [this] (wip ::-get-standard-offset))
(s/fdef -get-standard-offset :args ::get-standard-offset-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L465
(s/def ::get-offset-before-args (args))
(defn -get-offset-before [this] (wip ::-get-offset-before))
(s/fdef -get-offset-before :args ::get-offset-before-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L474
(s/def ::get-offset-after-args (args))
(defn -get-offset-after [this] (wip ::-get-offset-after))
(s/fdef -get-offset-after :args ::get-offset-after-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L487
(s/def ::create-transition-args (args ::j/int))
(defn -create-transition [this year] (wip ::-create-transition))
(s/fdef -create-transition :args ::create-transition-args :ret ::ZoneOffsetTransition/zone-offset-transition)

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
(s/def ::of-args (args ::Month/month ::j/int ::DayOfWeek/day-of-week ::LocalTime/local-time ::j/boolean ::j/wip ::ZoneOffset/zone-offset ::ZoneOffset/zone-offset ::ZoneOffset/zone-offset))
(defn of [month day-of-month-indicator day-of-week time time-end-of-day time-defnition standard-offset offset-before offset-after] (wip ::of))
(s/fdef of :args ::of-args :ret ::zone-offset-transition-rule)
