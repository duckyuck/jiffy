(ns jiffy.period
  (:require [clojure.spec.alpha :as s]
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

(defrecord Period [years months days])

(s/def ::create-args (s/tuple ::j/year ::j/month ::j/day))
(def create ->Period)
(s/def ::period (j/constructor-spec Period create ::create-args))
(s/fdef create :args ::create-args :ret ::period)

(defmacro args [& x] `(s/tuple ::period ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L139
(def ZERO (create 0 0 0))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L257
(s/def ::from-args (s/tuple ::temporal-amount/temporal-amount))
(defn from [amount]
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
(s/fdef from :args ::from-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L435
(s/def ::get-years-args (args))
(defn -get-years [this]
  (:years this))
(s/fdef -get-years :args ::get-years-args :ret ::j/year)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L437
(s/def ::get-months-args (args))
(defn -get-months [this]
  (:months this))
(s/fdef -get-months :args ::get-months-args :ret ::j/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L439
(s/def ::get-days-args (args))
(defn -get-days [this]
  (:days this))
(s/fdef -get-days :args ::get-days-args :ret ::j/day)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L558
(s/def ::with-years-args (args ::j/year))
(defn -with-years [this years]
  (if (= years (:years this))
    this
    (create years (:months this) (:days this))))
(s/fdef -with-years :args ::with-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L580
(s/def ::with-months-args (args ::j/month))
(defn -with-months [this months]
  (if (= months (:months this))
    this
    (create (:years this) months (:days this))))
(s/fdef -with-months :args ::with-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L598
(s/def ::with-days-args (args ::j/day))
(defn -with-days [this days]
  (if (= days (:days this))
    this
    (create (:years this) (:months this) days)))
(s/fdef -with-days :args ::with-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L647
(s/def ::plus-years-args (args ::j/year))
(defn -plus-years [this years-to-add]
  (if (= years-to-add 0)
    this
    (create (math/to-int-exact (math/add-exact (:years this) years-to-add)) (:months this) (:days this))))
(s/fdef -plus-years :args ::plus-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L667
(s/def ::plus-months-args (args ::j/long))
(defn -plus-months [this months-to-add]
  (if (= months-to-add 0)
    this
    (create (:years this) (math/to-int-exact (math/add-exact (:months this) months-to-add)) (:days this))))
(s/fdef -plus-months :args ::plus-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L687
(s/def ::plus-days-args (args ::j/long))
(defn -plus-days [this days-to-add]
  (if (= days-to-add 0)
    this
    (create (:years this) (:months this) (math/to-int-exact (math/add-exact (:days this) days-to-add)))))
(s/fdef -plus-days :args ::plus-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L736
(s/def ::minus-years-args (args ::j/long))
(defn -minus-years [this years-to-subtract]
  (if (= years-to-subtract math/long-min-value)
    (-> this
        (-plus-years math/long-min-value)
        (-plus-years 1))
    (-plus-years this (- years-to-subtract))))
(s/fdef -minus-years :args ::minus-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L753
(s/def ::minus-months-args (args ::j/long))
(defn -minus-months [this months-to-subtract]
  (if (= months-to-subtract math/long-min-value)
    (-> this
        (-plus-months math/long-min-value)
        (-plus-months 1))
    (-plus-months this (long (- months-to-subtract)))))
(s/fdef -minus-months :args ::minus-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L770
(s/def ::minus-days-args (args ::j/long))
(defn -minus-days [this days-to-subtract]
  (if (= days-to-subtract math/long-min-value)
    (-> this
        (-plus-days math/long-min-value)
        (-plus-days 1))
    (-plus-days this (- days-to-subtract))))
(s/fdef -minus-days :args ::minus-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L835
(s/def ::to-total-months-args (args))
(defn -to-total-months [this]
  (+ (* (:years this) 12) (:months this)))
(s/fdef -to-total-months :args ::to-total-months-args :ret ::j/month)

(extend-type Period
  period/IPeriod
  (get-years [this] (-get-years this))
  (get-months [this] (-get-months this))
  (get-days [this] (-get-days this))
  (with-years [this years] (-with-years this years))
  (with-months [this months] (-with-months this months))
  (with-days [this days] (-with-days this days))
  (plus-years [this years-to-add] (-plus-years this years-to-add))
  (plus-months [this months-to-add] (-plus-months this months-to-add))
  (plus-days [this days-to-add] (-plus-days this days-to-add))
  (minus-years [this years-to-subtract] (-minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (-minus-months this months-to-subtract))
  (minus-days [this days-to-subtract] (-minus-days this days-to-subtract))
  (to-total-months [this] (-to-total-months this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L473
(s/def ::get-chronology-args (args))
(defn -get-chronology [this]
  iso-chronology-impl/INSTANCE)
(s/fdef -get-chronology :args ::get-chronology-args :ret ::iso-chronology/iso-chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L485
(s/def ::is-zero-args (args))
(defn -is-zero [this]
  (= this ZERO))
(s/fdef -is-zero :args ::is-zero-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L496
(s/def ::is-negative-args (args))
(defn -is-negative [this]
  (or (< (:years this) 0) (< (:months this) 0) (< (:days this) 0)))
(s/fdef -is-negative :args ::is-negative-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L626
(s/def ::plus-args (args ::temporal-amount/temporal-amount))
(defn -plus [this amount-to-add]
  (let [iso-amount (from amount-to-add)]
    (create (math/add-exact-int (:years this) (:years iso-amount))
            (math/add-exact-int (:months this) (:months iso-amount))
            (math/add-exact-int (:days this) (:days iso-amount)))))
(s/fdef -plus :args ::plus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L715
(s/def ::minus-args (args ::temporal-amount/temporal-amount))
(defn -minus [this amount-to-subtract]
  (let [iso-amount (from amount-to-subtract)]
    (create (math/subtract-exact-int (:years this) (:years iso-amount))
            (math/subtract-exact-int (:months this) (:months iso-amount))
            (math/subtract-exact-int (:days this) (:days iso-amount)))))
(s/fdef -minus :args ::minus-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L789
(s/def ::multiplied-by-args (args ::j/int))
(defn -multiplied-by [this scalar]
  (if (or (= this ZERO) (= scalar 1))
    this
    (create (math/multiply-exact-int (:years this) scalar)
            (math/multiply-exact-int (:months this) scalar)
            (math/multiply-exact-int (:days this) scalar))))
(s/fdef -multiplied-by :args ::multiplied-by-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L812
(s/def ::negated-args (args))
(defn -negated [this]
  (-multiplied-by this -1))
(s/fdef -negated :args ::negated-args :ret ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L834
(s/def ::normalized-args (args))
(defn -normalized [this]
  (let [total-months (-to-total-months this)
        split-years (long (/ total-months 12))
        split-months (int (rem total-months 12))]
    (create (math/to-int-exact split-years) split-months (:days this))))
(s/fdef -normalized :args ::normalized-args :ret ::chrono-period/chrono-period)

(extend-type Period
  chrono-period/IChronoPeriod
  (get-chronology [this] (-get-chronology this))
  (is-zero [this] (-is-zero this))
  (is-negative [this] (-is-negative this))
  (plus [this amount-to-add] (-plus this amount-to-add))
  (minus [this amount-to-subtract] (-minus this amount-to-subtract))
  (multiplied-by [this scalar] (-multiplied-by this scalar))
  (negated [this] (-negated this))
  (normalized [this] (-normalized this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L433
(s/def ::get-args (args ::temporal-unit/temporal-unit))
(defn -get [this unit]
  (cond
    (= unit chrono-unit/YEARS) (:years this)
    (= unit chrono-unit/MONTHS) (:months this)
    (= unit chrono-unit/DAYS) (:days this)
    :default (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit " unit)))))
(s/fdef -get :args ::get-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L458
(s/def ::get-units-args (args))
(defn -get-units [this]
  [chrono-unit/YEARS chrono-unit/MONTHS chrono-unit/DAYS])
(s/fdef -get-units :args ::get-units-args :ret ::j/wip)

(defn- validate-chrono [temporal]
  (asserts/require-non-nil temporal "temporal")
  (let [temporal-chrono (temporal-accessor/query temporal (temporal-queries/chronology))]
    (when (and (not (nil? temporal-chrono)) (not= iso-chronology-impl/INSTANCE temporal-chrono))
      (throw (ex DateTimeException (str "Chronology mismatch, expected: ISO, actual: " (chronology/get-id temporal-chrono)))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L894
(s/def ::add-to-args (args ::temporal/temporal))
(defn -add-to [this temporal]
  (validate-chrono temporal)
  (let [total-months (-to-total-months this)]
    (cond-> temporal
      (and (= (:months this) 0)
           (not= (:years this) 0))
      (temporal/plus (:years this) chrono-unit/YEARS)

      (and (not= (:months this) 0)
           (not= total-months 0))
      (temporal/plus total-months chrono-unit/MONTHS)

      (not= (:days this) 0)
      (temporal/plus (:days this) chrono-unit/DAYS))))
(s/fdef -add-to :args ::add-to-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L947
(s/def ::subtract-from-args (args ::temporal/temporal))
(defn -subtract-from [this temporal]
  (validate-chrono temporal)
  (let [total-months (-to-total-months this)]
    (cond-> temporal
      (and (zero? (:months this))
           (not (zero? (:years this))))
      (temporal/minus (:years this) chrono-unit/YEARS)

      (and (not= (:months this) 0)
           (not= total-months 0))
      (temporal/minus total-months chrono-unit/MONTHS)

      (not= (:days this) 0)
      (temporal/minus (:days this) chrono-unit/DAYS))))
(s/fdef -subtract-from :args ::subtract-from-args :ret ::temporal/temporal)

(extend-type Period
  temporal-amount/ITemporalAmount
  (get [this unit] (-get this unit))
  (get-units [this] (-get-units this))
  (add-to [this temporal] (-add-to this temporal))
  (subtract-from [this temporal] (-subtract-from this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L178
(s/def ::of-years-args (s/tuple ::j/int))
(defn of-years [years]
  (create years 0 0))
(s/fdef of-years :args ::of-years-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L191
(s/def ::of-months-args (s/tuple ::j/int))
(defn of-months [months]
  (create 0 months 0))
(s/fdef of-months :args ::of-months-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L205
(s/def ::of-weeks-args (s/tuple ::j/int))
(defn of-weeks [weeks]
  (create 0 0 (math/multiply-exact-int weeks (int 7))))
(s/fdef of-weeks :args ::of-weeks-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L218
(s/def ::of-days-args (s/tuple ::j/int))
(defn of-days [days]
  (create 0 0 days))
(s/fdef of-days :args ::of-days-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L233
(s/def ::of-args (s/tuple ::j/int ::j/int ::j/int))
(defn of [years months days]
  (create years months days))
(s/fdef of :args ::of-args :ret ::period)

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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L325
(s/def ::parse-args (s/tuple ::iso8601-period))
(defn parse [text]
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
(s/fdef parse :args ::parse-args :ret ::period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Period.java#L386
(s/def ::between-args (s/tuple ::local-date/local-date ::local-date/local-date))
(defn between [start-date-inclusive end-date-exclusive]
  (chrono-local-date/until start-date-inclusive end-date-exclusive))
(s/fdef between :args ::between-args :ret ::period)
