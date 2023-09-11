(ns jiffy.local-date
  (:refer-clojure :exclude [range get second])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :refer [create #?@(:cljs [LocalDate])] :as impl]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.chrono.iso-era :as iso-era]
            [jiffy.protocols.clock :as clock]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.month :as month]
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
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.math :as math]
            [jiffy.chrono.chrono-local-date-defaults :as chrono-local-date-defaults]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults])
  #?(:clj (:import [jiffy.local_date_impl LocalDate])))

(def DAYS_PER_CYCLE impl/DAYS_PER_CYCLE)
(def DAYS_0000_TO_1970 impl/DAYS_0000_TO_1970)

(s/def ::local-date ::impl/local-date)

(defn --get-proleptic-month [this]
  (-> (math/multiply-exact (:year this) 12)
      (math/add-exact (:month this))
      (math/subtract-exact 1)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L609
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L694
(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::local-date]
  (day-of-week/of (-> (math/add-exact (to-epoch-day this) 3)
                      (math/floor-mod 7)
                      inc)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L698
(def-method get-day-of-year ::j/int
  [this ::local-date]
  (-> (get-month this)
      (month/-first-day-of-year (is-leap-year this))
      (+ (:day this)
         (- 1))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L761
(def-method get-year ::j/int
  [this ::local-date]
  (:year this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L775
(def-method get-month-value ::j/int
  [this ::local-date]
  (:month this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L801
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1081
(def-method with-year ::local-date
  [{:keys [year month day] :as this} ::local-date
   new-year ::j/int]
  (if (= new-year year)
    this
    (do
      (chrono-field/check-valid-value chrono-field/YEAR new-year)
      (resolve-previous-valid new-year month day))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1100
(def-method with-month ::local-date
  [{:keys [year month day] :as this} ::local-date
   new-month ::j/int]
  (if (= new-month month)
    this
    (do
      (chrono-field/check-valid-value chrono-field/MONTH_OF_YEAR new-month)
      (resolve-previous-valid year new-month day))))


;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1120
(def-method with-day-of-month ::local-date
  [{:keys [day year month] :as this} ::local-date
   day-of-month ::j/int]
  (if (= day day-of-month)
    this
    (impl/of year month day-of-month)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1139
(def-method with-day-of-year ::local-date
  [this ::local-date
   day-of-year ::j/int]
  (if (= (get-day-of-year this) day-of-year)
    this
    (of-year-day (:year this) day-of-year)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1298
(def-method plus-years ::local-date
  [{:keys [year month day] :as this} ::local-date
   years-to-add ::j/long]
  (if (zero? years-to-add)
    this
    (resolve-previous-valid
     (chrono-field/check-valid-int-value chrono-field/YEAR (+ year years-to-add))
     month
     day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1326
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1371
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

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1352
(def-method plus-weeks ::local-date
  [this ::local-date
   weeks-to-add ::j/long]
  (plus-days this (math/multiply-exact weeks-to-add 7)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1472
(def-method minus-years ::local-date
  [this ::local-date
   years-to-subtract ::j/long]
  (if (= years-to-subtract math/long-min-value)
    (plus-years (plus-years this math/long-max-value)
                1)
    (plus-years this (- years-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1496
(def-method minus-months ::local-date
  [this ::local-date
   months-to-subtract ::j/long]
  (if (= months-to-subtract math/long-min-value)
    (plus-months (plus-months this math/long-max-value)
                 1)
    (plus-months this (- months-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1515
(def-method minus-weeks ::local-date
  [this ::local-date
   weeks-to-subtract ::j/long]
  (if (= weeks-to-subtract math/long-min-value)
    (plus-weeks (plus-weeks this math/long-max-value)
                1)
    (plus-weeks this (- weeks-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1534
(def-method minus-days ::local-date
  [this ::local-date
   days-to-subtract ::j/long]
  (if (= days-to-subtract math/long-min-value)
    (plus-days (plus-days this math/long-max-value)
               1)
    (plus-days this (- days-to-subtract))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1661
(def-method days-until ::j/long
  [this ::local-date
   end ::local-date]
  (math/subtract-exact (to-epoch-day end) (to-epoch-day this)))

(def-method dates-until (s/coll-of ::local-date)
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1732
  ([this ::local-date
    end-exclusive ::local-date]
   (wip ::-dates-until))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1759
  ([this ::local-date
    end-exclusive ::local-date
    step ::period/period]
   (wip ::-dates-until)))

(def-method at-time ::local-date-time/local-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1848
  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1867
  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (wip ::-at-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1886
  ([this ::local-date
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (wip ::-at-time)))

(def-method at-start-of-day (s/or ::local-date-time/local-date-time
                                   ::zoned-date-time/zoned-date-time)
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1912
  ([this ::local-date]
   (wip ::-at-start-of-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1936
  ([this ::local-date
    zone ::zone-id/zone-id]
   (wip ::-at-start-of-day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1989
(def-method to-epoch-second ::j/long
  [this ::local-date
   time ::local-time/local-time
   offset ::zone-offset/zone-offset]
  (wip ::-to-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2020
(def-method compare-to0 ::j/int
  [this ::local-date
   other-date ::local-date]
  (wip ::-compare-to0))

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
    ([this hour minute] (at-time this hour minute))
    ([this hour minute second] (at-time this hour minute second))
    ([this hour minute second nano-of-second] (at-time this hour minute second nano-of-second)))
  (at-start-of-day
    ([this] (at-start-of-day this))
    ([this zone] (at-start-of-day this zone)))
  (to-epoch-second [this time offset] (to-epoch-second this time offset))
  (compare-to0 [this other-date] (compare-to0 this other-date)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2013
(def-method compare-to ::j/int
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (if-not (satisfies? chrono-local-date/IChronoLocalDate other)
    (chrono-local-date-defaults/-compare-to this other)
    (let [cmp (math/subtract-exact (:year this) (:year other))]
      (if-not (zero? cmp)
        cmp
        (let [cmp (math/subtract-exact (:month this) (:month other))]
          (if-not (zero? cmp)
            cmp
            (math/subtract-exact (:day this) (:day other))))))))

(extend-type LocalDate
  time-comparable/ITimeComparable
  (compare-to [this compare-to--overloaded-param] (compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L607
(def-method length-of-month ::j/int
  [this ::local-date]
  (month/-length (month/of (:month this)) (chronology/is-leap-year iso-chronology/INSTANCE (:year this))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L608
(def-method length-of-year ::j/int
  [this ::local-date]
  (wip ::-length-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L727
(def-method get-chronology ::chronology/chronology
  [this ::local-date]
  (wip ::-get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L747
(def-method get-era ::era/era
  [this ::local-date]
  (wip ::-get-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1704
(def-method until ::chrono-period/chrono-period
  [this ::local-date
   end-date-exclusive ::chrono-local-date/chrono-local-date]
  (wip ::-until))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1814
(def-method format string?
  [this ::local-date
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::-format))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1830
(def-method at-time--chrono ::offset-date-time/offset-date-time
  [this ::local-date
   at-time--chrono--overloaded-param ::offset-time/offset-time]
  (wip ::-at-time--chrono))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2053
(def-method is-after ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (wip ::-is-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2082
(def-method is-before ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (wip ::-is-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L2111
(def-method is-equal ::j/boolean
  [this ::local-date
   other ::chrono-local-date/chrono-local-date]
  (wip ::-is-equal))

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
  (at-time [this at-time--chrono--overloaded-param] (at-time--chrono this at-time--chrono--overloaded-param))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other))
  (is-equal [this other] (is-equal this other)))

(declare get-long of-epoch-day)

(def-method with ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L932
  ([this ::local-date
    adjuster ::temporal-adjuster/temporal-adjuster]
   (if (impl/local-date? adjuster)
     adjuster
     (temporal-adjuster/adjust-into adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1045
  ([this ::local-date
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (satisfies? temporal-field/ITemporalField field)
     (temporal-field/adjust-into field this new-value)
     (do
       (chrono-field/check-valid-value field new-value)
       (case field
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
           (with-year this (dec (- 1 (get-year this)))))

         (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:this this :field field})))))))

(defn with-field [this field new-value]
  (if (instance? java.time.temporal.ChronoField field)
    (let [f field]
      (.checkValidValue f new-value)
)
    (.adjustInto field this new-value)))


(def-method plus ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1168
  ([this ::local-date
    amount-to-add ::j/long]
   (wip ::-plus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1259
  ([this ::local-date
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-plus)))

(def-method minus ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1418
  ([this ::local-date
    amount-to-subtract ::j/long]
   (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1447
  ([this ::local-date
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (wip ::-minus)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1643
(def-method until--temporal ::j/long
  [this ::local-date
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (wip ::-until--temporal))

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

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L539
(def-method is-supported ::j/boolean
  [this ::local-date
   field-or-unit (s/or ::temporal-field/temporal-field
                       ::temporal-unit/temporal-unit)]
  (chrono-local-date-defaults/-is-supported this field-or-unit))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L602
(def-method range ::value-range/value-range
  [this ::local-date
   field ::temporal-field/temporal-field]
  (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L648
(def-method get ::j/int
  [this ::local-date
   field ::temporal-field/temporal-field]
  ;; TODO - this might not be right!
  (temporal-accessor-defaults/-get this field))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L679
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
      (case field
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
        (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field) {:this this :field field}))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1559
(def-method query ::j/wip
  [this ::local-date
   query ::temporal-query/temporal-query]
  (wip ::-query))

(extend-type LocalDate
  temporal-accessor/ITemporalAccessor
  (is-supported [this is-supported--overloaded-param] (is-supported this is-supported--overloaded-param))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this query] (query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L1591
(def-method adjust-into ::temporal/temporal
  [this ::local-date
   temporal ::temporal/temporal]
  (wip ::-adjust-into))

(extend-type LocalDate
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

(def-constructor now ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L197
  ([]
   (wip ::now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L213
  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (wip ::now)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L247
(def-constructor of ::local-date
  [year ::j/year
   month (s/or :number ::j/month-of-yearh
               :month ::month/month)
   day-of-month ::j/day-of-month]
  (impl/of year month day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L287
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
    (let [moy (month/of (inc (/ (dec day-of-year) 31)))
          month-end (-> (month/-first-day-of-year moy leap?)
                        (+ (month/-length moy leap?))
                        (- 1))
          moy (if (> day-of-year month-end)
                (month/-plus moy 1)
                moy)
          dom (-> (- day-of-year
                     (month/-first-day-of-year moy leap?))
                  (+ 1))]
      (impl/create year (month/-get-value moy) dom))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L318
(def-constructor of-instant ::local-date
  [instant ::instant/instant
   zone ::zone-id/zone-id]
  (wip ::of-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L340
(def-constructor of-epoch-day ::local-date
  [epoch-day ::j/long]
  (impl/of-epoch-day epoch-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L391
(def-constructor from ::local-date
  [temporal ::temporal-accessor/temporal-accessor]
  (wip ::from))

(def-constructor parse ::local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L412
  ([text ::j/char-sequence]
   (wip ::parse))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L426
  ([text ::j/char-sequence
    formatter ::date-time-formatter/date-time-formatter]
   (wip ::parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L146
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L151
(def MAX ::MAX--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java#L155
(def EPOCH ::EPOCH--not-implemented)
