(ns jiffy.local-time
  (:refer-clojure :exclude [format])
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date-impl :as LocalDate]
            [jiffy.local-date-time-impl :as LocalDateTime]
            [jiffy.local-time-impl :as impl]
            [jiffy.offset-time-impl :as OffsetTime]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java
(defprotocol ILocalTime
  (toNanoOfDay [this])
  (toSecondOfDay [this])
  (getHour [this])
  (getMinute [this])
  (getSecond [this])
  (getNano [this])
  (withHour [this hour])
  (withMinute [this minute])
  (withSecond [this second])
  (withNano [this nano-of-second])
  (truncatedTo [this unit])
  (plusHours [this hours-to-add])
  (plusMinutes [this minutes-to-add])
  (plusSeconds [this secondsto-add])
  (plusNanos [this nanos-to-add])
  (minusHours [this hours-to-subtract])
  (minusMinutes [this minutes-to-subtract])
  (minusSeconds [this seconds-to-subtract])
  (minusNanos [this nanos-to-subtract])
  (format [this formatter])
  (atDate [this date])
  (atOffset [this offset])
  (toEpochSecond [this date offset])
  (isAfter [this other])
  (isBefore [this other]))

(defrecord LocalTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::local-time (j/constructor-spec LocalTime create ::create-args))
(s/fdef create :args ::create-args :ret ::local-time)

(defmacro args [& x] `(s/tuple ::local-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L673
(s/def ::to-nano-of-day-args (args))
(defn -to-nano-of-day [this] (wip ::-to-nano-of-day))
(s/fdef -to-nano-of-day :args ::to-nano-of-day-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L692
(s/def ::to-second-of-day-args (args))
(defn -to-second-of-day [this] (wip ::-to-second-of-day))
(s/fdef -to-second-of-day :args ::to-second-of-day-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L710
(s/def ::get-hour-args (args))
(defn -get-hour [this] (wip ::-get-hour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L719
(s/def ::get-minute-args (args))
(defn -get-minute [this] (wip ::-get-minute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L728
(s/def ::get-second-args (args))
(defn -get-second [this] (wip ::-get-second))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L737
(s/def ::get-nano-args (args))
(defn -get-nano [this] (wip ::-get-nano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L891
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour] (wip ::-with-hour))
(s/fdef -with-hour :args ::with-hour-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L908
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute] (wip ::-with-minute))
(s/fdef -with-minute :args ::with-minute-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L925
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second] (wip ::-with-second))
(s/fdef -with-second :args ::with-second-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L942
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second] (wip ::-with-nano))
(s/fdef -with-nano :args ::with-nano-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L971
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit] (wip ::-truncated-to))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1094
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours-to-add] (wip ::-plus-hours))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1113
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes-to-add] (wip ::-plus-minutes))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1138
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this secondsto-add] (wip ::-plus-seconds))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1165
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos-to-add] (wip ::-plus-nanos))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1243
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours-to-subtract] (wip ::-minus-hours))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1258
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes-to-subtract] (wip ::-minus-minutes))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1273
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds-to-subtract] (wip ::-minus-seconds))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1288
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos-to-subtract] (wip ::-minus-nanos))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1433
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1448
(s/def ::at-date-args (args ::LocalDate/local-date))
(defn -at-date [this date] (wip ::-at-date))
(s/fdef -at-date :args ::at-date-args :ret ::LocalDateTime/local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1461
(s/def ::at-offset-args (args ::ZoneOffset/zone-offset))
(defn -at-offset [this offset] (wip ::-at-offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::OffsetTime/offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1508
(s/def ::to-epoch-second-args (args ::LocalDate/local-date ::ZoneOffset/zone-offset))
(defn -to-epoch-second [this date offset] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1550
(s/def ::is-after-args (args ::local-time))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1562
(s/def ::is-before-args (args ::local-time))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

(extend-type LocalTime
  ILocalTime
  (toNanoOfDay [this] (-to-nano-of-day this))
  (toSecondOfDay [this] (-to-second-of-day this))
  (getHour [this] (-get-hour this))
  (getMinute [this] (-get-minute this))
  (getSecond [this] (-get-second this))
  (getNano [this] (-get-nano this))
  (withHour [this hour] (-with-hour this hour))
  (withMinute [this minute] (-with-minute this minute))
  (withSecond [this second] (-with-second this second))
  (withNano [this nano-of-second] (-with-nano this nano-of-second))
  (truncatedTo [this unit] (-truncated-to this unit))
  (plusHours [this hours-to-add] (-plus-hours this hours-to-add))
  (plusMinutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plusSeconds [this secondsto-add] (-plus-seconds this secondsto-add))
  (plusNanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minusHours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minusMinutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minusSeconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minusNanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (format [this formatter] (-format this formatter))
  (atDate [this date] (-at-date this date))
  (atOffset [this offset] (-at-offset this offset))
  (toEpochSecond [this date offset] (-to-epoch-second this date offset))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1528
(s/def ::compare-to-args (args ::local-time))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type LocalTime
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L764
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L855
  ([this field new-value] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1009
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1066
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1203
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1227
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::Temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1406
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type LocalTime
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this field new-value] (-with this field new-value)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L539
(s/def ::is-supported-args (args ::TemporalUnit/temporal-unit))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L607
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L639
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L670
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1313
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type LocalTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L1354
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type LocalTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L359
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::local-time)

(s/def ::now-args (args ::j/wip))
(defn now
  ([] (now (Clock/systemDefaultZone)))

  ;; NB! This method is overloaded!
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L263
  ([zone-or-clock]
   (case (type zone-or-clock)
     ZoneId (now (Clock/system zone-or-clock))
     Clock (ofInstant (Clock/instant zone-or-clock)
                      (Clock/getZone zone-or-clock)))))
(s/fdef now :ret ::local-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L295
  ([hour minute] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L316
  ([hour minute second] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L338
  ([hour minute second nano-of-second] (wip ::of)))
(s/fdef of :args ::of-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L379
(s/def ::of-second-of-day-args (args ::j/long))
(defn ofSecondOfDay [second-of-day] (wip ::ofSecondOfDay))
(s/fdef ofSecondOfDay :args ::of-second-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L397
(s/def ::of-nano-of-day-args (args ::j/long))
(defn ofNanoOfDay [nano-of-day] (wip ::ofNanoOfDay))
(s/fdef ofNanoOfDay :args ::of-nano-of-day-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L426
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::local-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L447
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L461
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L132
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L137
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L141
(def MIDNIGHT ::MIDNIGHT--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalTime.java#L145
(def NOON ::NOON--not-implemented)

(def HOURS_PER_DAY impl/HOURS_PER_DAY)
(def MINUTES_PER_HOUR impl/MINUTES_PER_HOUR)
(def MINUTES_PER_DAY impl/MINUTES_PER_DAY)
(def SECONDS_PER_MINUTE impl/SECONDS_PER_MINUTE)
(def SECONDS_PER_HOUR impl/SECONDS_PER_HOUR)
(def SECONDS_PER_DAY impl/SECONDS_PER_DAY)
(def MILLIS_PER_DAY impl/MILLIS_PER_DAY)
(def MICROS_PER_DAY impl/MICROS_PER_DAY)
(def NANOS_PER_MILLI impl/NANOS_PER_MILLI)
(def NANOS_PER_SECOND impl/NANOS_PER_SECOND)
(def NANOS_PER_MINUTE impl/NANOS_PER_MINUTE)
(def NANOS_PER_HOUR impl/NANOS_PER_HOUR)
(def NANOS_PER_DAY impl/NANOS_PER_DAY)
