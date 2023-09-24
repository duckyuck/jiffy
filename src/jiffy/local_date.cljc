(ns jiffy.local-date
  (:refer-clojure :exclude [range get format second])
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [clojure.spec.alpha :as s]
            [jiffy.asserts :as asserts]
            [jiffy.chrono.chrono-local-date-defaults :as chrono-local-date-defaults]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.chrono.iso-era :as iso-era]
            [jiffy.clock :as clock-impl]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException JavaIllegalArgumentException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.local-date-impl-impl :as impl-impl]
            [jiffy.local-date-impl :refer [create #?@(:cljs [LocalDate])] :as impl]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.math :as math]
            [jiffy.month :as month]
            [jiffy.offset-date-time-impl :as offset-date-time-impl]
            [jiffy.period :as period-impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.string :as string]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.offset-time :as offset-time]
            [jiffy.protocols.period :as period]
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
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.value-range :as value-range-impl]
            [jiffy.year-impl :as year-impl]
            [jiffy.zoned-date-time-impl :as zoned-date-time-impl]
            [clojure.string :as str])
  #?(:clj (:import [jiffy.local_date_impl LocalDate])))

(s/def ::local-date ::impl/local-date)

(def DAYS_PER_CYCLE impl/DAYS_PER_CYCLE)
(def DAYS_0000_TO_1970 impl/DAYS_0000_TO_1970)

(def MIN impl/MIN)
(def MAX impl/MAX)
(def EPOCH impl/EPOCH)

(def-method get-month ::month/month
  [this ::local-date]
  (month/of (:month this)))

(def-method is-leap-year ::j/boolean
  [this ::local-date]
  (chronology/is-leap-year iso-chronology/INSTANCE (:year this)))

(def-method to-epoch-day ::j/long
  [this ::local-date]
  (let [{:keys [year month day]} this]
    (-> (math/multiply-exact 365 year)
        (cond-> (>= year 0)
          (math/add-exact (-> (math/add-exact year 3)
                              (/ 4)
                              long
                              (math/subtract-exact (long (/ (math/add-exact year 99) 100)))
                              (math/add-exact (long (/ (math/add-exact year 399) 400)))))
          (not (>= year 0))
          (math/subtract-exact (-> (long (/ year -4))
                                   (math/subtract-exact (long (/ year -100)))
                                   (math/add-exact (long (/ year -400))))))
        (math/add-exact (-> (math/multiply-exact 367 month)
                            (math/subtract-exact 362)
                            (/ 12)
                            long))
        (math/add-exact (math/subtract-exact day 1))
        (cond-> (> month 2)
          dec
          (and (> month 2)
               (not (is-leap-year this)))
          dec)
        (math/subtract-exact DAYS_0000_TO_1970))))

(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::local-date]
  (day-of-week/of (-> (math/add-exact (to-epoch-day this) 3)
                      (math/floor-mod 7)
                      inc)))

(def-method get-day-of-year ::j/int
  [this ::local-date]
  (-> (get-month this)
      (month/first-day-of-year (is-leap-year this))
      (+ (:day this)
         (- 1))))

(def-method get-year ::j/int
  [this ::local-date]
  (:year this))

(def-method get-month-value ::j/int
  [this ::local-date]
  (:month this))

(def-method get-day-of-month ::j/int
  [this ::local-date]
  (:day this))

(defn resolve-previous-valid [year month day]
  (let [new-day (cond
                  (= month 2)
                  (min day
                       (if (chronology/is-leap-year iso-chronology/INSTANCE year)
                         29
                         28))
                  (#{4 6 9 11} month)
                  (min day 30)

                  :else
                  day)]
    (impl/of year month new-day)))

(def-method with-year ::local-date
  [{:keys [year month day] :as this} ::local-date
   new-year ::j/int]
  (if (= new-year year)
    this
    (do
      (chrono-field/check-valid-value chrono-field/YEAR new-year)
      (resolve-previous-valid new-year month day))))

(def-method with-month ::local-date
  [{:keys [year month day] :as this} ::local-date
   new-month ::j/int]
  (if (= new-month month)
    this
    (do
      (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR new-month)
      (resolve-previous-valid year new-month day))))

(def-method with-day-of-month ::local-date
  [{:keys [day year month] :as this} ::local-date
   day-of-month ::j/int]
  (if (= day day-of-month)
    this
    (impl/of year month day-of-month)))

(declare of-year-day)

(def-method with-day-of-year ::local-date
  [this ::local-date
   day-of-year ::j/int]
  (if (= (get-day-of-year this) day-of-year)
    this
    (of-year-day (:year this) day-of-year)))

(def-method plus-years ::local-date
  [{:keys [year month day] :as this} ::local-date
   years-to-add ::j/long]
  (if (zero? years-to-add)
    this
    (resolve-previous-valid
     (chrono-field/check-valid-int-value chrono-field/YEAR (+ year years-to-add))
     month
     day)))

(def-method plus-months ::local-date
  [{:keys [year month day] :as this} ::local-date
   months-to-add ::j/long]
  (if (zero? months-to-add)
    this
    (let [month-count (math/add-exact (math/multiply-exact year 12)
                                      (math/subtract-exact month 1))
          calc-months (+ month-count months-to-add)
          new-year (chrono-field/check-valid-int-value chrono-field/YEAR (math/floor-div calc-months 12))
          new-month (math/add-exact (math/floor-mod calc-months 12) 1)]
      (resolve-previous-valid new-year new-month day))))

(declare length-of-month)

(def-method plus-days ::local-date
  [{:keys [year month day] :as this} ::local-date
   days-to-add ::j/long]
  (if (zero? days-to-add)
    this
    (let [dom (+ day days-to-add)]
      (if (pos? dom)
        (cond
          (<= dom 28)
          (impl/of year month (int dom))

          (<= dom 59)
          (let [month-len (length-of-month this)]
            (cond
              (<= dom month-len) (impl/of year month (int dom))
              (< month 12) (impl/of year (+ month 1) (int (- dom month-len)))
              :else (do
                      (chrono-field/check-valid-value chrono-field/YEAR (inc year))
                      (impl/of (inc year) 1 (int (- dom month-len))))))

          :else
          (let [mj-day (math/add-exact (to-epoch-day this) days-to-add)]
            (impl/of-epoch-day mj-day)))
        (let [mj-day (math/add-exact (to-epoch-day this) days-to-add)]
          (impl/of-epoch-day mj-day))))))

(def-method plus-weeks ::local-date
  [this ::local-date
   weeks-to-add ::j/long]
  (plus-days this (math/multiply-exact weeks-to-add 7)))

(def-method minus-years ::local-date
  [this ::local-date
   years-to-subtract ::j/long]
  (if (= years-to-subtract math/long-min-value)
    (plus-years (plus-years this math/long-max-value)
                1)
    (plus-years this (- years-to-subtract))))

(def-method minus-months ::local-date
  [this ::local-date
   months-to-subtract ::j/long]
  (if (= months-to-subtract math/long-min-value)
    (plus-months (plus-months this math/long-max-value)
                 1)
    (plus-months this (- months-to-subtract))))

(def-method minus-weeks ::local-date
  [this ::local-date
   weeks-to-subtract ::j/long]
  (if (= weeks-to-subtract math/long-min-value)
    (plus-weeks (plus-weeks this math/long-max-value)
                1)
    (plus-weeks this (- weeks-to-subtract))))

(def-method minus-days ::local-date
  [this ::local-date
   days-to-subtract ::j/long]
  (if (= days-to-subtract math/long-min-value)
    (plus-days (plus-days this math/long-max-value)
               1)
    (plus-days this (- days-to-subtract))))

(def-method days-until ::j/long
  [this ::local-date
   end ::local-date]
  (math/subtract-exact (to-epoch-day end) (to-epoch-day this)))

(defn --get-proleptic-month [this]
  (-> (math/multiply-exact (:year this) 12)
      (math/add-exact (:month this))
      (math/subtract-exact 1)))

(declare of-epoch-day)
(def-method dates-until (s/coll-of ::local-date)
  ([this ::local-date
    end-exclusive ::local-date]
   (let [end (to-epoch-day end-exclusive)
         start (to-epoch-day this)]
     (if (< end start)
       (throw (ex JavaIllegalArgumentException (str end-exclusive " < " this)))
       (->> (clojure.core/range start end)
            (map of-epoch-day)))))

  ([this ::local-date
    end-exclusive ::local-date
    step ::period/period]
   (when (chrono-period/is-zero step)
     (throw (ex JavaIllegalArgumentException "step is zero" {:step step})))
   (let [end (to-epoch-day end-exclusive)
         start (to-epoch-day this)
         until (math/subtract-exact end start)
         months (period/to-total-months step)
         days (period/get-days step)]
     (when (or (and (< months 0) (> days 0))
               (and (> months 0) (< days 0)))
       (throw (ex JavaIllegalArgumentException "period months and days are of opposite sign")))
     (if (zero? until)
       []
       (let [sign (if (or (> months 0) (> days 0)) 1 -1)]
         (when (not= (neg? sign) (neg? until))
           (throw (ex JavaIllegalArgumentException (str end-exclusive (if (< sign 0) " > " " < ") this))))
         (if (zero? months)
           (->> (clojure.core/range 0 (quot (math/subtract-exact until sign) days))
                (map #(of-epoch-day (math/add-exact start (math/multiply-exact % days)))))
           (let [steps (math/add-exact (long (/ (math/multiply-exact until 1600)
                                                (math/add-exact (math/multiply-exact months 48699)
                                                                (math/multiply-exact days 1600))))
                                       1)
                 add-months (math/multiply-exact months steps)
                 add-days (math/multiply-exact days steps)
                 max-add-months (if (pos? months)
                                  (math/subtract-exact (--get-proleptic-month MAX) (--get-proleptic-month this))
                                  (math/subtract-exact (--get-proleptic-month this) (--get-proleptic-month MIN)))
                 [steps add-months add-days] (if (or (> (math/multiply-exact add-months sign)
                                                        max-add-months)
                                                     (>= (math/multiply-exact (math/add-exact (to-epoch-day (plus-months this add-months)) add-days) sign)
                                                         (math/multiply-exact end sign)))
                                               [(dec steps)
                                                (math/subtract-exact add-months months)
                                                (math/subtract-exact add-days days)]
                                               [steps add-months add-days])
                 steps (if (or (> (math/multiply-exact add-months sign)
                                  max-add-months)
                               (>= (math/multiply-exact (math/add-exact (to-epoch-day (plus-months this add-months)) add-days) sign)
                                   (math/multiply-exact end sign)))
                         (dec steps)
                         steps)]
             (->> (clojure.core/range 0 steps)
                  (map #(-> this (plus-months (math/multiply-exact months %)) (plus-days (math/multiply-exact days %))))))))))))

(def-method at-time--chrono ::offset-date-time/offset-date-time
  [this ::local-date
   local-time ::local-time/local-time]
  (local-date-time-impl/of this local-time))

(def-method at-time (s/or :local-date-time ::local-date-time/local-date-time
                          :offset-time ::offset-time/offset-time)
  ([this ::local-date
    offset-time ::offset-time/offset-time]
   (offset-date-time-impl/of
    (local-date-time-impl/of
     this
     (offset-time/to-local-time offset-time))
    (offset-time/get-offset offset-time)))

  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (at-time--chrono this (local-time-impl/of hour minute)))

  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (at-time--chrono this (local-time-impl/of hour minute second)))

  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (at-time--chrono this (local-time-impl/of hour minute second nano-of-second))))

(def-method at-start-of-day (s/or :local-date-time ::local-date-time/local-date-time
                                  :zoned-date-time ::zoned-date-time/zoned-date-time)
  ([this ::local-date]
   (local-date-time-impl/of this local-time-impl/MIDNIGHT))

  ([this ::local-date
    zone ::zone-id/zone-id]
   (asserts/require-non-nil zone "zone")
   (let [ldt (at-time--chrono this local-time-impl/MIDNIGHT)]
     (if (satisfies? zone-offset/IZoneOffset zone)
       (zoned-date-time-impl/of ldt zone)
       (let [rules (zone-id/get-rules zone)
             trans (zone-rules/get-transition rules ldt)]
         (if (and trans (zone-offset-transition/is-gap trans))
           (zoned-date-time-impl/of
            (zone-offset-transition/get-date-time-after trans)
            zone)
           (zoned-date-time-impl/of ldt zone)))))))

(def-method to-epoch-second ::j/long
  [this ::local-date
   time ::local-time/local-time
   offset ::zone-offset/zone-offset]
  (asserts/require-non-nil time "time")
  (asserts/require-non-nil offset "offset")
  (math/subtract-exact (math/add-exact (math/multiply-exact (to-epoch-day this)
                                                            local-time-impl/SECONDS_PER_DAY)
                                       (local-time/to-second-of-day time))
                       (zone-offset/get-total-seconds offset)))

(def-method compare-to0 ::j/int
  [this ::local-date
   other ::local-date]
  (let [cmp (math/subtract-exact (:year this) (:year other))]
    (if-not (zero? cmp)
      cmp
      (let [cmp (math/subtract-exact (:month this) (:month other))]
        (if-not (zero? cmp)
          cmp
          (math/subtract-exact (:day this) (:day other)))))))

(extend-type LocalDate
  local-date/ILocalDate
  (get-month [this] (get-month this))
  (get-day-of-week [this] (get-day-of-week this))
  (get-day-of-year [this] (get-day-of-year this))
  (get-year [this] (get-year this))
  (get-month-value [this] (get-month-value this))
  (get-day-of-month [this] (get-day-of-month this))
  (with-year [this year] (with-year this year))
  (with-month [this month] (with-month this month))
  (with-day-of-month [this day-of-month] (with-day-of-month this day-of-month))
  (with-day-of-year [this day-of-year] (with-day-of-year this day-of-year))
  (plus-years [this years-to-add] (plus-years this years-to-add))
  (plus-months [this months-to-add] (plus-months this months-to-add))
  (plus-weeks [this weeks-to-add] (plus-weeks this weeks-to-add))
  (plus-days [this days-to-add] (plus-days this days-to-add))
  (minus-years [this years-to-subtract] (minus-years this years-to-subtract))
  (minus-months [this months-to-subtract] (minus-months this months-to-subtract))
  (minus-weeks [this weeks-to-subtract] (minus-weeks this weeks-to-subtract))
  (minus-days [this days-to-subtract] (minus-days this days-to-subtract))
  (days-until [this end] (days-until this end))
  (dates-until
    ([this end-exclusive] (dates-until this end-exclusive))
    ([this end-exclusive step] (dates-until this end-exclusive step)))
  (at-time
    ([this offset-time] (at-time this offset-time))
    ([this hour minute] (at-time this hour minute))
    ([this hour minute second] (at-time this hour minute second))
    ([this hour minute second nano-of-second] (at-time this hour minute second nano-of-second)))
  (at-start-of-day
    ([this] (at-start-of-day this))
    ([this zone] (at-start-of-day this zone)))
  (to-epoch-second [this time offset] (to-epoch-second this time offset)))

(def-method compare-to ::j/int
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (if-not (satisfies? chrono-local-date/IChronoLocalDate other)
    (chrono-local-date-defaults/-compare-to this other)
    (compare-to0 this other)))

(extend-type LocalDate
  time-comparable/ITimeComparable
  (compare-to [this compare-to--overloaded-param] (compare-to this compare-to--overloaded-param)))

(def-method length-of-month ::j/int
  [this ::local-date]
  (->> (:year this)
       (chronology/is-leap-year iso-chronology/INSTANCE)
       (month/length (month/of (:month this)))))

(def-method length-of-year ::j/int
  [this ::local-date]
  (if (is-leap-year this)
    366
    365))

(def-method get-chronology ::chronology/chronology
  [this ::local-date]
  iso-chronology/INSTANCE)

(def-method get-era ::era/era
  [this ::local-date]
  (if (>= (get-year this) 1)
    iso-era/CE
    iso-era/BCE))

(declare from)

(def-method until ::chrono-period/chrono-period
  [this ::local-date
   end-date-exclusive ::chrono-local-date/chrono-local-date]
  (let [end (from end-date-exclusive)
        total-months (math/subtract-exact (--get-proleptic-month end) (--get-proleptic-month this))
        days (math/subtract-exact (:day end) (:day this))
        [total-months days] (cond
                              (and (pos? total-months) (neg? days))
                              (let [total-months (dec total-months)]
                                [total-months
                                 (math/subtract-exact
                                  (to-epoch-day end)
                                  (to-epoch-day (plus-months this total-months)))])

                              (and (neg? total-months) (pos? days))
                              [(inc total-months)
                               (math/subtract-exact
                                days
                                (length-of-month end))]

                              :else [total-months days])
        years (long (/ total-months 12))
        months (rem total-months 12)]
    (period-impl/of years months days)))

(def-method format string?
  [this ::local-date
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

(def-method is-after ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (if (satisfies? local-date/ILocalDate other)
    (pos? (compare-to0 this other))
    (chrono-local-date-defaults/-is-after this other)))

(def-method is-before ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (if (satisfies? local-date/ILocalDate other)
    (neg? (compare-to0 this other))
    (chrono-local-date-defaults/-is-before this other)))

(def-method is-equal ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (if (satisfies? local-date/ILocalDate other)
    (zero? (compare-to0 this other))
    (chrono-local-date-defaults/-is-equal this other)))

(extend-type LocalDate
  chrono-local-date/IChronoLocalDate
  (length-of-month [this] (length-of-month this))
  (length-of-year [this] (length-of-year this))
  (is-leap-year [this] (is-leap-year this))
  (to-epoch-day [this] (to-epoch-day this))
  (get-chronology [this] (get-chronology this))
  (get-era [this] (get-era this))
  (until [this end-date-exclusive] (until this end-date-exclusive))
  (format [this formatter] (format this formatter))
  (at-time [this local-time] (at-time--chrono this local-time))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other))
  (is-equal [this other] (is-equal this other)))

(declare get-long of-epoch-day)

(def-method with ::local-date
  ([this ::local-date
    adjuster ::temporal-adjuster/temporal-adjuster]
   (if (impl/local-date? adjuster)
     adjuster
     (temporal-adjuster/adjust-into adjuster this)))

  ([this ::local-date
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (satisfies? temporal-field/ITemporalField field)
     (temporal-field/adjust-into field this new-value)
     (do
       (chrono-field/check-valid-value field new-value)
       (condp = field
         chrono-field/DAY_OF_WEEK
         (plus-days this (- new-value (day-of-week/get-value (get-day-of-week this))))

         chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH
         (plus-days this (- new-value (get-long this chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH)))

         chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR
         (plus-days this (- new-value (get-long this chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR)))

         chrono-field/DAY_OF_MONTH
         (with-day-of-month this (int new-value))

         chrono-field/DAY_OF_YEAR
         (with-day-of-year this (int new-value))

         chrono-field/EPOCH_DAY
         (of-epoch-day new-value)

         chrono-field/ALIGNED_WEEK_OF_MONTH
         (plus-weeks this (- new-value (get-long this chrono-field/ALIGNED_WEEK_OF_MONTH)))

         chrono-field/ALIGNED_WEEK_OF_YEAR
         (plus-weeks this (- new-value (get-long this chrono-field/ALIGNED_WEEK_OF_YEAR)))

         chrono-field/MONTH_OF_YEAR
         (with-month this (int new-value))

         chrono-field/PROLEPTIC_MONTH
         (plus-months this (- new-value (--get-proleptic-month this)))

         chrono-field/YEAR_OF_ERA
         (with-year this (int (if (>= (get-year this) 1) new-value (inc (- new-value)))))

         chrono-field/YEAR
         (with-year this (int new-value))

         chrono-field/ERA
         (if (= (get-long this chrono-field/ERA) new-value)
           this
           (with-year this (- 1 (get-year this))))

         (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " (pr-str field)) {:this this :field field})))))))

(def-method plus ::local-date
  ([this ::local-date
    amount-to-add ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-add)
     (-> this
         (plus-months (period/to-total-months amount-to-add))
         (plus-days (period/get-days amount-to-add)))
     (do
       (asserts/require-non-nil amount-to-add "amount-to-add")
       (temporal-amount/add-to amount-to-add this))))

  ([this ::local-date
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if-not (chrono-unit/chrono-unit? unit)
     (temporal-unit/add-to unit this amount-to-add)
     (condp = unit
       chrono-unit/DAYS (plus-days this amount-to-add)
       chrono-unit/WEEKS (plus-weeks this amount-to-add)
       chrono-unit/MONTHS (plus-months this amount-to-add)
       chrono-unit/YEARS (plus-years this amount-to-add)
       chrono-unit/DECADES (plus-years this (math/multiply-exact amount-to-add 10))
       chrono-unit/CENTURIES (plus-years this (math/multiply-exact amount-to-add 100))
       chrono-unit/MILLENNIA (plus-years this (math/multiply-exact amount-to-add 1000))
       chrono-unit/ERAS (with this
                              chrono-field/ERA
                              (math/add-exact (get-long this chrono-field/ERA) amount-to-add))
       (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                  {:this this :unit unit :amount-to-add amount-to-add}))))))

(def-method minus ::local-date
  ([this ::local-date
    amount-to-subtract ::temporal-amount/temporal-amount]
   (if (satisfies? period/IPeriod amount-to-subtract)
     (-> this
         (minus-months (period/to-total-months amount-to-subtract))
         (minus-days (period/get-days amount-to-subtract)))
     (do
       (asserts/require-non-nil amount-to-subtract "amount-to-subtract")
       (temporal-amount/subtract-from amount-to-subtract this))))

  ([this ::local-date
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-min-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

(defn- months-until [this end]
  (let [packed-1 (math/add-exact (math/multiply-exact (--get-proleptic-month this)
                                                      32)
                                 (get-day-of-month this))
        packed-2 (math/add-exact (math/multiply-exact (--get-proleptic-month end)
                                                      32)
                                 (get-day-of-month end))]
    (long (/ (math/subtract-exact packed-2 packed-1)
             32))))

(declare from)
(def-method until--temporal ::j/long
  [this ::local-date
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (condp = unit
        chrono-unit/DAYS (days-until this end)
        chrono-unit/WEEKS (long (/ (days-until this end) 7))
        chrono-unit/MONTHS (months-until this end)
        chrono-unit/YEARS (long (/ (months-until this end) 12))
        chrono-unit/DECADES (long (/ (months-until this end) 120))
        chrono-unit/CENTURIES (long (/ (months-until this end) 1200))
        chrono-unit/MILLENNIA (long (/ (months-until this end) 12000))
        chrono-unit/ERAS (math/subtract-exact (get-long end chrono-field/ERA)
                                              (get-long this chrono-field/ERA))
        (throw (ex UnsupportedTemporalTypeException (str "Unsupported unit: " unit)
                   {:this this :unit unit :end-exclusive end-exclusive}))))))

(extend-type LocalDate
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
  (until [this end-exclusive unit] (until--temporal this end-exclusive unit)))

(def-method is-supported ::j/boolean
  [this ::local-date
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (chrono-local-date-defaults/-is-supported this field-or-unit))

(def-method range ::value-range/value-range
  [this ::local-date
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/range-refined-by field this)
    (if-not (chrono-field/-is-date-based field)
      (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:this this :field field}))
      (condp = field
        chrono-field/DAY_OF_MONTH (value-range-impl/of 1 (length-of-month this))
        chrono-field/DAY_OF_YEAR (value-range-impl/of 1 (length-of-year this))
        chrono-field/ALIGNED_WEEK_OF_MONTH (value-range-impl/of 1
                                                                (if (and (= (get-month this) month/FEBRUARY)
                                                                         (not (is-leap-year this)))
                                                                  4
                                                                  5))
        chrono-field/YEAR_OF_ERA (if (pos? (get-year this))
                                   (value-range-impl/of 1 year-impl/MAX_VALUE)
                                   (value-range-impl/of 1 (inc year-impl/MAX_VALUE)))
        (temporal-field/range field)))))

(defn --get0 [{:keys [year month day] :as this} field]
  (condp = field
    chrono-field/DAY_OF_WEEK (day-of-week/get-value (get-day-of-week this))
    chrono-field/ALIGNED_DAY_OF_WEEK_IN_MONTH (inc (rem (dec day) 7))
    chrono-field/ALIGNED_DAY_OF_WEEK_IN_YEAR (inc (rem (dec (get-day-of-year this)) 7))
    chrono-field/DAY_OF_MONTH day
    chrono-field/DAY_OF_YEAR (get-day-of-year this)
    chrono-field/EPOCH_DAY (throw (ex UnsupportedTemporalTypeException "Invalid field 'EpochDay' for `get` method, use `get-long`) instead" {:this this :field field}))
    chrono-field/ALIGNED_WEEK_OF_MONTH (inc (quot (dec day) 7))
    chrono-field/ALIGNED_WEEK_OF_YEAR (inc (quot (dec (get-day-of-year this)) 7))
    chrono-field/MONTH_OF_YEAR month
    chrono-field/PROLEPTIC_MONTH (throw (ex UnsupportedTemporalTypeException "Invalid field 'ProlepticMonth' for `get` method, use `get-long` instead" {:this this :field field}))
    chrono-field/YEAR_OF_ERA (if (>= year 1) year (inc (- year)))
    chrono-field/YEAR year
    chrono-field/ERA (if (>= year 1) 1 0)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:this this :field field}))))

(def-method get ::j/int
  [this ::local-date
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (--get0 this field)
    (temporal-accessor-defaults/-get this field)))

(def-method get-long ::j/long
  [{:keys [year month day] :as this} ::local-date
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (cond
      (= field chrono-field/EPOCH_DAY)
      (to-epoch-day this)

      (= field chrono-field/PROLEPTIC_MONTH)
      (--get-proleptic-month this)

      :else
      (--get0 this field))))

(def-method query ::temporal-query/result
  [this ::local-date
   q ::temporal-query/temporal-query]
  (if (= q (temporal-queries/local-date))
    this
    (chrono-local-date-defaults/-query this q)))

(extend-type LocalDate
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param]
    (is-supported this is-supported--overloaded-param))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(def-method adjust-into ::temporal/temporal
  [this ::local-date
   temporal ::temporal/temporal]
  (chrono-local-date-defaults/-adjust-into this temporal))

(extend-type LocalDate
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(declare of-instant)
(def-constructor now ::local-date
  ([]
   (now (clock-impl/system-default-zone)))

  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (if (satisfies? zone-id/IZoneId clock-or-zone-id)
     (now (clock-impl/system clock-or-zone-id))
     (do
       (asserts/require-non-nil clock-or-zone-id "clock")
       (of-instant (clock/instant clock-or-zone-id)
                   (clock/get-zone clock-or-zone-id))))))

(def-constructor of ::local-date
  [year ::j/year
   month (s/or :number ::j/month-of-year
               :month ::month/month)
   day-of-month ::j/day-of-month]
  (impl/of year month day-of-month))

(def-constructor of-year-day ::local-date
  [year ::j/int
   day-of-year ::j/int]
  (chrono-field/check-valid-value chrono-field/YEAR year)
  (chrono-field/check-valid-value chrono-field/DAY_OF_YEAR day-of-year)
  (let [leap? (chronology/is-leap-year iso-chronology/INSTANCE year)]
    (when (and (= day-of-year 366)
               (not leap?))
      (throw (ex DateTimeException (str "Invalid date 'DayOfYear 366' as '" year "' is not a leap year")
                 {:year year :day-of-year day-of-year})))
    (let [moy (month/of (inc (long (/ (dec day-of-year) 31))))
          month-end (-> (month/first-day-of-year moy leap?)
                        (+ (month/length moy leap?))
                        (- 1))
          moy (if (> day-of-year month-end)
                (month/plus moy 1)
                moy)
          dom (-> (- day-of-year
                     (month/first-day-of-year moy leap?))
                  (+ 1))]
      (impl/create year (month/get-value moy) dom))))

(def-constructor of-instant ::local-date
  [instant ::instant/instant
   zone ::zone-id/zone-id]
  (asserts/require-non-nil instant "instant")
  (asserts/require-non-nil zone "zone")
  (let [rules (zone-id/get-rules zone)
        offset (zone-rules/get-offset rules instant)
        local-second (math/add-exact (instant/get-epoch-second instant)
                                     (zone-offset/get-total-seconds offset))
        local-epoch-day (math/floor-div local-second
                                        local-time-impl/SECONDS_PER_DAY)]
    (of-epoch-day local-epoch-day)))

(def-constructor of-epoch-day ::local-date
  [epoch-day ::j/long]
  (impl/of-epoch-day epoch-day))

(def-constructor from ::local-date
  [temporal ::temporal-accessor/temporal-accessor]
  (impl-impl/from temporal))

(def-constructor parse ::local-date
  ([text ::j/char-sequence]
   (impl/parse text))

  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (impl/parse text)))

(def-method to-string string?
  [{:keys [year month day]} ::local-date]
  (let [abs-year (Math/abs year)]
    (cond-> ""
      (< abs-year 1000)
      (cond->
          (neg? year) (-> (str (- year 10000))
                          (string/delete-char-at 1))
          (not (neg? year)) (-> (str (+ year 10000))
                                (string/delete-char-at 0)))

      (not (< abs-year 1000))
      (cond->
          (> year 9999) (str "+")
          true (str year))

      (< month 10) (str "-0")
      (not (< month 10)) (str "-")
      true (str month)
      (< day 10) (str "-0")
      (not (< day 10)) (str "-")
      true (str day))))

(extend-type LocalDate
  string/IString
  (to-string [this] (to-string this)))
