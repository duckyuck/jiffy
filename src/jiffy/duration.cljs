(ns jiffy.duration
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.temporal.temporal-amount :as TemporalAmount]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java
(defprotocol IDuration
  (isZero [this])
  (isNegative [this])
  (getSeconds [this])
  (getNano [this])
  (withSeconds [this seconds])
  (withNanos [this nano-of-second])
  (plus [this duration] [this amount-to-add unit])
  (plusDays [this days-to-add])
  (plusHours [this hours-to-add])
  (plusMinutes [this minutes-to-add])
  (plusSeconds [this seconds-to-add])
  (plusMillis [this millis-to-add])
  (plusNanos [this nanos-to-add])
  (minus [this duration] [this amount-to-subtract unit])
  (minusDays [this days-to-subtract])
  (minusHours [this hours-to-subtract])
  (minusMinutes [this minutes-to-subtract])
  (minusSeconds [this seconds-to-subtract])
  (minusMillis [this millis-to-subtract])
  (minusNanos [this nanos-to-subtract])
  (multipliedBy [this multiplicand])
  (dividedBy [this divided-by--overloaded-param])
  (negated [this])
  (abs [this])
  (toDays [this])
  (toHours [this])
  (toMinutes [this])
  (toSeconds [this])
  (toMillis [this])
  (toNanos [this])
  (toDaysPart [this])
  (toHoursPart [this])
  (toMinutesPart [this])
  (toSecondsPart [this])
  (toMillisPart [this])
  (toNanosPart [this])
  (truncatedTo [this unit]))

(defrecord Duration [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L592
(defn -is-zero [this] (wip ::-is-zero))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L605
(defn -is-negative [this] (wip ::-is-negative))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L624
(defn -get-seconds [this] (wip ::-get-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L642
(defn -get-nano [this] (wip ::-get-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L658
(defn -with-seconds [this seconds] (wip ::-with-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L674
(defn -with-nanos [this nano-of-second] (wip ::-with-nanos))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L689
  ([this duration] (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L709
  ([this amount-to-add unit] (wip ::-plus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L746
(defn -plus-days [this days-to-add] (wip ::-plus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L759
(defn -plus-hours [this hours-to-add] (wip ::-plus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L772
(defn -plus-minutes [this minutes-to-add] (wip ::-plus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L785
(defn -plus-seconds [this seconds-to-add] (wip ::-plus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L798
(defn -plus-millis [this millis-to-add] (wip ::-plus-millis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L811
(defn -plus-nanos [this nanos-to-add] (wip ::-plus-nanos))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L846
  ([this duration] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L870
  ([this amount-to-subtract unit] (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L887
(defn -minus-days [this days-to-subtract] (wip ::-minus-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L902
(defn -minus-hours [this hours-to-subtract] (wip ::-minus-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L917
(defn -minus-minutes [this minutes-to-subtract] (wip ::-minus-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L930
(defn -minus-seconds [this seconds-to-subtract] (wip ::-minus-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L943
(defn -minus-millis [this millis-to-subtract] (wip ::-minus-millis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L956
(defn -minus-nanos [this nanos-to-subtract] (wip ::-minus-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L970
(defn -multiplied-by [this multiplicand] (wip ::-multiplied-by))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L989
(defn -divided-by [this divided-by--overloaded-param] (wip ::-divided-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1055
(defn -negated [this] (wip ::-negated))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1070
(defn -abs [this] (wip ::-abs))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1157
(defn -to-days [this] (wip ::-to-days))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1171
(defn -to-hours [this] (wip ::-to-hours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1185
(defn -to-minutes [this] (wip ::-to-minutes))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1199
(defn -to-seconds [this] (wip ::-to-seconds))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1216
(defn -to-millis [this] (wip ::-to-millis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1239
(defn -to-nanos [this] (wip ::-to-nanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1265
(defn -to-days-part [this] (wip ::-to-days-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1281
(defn -to-hours-part [this] (wip ::-to-hours-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1297
(defn -to-minutes-part [this] (wip ::-to-minutes-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1313
(defn -to-seconds-part [this] (wip ::-to-seconds-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1331
(defn -to-millis-part [this] (wip ::-to-millis-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1348
(defn -to-nanos-part [this] (wip ::-to-nanos-part))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1377
(defn -truncated-to [this unit] (wip ::-truncated-to))

(extend-type Duration
  IDuration
  (isZero [this] (-is-zero this))
  (isNegative [this] (-is-negative this))
  (getSeconds [this] (-get-seconds this))
  (getNano [this] (-get-nano this))
  (withSeconds [this seconds] (-with-seconds this seconds))
  (withNanos [this nano-of-second] (-with-nanos this nano-of-second))
  (plus
    ([this duration] (-plus this duration))
    ([this amount-to-add unit] (-plus this amount-to-add unit)))
  (plusDays [this days-to-add] (-plus-days this days-to-add))
  (plusHours [this hours-to-add] (-plus-hours this hours-to-add))
  (plusMinutes [this minutes-to-add] (-plus-minutes this minutes-to-add))
  (plusSeconds [this seconds-to-add] (-plus-seconds this seconds-to-add))
  (plusMillis [this millis-to-add] (-plus-millis this millis-to-add))
  (plusNanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minus
    ([this duration] (-minus this duration))
    ([this amount-to-subtract unit] (-minus this amount-to-subtract unit)))
  (minusDays [this days-to-subtract] (-minus-days this days-to-subtract))
  (minusHours [this hours-to-subtract] (-minus-hours this hours-to-subtract))
  (minusMinutes [this minutes-to-subtract] (-minus-minutes this minutes-to-subtract))
  (minusSeconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minusMillis [this millis-to-subtract] (-minus-millis this millis-to-subtract))
  (minusNanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (multipliedBy [this multiplicand] (-multiplied-by this multiplicand))
  (dividedBy [this divided-by--overloaded-param] (-divided-by this divided-by--overloaded-param))
  (negated [this] (-negated this))
  (abs [this] (-abs this))
  (toDays [this] (-to-days this))
  (toHours [this] (-to-hours this))
  (toMinutes [this] (-to-minutes this))
  (toSeconds [this] (-to-seconds this))
  (toMillis [this] (-to-millis this))
  (toNanos [this] (-to-nanos this))
  (toDaysPart [this] (-to-days-part this))
  (toHoursPart [this] (-to-hours-part this))
  (toMinutesPart [this] (-to-minutes-part this))
  (toSecondsPart [this] (-to-seconds-part this))
  (toMillisPart [this] (-to-millis-part this))
  (toNanosPart [this] (-to-nanos-part this))
  (truncatedTo [this unit] (-truncated-to this unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1408
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))

(extend-type Duration
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L546
(defn -get [this unit] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L569
(defn -get-units [this] (wip ::-get-units))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1100
(defn -add-to [this temporal] (wip ::-add-to))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L1135
(defn -subtract-from [this temporal] (wip ::-subtract-from))

(extend-type Duration
  TemporalAmount/ITemporalAmount
  (get [this unit] (-get this unit))
  (getUnits [this] (-get-units this))
  (addTo [this temporal] (-add-to this temporal))
  (subtractFrom [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L180
(defn ofDays [days] (wip ::ofDays))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L195
(defn ofHours [hours] (wip ::ofHours))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L210
(defn ofMinutes [minutes] (wip ::ofMinutes))

(defn ofSeconds
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L223
  ([seconds] (wip ::ofSeconds))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L246
  ([seconds nano-adjustment] (wip ::ofSeconds)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L261
(defn ofMillis [millis] (wip ::ofMillis))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L280
(defn ofNanos [nanos] (wip ::ofNanos))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L309
(defn of [amount unit] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L334
(defn from [amount] (wip ::from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L388
(defn parse [text] (wip ::parse))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L486
(defn between [start-inclusive end-exclusive] (wip ::between))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L139
(def ZERO ::ZERO--not-implemented)