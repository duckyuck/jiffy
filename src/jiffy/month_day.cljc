(ns jiffy.month-day
  (:refer-clojure :exclude [format])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java
(defprotocol IMonthDay
  (getMonth [this])
  (getMonthValue [this])
  (getDayOfMonth [this])
  (isValidYear [this year])
  (withMonth [this month])
  (with [this month])
  (withDayOfMonth [this day-of-month])
  (format [this formatter])
  (atYear [this year])
  (isAfter [this other])
  (isBefore [this other]))

(defrecord MonthDay [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L384
(defn -get-month [this] (wip ::-get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L467
(defn -get-month-value [this] (wip ::-get-month-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L493
(defn -get-day-of-month [this] (wip ::-get-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L508
(defn -is-valid-year [this year] (wip ::-is-valid-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L526
(defn -with-month [this month] (wip ::-with-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L542
(defn -with [this month] (wip ::-with))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L564
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L644
(defn -format [this formatter] (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L664
(defn -at-year [this year] (wip ::-at-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L693
(defn -is-after [this other] (wip ::-is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L703
(defn -is-before [this other] (wip ::-is-before))

(extend-type MonthDay
  IMonthDay
  (getMonth [this] (-get-month this))
  (getMonthValue [this] (-get-month-value this))
  (getDayOfMonth [this] (-get-day-of-month this))
  (isValidYear [this year] (-is-valid-year this year))
  (withMonth [this month] (-with-month this month))
  (with [this month] (-with this month))
  (withDayOfMonth [this day-of-month] (-with-day-of-month this day-of-month))
  (format [this formatter] (-format this formatter))
  (atYear [this year] (-at-year this year))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L679
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type MonthDay
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L349
(defn -is-supported [this field] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L380
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L416
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L444
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L592
(defn -query [this query] (wip ::-query))

(extend-type MonthDay
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L627
(defn -adjust-into [this temporal] (wip ::-adjust-into))

(extend-type MonthDay
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L165
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L181
  ([now--overloaded-param] (wip ::now)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L217
(defn of [of--overloaded-param-1 of--overloaded-param-2] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L267
(defn from [temporal] (wip ::from))

(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L293
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L307
  ([text formatter] (wip ::parse)))
