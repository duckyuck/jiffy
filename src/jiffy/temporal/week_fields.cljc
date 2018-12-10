(ns jiffy.temporal.week-fields
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.week-fields :as week-fields]
            [jiffy.specs :as j]))

(defrecord WeekFields [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::week-fields (j/constructor-spec WeekFields create ::create-args))
(s/fdef create :args ::create-args :ret ::week-fields)

(defmacro args [& x] `(s/tuple ::week-fields ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L403
(s/def ::get-first-day-of-week-args (args))
(defn -get-first-day-of-week [this] (wip ::-get-first-day-of-week))
(s/fdef -get-first-day-of-week :args ::get-first-day-of-week-args :ret ::day-of-week/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L417
(s/def ::get-minimal-days-in-first-week-args (args))
(defn -get-minimal-days-in-first-week [this] (wip ::-get-minimal-days-in-first-week))
(s/fdef -get-minimal-days-in-first-week :args ::get-minimal-days-in-first-week-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L440
(s/def ::day-of-week-args (args))
(defn -day-of-week [this] (wip ::-day-of-week))
(s/fdef -day-of-week :args ::day-of-week-args :ret ::temporal-field/temporal-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L486
(s/def ::week-of-month-args (args))
(defn -week-of-month [this] (wip ::-week-of-month))
(s/fdef -week-of-month :args ::week-of-month-args :ret ::temporal-field/temporal-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L531
(s/def ::week-of-year-args (args))
(defn -week-of-year [this] (wip ::-week-of-year))
(s/fdef -week-of-year :args ::week-of-year-args :ret ::temporal-field/temporal-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L581
(s/def ::week-of-week-based-year-args (args))
(defn -week-of-week-based-year [this] (wip ::-week-of-week-based-year))
(s/fdef -week-of-week-based-year :args ::week-of-week-based-year-args :ret ::temporal-field/temporal-field)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L623
(s/def ::week-based-year-args (args))
(defn -week-based-year [this] (wip ::-week-based-year))
(s/fdef -week-based-year :args ::week-based-year-args :ret ::temporal-field/temporal-field)

(extend-type WeekFields
  week-fields/IWeekFields
  (get-first-day-of-week [this] (-get-first-day-of-week this))
  (get-minimal-days-in-first-week [this] (-get-minimal-days-in-first-week this))
  (day-of-week [this] (-day-of-week this))
  (week-of-month [this] (-week-of-month this))
  (week-of-year [this] (-week-of-year this))
  (week-of-week-based-year [this] (-week-of-week-based-year this))
  (week-based-year [this] (-week-based-year this)))

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L298
  ([locale] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L327
  ([first-day-of-week minimal-days-in-first-week] (wip ::of)))
(s/fdef of :args ::of-args :ret ::week-fields)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L213
(def ISO ::ISO--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L222
(def SUNDAY_START ::SUNDAY_START--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/WeekFields.java#L240
(def WEEK_BASED_YEARS ::WEEK_BASED_YEARS--not-implemented)
