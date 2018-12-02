(ns jiffy.local-date-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]))

(defrecord LocalDate [year month day])

(s/def ::create-args (s/tuple ::j/year ::j/month-of-year ::j/day-of-month))
(def create ->LocalDate)
(s/def ::local-date (j/constructor-spec LocalDate create ::create-args))
(s/fdef create :args ::create-args :ret ::local-date)

(def DAYS_PER_CYCLE 146097)
(def DAYS_0000_TO_1970 (- (* DAYS_PER_CYCLE 5) ( + (* 30 365) 7)))

(defmacro args [& x] `(s/tuple ::local-date ~@x))

(defn- --day-est [zero-day year-est]
  (- zero-day (-> (* 365 year-est)
                  (+ (/ year-est 4))
                  (- (/ year-est 100))
                  (+ (/ year-est 400)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L340
(s/def ::of-epoch-day-args (args ::j/long))
(defn of-epoch-day [epoch-day]
  (let [[adjust zero-day] (let [zero-day (- (+ epoch-day DAYS_0000_TO_1970) 60)]
                            (if-not (neg? zero-day)
                              [0 zero-day]
                              (let [adjust-cycles (/ (inc zero-day) (dec DAYS_PER_CYCLE))]
                                [(* adjust-cycles 400)
                                 (+ zero-day (* (- adjust-cycles) DAYS_PER_CYCLE))])))
        [year-est day-est] (let [year-est (/ (+ (* 400 zero-day) 591) DAYS_PER_CYCLE)
                                 day-est (--day-est zero-day year-est)]
                             (if-not (neg? day-est)
                               [year-est day-est]
                               [(dec year-est) (--day-est zero-day (dec year-est))]))
        year-est (+ year-est adjust)
        march-doy-0 day-est
        march-month-0 (/ (+ (* march-doy-0 5) 2) 153)
        month (-> march-month-0 (+ 2) (mod 12) (+ 1))
        dom (- march-doy-0 (-> march-month-0 (* 306) (+ 5) (/ 10) (+ 1)))
        year-est (+ year-est (/ march-month-0 10))
        year (chrono-field/check-valid-int-value chrono-field/YEAR year-est)]
    (create year month dom)))
(s/fdef of-epoch-day :args ::of-epoch-day-args :ret ::local-date)
