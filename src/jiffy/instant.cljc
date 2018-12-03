(ns jiffy.instant
  (:require [clojure.spec.alpha :as s]
            [jiffy.clock :as clock]
            #?(:clj [jiffy.conversion :as conversion])
            [jiffy.duration :as duration]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.date-time-formatter :as date-time-formatter]
            [jiffy.local-time-impl :refer [NANOS_PER_DAY NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as offset-date-time]
            [jiffy.temporal.chrono-field :as chrono-field :refer [INSTANT_SECONDS MICRO_OF_SECOND MILLI_OF_SECOND NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as chrono-unit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS]]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-amount :as temporal-amount]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.temporal-unit :as temporal-unit]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zoned-date-time :as zoned-date-time]
            [jiffy.instant-impl :refer [create #?@(:cljs [Instant])] :as impl]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults])
  #?(:clj (:import [jiffy.instant_impl Instant])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java

(defprotocol IInstant
  (get-epoch-second [this])
  (get-nano [this])
  (truncated-to [this unit])
  (plus-seconds [this seconds-to-add])
  (plus-millis [this millis-to-add])
  (plus-nanos [this nanos-to-add])
  (minus-seconds [this seconds-to-subtract])
  (minus-millis [this millis-to-subtract])
  (minus-nanos [this nanos-to-subtract])
  (to-epoch-milli [this])
  (at-offset [this offset])
  (at-zone [this zone])
  (is-after [this other-instant])
  (is-before [this other-instant]))

(def EPOCH impl/EPOCH)
(def MAX_SECOND impl/MAX_SECOND)
(def MIN_SECOND impl/MIN_SECOND)

(s/def ::instant ::impl/instant)

(defmacro args [& x] `(s/tuple ::instant ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L747
(s/def ::truncated-to-args (args ::temporal-unit/temporal-unit))
(defn -truncated-to [this unit]
  (let [unit-dur (temporal-unit/get-duration unit)
        dur (delay (duration/to-nanos unit-dur))]
    (cond
      (= unit NANOS)
      this

      (> (duration/get-seconds unit-dur) SECONDS_PER_DAY)
      (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:instant this :unit unit}))

      (-> NANOS_PER_DAY (mod @dur) zero? not)
      (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:instant this :unit unit}))

      :else
      (let [nod (-> (mod (:seconds this) SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result (-> nod
                       (math/floor-div @dur)
                       (* @dur))]
        (plus-nanos this (- result nod))))))

(s/def ::of-epoch-second-args ::impl/of-epoch-second-args)
(def of-epoch-second #'impl/of-epoch-second)
(s/fdef of-epoch-second :args ::of-epoch-second-args :ret ::instant)

(defn- --plus [this seconds-to-add nanos-to-add]
  (if (and (zero? seconds-to-add) (zero? nanos-to-add))
    this
    (of-epoch-second
     (-> (:seconds this)
         (math/add-exact (long seconds-to-add))
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
    (-> this (plus-seconds math/long-max-value) (plus-seconds 1))
    (plus-seconds this (- seconds-to-subtract))))
(s/fdef -minus-seconds :args ::minus-seconds-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1009
(s/def ::minus-millis-args (args ::j/milli))
(defn -minus-millis [this millis-to-subtract]
  (if (= millis-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plus-millis math/long-max-value) (plus-millis 1))
    (plus-millis this (- millis-to-subtract))))
(s/fdef -minus-millis :args ::minus-millis-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1026
(s/def ::minus-nanos-args (args ::j/nano))
(defn -minus-nanos [this nanos-to-subtract]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this (plus-nanos math/long-max-value) (plus-nanos 1))
    (plus-nanos this (- nanos-to-subtract))))
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
(s/def ::at-offset-args (args ::zone-offset/zone-offset))
(defn -at-offset [this offset]
  (offset-date-time/of-instant this offset))
(s/fdef -at-offset :args ::at-offset-args :ret ::offset-date-time/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1211
(s/def ::at-zone-args (args ::zone-id/zone-id))
(defn -at-zone [this zone]
  (zoned-date-time/of-instant this zone))
(s/fdef -at-zone :args ::at-zone-args :ret ::zoned-date-time/zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1270
(s/def ::is-after-args (args ::instant))
(defn -is-after [this other-instant]
  (pos? (time-comparable/compare-to this other-instant)))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1283
(s/def ::is-before-args (args ::instant))
(defn -is-before [this other-instant]
  (neg? (time-comparable/compare-to this other-instant)))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L617
(s/def ::get-epoch-second-args ::impl/get-epoch-second-args)
(s/def ::get-nano-args ::impl/get-nano-args)

(extend-type Instant
  IInstant
  (get-epoch-second [this] (impl/-get-epoch-second this))
  (get-nano [this] (impl/-get-nano this))
  (truncated-to [this unit] (-truncated-to this unit))
  (plus-seconds [this seconds-to-add] (-plus-seconds this seconds-to-add))
  (plus-millis [this millis-to-add] (-plus-millis this millis-to-add))
  (plus-nanos [this nanos-to-add] (-plus-nanos this nanos-to-add))
  (minus-seconds [this seconds-to-subtract] (-minus-seconds this seconds-to-subtract))
  (minus-millis [this millis-to-subtract] (-minus-millis this millis-to-subtract))
  (minus-nanos [this nanos-to-subtract] (-minus-nanos this nanos-to-subtract))
  (to-epoch-milli [this] (-to-epoch-milli this))
  (at-offset [this offset] (-at-offset this offset))
  (at-zone [this zone] (-at-zone this zone))
  (is-after [this other-instant] (-is-after this other-instant))
  (is-before [this other-instant] (-is-before this other-instant)))

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
  time-comparable/ITimeComparable
  (compare-to [this other-instant] (-compare-to this other-instant)))

(s/def ::with-args (s/or :arity-2 (args ::temporal-adjuster/temporal-adjuster)
                         :arity-3 (args ::temporal-field/temporal-field ::j/long)))
(defn -with
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L654
  ([this adjuster]
   (temporal-adjuster/adjust-into adjuster this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L703
  ([this field new-value]
   (if-not (satisfies? chrono-field/IChronoField field)
     (temporal-field/adjust-into field this new-value)
     (do
       (chrono-field/check-valid-value field new-value)
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
(s/fdef -with :args ::with-args :ret ::temporal/temporal)

(s/def ::plus-args (s/or :arity-2 (args ::temporal-amount/temporal-amount)
                         :arity-3 (args ::j/long ::temporal-unit/temporal-unit)))
(defn -plus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L786
  ([this amount-to-add]
   (temporal-amount/add-to amount-to-add this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L849
  ([this amount-to-add unit]
   (if (satisfies? chrono-unit/IChronoUnit unit)
     (condp = unit
       NANOS (plus-nanos this amount-to-add)
       MICROS (--plus this (long (/ amount-to-add 1000000)) (* (rem amount-to-add 1000000) 1000))
       MILLIS (plus-millis this amount-to-add)
       SECONDS (plus-seconds this amount-to-add)
       MINUTES (plus-seconds this (math/multiply-exact amount-to-add SECONDS_PER_MINUTE))
       HOURS (plus-seconds this (math/multiply-exact amount-to-add SECONDS_PER_HOUR))
       HALF_DAYS (plus-seconds this (math/multiply-exact amount-to-add (/ SECONDS_PER_DAY 2)))
       DAYS (plus-seconds this (math/multiply-exact amount-to-add (int SECONDS_PER_DAY)))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit))))
     (temporal-unit/add-to unit this amount-to-add))))
(s/fdef -plus :args ::plus-args :ret ::temporal/temporal)

(s/def ::minus-args (s/or :arity-2 (args ::temporal-amount/temporal-amount)
                          :arity-3 (args ::j/long ::temporal-unit/temporal-unit)))
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L953
  ([this amount-to-subtract]
   (temporal-amount/subtract-from amount-to-subtract this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L977
  ([this amount-to-subtract unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (temporal/plus math/long-max-value unit)
         (temporal/plus 1 unit))
     (temporal/plus this (- amount-to-subtract) unit))))
(s/fdef -minus :args ::minus-args :ret ::instant)

(declare from)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1161
;; TODO: remove?
(defn- nanos-until [this end]
  (let [secs-diff (math/subtract-exact (:seconds end) (:seconds this))
        total-nanos (math/multiply-exact secs-diff NANOS_PER_SECOND)]
    (math/add-exact total-nanos (- (:nanos end) (:nanos this)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1167
;; TODO: remove?
(defn- seconds-until [this end]
  (let [secs-diff (math/subtract-exact (:seconds end) (:seconds this))
        nanos-diff (- (:nanos end) (:nanos this))]
    (cond
      (and (pos? secs-diff) (neg? nanos-diff))
      (dec secs-diff)

      (and (neg? secs-diff) (pos? nanos-diff))
      (inc secs-diff)

      :else secs-diff)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1142
(s/def ::until-args (args ::temporal/temporal ::temporal-unit/temporal-unit))
(defn -until [this end-exclusive unit]
  (let [end (from end-exclusive)]
    (if (satisfies? chrono-unit/IChronoUnit unit)
      (long
       (condp = unit
         NANOS (nanos-until this end)
         MICROS (-> (nanos-until this end) (/ 1000))
         MILLIS (math/subtract-exact (to-epoch-milli end) (to-epoch-milli this))
         SECONDS (seconds-until this end)
         MINUTES (-> (seconds-until this end) (/ SECONDS_PER_MINUTE))
         HOURS (-> (seconds-until this end) (/ SECONDS_PER_HOUR))
         HALF_DAYS (-> (seconds-until this end) (/ (* 12 SECONDS_PER_HOUR)))
         DAYS (-> (seconds-until this end) (/ SECONDS_PER_DAY))
         (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit) {:instant this :unit unit}))))
      (temporal-unit/between unit this end))))
(s/fdef -until :args ::until-args :ret ::j/long)

(extend-type Instant
  temporal/ITemporal
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
  (if (satisfies? chrono-field/IChronoField field)
    (some? (some #(= field %) [INSTANT_SECONDS NANO_OF_SECOND MICRO_OF_SECOND MILLI_OF_SECOND]))
    (and field (temporal-field/is-supported-by field this))))

(defn --is-supported-unit [this unit]
  (if (satisfies? chrono-unit/IChronoUnit unit)
    (or (temporal-unit/is-time-based unit) (= unit DAYS))
    (and unit (temporal-unit/is-supported-by unit this))))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L457
(s/def ::is-supported-args (args ::temporal-unit/temporal-unit))
;; TODO: clean up dispatch via multimethods
(defn -is-supported [this field-or-unit]
  (cond
    (satisfies? temporal-field/ITemporalField field-or-unit)
    (--is-supported-field this field-or-unit)

    (satisfies? temporal-unit/ITemporalUnit field-or-unit)
    (--is-supported-unit this field-or-unit)

    :else
    (throw (ex-info "Unsupported field or unit" {:instant this :field-or-unit field-or-unit}))))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L526
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field]
  (temporal-accessor-defaults/-range this field))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L558
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field]
  (if (satisfies? chrono-field/IChronoField field)
    (long
     (condp = field
       NANO_OF_SECOND (:nanos this)
       MICRO_OF_SECOND (/ (:nanos this) 1000)
       MILLI_OF_SECOND (/ (:nanos this) 1000000)
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field}))))
    (value-range/check-valid-int-value
     (-range this field)
     (temporal-field/get-from field this)
     field)))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L594
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field]
  (if (satisfies? chrono-field/IChronoField field)
    (long
     (condp = field
       NANO_OF_SECOND (:nanos this)
       MICRO_OF_SECOND (/ (:nanos this) 1000)
       MILLI_OF_SECOND (/ (:nanos this) 1000000)
       INSTANT_SECONDS (:seconds this)
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field}))))
    (temporal-field/get-from field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1054
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query]
  (if (= query (temporal-queries/precision))
    NANOS
    (when-not (some #(= query %) [(temporal-queries/chronology)
                                  (temporal-queries/zone-id)
                                  (temporal-queries/zone)
                                  (temporal-queries/offset)
                                  (temporal-queries/local-date)
                                  (temporal-queries/local-time)])
      (temporal-query/query-from query this))))
(s/fdef -query :args ::query-args :ret ::j/any)

(extend-type Instant
  temporal-accessor/ITemporalAccessor
  (is-supported [this field-or-unit] (-is-supported this field-or-unit))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1093
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal]
  (-> temporal
      (temporal/with INSTANT_SECONDS (:seconds this))
      (temporal/with NANO_OF_SECOND (:nanos this))))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type Instant
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

(s/def ::now-args (s/cat :clock (s/? ::clock/clock)))
(defn now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L272
  ([] (clock/instant (clock/system-utc)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L287
  ([clock] (clock/instant clock)))
(s/fdef now :args ::now-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L343
(s/def ::of-epoch-milli-args ::impl/of-epoch-milli-args)
(def of-epoch-milli #'impl/of-epoch-milli)
(s/fdef of-epoch-milli :args ::of-epoch-milli-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L367
(s/def ::from-args (s/tuple ::temporal-accessor/temporal-accessor))
(defn from [temporal]
  (if (satisfies? IInstant temporal)
    temporal
    (try*
     (of-epoch-second
      (temporal-accessor/get-long temporal chrono-field/INSTANT_SECONDS)
      (temporal-accessor/get temporal chrono-field/NANO_OF_SECOND))
     (catch :default e
       (throw (ex DateTimeException
                  (str "Unable to obtain Instant from TemporalAccessor: "
                       temporal " of type " (type temporal))
                  {:temporal temporal
                   :type (type temporal)}
                  e))))))
(s/fdef from :args ::from-args :ret ::instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L394
(s/def ::parse-args (args ::j/char-sequence))
(defn parse [text]
  (date-time-formatter/parse date-time-formatter/ISO_INSTANT text from))
(s/fdef parse :args ::parse-args :ret ::instant)

(def MIN (of-epoch-second MIN_SECOND 0))
(def MAX (of-epoch-second MAX_SECOND 999999999))

#?(:clj
   (defmethod conversion/jiffy->java Instant [{:keys [seconds nanos]}]
     (.plusNanos (java.time.Instant/ofEpochSecond seconds) nanos)))

#?(:clj
   (defmethod conversion/same? Instant
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:seconds :nanos])
        (map #(% java-object) [(memfn getEpochSecond) (memfn getNano)]))))
