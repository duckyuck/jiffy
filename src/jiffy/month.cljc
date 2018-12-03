(ns jiffy.month
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.conversion :refer [jiffy->java same?]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.enum #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as temporal]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.chrono.iso-chronology-impl :as iso-chronology]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.chrono.chronology :as chronology]
            ;; [jiffy.local-date-impl :as local-date]
            ))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java
(defprotocol IMonth
  (get-value [this])
  (get-display-name [this style locale])
  (plus [this months])
  (minus [this months])
  (length [this leap-year])
  (min-length [this])
  (max-length [this])
  (first-day-of-year [this leap-year])
  (first-month-of-quarter [this]))

(defrecord Month [ordinal enum-name])

(s/def ::create-args (s/tuple ::j/long string?))
(def create ->Month)
(s/def ::month (j/constructor-spec Month create ::create-args))
(s/fdef create :args ::create-args :ret ::month)

(defenum create
  [JANUARY []
   FEBRUARY []
   MARCH []
   APRIL []
   MAY []
   JUNE []
   JULY []
   AUGUST []
   SEPTEMBER []
   OCTOBER []
   NOVEMBER []
   DECEMBER []])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java
(s/def ::values-args empty?)
(defn values [] (sort-by :ordinal (vals @enums)))
(s/fdef values :ret (s/coll-of #(satisfies? IMonth %)))

(defmacro args [& x] `(s/tuple ::month ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L95
(s/def ::get-value-args (args))
(defn -get-value [this] (inc (:ordinal this)))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L253
(s/def ::get-display-name-args (args ::text-style/text-style ::j/locale))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L392
(s/def ::plus-args (args ::j/long))
(defn -plus [this months]
  (nth (values)
       (rem (+ (:ordinal this)
               (int (rem months 12))
               12)
            12)))
(s/fdef -plus :args ::plus-args :ret ::month)
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L408
(s/def ::minus-args (args ::j/long))
(defn -minus [this months]
  (-plus this (- (rem months 12))))
(s/fdef -minus :args ::minus-args :ret ::month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L425
(s/def ::length-args (args ::j/boolean))
(defn -length [this leap-year]
  (cond
    (= this FEBRUARY) (if leap-year 29 28)
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))
(s/fdef -length :args ::length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L448
(s/def ::min-length-args (args))
(defn -min-length [this]
  (cond
    (= this FEBRUARY) 28
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))
(s/fdef -min-length :args ::min-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L471
(s/def ::max-length-args (args))
(defn -max-length [this]
  (cond
    (= this FEBRUARY) 29
    (some #(= this %) [APRIL JUNE SEPTEMBER NOVEMBER]) 30
    :else 31))
(s/fdef -max-length :args ::max-length-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L495
(s/def ::first-day-of-year-args (args ::j/boolean))
(defn -first-day-of-year [this leap-year]
  (let [leap (if leap-year 1 0)]
    (condp = this
      JANUARY 1
      FEBRUARY 32
      MARCH (+ 60 leap)
      APRIL (+ 91 leap)
      MAY (+ 121 leap)
      JUNE (+ 152 leap)
      JULY (+ 182 leap)
      AUGUST (+ 213 leap)
      SEPTEMBER (+ 244 leap)
      OCTOBER (+ 274 leap)
      NOVEMBER (+ 305 leap)
      (+ 335 leap))))
(s/fdef -first-day-of-year :args ::first-day-of-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L538
(s/def ::first-month-of-quarter-args (args))
(defn -first-month-of-quarter [this]
  (nth (values) (* (int (/ (:ordinal this) 3)) 3)))
(s/fdef -first-month-of-quarter :args ::first-month-of-quarter-args :ret ::month)

(extend-type Month
  IMonth
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale))
  (plus [this months] (-plus this months))
  (minus [this months] (-minus this months))
  (length [this leap-year] (-length this leap-year))
  (min-length [this] (-min-length this))
  (max-length [this] (-max-length this))
  (first-day-of-year [this leap-year] (-first-day-of-year this leap-year))
  (first-month-of-quarter [this] (-first-month-of-quarter this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L278
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this field]
  (if (chrono-field/chrono-field? field)
    (= field chrono-field/MONTH_OF_YEAR)
    (and field (temporal-field/is-supported-by field this))))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L308
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field]
  (if (= field chrono-field/MONTH_OF_YEAR)
    (temporal-field/range field)
    (temporal-accessor-defaults/-range this field)))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L341
(s/def ::get-args (args ::temporal-field/temporal-field))
(defn -get [this field]
  (if (= field chrono-field/MONTH_OF_YEAR)
    (get-value this)
    (temporal-accessor-defaults/-range this field)))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L371
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field]
  (cond
    (= field chrono-field/MONTH_OF_YEAR)
    (get-value this)

    (chrono-field/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
               {:month this :field field}))

    :else
    (temporal-field/get-from field this)))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L563
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query]
  (condp = query
    (temporal-queries/chronology)
    iso-chronology/INSTANCE

    (temporal-queries/precision)
    chrono-unit/MONTHS

    (temporal-accessor-defaults/-query this query)))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Month
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L608
(s/def ::adjust-into-args (args ::temporal/temporal))
(defn -adjust-into [this temporal]
  (if (not= (chronology/from temporal) iso-chronology/INSTANCE)
    (throw (ex DateTimeException "Adjustment only supported on ISO date-time"))
    (temporal/with temporal chrono-field/MONTH_OF_YEAR (get-value this))))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

(extend-type Month
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java
(s/def ::value-of-args (s/tuple string?))
(defn value-of [enum-name]
  (or (@enums enum-name)
      (throw (ex JavaIllegalArgumentException (str "no enum constant jiffy.month/" enum-name)))))
(s/fdef value-of :args ::value-of-args :ret ::month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L185
(s/def ::of-args (s/tuple ::j/month-of-year))
(defn of [month]
  (when (not (<= 1 month 12))
    (throw (ex DateTimeException (str "Invalid value for MonthOfYear: " month))))
  (nth (values) (dec month)))
(s/fdef of :args ::of-args :ret ::month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Month.java#L211
(s/def ::from-args (s/tuple ::temporal-accessor/temporal-accessor))
(defn from [temporal]
  (wip ::from)

  ;; TODO: fix circular deps caused by local-date dependency

  ;; (if (= (type temporal) Month)
  ;;   temporal
  ;;   (try*
  ;;    (let [temporal (if (not= iso-chronology/INSTANCE (chronology/from temporal))
  ;;                     (local-date/from temporal)
  ;;                     temporal)]
  ;;      (of (temporal-accessor/get temporal chrono-field/MONTH_OF_YEAR)))
  ;;    (catch DateTimeException e
  ;;      (throw (ex DateTimeException (str "Unable to obtain Month from TemporalAccessor: " temporal " of type " (type temporal))
  ;;                 {:temporal temporal})))))
  )
(s/fdef from :args ::from-args :ret ::month)

#?(:clj
   (defmethod jiffy->java Month [month]
     (java.time.Month/valueOf (:enum-name month))))

#?(:clj
   (defmethod same? Month
     [jiffy-object java-object]
     (= (map #(% jiffy-object) [:ordinal
                                :enum-name])
        (map #(% java-object) [(memfn ordinal)
                               (memfn name)]))))
