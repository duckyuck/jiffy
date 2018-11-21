(ns jiffy.month-day
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.local-date :as LocalDate]
            [jiffy.month :as Month]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::month-day (j/constructor-spec MonthDay create ::create-args))
(s/fdef create :args ::create-args :ret ::month-day)

(defmacro args [& x] `(s/tuple ::month-day ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L384
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::Month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L467
(s/def ::get-month-value-args (args))
(defn -get-month-value [this] (wip ::-get-month-value))
(s/fdef -get-month-value :args ::get-month-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L493
(s/def ::get-day-of-month-args (args))
(defn -get-day-of-month [this] (wip ::-get-day-of-month))
(s/fdef -get-day-of-month :args ::get-day-of-month-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L508
(s/def ::is-valid-year-args (args ::j/int))
(defn -is-valid-year [this year] (wip ::-is-valid-year))
(s/fdef -is-valid-year :args ::is-valid-year-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L526
(s/def ::with-month-args (args ::j/int))
(defn -with-month [this month] (wip ::-with-month))
(s/fdef -with-month :args ::with-month-args :ret ::month-day)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L542
(s/def ::with-args (args ::Month/month))
(defn -with [this month] (wip ::-with))
(s/fdef -with :args ::with-args :ret ::month-day)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L564
(s/def ::with-day-of-month-args (args ::j/int))
(defn -with-day-of-month [this day-of-month] (wip ::-with-day-of-month))
(s/fdef -with-day-of-month :args ::with-day-of-month-args :ret ::month-day)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L644
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L664
(s/def ::at-year-args (args ::j/int))
(defn -at-year [this year] (wip ::-at-year))
(s/fdef -at-year :args ::at-year-args :ret ::LocalDate/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L693
(s/def ::is-after-args (args ::month-day))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L703
(s/def ::is-before-args (args ::month-day))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

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
(s/def ::compare-to-args (args ::month-day))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type MonthDay
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L349
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L380
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L416
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L444
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L592
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type MonthDay
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L627
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type MonthDay
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L165
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L181
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::month-day)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L217
(s/def ::of-args (args ::j/int ::j/int))
(defn of [of--overloaded-param-1 of--overloaded-param-2] (wip ::of))
(s/fdef of :args ::of-args :ret ::month-day)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L267
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::month-day)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L293
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/MonthDay.java#L307
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::month-day)
