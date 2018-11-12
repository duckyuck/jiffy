(ns jiffy.instant
  (:require [jiffy.clock :as Clock]
            [jiffy.date-time-exception :refer [DateTimeException]]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as Duration]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.local-time :refer [NANOS_PER_DAY NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as OffsetDateTime]
            [jiffy.temporal.chrono-field :as ChronoField :refer [INSTANT_SECONDS MICRO_OF_SECOND MILLI_OF_SECOND NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as ChronoUnit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-queries :as TemporalQueries]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.unsupported-temporal-type-exception :refer [UnsupportedTemporalTypeException]]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zoned-date-time :as ZonedDateTime]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java
(defprotocol IInstant
  (getEpochSecond [this])
  (getNano [this])
  (truncatedTo [this unit])
  (plusSeconds [this seconds-to-add])
  (plusMillis [this millis-to-add])
  (plusNanos [this nanos-to-add])
  (minusSeconds [this seconds-to-subtract])
  (minusMillis [this millis-to-subtract])
  (minusNanos [this nanos-to-subtract])
  (toEpochMilli [this])
  (atOffset [this offset])
  (atZone [this zone])
  (isAfter [this other-instant])
  (isBefore [this other-instant]))

(defrecord Instant [seconds nanos])

(def EPOCH (->Instant 0 0))
(def MAX_SECOND 31556889864403199)
(def MIN_SECOND -31557014167219200)

(defn create-instant [seconds nano-of-second]
  (cond
    (zero? (bit-or seconds nano-of-second))
    EPOCH

    (not (< MIN_SECOND seconds MAX_SECOND))
    (throw (DateTimeException "Instant exceeds minimum or maximum instant"
                              {:max-second MAX_SECOND
                               :min-second MIN_SECOND
                               :seconds seconds}))

    :else
    (->Instant seconds nano-of-second)))

(def -get-epoch-second :seconds)
(def -get-nano :nanos)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L747
(defn -truncated-to [this unit]
  (let [unit-dur (TemporalUnit/getDuration unit)
        dur (Duration/toNanos unit-dur)]
    (cond
      (= unit NANOS)
      this

      (> (Duration/getSeconds unit-dur) SECONDS_PER_DAY)
      (throw (UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:instant this :unit unit}))

      (-> (NANOS_PER_DAY) (mod dur) zero? not)
      (throw (UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:instant this :unit unit}))

      :else
      (let [nod (-> (mod (:seconds this) SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result (* (math/floor-div nod dur) dur)]
        (plusNanos this (- result nod))))))

(defn ofEpochSecond
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L303
  ([epoch-second]
   (create-instant epoch-second 0))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L327
  ([epoch-second nano-adjustment]
   (create-instant
    (math/add-exact epoch-second (math/floor-div nano-adjustment NANOS_PER_SECOND))
    (math/floor-mod nano-adjustment NANOS_PER_SECOND))))

(defn- --plus [this seconds-to-add nanos-to-add]
  (if (and (zero? seconds-to-add) (zero? nanos-to-add))
    this
    (ofEpochSecond
     (-> (:seconds this)
         (math/add-exact seconds-to-add)
         (math/add-exact (/ nanos-to-add NANOS_PER_SECOND)))
     (+ (:nanos this) (mod nanos-to-add NANOS_PER_SECOND)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L877
(defn -plus-seconds [this seconds-to-add]
  (--plus this seconds-to-add 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L891
(defn -plus-millis [this millis-to-add]
  (--plus this (/ millis-to-add 1000) (* (mod millis-to-add 1000) 1000000)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L905
(defn -plus-nanos [this nanos-to-add]
  (--plus this 0 nanos-to-add))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L992
(defn -minus-seconds [this seconds-to-subtract]
  (if (= seconds-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plusSeconds math/long-max-value) (plusSeconds 1))
    (plusSeconds this (- seconds-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1009
(defn -minus-millis [this millis-to-subtract]
  (if (= millis-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plusMillis math/long-max-value) (plusMillis 1))
    (plusMillis this (- millis-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1026
(defn -minus-nanos [this nanos-to-subtract]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this (plusNanos math/long-max-value) (plusNanos 1))
    (plusNanos this (- nanos-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1149
(defn -to-epoch-milli [this]
  (if (and (neg? (:seconds this)) (pos? (:nanos this)))
    (let [millis (math/multiply-exact (inc (:seconds this)) 1000)
          adjustment (- (/ (:nanos this) 1000000) 1000)]
      (math/add-exact millis adjustment))
    (let [millis (math/multiply-exact (:seconds this) 1000)]
      (math/add-exact millis (/ (:nanos this) 1000000)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1193
(defn -at-offset [this offset]
  (OffsetDateTime/ofInstant this offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1211
(defn -at-zone [this zone]
  (ZonedDateTime/ofInstant this zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1270
(defn -is-after [this other-instant]
  (pos? (TimeComparable/compareTo this other-instant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1283
(defn -is-before [this other-instant]
  (neg? (TimeComparable/compareTo this other-instant)))

(extend-type Instant
  IInstant
  (getEpochSecond [this] (-get-epoch-second this))
  (getNano [this] (-get-nano this))
  (truncatedTo [this unit] (-truncated-to this unit))
  (plusSeconds [this seconds-to-add] (-plus-seconds this seconds-to-add))
  (plusMillis [this millis-to-add] (-plus-millis this millis-to-add))
  (plusNanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minusSeconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minusMillis [this millis-to-subtract] (-minus-millis this millis-to-subtract))
  (minusNanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (toEpochMilli [this] (-to-epoch-milli this))
  (atOffset [this offset] (-at-offset this offset))
  (atZone [this zone] (-at-zone this zone))
  (isAfter [this other-instant] (-is-after this other-instant))
  (isBefore [this other-instant] (-is-before this other-instant)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1253
(defn -compare-to [this other-instant]
  (let [cmp (compare (:seconds this) (:seconds other-instant))]
    (if-not (zero? cmp)
      cmp
      (- (:nanos this) (:nanos other-instant)))))

(extend-type Instant
  TimeComparable/ITimeComparable
  (compareTo [this other-instant] (-compare-to this other-instant)))

(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L654
  ([this adjuster]
   (TemporalAdjuster/adjustInto adjuster this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L703
  ([this field new-value]
   (if (instance? field ChronoField/IChronoField)
     (do
       (ChronoField/checkValidValue field new-value)
       (condp = field
         MILLI_OF_SECOND
         (let [val (* new-value 1000000)]
           (if (= val (:nanos this))
             this
             (create-instant (:seconds this) val)))

         MICRO_OF_SECOND
         (let [val (* new-value 1000)]
           (if (= val (:nanos this))
             this
             (create-instant (:seconds this) val)))

         NANO_OF_SECOND
         (if (= new-value (:nanos this))
           this
           (create-instant (:seconds this) new-value))

         INSTANT_SECONDS
         (if (= new-value (:seconds this))
           this
           (create-instant new-value (:nanos this))))))))

(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L786
  ([this amount-to-add]
   (TemporalAmount/addTo amount-to-add this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L849
  ([this amount-to-add unit]
   (if (instance? unit ChronoUnit/IChronoUnit)
     (condp = unit
       NANOS (plusNanos this amount-to-add)
       MICROS (--plus this (/ amount-to-add 1000000) (* (mod amount-to-add 1000000) 1000))
       MILLIS (plusMillis this amount-to-add)
       SECONDS (plusSeconds this amount-to-add)
       MINUTES (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_MINUTE))
       HOURS (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_HOUR))
       HALF_DAYS (plusSeconds this (math/multiply-exact amount-to-add (/ SECONDS_PER_DAY 2)))
       DAYS (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_DAY))
       (throw (UnsupportedTemporalTypeException (str "Unsupported unit: " unit))))
     (TemporalUnit/addTo unit this amount-to-add))))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L953
  ([this amount-to-subtract]
   (TemporalAmount/subtractFrom amount-to-subtract this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L977
  ([this amount-to-subtract unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (Temporal/plus math/long-max-value unit)
         (Temporal/plus 1 unit))
     (Temporal/plus this (- amount-to-subtract) unit))))

(declare from)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1161
(defn- nanosUntil [this end]
  (let [secs-diff (math/subtract-exact (:seconds end) (:seconds this))
        total-nanos (math/multiply-exact secs-diff NANOS_PER_SECOND)]
    (math/add-exact total-nanos (- (:nanos end) (:nanos this)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1167
(defn- secondsUntil [this end]
  (let [secs-diff (math/subtract-exact (:seconds end) (:seconds this))
        nanos-diff (- (:nanos end) (:nanos this))]
    (cond
      (and (pos? secs-diff) (neg? nanos-diff))
      (dec secs-diff)

      (and (neg? secs-diff) (pos? nanos-diff))
      (inc secs-diff)

      :else secs-diff)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1142
(defn -until [this end-exclusive unit]
  (let [end (from end-exclusive)]
    (if (instance? unit ChronoUnit/IChronoUnit)
      (condp (= unit)
          NANOS (nanosUntil this end)
          MICROS (-> (nanosUntil this end) (/ 1000))
          MILLIS (math/subtract-exact (toEpochMilli end) (toEpochMilli this))
          SECONDS (secondsUntil this end)
          MINUTES (-> (secondsUntil this end) (/ SECONDS_PER_MINUTE))
          HOURS (-> (secondsUntil this end) (/ SECONDS_PER_HOUR))
          HALF_DAYS (-> (secondsUntil this end) (/ (* 12 SECONDS_PER_HOUR)))
          DAYS (-> (secondsUntil this end) (/ SECONDS_PER_DAY))
          (throw (UnsupportedTemporalTypeException (str "Unsupported unit: " unit) {:instant instant :unit unit})))
      (TemporalUnit/between unit this end))))


(extend-type Instant
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

(defn --is-supported-field [this field]
  (if (instance? field ChronoField/IChronoField)
    (some? (some #(= field %) [INSTANT_SECONDS NANO_OF_SECOND MICRO_OF_SECOND MILLI_OF_SECOND]))
    (and field (TemporalField/isSupportedBy field this))))

(defn --is-supported-unit [this unit]
  (if (instance? unit ChronoUnit/IChronoUnit)
    (or (TemporalUnit/isTimeBased unit) (= unit DAYS))
    (and unit (TemporalUnit/isSupportedBy unit this))))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L457
;; TODO: clean up dispatch via multimethods
(defn -is-supported [this field-or-unit]
  (cond
    (instance? field-or-unit TemporalField/ITemporalField)
    (--is-supported-field this  field-or-unit)

    (instance? field-or-unit TemporalUnit/ITemporalUnit)
    (--is-supported-unit this field-or-unit)

    :else
    (wip ::-is-supported)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L526
(defn -range [this field]
  (TemporalAccessor/range this field))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L558
(defn -get [this field]
  (if (instance? field ChronoField/IChronoField)
    (condp = field
      NANO_OF_SECOND (:nanos this)
      MICRO_OF_SECOND (/ (:nanos this) 1000)
      MILLI_OF_SECOND (/ (:nanos this) 1000000)
      (throw (UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field})))
    (ValueRange/checkValidIntValue
     (-range this field)
     (TemporalField/getFrom field this)
     field)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L594
(defn -get-long [this field]
  (if (instance? field ChronoField/IChronoField)
    (condp = field
      NANO_OF_SECOND (:nanos this)
      MICRO_OF_SECOND (/ (:nanos this) 1000)
      MILLI_OF_SECOND (/ (:nanos this) 1000000)
      INSTANT_SECONDS (:seconds this)
      (throw (UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field})))
    (TemporalField/getFrom field this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1054
(defn -query [this query]
  (if (= query (TemporalQueries/precision))
    NANOS
    (when-not (some #(= query %) [(TemporalQueries/chronology)
                                  (TemporalQueries/zoneId)
                                  (TemporalQueries/zone)
                                  (TemporalQueries/offset)
                                  (TemporalQueries/localDate)
                                  (TemporalQueries/localTime)])
      (TemporalQuery/queryFrom query this))))

(extend-type Instant
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field-or-unit] (-is-supported this field-or-unit))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1093
(defn -adjust-into [this temporal]
  (-> temporal
      (Temporal/with INSTANT_SECONDS (:seconds this))
      (Temporal/with NANO_OF_SECOND (:nanos this))))

(extend-type Instant
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L272
  ([] (Clock/instant (Clock/systemUTC)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L287
  ([clock] (Clock/instant clock)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L343
(defn ofEpochMilli [epoch-milli]
  (create-instant (math/floor-div epoch-milli 1000)
                  (int (* (math/floor-mod epoch-milli 1000)
                          1000000))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L367
(defn from [temporal]
  (if (instance? temporal IInstant)
    temporal
    (try
      (ofEpochSecond
       (TemporalAccessor/getLong temporal ChronoField/INSTANT_SECONDS)
       (TemporalAccessor/get temporal ChronoField/NANO_OF_SECOND))
      (catch js/Error e
        (throw (DateTimeException (str "Unable to obtain Instant from TemporalAccessor: "
                                       temporal " of type " (type temporal))
                                  {:temporal temporal
                                   :type (type temporal)}
                                  e))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L394
(defn parse [text]
  (DateTimeFormatter/parse DateTimeFormatter/ISO_INSTANT text from))

(def MIN (ofEpochSecond MIN_SECOND 0))
(def MAX (ofEpochSecond MAX_SECOND 999999999))
