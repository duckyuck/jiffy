(ns jiffy.period
  (:refer-clojure :exclude [get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [clojure.spec.gen.alpha :as gen]
            [clojure.test.check.generators]
            [jiffy.asserts :as asserts]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.chrono.iso-chronology :as iso-chronology-impl]
            [jiffy.protocols.chrono.iso-chronology :as iso-chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex try* JavaArithmeticException DateTimeException DateTimeParseException UnsupportedTemporalTypeException]]
            [jiffy.local-date-impl :as local-date]
            [jiffy.math :as math]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.period :as period]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]))

(def-record Period ::period
  [years ::j/year
   months ::j/month
   days ::j/day])

(def ZERO (->Period 0 0 0))

(def-constructor create ::period
  [years ::j/year
   months ::j/month
   days ::j/day]
  (if (zero? (bit-or years months days))
    ZERO
    (->Period years months days)))

(def-constructor from ::period
  [amount ::temporal-amount/temporal-amount]
  (asserts/require-non-nil amount "amount")
  (cond
    (instance? Period amount)
    amount

    (and (satisfies? chrono-period/IChronoPeriod amount)
         (not= iso-chronology-impl/INSTANCE (chrono-period/get-chronology amount)))
    (throw (ex DateTimeException (str "Period requires ISO chronology: " amount)))

    :default
    (loop [years 0
           months 0
           days 0
           units (temporal-amount/get-units amount)]
      (if-let [unit (first units)]
        (let [unit-amount (temporal-amount/get amount unit)]
          (cond
            (= unit chrono-unit/YEARS)
            (recur (math/to-int-exact unit-amount) months days (rest units))

            (= unit chrono-unit/MONTHS)
            (recur years (math/to-int-exact unit-amount) days (rest units))

            (= unit chrono-unit/DAYS)
            (recur years months (math/to-int-exact unit-amount) (rest units))

            :default (throw (ex DateTimeException (str "Unit must be Years, Months or Days, but was " unit)))))
        (create years months days)))))

(def-method get-years ::j/year
  [this ::period]
  (:years this))

(def-method get-months ::j/month
  [this ::period]
  (:months this))

(def-method get-days ::j/day
  [this ::period]
  (:days this))

(def-method with-years ::period
  [this ::period
   years ::j/year]
  (if (= years (:years this))
    this
    (create years (:months this) (:days this))))

(def-method with-months ::period
  [this ::period
   months ::j/month]
  (if (= months (:months this))
    this
    (create (:years this) months (:days this))))

(def-method with-days ::period
  [this ::period
   days ::j/day]
  (if (= days (:days this))
    this
    (create (:years this) (:months this) days)))

(def-method plus-years ::period
  [this ::period
   years-to-add ::j/year]
  (if (= years-to-add 0)
    this
    (create (math/to-int-exact (math/add-exact (:years this) years-to-add)) (:months this) (:days this))))

(def-method plus-months ::period
  [this ::period
   months-to-add ::j/long]
  (if (= months-to-add 0)
    this
    (create (:years this) (math/to-int-exact (math/add-exact (:months this) months-to-add)) (:days this))))

(def-method plus-days ::period
  [this ::period
   days-to-add ::j/long]
  (if (= days-to-add 0)
    this
    (create (:years this) (:months this) (math/to-int-exact (math/add-exact (:days this) days-to-add)))))

(def-method minus-years ::period
  [this ::period
   years-to-subtract ::j/long]
  (if (= years-to-subtract math/long-min-value)
    (-> this
        (plus-years math/long-min-value)
        (plus-years 1))
    (plus-years this (- years-to-subtract))))

(def-method minus-months ::period
  [this ::period
   months-to-subtract ::j/long]
  (if (= months-to-subtract math/long-min-value)
    (-> this
        (plus-months math/long-min-value)
        (plus-months 1))
    (plus-months this (long (- months-to-subtract)))))

(def-method minus-days ::period
  [this ::period
   days-to-subtract ::j/long]
  (if (= days-to-subtract math/long-min-value)
    (-> this
        (plus-days math/long-min-value)
        (plus-days 1))
    (plus-days this (- days-to-subtract))))

(def-method to-total-months ::j/month
  [this ::period]
  (+ (* (:years this) 12) (:months this)))

(extend-type Period
  period/IPeriod
  (get-years [this] (get-years this))
  (get-months [this] (get-months this))
  (get-days [this] (get-days this))
  (with-years [this years] (with-years this years))
  (with-months [this months] (with-months this months))
  (with-days [this days] (with-days this days))
  (plus-years [this years-to-add] (plus-years this years-to-add))
  (plus-months [this months-to-add] (plus-months this months-to-add))
  (plus-days [this days-to-add] (plus-days this days-to-add))
  (minus-years [this years-to-subtract] (minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (minus-months this months-to-subtract))
  (minus-days [this days-to-subtract] (minus-days this days-to-subtract))
  (to-total-months [this] (to-total-months this)))

(def-method get-chronology ::iso-chronology/iso-chronology
  [this ::period]
  iso-chronology-impl/INSTANCE)

(def-method is-zero ::j/boolean
  [this ::period]
  (= this ZERO))

(def-method is-negative ::j/boolean
  [this ::period]
  (or (< (:years this) 0) (< (:months this) 0) (< (:days this) 0)))

(def-method plus ::chrono-period/chrono-period
  [this ::period
   amount-to-add ::temporal-amount/temporal-amount]
  (let [iso-amount (from amount-to-add)]
    (create (math/add-exact-int (:years this) (:years iso-amount))
            (math/add-exact-int (:months this) (:months iso-amount))
            (math/add-exact-int (:days this) (:days iso-amount)))))

(def-method minus ::chrono-period/chrono-period
  [this ::period
   amount-to-subtract ::temporal-amount/temporal-amount]
  (let [iso-amount (from amount-to-subtract)]
    (create (math/subtract-exact-int (:years this) (:years iso-amount))
            (math/subtract-exact-int (:months this) (:months iso-amount))
            (math/subtract-exact-int (:days this) (:days iso-amount)))))

(def-method multiplied-by ::period
  [this ::period
   scalar ::j/int]
  (if (or (= this ZERO) (= scalar 1))
    this
    (create (math/multiply-exact-int (:years this) scalar)
            (math/multiply-exact-int (:months this) scalar)
            (math/multiply-exact-int (:days this) scalar))))

(def-method negated ::chrono-period/chrono-period
  [this ::period]
  (multiplied-by this -1))

(def-method normalized ::chrono-period/chrono-period
  [this ::period]
  (let [total-months (to-total-months this)
        split-years (long (/ total-months 12))
        split-months (int (rem total-months 12))]
    (create (math/to-int-exact split-years) split-months (:days this))))

(extend-type Period
  chrono-period/IChronoPeriod
  (get-chronology [this] (get-chronology this))
  (is-zero [this] (is-zero this))
  (is-negative [this] (is-negative this))
  (plus [this amount-to-add] (plus this amount-to-add))
  (minus [this amount-to-subtract] (minus this amount-to-subtract))
  (multiplied-by [this scalar] (multiplied-by this scalar))
  (negated [this] (negated this))
  (normalized [this] (normalized this)))

(def-method get ::j/long
  [this ::period
   unit ::temporal-unit/temporal-unit]
  (cond
    (= unit chrono-unit/YEARS) (:years this)
    (= unit chrono-unit/MONTHS) (:months this)
    (= unit chrono-unit/DAYS) (:days this)
    :default (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit " unit)))))

(def-method get-units ::j/wip
  [this ::period]
  [chrono-unit/YEARS chrono-unit/MONTHS chrono-unit/DAYS])

(defn- validate-chrono [temporal]
  (asserts/require-non-nil temporal "temporal")
  (let [temporal-chrono (temporal-accessor/query temporal (temporal-queries/chronology))]
    (when (and (not (nil? temporal-chrono)) (not= iso-chronology-impl/INSTANCE temporal-chrono))
      (throw (ex DateTimeException (str "Chronology mismatch, expected: ISO, actual: " (chronology/get-id temporal-chrono)))))))

(def-method add-to ::temporal/temporal
  [this ::period
   temporal ::temporal/temporal]
  (validate-chrono temporal)
  (let [total-months (to-total-months this)]
    (cond-> temporal
      (and (= (:months this) 0)
           (not= (:years this) 0))
      (temporal/plus (:years this) chrono-unit/YEARS)

      (and (not= (:months this) 0)
           (not= total-months 0))
      (temporal/plus total-months chrono-unit/MONTHS)

      (not= (:days this) 0)
      (temporal/plus (:days this) chrono-unit/DAYS))))


(def-method subtract-from ::temporal/temporal
  [this ::period
   temporal ::temporal/temporal]
  (validate-chrono temporal)
  (let [total-months (to-total-months this)]
    (cond-> temporal
      (and (zero? (:months this))
           (not (zero? (:years this))))
      (temporal/minus (:years this) chrono-unit/YEARS)

      (and (not= (:months this) 0)
           (not= total-months 0))
      (temporal/minus total-months chrono-unit/MONTHS)

      (not= (:days this) 0)
      (temporal/minus (:days this) chrono-unit/DAYS))))

(extend-type Period
  temporal-amount/ITemporalAmount
  (get [this unit] (get this unit))
  (get-units [this] (get-units this))
  (add-to [this temporal] (add-to this temporal))
  (subtract-from [this temporal] (subtract-from this temporal)))

(def-constructor of-years ::period
  [years ::j/int]
  (create years 0 0))

(def-constructor of-months ::period
  [months ::j/int]
  (create 0 months 0))

(def-constructor of-weeks ::period
  [weeks ::j/int]
  (create 0 0 (math/multiply-exact-int weeks (int 7))))

(def-constructor of-days ::period
  [days ::j/int]
  (create 0 0 days))

(def-constructor of ::period
  [years ::j/int
   months ::j/int
   days ::j/int]
  (create years months days))

(def ^:private PATTERN #"([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?")

(defn- period-part [prefix n postfix]
  (when-not (nil? n)
    (str prefix n postfix)))

(s/def ::iso8601-period
  #?(:cljs (s/and string? #(re-find PATTERN %))
     :clj (s/with-gen (s/and string? #(re-find PATTERN %))
            (fn []
              (let [spec (s/or :nil nil? :int (s/int-in 0 math/integer-max-value))]
                (clojure.test.check.generators/let
                    [prefix (s/gen #{"" "-" "+"})
                     year (s/gen spec)
                     year-prefix (s/gen #{"" "-" "+"})
                     month (s/gen spec)
                     month-prefix (s/gen #{"" "-" "+"})
                     week (s/gen spec)
                     week-prefix (s/gen #{"" "-" "+"})
                     day (s/gen spec)
                     day-prefix (s/gen #{"" "-" "+"})]
                  (str prefix
                       "P"
                       (period-part year-prefix year "Y")
                       (period-part month-prefix month "M")
                       (period-part week-prefix week "W")
                       (period-part day-prefix day "D"))))))))

(def-constructor parse ::period
  [text ::iso8601-period]
  (asserts/require-non-nil text "text")
  (if-let [matches (re-find PATTERN text)]
    (try*
     (let [[_ prefix & nums] matches
           factor (if (= "-" prefix) -1 1)
           [y m w d] (map #(-> (or (math/parse-int %) 0) (* factor) math/to-int-exact) nums)]
       (when (apply = nil nums)
         (throw (ex DateTimeParseException "Text cannot be parsed to a Period" {:parsed-data text :error-index 0})))
       (create y m (math/add-exact-int (int d) (int (math/multiply-exact-int w (int 7))))))
     (catch JavaArithmeticException e
       (throw e))
     (catch :default e
       (throw (ex DateTimeParseException "Text cannot be parsed to a Period" {:parsed-data text :error-index 0} e))))
    (throw (ex DateTimeParseException "Text cannot be parsed to a Period" {:parsed-data text :error-index 0}))))

(def-method between ::period
  [start-date-inclusive ::local-date/local-date
   end-date-exclusive ::local-date/local-date]
  (chrono-local-date/until start-date-inclusive end-date-exclusive))
