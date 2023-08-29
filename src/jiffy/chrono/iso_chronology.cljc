(ns jiffy.chrono.iso-chronology
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.abstract-chronology :as abstract-chronology]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.chrono.iso-chronology :as iso-chronology]
            [jiffy.protocols.chrono.iso-era :as iso-era]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.resolver-style :as resolver-style]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.period :as period]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zoned-date-time :as zoned-date-time]
            [jiffy.specs :as j]))

(def-record IsoChronology ::iso-chronology [wip ::j/wip])

(def-constructor create ::iso-chronology [] (IsoChronology. ::wip))

(defmacro args [& x] `(s/tuple ::iso-chronology ~@x))

;; FIXME: no implementation found from inherited class interface java.lang.Comparable

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L589
(s/def ::resolve-proleptic-month-args (args ::j/wip ::resolver-style/resolver-style))
(defn -resolve-proleptic-month [this field-values resolver-style] (wip ::-resolve-proleptic-month))
(s/fdef -resolve-proleptic-month :args ::resolve-proleptic-month-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L601
(s/def ::resolve-year-of-era-args (args ::j/wip ::resolver-style/resolver-style))
(defn -resolve-year-of-era [this field-values resolver-style] (wip ::-resolve-year-of-era))
(s/fdef -resolve-year-of-era :args ::resolve-year-of-era-args :ret ::local-date/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L636
(s/def ::resolve-ymd-args (args ::j/wip ::resolver-style/resolver-style))
(defn -resolve-ymd [this field-values resolver-style] (wip ::-resolve-ymd))
(s/fdef -resolve-ymd :args ::resolve-ymd-args :ret ::chrono-local-date/chrono-local-date)

(extend-type IsoChronology
  abstract-chronology/IAbstractChronology
  (resolve-proleptic-month [this field-values resolver-style] (-resolve-proleptic-month this field-values resolver-style))
  (resolve-year-of-era [this field-values resolver-style] (-resolve-year-of-era this field-values resolver-style))
  (resolve-ymd [this field-values resolver-style] (-resolve-ymd this field-values resolver-style)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L157
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L174
(s/def ::get-calendar-type-args (args))
(defn -get-calendar-type [this] (wip ::-get-calendar-type))
(s/fdef -get-calendar-type :args ::get-calendar-type-args :ret string?)

(s/def ::date-args (args ::j/wip))
(defn -date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L267
  ([this temporal] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L209
  ([this proleptic-year month day-of-month] (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L192
  ([this era year-of-era month day-of-month] (wip ::-date)))
(s/fdef -date :args ::date-args :ret ::local-date/local-date)

(s/def ::date-year-day-args (args ::j/wip))
(defn -date-year-day
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L238
  ([this proleptic-year day-of-year] (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L223
  ([this era year-of-era day-of-year] (wip ::-date-year-day)))
(s/fdef -date-year-day :args ::date-year-day-args :ret ::local-date/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L252
(s/def ::date-epoch-day-args (args ::j/long))
(defn -date-epoch-day [this epoch-day] (wip ::-date-epoch-day))
(s/fdef -date-epoch-day :args ::date-epoch-day-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L291
(s/def ::epoch-second-args (args ::j/int ::j/int ::j/int ::j/int ::j/int ::j/int ::zone-offset/zone-offset))
(defn -epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] (wip ::-epoch-second))
(s/fdef -epoch-second :args ::epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L369
(s/def ::local-date-time-args (args ::temporal-accessor/temporal-accessor))
(defn -local-date-time [this temporal] (wip ::-local-date-time))
(s/fdef -local-date-time :args ::local-date-time-args :ret ::chrono-local-date-time/chrono-local-date-time)

(s/def ::zoned-date-time-args (args ::j/wip))
(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L383
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L398
  ([this instant zone] (wip ::-zoned-date-time)))
(s/fdef -zoned-date-time :args ::zoned-date-time-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

(s/def ::date-now-args (args ::j/wip))
(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L416
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L433
  ([this date-now--overloaded-param] (wip ::-date-now)))
(s/fdef -date-now :args ::date-now-args :ret ::local-date/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L475
(def-method -is-leap-year ::j/boolean
  [this ::iso-chronology
   proleptic-year ::j/long]
  (and (zero? (bit-and proleptic-year 3))
       (or (not (zero? (mod proleptic-year 100)))
           (zero? (mod proleptic-year 400)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L480
(s/def ::proleptic-year-args (args ::era/era ::j/int))
(defn -proleptic-year [this era year-of-era] (wip ::-proleptic-year))
(s/fdef -proleptic-year :args ::proleptic-year-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L488
(s/def ::era-of-args (args ::j/int))
(defn -era-of [this era-value] (wip ::-era-of))
(s/fdef -era-of :args ::era-of-args :ret ::iso-era/iso-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L493
(s/def ::eras-args (args))
(defn -eras [this] (wip ::-eras))
(s/fdef -eras :args ::eras-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L584
(s/def ::resolve-date-args (args ::j/wip ::resolver-style/resolver-style))
(defn -resolve-date [this field-values resolver-style] (wip ::-resolve-date))
(s/fdef -resolve-date :args ::resolve-date-args :ret ::local-date/local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L658
(s/def ::range-args (args ::chrono-field/chrono-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L676
(s/def ::period-args (args ::j/int ::j/int ::j/int))
(defn -period [this years months days] (wip ::-period))
(s/fdef -period :args ::period-args :ret ::period/period)

(extend-type IsoChronology
  chronology/IChronology
  (get-id [this] (-get-id this))
  (get-calendar-type [this] (-get-calendar-type this))
  (date
    ([this temporal] (-date this temporal))
    ([this proleptic-year month day-of-month] (-date this proleptic-year month day-of-month))
    ([this era year-of-era month day-of-month] (-date this era year-of-era month day-of-month)))
  (date-year-day
    ([this proleptic-year day-of-year] (-date-year-day this proleptic-year day-of-year))
    ([this era year-of-era day-of-year] (-date-year-day this era year-of-era day-of-year)))
  (date-epoch-day [this epoch-day] (-date-epoch-day this epoch-day))
  (epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] (-epoch-second this proleptic-year month day-of-month hour minute second zone-offset))
  (local-date-time [this temporal] (-local-date-time this temporal))
  (zoned-date-time
    ([this temporal] (-zoned-date-time this temporal))
    ([this instant zone] (-zoned-date-time this instant zone)))
  (date-now
    ([this] (-date-now this))
    ([this date-now--overloaded-param] (-date-now this date-now--overloaded-param)))
  (is-leap-year [this proleptic-year] (-is-leap-year this proleptic-year))
  (proleptic-year [this era year-of-era] (-proleptic-year this era year-of-era))
  (era-of [this era-value] (-era-of this era-value))
  (eras [this] (-eras this))
  (resolve-date [this field-values resolver-style] (-resolve-date this field-values resolver-style))
  (range [this field] (-range this field))
  (period [this years months days] (-period this years months days)))

(def INSTANCE (create))
