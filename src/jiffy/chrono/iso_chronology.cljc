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
            [jiffy.specs :as j]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]))

(def-record IsoChronology ::iso-chronology [dummy ::dummy])

(def-constructor create ::iso-chronology [] (IsoChronology. :dummy))

(s/def ::field-values (s/map-of ::temporal-field/temporal-field ::j/long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L589
(def-method resolve-proleptic-month ::j/void
  [this ::iso-chronology
   field-values ::field-values
   resolver-style ::resolver-style/resolver-style]
  (wip ::-resolve-proleptic-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L601
(def-method resolve-year-of-era ::local-date/local-date
  [this ::iso-chronology
   field-values ::field-values
   resolver-style ::resolver-style/resolver-style]
  (wip ::-resolve-year-of-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L636
(def-method resolve-ymd ::chrono-local-date/chrono-local-date
  [this ::iso-chronology
   field-values ::field-values
   resolver-style ::resolver-style/resolver-style]
  (wip ::-resolve-ymd))

(extend-type IsoChronology
  abstract-chronology/IAbstractChronology
  (resolve-proleptic-month [this field-values resolver-style] (resolve-proleptic-month this field-values resolver-style))
  (resolve-year-of-era [this field-values resolver-style] (resolve-year-of-era this field-values resolver-style))
  (resolve-ymd [this field-values resolver-style] (resolve-ymd this field-values resolver-style)))

(def-method get-id string?
  [this ::iso-chronology]
  "ISO")

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L174
(def-method get-calendar-type string?
  [this ::iso-chronology]
  (wip ::-get-calendar-type))

(def-method date ::local-date/local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L267
  ([this ::iso-chronology
    temporal ::temporal-accessor/temporal-accessor]
   (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L209
  ([this ::iso-chronology
    proleptic-year ::j/int
    month ::j/int
    day-of-month ::j/int]
   (wip ::-date))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L192
  ([this ::iso-chronology
    era ::era/era
    year-of-era ::j/int
    month ::j/int
    day-of-month ::j/int]
   (wip ::-date)))

(def-method date-year-day  ::local-date/local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L238
  ([this ::iso-chronology
    proleptic-year ::j/int
    day-of-year ::j/int]
   (wip ::-date-year-day))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L223
  ([this ::iso-chronology
    era ::era/era
    year-of-era ::j/int
    day-of-year ::j/int]
   (wip ::-date-year-day)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L252
(def-method date-epoch-day ::chrono-local-date/chrono-local-date
  [this ::iso-chronology
  epoch-day ::j/long]
  (wip ::-date-epoch-day))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L291
(def-method epoch-second ::j/long
  [this ::iso-chronology
   proleptic-year ::j/int
   month ::j/int
   day-of-month ::j/int
   hour ::j/int
   minute ::j/int
   second ::j/int
   zone-offset ::zone-offset/zone-offset]
  (wip ::-epoch-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L369
(def-method local-date-time ::chrono-local-date-time/chrono-local-date-time
  [this ::iso-chronology
  temporal ::temporal-accessor/temporal-accessor]
  (wip ::-local-date-time))

(def-method zoned-date-time ::chrono-zoned-date-time/chrono-zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L383
  ([this ::iso-chronology
    temporal ::temporal-accessor/temporal-accessor]
   (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L398
  ([this ::iso-chronology
    instant ::instant/instant
    zone ::zone-id/zone-id]
   (wip ::-zoned-date-time)))

(def-method date-now ::local-date/local-date
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L416
  ([this ::iso-chronology]
   (wip ::-date-now))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L433
  ([this ::iso-chronology
    clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (wip ::-date-now)))

(def-method is-leap-year ::j/boolean
  [this ::iso-chronology
   proleptic-year ::j/long]
  (and (zero? (bit-and proleptic-year 3))
       (or (not (zero? (mod proleptic-year 100)))
           (zero? (mod proleptic-year 400)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L480
(def-method proleptic-year ::j/int
  [this ::iso-chronology
   era ::era/era
   year-of-era ::j/int]
  (wip ::-proleptic-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L488
(def-method era-of ::iso-era/iso-era
  [this ::iso-chronology
   era-value ::j/int]
  (wip ::-era-of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L493
(def-method eras (s/coll-of ::era/era)
  [this ::iso-chronology]
  (wip ::-eras))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L584
(def-method resolve-date ::local-date/local-date
  [this ::iso-chronology
   field-values ::j/wip
   resolver-style ::resolver-style/resolver-style]
  (wip ::-resolve-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L658
(def-method range ::value-range/value-range
  [this ::iso-chronology
  field ::chrono-field/chrono-field]
  (wip ::-range))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/IsoChronology.java#L676
(def-method period ::period/period
  [this ::iso-chronology
   years ::j/int
   months ::j/int
   days ::j/int]
  (wip ::-period))

(extend-type IsoChronology
  chronology/IChronology
  (get-id [this] (get-id this))
  (get-calendar-type [this] (get-calendar-type this))
  (date
    ([this temporal] (date this temporal))
    ([this proleptic-year month day-of-month] (date this proleptic-year month day-of-month))
    ([this era year-of-era month day-of-month] (date this era year-of-era month day-of-month)))
  (date-year-day
    ([this proleptic-year day-of-year] (date-year-day this proleptic-year day-of-year))
    ([this era year-of-era day-of-year] (date-year-day this era year-of-era day-of-year)))
  (date-epoch-day [this epoch-day] (date-epoch-day this epoch-day))
  (epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] (epoch-second this proleptic-year month day-of-month hour minute second zone-offset))
  (local-date-time [this temporal] (local-date-time this temporal))
  (zoned-date-time
    ([this temporal] (zoned-date-time this temporal))
    ([this instant zone] (zoned-date-time this instant zone)))
  (date-now
    ([this] (date-now this))
    ([this date-now--overloaded-param] (date-now this date-now--overloaded-param)))
  (is-leap-year [this proleptic-year] (is-leap-year this proleptic-year))
  (proleptic-year [this era year-of-era] (proleptic-year this era year-of-era))
  (era-of [this era-value] (era-of this era-value))
  (eras [this] (eras this))
  (resolve-date [this field-values resolver-style] (resolve-date this field-values resolver-style))
  (range [this field] (range this field))
  (period [this years months days] (period this years months days)))

(def INSTANCE (create))
