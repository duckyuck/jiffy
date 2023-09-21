(ns jiffy.local-date-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [DateTimeParseException DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [clojure.spec.alpha :as s]
            [jiffy.protocols.month :as month]
            [jiffy.asserts :as asserts]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.chrono.iso-chronology :as iso-chronology-2]
            [jiffy.year-impl :as year-impl]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.math :as math]))

(def-record LocalDate ::local-date-record
  [year ::j/year
   month ::j/month-of-year
   day ::j/day-of-month])

(defn local-date? [x] (instance? LocalDate x))

(defn valid? [{:keys [year month day]}]
  (try*
    (chrono-field/check-valid-value chrono-field/YEAR year)
    (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR month)
    (chrono-field/check-valid-value chrono-field/DAY_OF_MONTH day)
    (or (<= day 28)
        (let [dom (cond
                    (= month 2) (if (chronology/is-leap-year iso-chronology-2/INSTANCE year)
                                  29
                                  28)
                    (#{4 6 9 11} month) 30
                    :else 31)]
          (<= day dom)))
    (catch :default e
      false)))

(s/def ::local-date (s/and ::local-date-record valid?))

(def-constructor create ::local-date
  [year ::j/year
   month ::j/month-of-year
   day ::j/day-of-month]
  (if (valid? {:year year :month month :day day})
    (->LocalDate year month day)
    (throw (ex DateTimeException (str "Invalid date. Month: '" (pr-str month) "', day: '" day "'")))))

(def DAYS_PER_CYCLE 146097)
(def DAYS_0000_TO_1970 (- (* DAYS_PER_CYCLE 5) ( + (* 30 365) 7)))

(defmacro args [& x] `(s/tuple ::local-date ~@x))

(defn- --day-est [zero-day year-est]
  (- zero-day (-> (* 365 year-est)
                  (+ (long (/ year-est 4)))
                  (- (long (/ year-est 100)))
                  (+ (long (/ year-est 400))))))

(def-constructor of-epoch-day ::local-date
  [epoch-day ::j/long]
  (chrono-field/check-valid-value chrono-field/EPOCH_DAY epoch-day)
  (let [[adjust zero-day] (let [zero-day (- (+ epoch-day DAYS_0000_TO_1970) 60)]
                            (if-not (neg? zero-day)
                              [0 zero-day]
                              (let [adjust-cycles (dec (long (/ (inc zero-day) DAYS_PER_CYCLE)))]
                                [(* adjust-cycles 400)
                                 (+ zero-day (* (- adjust-cycles) DAYS_PER_CYCLE))])))
        [year-est day-est] (let [year-est (long (/ (+ (* 400 zero-day) 591) DAYS_PER_CYCLE))
                                 day-est (--day-est zero-day year-est)]
                             (if-not (neg? day-est)
                               [year-est day-est]
                               [(dec year-est) (--day-est zero-day (dec year-est))]))
        year-est (+ year-est adjust)
        march-doy-0 day-est
        march-month-0 (long (/ (+ (* march-doy-0 5) 2) 153))
        month (-> march-month-0 (+ 2) (mod 12) (+ 1))
        dom (-> march-doy-0
                (- (-> march-month-0 (* 306) (+ 5) (/ 10) long))
                (+ 1))
        year-est (+ year-est (long (/ march-month-0 10)))
        year (chrono-field/check-valid-int-value chrono-field/YEAR year-est)]
    (create year month dom)))

(def-constructor of ::local-date
  [year ::j/year
   month (s/or :number ::j/month-of-year
               :month ::month/month)
   day-of-month ::j/day-of-month]
  (chrono-field/check-valid-value chrono-field/YEAR year)
  (chrono-field/check-valid-value chrono-field/DAY_OF_MONTH day-of-month)
  (if (satisfies? month/IMonth month)
    (asserts/require-non-nil month "month")
    (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR month))
  (if (satisfies? month/IMonth month)
    (create year (month/get-value month) day-of-month)
    (create year month day-of-month)))

(def-constructor parse ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L412
  ([text ::j/char-sequence]
   (if-let [[year month day] (some->> (re-matches #"([\+-]?\d{4})-(\d{2})-(\d{2})*" text)
                                      rest
                                      (map math/parse-long))]
     (of year month day)
     (throw (ex DateTimeParseException (str "Failed to parse LocalDate: '" text "'")))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L426
  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))

(def MIN (of year-impl/MIN_VALUE 1 1))
(def MAX (of year-impl/MAX_VALUE 12 31))
(def EPOCH (of 1970 1 1))
