(ns jiffy.instant
  (:refer-clojure :exclude [range get])
  (:require #?(:clj [clojure.spec.alpha :as s])
            #?(:cljs [cljs.spec.alpha :as s])
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.date-time-formatter :as date-time-formatter-impl]
            [jiffy.clock :as clock-impl]
            [jiffy.instant-impl :refer [#?@(:cljs [Instant])] :as impl]
            [jiffy.local-time-impl :refer [NANOS_PER_DAY NANOS_PER_SECOND SECONDS_PER_DAY SECONDS_PER_HOUR SECONDS_PER_MINUTE]]
            [jiffy.math :as math]
            [jiffy.offset-date-time :as offset-date-time]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field :refer [INSTANT_SECONDS MICRO_OF_SECOND MILLI_OF_SECOND NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as chrono-unit :refer [DAYS HALF_DAYS HOURS MICROS MILLIS MINUTES NANOS SECONDS]]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.zoned-date-time :as zoned-date-time-impl]
            [jiffy.instant-impl :as impl])
  #?(:clj (:import [jiffy.instant_impl Instant])))

(def EPOCH impl/EPOCH)
(def MAX_SECOND impl/MAX_SECOND)
(def MIN_SECOND impl/MIN_SECOND)

(s/def ::instant ::impl/instant)
(def create impl/create)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L343
(def-constructor of-epoch-milli ::instant
  [epoch-milli ::j/milli]
  (impl/of-epoch-milli epoch-milli))

(def-constructor of-epoch-second ::instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L303
  ([epoch-second ::j/second]
   (impl/of-epoch-second epoch-second))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L327
  ([epoch-second ::j/second
    nano-adjustment ::j/nano]
   (impl/of-epoch-second epoch-second nano-adjustment)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L617
(def-method get-epoch-second ::j/long
  [this ::instant]
  (:seconds this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L617
(def-method get-nano ::j/long
  [this ::instant]
  (:nanos this))


(declare from
         to-epoch-milli
         plus-nanos
         plus-seconds
         plus-millis
         plus-nanos)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L747
(def-method truncated-to ::impl/instant
  [this ::impl/instant
   unit ::temporal-unit/temporal-unit]
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

(defn- --plus [this seconds-to-add nanos-to-add]
  (if (and (zero? seconds-to-add) (zero? nanos-to-add))
    this
    (impl/of-epoch-second
     (-> (:seconds this)
         (math/add-exact (long seconds-to-add))
         (math/add-exact (long (math/floor-div nanos-to-add NANOS_PER_SECOND))))
     (+ (:nanos this) (mod nanos-to-add NANOS_PER_SECOND)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L877
(def-method plus-seconds ::impl/instant
  [this ::impl/instant
   seconds-to-add ::j/second]
  (--plus this seconds-to-add 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L891
(def-method plus-millis ::impl/instant
  [this ::impl/instant
   millis-to-add ::j/milli]
  (--plus this (math/floor-div millis-to-add 1000) (* (mod millis-to-add 1000) 1000000)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L905
(def-method plus-nanos ::impl/instant
  [this ::impl/instant
   nanos-to-add ::j/nano]
  (--plus this 0 nanos-to-add))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L992
(def-method minus-seconds ::impl/instant
  [this ::impl/instant
   seconds-to-subtract ::j/second]
  (if (= seconds-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plus-seconds math/long-max-value) (plus-seconds 1))
    (plus-seconds this (- seconds-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1009
(def-method minus-millis ::impl/instant
  [this ::impl/instant
   millis-to-subtract ::j/milli]
  (if (= millis-to-subtract math/long-min-value)
    ;; TODO: wtf? plus'ing beyond max-value?
    (-> this (plus-millis math/long-max-value) (plus-millis 1))
    (plus-millis this (- millis-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1026
(def-method minus-nanos ::impl/instant
  [this ::impl/instant
   nanos-to-subtract ::j/nano]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this (plus-nanos math/long-max-value) (plus-nanos 1))
    (plus-nanos this (- nanos-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/blob/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1230
(def-method to-epoch-milli ::j/long
  [this ::impl/instant]
  (if (and (neg? (:seconds this)) (pos? (:nanos this)))
    (let [millis (math/multiply-exact (inc (:seconds this)) 1000)
          adjustment (- (long (/ (:nanos this) 1000000)) 1000)]
      (math/add-exact millis adjustment))
    (let [millis (math/multiply-exact (:seconds this) 1000)]
      (math/add-exact millis (long (/ (:nanos this) 1000000))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1193
(def-method at-offset ::offset-date-time/offset-date-time
  [this ::impl/instant
   offset ::zone-offset/zone-offset]
  (offset-date-time/of-instant this offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1211
(def-method at-zone ::zoned-date-time/zoned-date-time
  [this ::impl/instant
   zone ::zone-id/zone-id]
  (zoned-date-time-impl/of-instant this zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1270
(def-method is-after ::j/boolean
  [this ::impl/instant
   other-instant ::impl/instant]
  (pos? (time-comparable/compare-to this other-instant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1283
(def-method is-before ::j/boolean
  [this ::impl/instant
   other-instant ::impl/instant]
  (neg? (time-comparable/compare-to this other-instant)))

(extend-type Instant
  instant/IInstant
  (get-epoch-second [this] (impl/get-epoch-second this))
  (get-nano [this] (impl/get-nano this))
  (truncated-to [this unit] (truncated-to this unit))
  (plus-seconds [this seconds-to-add] (plus-seconds this seconds-to-add))
  (plus-millis [this millis-to-add] (plus-millis this millis-to-add))
  (plus-nanos [this nanos-to-add] (plus-nanos this nanos-to-add))
  (minus-seconds [this seconds-to-subtract] (minus-seconds this seconds-to-subtract))
  (minus-millis [this millis-to-subtract] (minus-millis this millis-to-subtract))
  (minus-nanos [this nanos-to-subtract] (minus-nanos this nanos-to-subtract))
  (to-epoch-milli [this] (to-epoch-milli this))
  (at-offset [this offset] (at-offset this offset))
  (at-zone [this zone] (at-zone this zone))
  (is-after [this other-instant] (is-after this other-instant))
  (is-before [this other-instant] (is-before this other-instant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1253
(def-method compare-to ::j/int
  [this ::impl/instant
   other-instant ::impl/instant]
  (let [cmp (compare (:seconds this) (:seconds other-instant))]
    (if-not (zero? cmp)
      cmp
      (math/subtract-exact (:nanos this) (:nanos other-instant)))))

(extend-type Instant
  time-comparable/ITimeComparable
  (compare-to [this other-instant] (compare-to this other-instant)))

(def-method with ::temporal/temporal
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L654
  ([this ::impl/instant
    adjuster ::temporal-adjuster/temporal-adjuster]
   (temporal-adjuster/adjust-into adjuster this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L703
  ([this ::impl/instant
    field ::temporal-field/temporal-field
    new-value ::j/long]
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

(def-method plus ::temporal/temporal
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L786
  ([this ::impl/instant
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L849
  ([this ::impl/instant
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (chrono-unit/chrono-unit? unit)
     (condp = unit
       NANOS (plus-nanos this amount-to-add)
       MICROS (--plus this (long (/ amount-to-add 1000000)) (* (rem amount-to-add 1000000) 1000))
       MILLIS (plus-millis this amount-to-add)
       SECONDS (plus-seconds this amount-to-add)
       MINUTES (plus-seconds this (math/multiply-exact (long amount-to-add) SECONDS_PER_MINUTE))
       HOURS (plus-seconds this (math/multiply-exact (long amount-to-add) SECONDS_PER_HOUR))
       HALF_DAYS (plus-seconds this (math/multiply-exact (long amount-to-add) (/ SECONDS_PER_DAY 2)))
       DAYS (plus-seconds this (math/multiply-exact (long amount-to-add) (int SECONDS_PER_DAY)))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit))))
     (temporal-unit/add-to unit this amount-to-add))))

(def-method minus ::impl/instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L953
  ([this ::impl/instant
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L977
  ([this ::impl/instant
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (temporal/plus math/long-max-value unit)
         (temporal/plus 1 unit))
     (temporal/plus this (- amount-to-subtract) unit))))

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
(def-method until ::j/long
  [this ::impl/instant
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if (chrono-unit/chrono-unit? unit)
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

(extend-type Instant
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this field new-value] (with this field new-value)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

(defn --is-supported-field [this field]
  (if (satisfies? chrono-field/IChronoField field)
    (some? (some #(= field %) [INSTANT_SECONDS NANO_OF_SECOND MICRO_OF_SECOND MILLI_OF_SECOND]))
    (and field (temporal-field/is-supported-by field this))))

(defn --is-supported-unit [this unit]
  (if (chrono-unit/chrono-unit? unit)
    (or (temporal-unit/is-time-based unit) (= unit DAYS))
    (and unit (temporal-unit/is-supported-by unit this))))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L457
;; TODO: clean up dispatch via multimethods
(def-method is-supported ::j/boolean
  [this ::impl/instant
   field-or-unit ::temporal-unit/temporal-unit]
  (cond
    (satisfies? temporal-field/ITemporalField field-or-unit)
    (--is-supported-field this field-or-unit)

    (satisfies? temporal-unit/ITemporalUnit field-or-unit)
    (--is-supported-unit this field-or-unit)

    :else
    (throw (ex-info "Unsupported field or unit" {:instant this :field-or-unit field-or-unit}))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L526
(def-method range ::value-range/value-range
  [this ::impl/instant
   field ::temporal-field/temporal-field]
  (temporal-accessor-defaults/-range this field))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L558
(def-method get ::j/int
  [this ::impl/instant
   field ::temporal-field/temporal-field]
  (if (satisfies? chrono-field/IChronoField field)
    (long
     (condp = field
       NANO_OF_SECOND (:nanos this)
       MICRO_OF_SECOND (/ (:nanos this) 1000)
       MILLI_OF_SECOND (/ (:nanos this) 1000000)
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field}))))
    (value-range/check-valid-int-value
     (range this field)
     (temporal-field/get-from field this)
     field)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L594
(def-method get-long ::j/long
  [this ::impl/instant
   field ::temporal-field/temporal-field]
  (if (satisfies? chrono-field/IChronoField field)
    (long
     (condp = field
       NANO_OF_SECOND (:nanos this)
       MICRO_OF_SECOND (/ (:nanos this) 1000)
       MILLI_OF_SECOND (/ (:nanos this) 1000000)
       INSTANT_SECONDS (:seconds this)
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:instant this :field field}))))
    (temporal-field/get-from field this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1054
(def-method query ::j/any
  [this ::impl/instant
   q ::temporal-query/temporal-query]
  (if (= q (temporal-queries/precision))
    NANOS
    (when-not (some #(= q %) [(temporal-queries/chronology)
                              (temporal-queries/zone-id)
                              (temporal-queries/zone)
                              (temporal-queries/offset)
                              (temporal-queries/local-date)
                              (temporal-queries/local-time)])
      (temporal-query/query-from q this))))

(extend-type Instant
  temporal-accessor/ITemporalAccessor
  (is-supported [this field-or-unit] (is-supported this field-or-unit))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L1093
(def-method adjust-into ::temporal/temporal
  [this ::impl/instant
   temporal ::temporal/temporal]
  (-> temporal
      (temporal/with INSTANT_SECONDS (:seconds this))
      (temporal/with NANO_OF_SECOND (:nanos this))))

(extend-type Instant
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor now ::impl/instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L272
  ([] (clock/instant (clock-impl/system-utc)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L287
  ([clock ::clock/clock] (clock/instant clock)))

(def MIN (impl/of-epoch-second MIN_SECOND 0))
(def MAX (impl/of-epoch-second MAX_SECOND 999999999))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L367
(def-constructor from ::impl/instant
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? instant/IInstant temporal)
    temporal
    (try*
     (impl/of-epoch-second
      (temporal-accessor/get-long temporal chrono-field/INSTANT_SECONDS)
      (temporal-accessor/get temporal chrono-field/NANO_OF_SECOND))
     (catch :default e
       (throw (ex DateTimeException
                  (str "Unable to obtain Instant from TemporalAccessor: "
                       temporal " of type " (type temporal))
                  {:temporal temporal
                   :type (type temporal)}
                  e))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Instant.java#L394
(def-constructor parse ::impl/instant
  [text ::j/char-sequence]
  (date-time-formatter/parse date-time-formatter-impl/ISO_INSTANT text from))
