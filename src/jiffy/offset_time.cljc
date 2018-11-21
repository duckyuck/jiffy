(ns jiffy.offset-time
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.instant-impl :as Instant]
            [jiffy.local-date :as LocalDate]
            [jiffy.local-time :as LocalTime]
            [jiffy.offset-time-impl :refer [create #?@(:cljs [OffsetTime])] :as impl]
            [jiffy.offset-date-time-impl :as OffsetDateTime]
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
            [jiffy.zone-offset-impl :as ZoneOffset])
  #?(:clj (:import [jiffy.offset_time_impl OffsetTime])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java
(defprotocol IOffsetTime
  (getOffset [this])
  (withOffsetSameLocal [this offset])
  (withOffsetSameInstant [this offset])
  (toLocalTime [this])
  (getHour [this])
  (getMinute [this])
  (getSecond [this])
  (getNano [this])
  (withHour [this hour])
  (withMinute [this minute])
  (withSecond [this second])
  (withNano [this nano-of-second])
  (truncatedTo [this unit])
  (plusHours [this hours])
  (plusMinutes [this minutes])
  (plusSeconds [this seconds])
  (plusNanos [this nanos])
  (minusHours [this hours])
  (minusMinutes [this minutes])
  (minusSeconds [this seconds])
  (minusNanos [this nanos])
  (format [this formatter])
  (atDate [this date])
  (toEpochSecond [this date])
  (isAfter [this other])
  (isBefore [this other])
  (isEqual [this other]))

(s/def ::offset-time ::impl/offset-time)

(defmacro args [& x] `(s/tuple ::offset-time ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L547
(s/def ::get-offset-args (args))
(defn -get-offset [this] (wip ::-get-offset))
(s/fdef -get-offset :args ::get-offset-args :ret ::ZoneOffset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L568
(s/def ::with-offset-same-local-args (args ::ZoneOffset/zone-offset))
(defn -with-offset-same-local [this offset] (wip ::-with-offset-same-local))
(s/fdef -with-offset-same-local :args ::with-offset-same-local-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L590
(s/def ::with-offset-same-instant-args (args ::ZoneOffset/zone-offset))
(defn -with-offset-same-instant [this offset] (wip ::-with-offset-same-instant))
(s/fdef -with-offset-same-instant :args ::with-offset-same-instant-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L608
(s/def ::to-local-time-args (args))
(defn -to-local-time [this] (wip ::-to-local-time))
(s/fdef -to-local-time :args ::to-local-time-args :ret ::LocalTime/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L618
(s/def ::get-hour-args (args))
(defn -get-hour [this] (wip ::-get-hour))
(s/fdef -get-hour :args ::get-hour-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L627
(s/def ::get-minute-args (args))
(defn -get-minute [this] (wip ::-get-minute))
(s/fdef -get-minute :args ::get-minute-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L636
(s/def ::get-second-args (args))
(defn -get-second [this] (wip ::-get-second))
(s/fdef -get-second :args ::get-second-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L645
(s/def ::get-nano-args (args))
(defn -get-nano [this] (wip ::-get-nano))
(s/fdef -get-nano :args ::get-nano-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L750
(s/def ::with-hour-args (args ::j/int))
(defn -with-hour [this hour] (wip ::-with-hour))
(s/fdef -with-hour :args ::with-hour-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L765
(s/def ::with-minute-args (args ::j/int))
(defn -with-minute [this minute] (wip ::-with-minute))
(s/fdef -with-minute :args ::with-minute-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L780
(s/def ::with-second-args (args ::j/int))
(defn -with-second [this second] (wip ::-with-second))
(s/fdef -with-second :args ::with-second-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L795
(s/def ::with-nano-args (args ::j/int))
(defn -with-nano [this nano-of-second] (wip ::-with-nano))
(s/fdef -with-nano :args ::with-nano-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L822
(s/def ::truncated-to-args (args ::TemporalUnit/temporal-unit))
(defn -truncated-to [this unit] (wip ::-truncated-to))
(s/fdef -truncated-to :args ::truncated-to-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L897
(s/def ::plus-hours-args (args ::j/long))
(defn -plus-hours [this hours] (wip ::-plus-hours))
(s/fdef -plus-hours :args ::plus-hours-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L912
(s/def ::plus-minutes-args (args ::j/long))
(defn -plus-minutes [this minutes] (wip ::-plus-minutes))
(s/fdef -plus-minutes :args ::plus-minutes-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L927
(s/def ::plus-seconds-args (args ::j/long))
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L942
(s/def ::plus-nanos-args (args ::j/long))
(defn -plus-nanos [this nanos] (wip ::-plus-nanos))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1008
(s/def ::minus-hours-args (args ::j/long))
(defn -minus-hours [this hours] (wip ::-minus-hours))
(s/fdef -minus-hours :args ::minus-hours-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1023
(s/def ::minus-minutes-args (args ::j/long))
(defn -minus-minutes [this minutes] (wip ::-minus-minutes))
(s/fdef -minus-minutes :args ::minus-minutes-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1038
(s/def ::minus-seconds-args (args ::j/long))
(defn -minus-seconds [this seconds] (wip ::-minus-seconds))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1053
(s/def ::minus-nanos-args (args ::j/long))
(defn -minus-nanos [this nanos] (wip ::-minus-nanos))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1204
(s/def ::format-args (args ::DateTimeFormatter/date-time-formatter))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1219
(s/def ::at-date-args (args ::LocalDate/local-date))
(defn -at-date [this date] (wip ::-at-date))
(s/fdef -at-date :args ::at-date-args :ret ::OffsetDateTime/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1249
(s/def ::to-epoch-second-args (args ::LocalDate/local-date))
(defn -to-epoch-second [this date] (wip ::-to-epoch-second))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1308
(s/def ::is-after-args (args ::offset-time))
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1323
(s/def ::is-before-args (args ::offset-time))
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1338
(s/def ::is-equal-args (args ::offset-time))
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)

(extend-type OffsetTime
  IOffsetTime
  (getOffset [this] (-get-offset this))
  (withOffsetSameLocal [this offset] (-with-offset-same-local this offset))
  (withOffsetSameInstant [this offset] (-with-offset-same-instant this offset))
  (toLocalTime [this] (-to-local-time this))
  (getHour [this] (-get-hour this))
  (getMinute [this] (-get-minute this))
  (getSecond [this] (-get-second this))
  (getNano [this] (-get-nano this))
  (withHour [this hour] (-with-hour this hour))
  (withMinute [this minute] (-with-minute this minute))
  (withSecond [this second] (-with-second this second))
  (withNano [this nano-of-second] (-with-nano this nano-of-second))
  (truncatedTo [this unit] (-truncated-to this unit))
  (plusHours [this hours] (-plus-hours this hours))
  (plusMinutes [this minutes] (-plus-minutes this minutes))
  (plusSeconds [this seconds] (-plus-seconds this seconds))
  (plusNanos [this nanos] (-plus-nanos this nanos))
  (minusHours [this hours] (-minus-hours this hours))
  (minusMinutes [this minutes] (-minus-minutes this minutes))
  (minusSeconds [this seconds] (-minus-seconds this seconds))
  (minusNanos [this nanos] (-minus-nanos this nanos))
  (format [this formatter] (-format this formatter))
  (atDate [this date] (-at-date this date))
  (toEpochSecond [this date] (-to-epoch-second this date))
  (isAfter [this other] (-is-after this other))
  (isBefore [this other] (-is-before this other))
  (isEqual [this other] (-is-equal this other)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1285
(s/def ::compare-to-args (args ::j/wip))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type OffsetTime
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L679
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L348
  ([this time offset] (wip ::-with)))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L848
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L878
  ([this amount-to-add unit] (wip ::-plus)))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L968
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L992
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1177
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit] (wip ::-until))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type OffsetTime
  Temporal/ITemporal
  (with
    ([this adjuster] (-with this adjuster))
    ([this time offset] (-with this time offset)))
  (plus
    ([this amount-to-add] (-plus this amount-to-add))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (-minus this amount-to-subtract))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (-until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L395
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L463
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L501
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L529
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1078
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type OffsetTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1120
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type OffsetTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L165
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L182
  ([now--overloaded-param] (wip ::now)))
(s/fdef now :args ::now-args :ret ::offset-time)

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L212
  ([time offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L235
  ([hour minute second nano-of-second offset] (wip ::of)))
(s/fdef of :args ::of-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L255
(s/def ::of-instant-args (args ::Instant/instant ::ZoneId/zone-id))
(defn ofInstant [instant zone] (wip ::ofInstant))
(s/fdef ofInstant :args ::of-instant-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L286
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::offset-time)

(s/def ::parse-args (args ::j/wip))
(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L311
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L325
  ([text formatter] (wip ::parse)))
(s/fdef parse :args ::parse-args :ret ::offset-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L128
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L136
(def MAX ::MAX--not-implemented)
