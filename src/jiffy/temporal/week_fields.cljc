(ns jiffy.temporal.week-fields
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java
(defprotocol IWeekFields
  (getFirstDayOfWeek [this])
  (getMinimalDaysInFirstWeek [this])
  (dayOfWeek [this])
  (weekOfMonth [this])
  (weekOfYear [this])
  (weekOfWeekBasedYear [this])
  (weekBasedYear [this]))

(defrecord WeekFields [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L403
(defn -get-first-day-of-week [this] (wip ::-get-first-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L417
(defn -get-minimal-days-in-first-week [this] (wip ::-get-minimal-days-in-first-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L440
(defn -day-of-week [this] (wip ::-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L486
(defn -week-of-month [this] (wip ::-week-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L531
(defn -week-of-year [this] (wip ::-week-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L581
(defn -week-of-week-based-year [this] (wip ::-week-of-week-based-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L623
(defn -week-based-year [this] (wip ::-week-based-year))

(extend-type WeekFields
  IWeekFields
  (getFirstDayOfWeek [this] (-get-first-day-of-week this))
  (getMinimalDaysInFirstWeek [this] (-get-minimal-days-in-first-week this))
  (dayOfWeek [this] (-day-of-week this))
  (weekOfMonth [this] (-week-of-month this))
  (weekOfYear [this] (-week-of-year this))
  (weekOfWeekBasedYear [this] (-week-of-week-based-year this))
  (weekBasedYear [this] (-week-based-year this)))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L298
  ([locale] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L327
  ([first-day-of-week minimal-days-in-first-week] (wip ::of)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L213
(def ISO ::ISO--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L222
(def SUNDAY_START ::SUNDAY_START--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L240
(def WEEK_BASED_YEARS ::WEEK_BASED_YEARS--not-implemented)