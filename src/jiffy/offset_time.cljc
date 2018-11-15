(ns jiffy.offset-time
  (:refer-clojure :exclude [format])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

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

(defrecord OffsetTime [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L547
(defn -get-offset [this] (wip ::-get-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L568
(defn -with-offset-same-local [this offset] (wip ::-with-offset-same-local))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L590
(defn -with-offset-same-instant [this offset] (wip ::-with-offset-same-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L608
(defn -to-local-time [this] (wip ::-to-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L618
(defn -get-hour [this] (wip ::-get-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L627
(defn -get-minute [this] (wip ::-get-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L636
(defn -get-second [this] (wip ::-get-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L645
(defn -get-nano [this] (wip ::-get-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L750
(defn -with-hour [this hour] (wip ::-with-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L765
(defn -with-minute [this minute] (wip ::-with-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L780
(defn -with-second [this second] (wip ::-with-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L795
(defn -with-nano [this nano-of-second] (wip ::-with-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L822
(defn -truncated-to [this unit] (wip ::-truncated-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L897
(defn -plus-hours [this hours] (wip ::-plus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L912
(defn -plus-minutes [this minutes] (wip ::-plus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L927
(defn -plus-seconds [this seconds] (wip ::-plus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L942
(defn -plus-nanos [this nanos] (wip ::-plus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1008
(defn -minus-hours [this hours] (wip ::-minus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1023
(defn -minus-minutes [this minutes] (wip ::-minus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1038
(defn -minus-seconds [this seconds] (wip ::-minus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1053
(defn -minus-nanos [this nanos] (wip ::-minus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1204
(defn -format [this formatter] (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1219
(defn -at-date [this date] (wip ::-at-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1249
(defn -to-epoch-second [this date] (wip ::-to-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1308
(defn -is-after [this other] (wip ::-is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1323
(defn -is-before [this other] (wip ::-is-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1338
(defn -is-equal [this other] (wip ::-is-equal))

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
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type OffsetTime
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L679
  ([this adjuster] (wip ::-with))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L348
  ([this time offset] (wip ::-with)))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L848
  ([this amount-to-add] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L878
  ([this amount-to-add unit] (wip ::-plus)))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L968
  ([this amount-to-subtract] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L992
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1177
(defn -until [this end-exclusive unit] (wip ::-until))

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
(defn -is-supported [this is-supported--overloaded-param] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L463
(defn -range [this field] (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L501
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L529
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1078
(defn -query [this query] (wip ::-query))

(extend-type OffsetTime
  TemporalAccessor/ITemporalAccessor
  (isSupported [this is-supported--overloaded-param] (-is-supported this is-supported--overloaded-param))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L1120
(defn -adjust-into [this temporal] (wip ::-adjust-into))

(extend-type OffsetTime
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L165
  ([] (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L182
  ([now--overloaded-param] (wip ::now)))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L212
  ([time offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L235
  ([hour minute second nano-of-second offset] (wip ::of)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L255
(defn ofInstant [instant zone] (wip ::ofInstant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L286
(defn from [temporal] (wip ::from))

(defn parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L311
  ([text] (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L325
  ([text formatter] (wip ::parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L128
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L136
(def MAX ::MAX--not-implemented)
