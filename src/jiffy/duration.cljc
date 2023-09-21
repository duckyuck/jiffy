(ns jiffy.duration
  (:refer-clojure :exclude [abs])
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as gen]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.asserts :refer [require-non-nil]]
            [jiffy.math.big-decimal :as big-decimal]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.duration-impl :refer [create #?@(:cljs [Duration])] :as impl]
            [jiffy.exception :refer [DateTimeParseException JavaArithmeticException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.local-time-impl :refer [NANOS_PER_SECOND NANOS_PER_DAY SECONDS_PER_DAY SECONDS_PER_MINUTE SECONDS_PER_HOUR NANOS_PER_MILLI MINUTES_PER_HOUR]]
            [jiffy.math :as math]
            [jiffy.temporal.chrono-field :as chrono-field :refer [NANO_OF_SECOND]]
            [jiffy.temporal.chrono-unit :as chrono-unit :refer [DAYS MICROS MILLIS NANOS SECONDS]]
            [jiffy.specs :as j]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.string :as string]
            [jiffy.local-time-impl :as local-time])
  #?(:clj (:import [jiffy.duration_impl Duration])))

(def ZERO impl/ZERO)

(s/def ::duration ::impl/duration)

(def-method get-seconds ::j/long
  [this ::duration]
  (:seconds this))

(def-method get-nano ::j/int
  [this ::duration]
  (:nanos this))

(def-method is-zero ::j/boolean
  [this ::duration]
  (zero? (bit-or (:seconds this) (:nanos this))))

(def-method is-negative ::j/boolean
  [this ::duration]
  (neg? (:seconds this)))

(def-method with-seconds ::duration
  [this ::duration
   seconds ::j/long]
  (create seconds (:nanos this)))

(def-method with-nanos ::duration
  [this ::duration
   nano-of-second ::j/nano-of-second]
  (chrono-field/check-valid-int-value NANO_OF_SECOND nano-of-second)
  (create (:seconds this) nano-of-second))

(def-method of-seconds ::duration
  ([seconds ::j/long]
   (impl/of-seconds seconds))

  ([seconds ::j/long
    nano-adjustment ::j/long]
   (impl/of-seconds seconds nano-adjustment)))

(declare -plus
         -plus-nanos
         -plus-millis
         -plus-seconds
         -plus-minutes
         -plus-hours
         -multiplied-by
         -negated
         -to-hours
         -to-minutes
         -to-nanos
         -get-seconds)

(defn- --plus [this seconds-to-add nanos-to-add]
  (if (zero? (bit-or seconds-to-add nanos-to-add))
    this
    (do
      (let [epoch-sec (-> (:seconds this)
                          (math/add-exact seconds-to-add)
                          (math/add-exact (long (/ nanos-to-add NANOS_PER_SECOND))))
            nano-adjustment (-> (rem nanos-to-add NANOS_PER_SECOND)
                                (+ (:nanos this)))]
        (of-seconds epoch-sec nano-adjustment)))))

(def-method plus ::duration
  ([this ::duration
    duration ::duration]
   (--plus this (:seconds duration) (:nanos duration)))

  ([this ::duration
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (cond
     (= unit DAYS)
     (--plus this (math/multiply-exact amount-to-add SECONDS_PER_DAY) 0)

     (temporal-unit/is-duration-estimated unit)
     (throw (ex UnsupportedTemporalTypeException "Unit must not have an estimated duration" {:duration this :unit unit}))

     (zero? amount-to-add)
     this

     (chrono-unit/chrono-unit? unit)
     (condp = unit
       NANOS (duration/plus-nanos this amount-to-add)
       MICROS (-> this
                  (duration/plus-seconds (-> amount-to-add (/ (* 1000000 1000)) long (* 1000)))
                  (duration/plus-nanos (-> amount-to-add (rem (* 1000000 1000)) (* 1000))))
       MILLIS (duration/plus-millis this amount-to-add)
       SECONDS (duration/plus-seconds this amount-to-add)
       (duration/plus-seconds this (-> unit temporal-unit/get-duration :seconds (math/multiply-exact amount-to-add))))

     :else
     (let [duration (-> unit temporal-unit/get-duration (duration/multiplied-by amount-to-add))]
       (-> this
           (duration/plus-seconds (:seconds duration))
           (duration/plus-nanos (:nanos duration)))))))

(def-method plus-days ::duration
  [this ::duration
   days-to-add ::j/long]
  (--plus this (math/multiply-exact days-to-add SECONDS_PER_DAY) 0))

(def-method plus-hours ::duration
  [this ::duration
   hours-to-add ::j/long]

  (--plus this (math/multiply-exact hours-to-add SECONDS_PER_HOUR) 0))

(def-method plus-minutes ::duration
  [this ::duration
   minutes-to-add ::j/long]
  (--plus this (math/multiply-exact minutes-to-add SECONDS_PER_MINUTE) 0))

(def-method plus-seconds ::duration
  [this ::duration
   seconds-to-add ::j/long]
  (--plus this seconds-to-add 0))

(def-method plus-millis ::duration
  [this ::duration
   millis-to-add ::j/long]
  (--plus this (long (/ millis-to-add 1000)) (* (rem millis-to-add 1000) 1000000)))

(def-method plus-nanos ::duration
  [this ::duration
   nanos-to-add ::j/long]
  (--plus this 0 nanos-to-add))

(def-method minus ::duration
  ([this ::duration
    duration ::duration]
   (if (= (:seconds duration) math/long-min-value)
     (-> this
         (--plus math/long-max-value (- (:nanos duration)))
         (--plus 1 0))
     (--plus this (- (:seconds duration)) (- (:nanos duration)))))

  ([this ::duration
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(def-method minus-days ::duration
  [this ::duration
   days-to-subtract ::j/long]
  (if (= days-to-subtract math/long-min-value)
    (-> this
        (duration/plus-days math/long-max-value)
        (duration/plus-days 1))
    (duration/plus-days this (- days-to-subtract))))

(def-method minus-hours ::duration
  [this ::duration
   hours-to-subtract ::j/long]
  (if (= hours-to-subtract math/long-min-value)
    (-> this
        (plus-hours math/long-max-value)
        (plus-hours 1))
    (plus-hours this (- hours-to-subtract))))

(def-method minus-minutes ::duration
  [this ::duration
   minutes-to-subtract ::j/long]
  (if (= minutes-to-subtract math/long-min-value)
    (-> this
        (plus-minutes math/long-max-value)
        (plus-minutes 1))
    (plus-minutes this (- minutes-to-subtract))))

(def-method minus-seconds ::duration
  [this ::duration
   seconds-to-subtract ::j/long]
  (if (= seconds-to-subtract math/long-min-value)
    (-> this
        (plus-seconds math/long-max-value)
        (plus-seconds 1))
    (plus-seconds this (- seconds-to-subtract))))

(def-method minus-millis ::duration
  [this ::duration
   millis-to-subtract ::j/long]

  (if (= millis-to-subtract math/long-min-value)
    (-> this
        (plus-millis math/long-max-value)
        (plus-millis 1))
    (plus-millis this (- millis-to-subtract))))

(def-method minus-nanos ::duration
  [this ::duration
   nanos-to-subtract ::j/long]
  (if (= nanos-to-subtract math/long-min-value)
    (-> this
        (plus-nanos math/long-max-value)
        (plus-nanos 1))
    (plus-nanos this (- nanos-to-subtract))))

(def-method multiplied-by ::duration
  [this ::duration
   multiplicand ::j/long]
  (impl/multiplied-by this multiplicand))

(defn -divided-by-long [this divisor]
  (condp = divisor
    0 (throw (ex JavaArithmeticException "Cannot divide by zero" {:duration this :divisor divisor}))
    1 this
    (create (big-decimal/to-decimal-places
             (big-decimal/divide (impl/to-big-decimal-seconds this) (big-decimal/value-of divisor) :rounding.mode/down)
             9))))

(defn -divided-by-duration [this duration]
  (-> (impl/to-big-decimal-seconds this)
      (big-decimal/divide-to-integral-value (impl/to-big-decimal-seconds duration))
      big-decimal/long-value-exact))

(def-method divided-by ::duration
  [this ::duration
   divisor (s/or :long ::j/long
                 :duration ::duration)]
  (if (satisfies? duration/IDuration divisor)
    (-divided-by-duration this divisor)
    (-divided-by-long this divisor)))

(def-method negated ::duration
  [this ::duration]
  (impl/negated this))

(def-method abs ::duration
  [this ::duration]
  (if (is-negative this)
    (negated this)
    this))

(def-method to-days ::j/long
  [this ::duration]
  (-> this :seconds (/ SECONDS_PER_DAY) long))

(def-method to-hours ::j/long
  [this ::duration]
  (-> this :seconds (/ SECONDS_PER_HOUR) long))

(def-method to-minutes ::j/long
  [this ::duration]
  (-> this :seconds (/ SECONDS_PER_MINUTE) long))

(def-method to-seconds ::j/long
  [this ::duration]
  (:seconds this))

(def-method to-millis ::j/long
  [this ::duration]
  (let [[seconds nanos] (if (neg? (:seconds this))
                          ;; change the seconds and nano value to
                          ;; handle Long.MIN_VALUE case
                          [(inc (:seconds this))
                           (- (:nanos this) NANOS_PER_SECOND)]
                          [(:seconds this)
                           (:nanos this)])]
    (-> seconds
        (math/multiply-exact 1000)
        (math/add-exact (long (/ nanos NANOS_PER_MILLI))))))

(def-method to-nanos ::j/long
  [this ::duration]
  (let [[seconds nanos] (if (neg? (:seconds this))
                          ;; change the seconds and nano value to
                          ;; handle Long.MIN_VALUE case
                          [(inc (:seconds this))
                           (- (:nanos this) NANOS_PER_SECOND)]
                          [(:seconds this)
                           (:nanos this)])]
    (-> seconds
        (math/multiply-exact NANOS_PER_SECOND)
        (math/add-exact (long nanos)))))

(def-method to-days-part ::j/long
  [this ::duration]
  (-> this :seconds (/ SECONDS_PER_DAY) long))

(def-method to-hours-part ::j/int
  [this ::duration]
  (int (rem (to-hours this) 24)))

(def-method to-minutes-part ::j/int
  [this ::duration]
  (int (rem (to-minutes this) MINUTES_PER_HOUR)))

(def-method to-seconds-part ::j/int
  [this ::duration]
  (int (rem (:seconds this) SECONDS_PER_MINUTE)))

(def-method to-millis-part ::j/int
  [this ::duration]
  (-> this :nanos (/ 1000000) long))

(def-method to-nanos-part ::j/int
  [this ::duration]
  (:nanos this))

(def-method truncated-to ::duration
  [this ::duration
   unit ::temporal-unit/temporal-unit]
  (let [unit-dur (temporal-unit/get-duration unit)
        dur (delay (to-nanos unit-dur))]
    (cond
      (and (= unit chrono-unit/SECONDS)
           (or (>= (:seconds this) 0)
               (== (:nanos this) 0)))
      (impl/->Duration (:seconds this) 0)

      (= unit chrono-unit/NANOS)
      this

      (> (get-seconds unit-dur) SECONDS_PER_DAY)
      (throw (ex UnsupportedTemporalTypeException "Unit is too large to be used for truncation" {:duration this :unit unit}))

      (-> NANOS_PER_DAY (rem @dur) zero? not)
      (throw (ex UnsupportedTemporalTypeException "Unit must divide into a standard day without remainder" {:duration this :unit unit}))

      :else
      (let [nod (-> (:seconds this)
                    (rem SECONDS_PER_DAY)
                    (* NANOS_PER_SECOND)
                    (+ (:nanos this)))
            result  (-> nod
                        (/ @dur)
                        long
                        (* @dur))]
        (plus-nanos this (- result nod))))))

(extend-type Duration
  duration/IDuration
  (is-zero [this] (is-zero this))
  (is-negative [this] (is-negative this))
  (get-seconds [this] (get-seconds this))
  (get-nano [this] (get-nano this))
  (with-seconds [this seconds] (with-seconds this seconds))
  (with-nanos [this nano-of-second] (with-nanos this nano-of-second))
  (plus
    ([this duration] (plus this duration))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (plus-days [this days-to-add] (plus-days this days-to-add))
  (plus-hours [this hours-to-add] (plus-hours this hours-to-add))
  (plus-minutes [this minutes-to-add] (plus-minutes this minutes-to-add))
  (plus-seconds [this seconds-to-add] (plus-seconds this seconds-to-add))
  (plus-millis [this millis-to-add] (plus-millis this millis-to-add))
  (plus-nanos [this nanos-to-add] (plus-nanos this nanos-to-add))
  (minus
    ([this duration] (minus this duration))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (minus-days [this days-to-subtract] (minus-days this days-to-subtract))
  (minus-hours [this hours-to-subtract] (minus-hours this hours-to-subtract))
  (minus-minutes [this minutes-to-subtract] (minus-minutes this minutes-to-subtract))
  (minus-seconds [this seconds-to-subtract] (minus-seconds this seconds-to-subtract))
  (minus-millis [this millis-to-subtract] (minus-millis this millis-to-subtract))
  (minus-nanos [this nanos-to-subtract] (minus-nanos this nanos-to-subtract))
  (multiplied-by [this multiplicand] (multiplied-by this multiplicand))
  (divided-by [this divisor] (divided-by this divisor))
  (negated [this] (negated this))
  (abs [this] (abs this))
  (to-days [this] (to-days this))
  (to-hours [this] (to-hours this))
  (to-minutes [this] (to-minutes this))
  (to-seconds [this] (to-seconds this))
  (to-millis [this] (to-millis this))
  (to-nanos [this] (to-nanos this))
  (to-days-part [this] (to-days-part this))
  (to-hours-part [this] (to-hours-part this))
  (to-minutes-part [this] (to-minutes-part this))
  (to-seconds-part [this] (to-seconds-part this))
  (to-millis-part [this] (to-millis-part this))
  (to-nanos-part [this] (to-nanos-part this))
  (truncated-to [this unit] (truncated-to this unit)))

(def-method compare-to ::j/int
  [this ::duration
   other ::duration]
  (let [cmp (compare (:seconds this) (:seconds other))]
    (if-not (zero? cmp)
      cmp
      (- (:nanos this) (:nanos other)))))

(extend-type Duration
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(def-method get ::j/long
  [this ::duration
   unit ::temporal-unit/temporal-unit]
  (condp = unit
    SECONDS (:seconds this)
    NANOS (:nanos this)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit) {:duration this :unit unit}))))

(def UNITS (delay [SECONDS NANOS]))

(def-method get-units (s/coll-of ::temporal-unit/temporal-unit)
  [this ::duration]
  @UNITS)

(def-method add-to ::duration
  [this ::duration
   temporal ::temporal/temporal]

  (cond-> temporal
    (not (zero? (:seconds this)))
    (temporal/plus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (temporal/plus (:nanos this) NANOS)))

(def-method subtract-from ::duration
  [this ::duration
   temporal ::temporal/temporal]
  (cond-> temporal
    (not (zero? (:seconds this)))
    (temporal/minus (:seconds this) SECONDS)

    (not (zero? (:nanos this)))
    (temporal/minus (:nanos this) NANOS)))

(extend-type Duration
  temporal-amount/ITemporalAmount
  (get [this unit] (get this unit))
  (get-units [this] (get-units this))
  (add-to [this temporal] (add-to this temporal))
  (subtract-from [this temporal] (subtract-from this temporal)))

(def-constructor of-days ::duration
  [days ::j/long]
  (create (math/multiply-exact days SECONDS_PER_DAY) 0))

(def-constructor of-hours ::duration
  [hours ::j/long]
  (create (math/multiply-exact hours SECONDS_PER_HOUR) 0))

(def-constructor of-minutes ::duration
  [minutes ::j/long]
  (create (math/multiply-exact minutes SECONDS_PER_MINUTE) 0))

(def-constructor of-millis ::duration
  [millis ::j/long]
  (let [mos (int (rem millis 1000))
        secs (cond-> (long (/ millis 1000)) (neg? mos) dec)
        mos (cond-> mos (neg? mos) (+ 1000))]
    (create secs (* mos 1000000))))

(def-constructor of-nanos ::duration
  [nanos ::j/long]
  (impl/of-nanos nanos))

(def-constructor of ::duration
  [amount ::j/long
   unit ::temporal-unit/temporal-unit]
  (plus ZERO amount unit))

(def-constructor from ::duration
  [amount ::temporal-amount/temporal-amount]
  (reduce #(plus %1 (temporal-amount/get amount %2) %2)
          ZERO
          (temporal-amount/get-units amount)))

(defn- duration-part [[[sign] n] & [suffix]]
  (when (and n (not (zero? n)))
    (str (str sign n) suffix)))

(defn num-digits [n]
  (count (str (Math/abs n))))

(defn second-fraction [n]
  (let [trailing-zeros (- 9 (num-digits n))]
   (* n (reduce * (repeat trailing-zeros 10)))))

(defn parse-fraction [s]
  (let [n (or (math/parse-int s) 0)
        leading-zeros (- (count s) (num-digits n))
        trailing-zeros (- 9 leading-zeros (num-digits n))]
    (try*
      (math/multiply-exact n (math/to-int-exact (reduce * (repeat trailing-zeros 10))))
      (catch :default e
        (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text s}))))))

(defn parse-number [multiplier s]
  (try*
   (math/multiply-exact (or (math/parse-long s) 0) multiplier)
   (catch :default e
     (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text s :multiplier multiplier})))))

(def PATTERN (delay (re-pattern (str "(?i)([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?"))))

(s/def ::iso8601-duration
  #?(:cljs (s/and string? #(re-find @PATTERN %))
     :clj (s/with-gen (s/and string? #(re-find @PATTERN %))
            (fn []
              (let [sign #{"" "-" "+"}
                    signed-long (s/nilable (s/tuple (s/? sign) ::j/pos-long))
                    ->str (fn [[[sign] n]] (str sign n))]
                (gen/let
                    [prefix (s/gen sign)
                     days (s/gen signed-long)
                     hours (s/gen signed-long)
                     minutes (s/gen signed-long)
                     seconds (s/gen signed-long)
                     second-fraction (s/gen (s/nilable ::j/pos-int))]
                    (str prefix
                         "P"
                         (duration-part days "D")
                         (when (some #(some-> % ->str) [hours minutes seconds])
                           "T")
                         (duration-part hours "H")
                         (duration-part minutes "M")
                         (when (ffirst seconds)
                           (str (duration-part seconds)
                                (when second-fraction
                                  (str "." second-fraction))
                                "S")))))))))

(def-constructor parse ::duration
  [text ::iso8601-duration]
  (let [matches (re-matches @PATTERN text)]
    (if-not matches
      (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text text}))
      (let [[_ prefix days T & [hours minutes seconds fraction :as more]] matches
            nums (cons days more)
            factor (if (= "-" prefix) -1 1)
            multiplicands [(partial parse-number SECONDS_PER_DAY)
                           (partial parse-number SECONDS_PER_HOUR)
                           (partial parse-number SECONDS_PER_MINUTE)
                           (partial parse-number 1)
                           (partial parse-fraction)]
            [d h m s f :as parts] (map #(%1 %2) multiplicands nums)]
        (when (or (and T (not (some some? more)))
                  (= d h m s f 0))
          (throw (ex DateTimeParseException "Text cannot be parsed to a Duration" {:text text :error-index 0})))
        (create (neg? factor) d h m s (if (neg? s) (- f) f))))))

(def-constructor between ::duration
  [start-inclusive ::temporal/temporal
   end-exclusive ::temporal/temporal]
  (try*
   (-> start-inclusive (temporal/until end-exclusive NANOS) of-nanos)
   (catch :default e
     (let [secs (-> start-inclusive (temporal/until end-exclusive SECONDS))
           [secs nanos] (try*
                         (let [n (- (temporal-accessor/get-long end-exclusive NANO_OF_SECOND)
                                    (temporal-accessor/get-long start-inclusive NANO_OF_SECOND))
                               s (cond-> secs
                                   (and (pos? secs) (neg? n))
                                   inc
                                   (and (neg? secs) (pos? n))
                                   dec)]
                           [s n])
                         (catch :default e [secs 0]))]
       (of-seconds secs nanos)))))

(defn set-char-at [s ch idx]
  (let [[a b] (split-at idx s)]
    (str (apply str a)
         ch
         (apply str (rest b)))))

(def-method to-string string?
  [{:keys [seconds nanos] :as this} ::duration]
  (if (= this ZERO)
    "PT0S"
    (let [effective-total-seconds (if (and (neg? seconds) (pos? nanos))
                                    (inc seconds)
                                    seconds)
          hours (long (/ effective-total-seconds local-time/SECONDS_PER_HOUR))
          minutes (long (/ (rem effective-total-seconds local-time/SECONDS_PER_HOUR)
                           local-time/SECONDS_PER_MINUTE))
          secs (rem effective-total-seconds local-time/SECONDS_PER_MINUTE)
          s (str "PT"
                 (when (not (zero? hours)) (str hours "H"))
                 (when (not (zero? minutes)) (str minutes "M")))]
      (if (and (zero? secs) (zero? nanos) (> (count s) 2))
        s
        (let [s (str s
                     (if (and (neg? seconds)
                              (pos? nanos)
                              (zero? secs))
                       "-0"
                       secs))]
          (str (if-not (pos? nanos)
                 s
                 (let [pos (count s)
                       s (str s
                              (if (neg? seconds)
                                (-> 2
                                    (* local-time/NANOS_PER_SECOND)
                                    (- nanos))
                                (+ nanos local-time/NANOS_PER_SECOND)))]
                   (set-char-at
                    (->> s
                         reverse
                         (drop-while #(= % \0))
                         reverse
                         (apply str))
                    "."
                                pos)))
               "S"))))))

(extend-type Duration
  string/IString
  (to-string [this] (to-string this)))
