(ns jiffy.instant
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as Clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration :as Duration]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.local-time-impl :refer [NANOS_PER_DAY NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as OffsetDateTime]
            [jiffy.temporal.chrono-field :as ChronoField :refer [INSTANT_SECONDS MICRO_OF_SECOND MILLI_OF_SECOND NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as ChronoUnit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS]]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-amount :as TemporalAmount]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-queries :as TemporalQueries]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.temporal-unit :as TemporalUnit]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zoned-date-time :as ZonedDateTime]
            [jiffy.instant-impl :refer [create #?@(:cljs [Instant])] :as impl]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset :as ZoneOffset]
            [jiffy.specs :as j])
  #?(:clj (:import [jiffy.instant_impl Instant])))

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

(def EPOCH impl/EPOCH)
(def MAX_SECOND impl/MAX_SECOND)
(def MIN_SECOND impl/MIN_SECOND)

(s/def ::instant ::impl/instant)

(defmacro args [& x] `(s/tuple ::instant ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L747
(defn -truncated-to [this unit]
  (let [unit-dur (TemporalUnit/getDuration unit)
        dur (Duration/toNanos unit-dur)]
    (cond
      (= unit NANOS)
      this

      (> (Duration/getSeconds unit-dur) SECONDS_PER_DAY)
      (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:instant this :unit unit}))

      (-> NANOS_PER_DAY (mod dur) zero? not)
      (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:instant this :unit unit}))

      :else
      (let [nod (-> (mod (:seconds this) SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result (-> nod
                       (math/floor-div dur)
                       (* dur))]
        (plusNanos this (- result nod))))))

(s/def ::of-epoch-second-args ::impl/of-epoch-second-args)
(def ofEpochSecond #'impl/ofEpochSecond)
(s/fdef ofEpochSecond :args ::of-epoch-second-args :ret ::instant)

(defn- --plus [this seconds-to-add nanos-to-add]
  (if (and (zero? seconds-to-add) (zero? nanos-to-add))
    this
    (ofEpochSecond
     (-> (:seconds this)
         (math/add-exact seconds-to-add)
         (math/add-exact (long (math/floor-div nanos-to-add NANOS_PER_SECOND))))
     (+ (:nanos this) (mod nanos-to-add NANOS_PER_SECOND)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L877
(s/def ::plus-seconds-args (args ::j/second))
(defn -plus-seconds [this seconds-to-add]
  (--plus this seconds-to-add 0))
(s/fdef -plus-seconds :args ::plus-seconds-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L891
(s/def ::plus-millis-args (args ::j/milli))
(defn -plus-millis [this millis-to-add]
  (--plus this (math/floor-div millis-to-add 1000) (* (mod millis-to-add 1000) 1000000)))
(s/fdef -plus-millis :args ::plus-millis-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L905
(s/def ::plus-nanos-args (args ::j/nano))
(defn -plus-nanos [this nanos-to-add]
  (--plus this 0 nanos-to-add))
(s/fdef -plus-nanos :args ::plus-nanos-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L992
(s/def ::minus-seconds-args (args ::j/second))
(defn -minus-seconds [this seconds-to-subtract]
  (if (= seconds-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plusSeconds math/long-max-value) (plusSeconds 1))
    (plusSeconds this (- seconds-to-subtract))))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1009
(s/def ::minus-millis-args (args ::j/milli))
(defn -minus-millis [this millis-to-subtract]
  (if (= millis-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plusMillis math/long-max-value) (plusMillis 1))
    (plusMillis this (- millis-to-subtract))))
(s/fdef -minus-millis :args ::minus-millis-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1026
(s/def ::minus-nanos-args (args ::j/nano))
(defn -minus-nanos [this nanos-to-subtract]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this (plusNanos math/long-max-value) (plusNanos 1))
    (plusNanos this (- nanos-to-subtract))))
(s/fdef -minus-nanos :args ::minus-nanos-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1149
(s/def ::to-epoch-milli-args (args))
(defn -to-epoch-milli [this]
  (if (and (neg? (:seconds this)) (pos? (:nanos this)))
    (let [millis (math/multiply-exact (inc (:seconds this)) 1000)
          adjustment (- (long (/ (:nanos this) 1000000)) 1000)]
      (math/add-exact millis adjustment))
    (let [millis (math/multiply-exact (:seconds this) 1000)]
      (math/add-exact millis (long (/ (:nanos this) 1000000))))))
(s/fdef -to-epoch-milli :args ::to-epoch-milli-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1193
(s/def ::at-offset-args (args ::ZoneOffset/zone-offset))
(defn -at-offset [this offset]
  (OffsetDateTime/ofInstant this offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::OffsetDateTime/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1211
(s/def ::at-zone-args (args ::ZoneId/zone-id))
(defn -at-zone [this zone]
  (ZonedDateTime/ofInstant this zone))
(s/fdef -at-zone :args ::at-zone-args :ret ::ZonedDateTime/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1270
(s/def ::is-after-args (args ::instant))
(defn -is-after [this other-instant]
  (pos? (TimeComparable/compareTo this other-instant)))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1283
(s/def ::is-before-args (args ::instant))
(defn -is-before [this other-instant]
  (neg? (TimeComparable/compareTo this other-instant)))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L617
(s/def ::get-epoch-second-args ::impl/get-epoch-second-args)
(s/def ::get-nano-args ::impl/get-nano-args)

(extend-type Instant
  IInstant
  (getEpochSecond [this] (impl/-get-epoch-second this))
  (getNano [this] (impl/-get-nano this))
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
(s/def ::compare-to-args (args ::instant))
(defn -compare-to [this other-instant]
  (let [cmp (compare (:seconds this) (:seconds other-instant))]
    (if-not (zero? cmp)
      cmp
      (- (:nanos this) (:nanos other-instant)))))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type Instant
  TimeComparable/ITimeComparable
  (compareTo [this other-instant] (-compare-to this other-instant)))

(s/def ::with-args (args ::j/wip))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L654
  ([this adjuster]
   (TemporalAdjuster/adjustInto adjuster this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L703
  ([this field new-value]
   (if-not (satisfies? ChronoField/IChronoField field)
     (TemporalField/adjustInto field this new-value)
     (do
       (ChronoField/checkValidValue field new-value)
       (condp = field
         MILLI_OF_SECOND
         (let [val (* new-value 1000000)]
           (if (= val (:nanos this))
             this
             (create (:seconds this) val)))

         MICRO_OF_SECOND
         (let [val (* new-value 1000)]
           (if (= val (:nanos this))
             this
             (create (:seconds this) val)))

         NANO_OF_SECOND
         (if (= new-value (:nanos this))
           this
           (create (:seconds this) new-value))

         INSTANT_SECONDS
         (if (= new-value (:seconds this))
           this
           (create new-value (:nanos this)))

         (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field})))))))
(s/fdef -with :args ::with-args :ret ::Temporal/temporal)

(s/def ::plus-args (args ::j/wip))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L786
  ([this amount-to-add]
   (TemporalAmount/addTo amount-to-add this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L849
  ([this amount-to-add unit]
   (if (satisfies? ChronoUnit/IChronoUnit unit)
     (condp = unit
       NANOS (plusNanos this amount-to-add)
       MICROS (--plus this (/ amount-to-add 1000000) (* (mod amount-to-add 1000000) 1000))
       MILLIS (plusMillis this amount-to-add)
       SECONDS (plusSeconds this amount-to-add)
       MINUTES (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_MINUTE))
       HOURS (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_HOUR))
       HALF_DAYS (plusSeconds this (math/multiply-exact amount-to-add (/ SECONDS_PER_DAY 2)))
       DAYS (plusSeconds this (math/multiply-exact amount-to-add SECONDS_PER_DAY))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit))))
     (TemporalUnit/addTo unit this amount-to-add))))
(s/fdef -plus :args ::plus-args :ret ::Temporal/temporal)

(s/def ::minus-args (args ::j/wip))
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
(s/fdef -minus :args ::minus-args :ret ::instant)

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
(s/def ::until-args (args ::Temporal/temporal ::TemporalUnit/temporal-unit))
(defn -until [this end-exclusive unit]
  (let [end (from end-exclusive)]
    (if (satisfies? ChronoUnit/IChronoUnit unit)
      (condp = unit
        NANOS (nanosUntil this end)
        MICROS (-> (nanosUntil this end) (/ 1000))
        MILLIS (math/subtract-exact (toEpochMilli end) (toEpochMilli this))
        SECONDS (secondsUntil this end)
        MINUTES (-> (secondsUntil this end) (/ SECONDS_PER_MINUTE))
        HOURS (-> (secondsUntil this end) (/ SECONDS_PER_HOUR))
        HALF_DAYS (-> (secondsUntil this end) (/ (* 12 SECONDS_PER_HOUR)))
        DAYS (-> (secondsUntil this end) (/ SECONDS_PER_DAY))
        (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit) {:instant this :unit unit})))
      (TemporalUnit/between unit this end))))
(s/fdef -until :args ::until-args :ret ::j/long)

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
  (if (satisfies? ChronoField/IChronoField field)
    (some? (some #(= field %) [INSTANT_SECONDS NANO_OF_SECOND MICRO_OF_SECOND MILLI_OF_SECOND]))
    (and field (TemporalField/isSupportedBy field this))))

(defn --is-supported-unit [this unit]
  (if (satisfies? ChronoUnit/IChronoUnit unit)
    (or (TemporalUnit/isTimeBased unit) (= unit DAYS))
    (and unit (TemporalUnit/isSupportedBy unit this))))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L457
(s/def ::is-supported-args (args ::TemporalUnit/temporal-unit))
;; TODO: clean up dispatch via multimethods
(defn -is-supported [this field-or-unit]
  (cond
    (satisfies? TemporalField/ITemporalField field-or-unit)
    (--is-supported-field this  field-or-unit)

    (satisfies? TemporalUnit/ITemporalUnit field-or-unit)
    (--is-supported-unit this field-or-unit)

    :else
    (wip ::-is-supported)))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L526
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field]
  (TemporalAccessor/range this field))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L558
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field]
  (if (satisfies? ChronoField/IChronoField field)
    (condp = field
      NANO_OF_SECOND (:nanos this)
      MICRO_OF_SECOND (/ (:nanos this) 1000)
      MILLI_OF_SECOND (/ (:nanos this) 1000000)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field})))
    (ValueRange/checkValidIntValue
     (-range this field)
     (TemporalField/getFrom field this)
     field)))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L594
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field]
  (if (satisfies? ChronoField/IChronoField field)
    (condp = field
      NANO_OF_SECOND (:nanos this)
      MICRO_OF_SECOND (/ (:nanos this) 1000)
      MILLI_OF_SECOND (/ (:nanos this) 1000000)
      INSTANT_SECONDS (:seconds this)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field})))
    (TemporalField/getFrom field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1054
(s/def ::query-args (args ::TemporalQuery/temporal-query))
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
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Instant
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field-or-unit] (-is-supported this field-or-unit))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1093
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal]
  (-> temporal
      (Temporal/with INSTANT_SECONDS (:seconds this))
      (Temporal/with NANO_OF_SECOND (:nanos this))))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type Instant
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (args ::j/wip))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L272
  ([] (Clock/instant (Clock/systemUTC)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L287
  ([clock] (Clock/instant clock)))
(s/fdef now :args ::now-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L343
(s/def ::of-epoch-milli-args ::impl/of-epoch-milli-args)
(def ofEpochMilli #'impl/ofEpochMilli)
(s/fdef ofEpochMilli :args ::of-epoch-milli-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L367
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal]
  (if (satisfies? IInstant temporal)
    temporal
    (try*
     (ofEpochSecond
      (TemporalAccessor/getLong temporal ChronoField/INSTANT_SECONDS)
      (TemporalAccessor/get temporal ChronoField/NANO_OF_SECOND))
     (catch :default e
       (throw (ex DateTimeException
                  (str "Unable to obtain Instant from TemporalAccessor: "
                       temporal " of type " (type temporal))
                  {:temporal temporal
                   :type (type temporal)}
                  e))))))
(s/fdef from :args ::from-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L394
(s/def ::parse-args (args ::j/wip))
(defn parse [text]
  (DateTimeFormatter/parse DateTimeFormatter/ISO_INSTANT text from))
(s/fdef parse :args ::parse-args :ret ::instant)

(def MIN (ofEpochSecond MIN_SECOND 0))
(def MAX (ofEpochSecond MAX_SECOND 999999999))
