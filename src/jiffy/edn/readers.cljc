(ns jiffy.edn.readers
  (:refer-clojure :exclude [range time])
  (:require [clojure.string :as str]
            [jiffy.clock]
            [jiffy.day-of-week]
            [jiffy.duration]
            [jiffy.instant-impl]
            [jiffy.local-date]
            [jiffy.local-date-time]
            [jiffy.local-time]
            [jiffy.month]
            [jiffy.month-day]
            [jiffy.offset-date-time]
            [jiffy.offset-time]
            [jiffy.period]
            [jiffy.temporal.chrono-field]
            [jiffy.temporal.chrono-unit]
            [jiffy.temporal.temporal-queries]
            [jiffy.temporal.value-range]
            [jiffy.year]
            [jiffy.year-month]
            [jiffy.zone-id]
            [jiffy.zone-region]
            [jiffy.zone.zone-offset-transition-rule]
            [jiffy.zone.zone-offset-transition-impl]
            [jiffy.zone.zone-rules-impl]
            [jiffy.zoned-date-time]))

(defn read-enum [value-fn kw]
  (-> kw name str/upper-case (str/replace #"-" "_") value-fn))

(def read-chrono-unit (partial read-enum jiffy.temporal.chrono-unit/value-of))
(def read-day-of-week (partial read-enum jiffy.day-of-week/value-of))
(def read-chrono-field (partial read-enum jiffy.temporal.chrono-field/valueOf))
(def read-iso-era (partial read-enum jiffy.chrono.iso-era/value-of))
(def read-time-definition (partial read-enum jiffy.zone.zone-offset-transition-rule/value-of))

(defn read-value-range [args]
  (apply jiffy.temporal.value-range/->ValueRange args))

(defn read-instant [x] (jiffy.instant-impl/map->Instant x))

(defn instant [x]
  #?(:clj (read-instant x)
     :cljs `(read-instant x)))

(defn read-duration [x] (jiffy.duration/parse x))

(defn duration [x]
  #?(:clj (read-duration x)
     :cljs `(read-duration x)))

(defn read-query [x] (jiffy.temporal.temporal-queries/id->query x))

(defn query [x]
  #?(:clj (read-query x)
     :cljs `(read-query x)))

(defn read-field [x] (read-chrono-field x))

(defn field [x]
  #?(:clj (read-field x)
     :cljs `(read-field x)))

(defn read-unit [x] (read-chrono-unit x))

(defn unit [x]
  #?(:clj (read-unit x)
     :cljs `(read-unit x)))

(defn read-range [x] (read-value-range x))

(defn range [x]
  #?(:clj (read-range x)
     :cljs `(read-range x)))

(defn read-period [x] (jiffy.period/parse x))

(defn period [x]
  #?(:clj (read-period x)
     :cljs `(read-period x)))

(defn read-day [x] (read-day-of-week x))

(defn day [x]
  #?(:clj (read-day x)
     :cljs `(read-day x)))

(defn read-ldt [x] (jiffy.local-date-time/parse x))

(defn ldt [x]
  #?(:clj (read-ldt x)
     :cljs `(read-ldt x)))

(defn read-lt [x] (jiffy.local-time/parse x))

(defn lt [x]
  #?(:clj (read-lt x)
     :cljs `(read-lt x)))

(defn read-ld [x] (jiffy.local-date/parse x))

(defn ld [x]
  #?(:clj (read-ld x)
     :cljs `(read-ld x)))

(defn read-month [x] (read-enum jiffy.month/value-of x))

(defn month [x]
  #?(:clj (read-month x)
     :cljs `(read-month x)))

(defn read-month-day [x] (jiffy.month-day/parse x))

(defn month-day [x]
  #?(:clj (read-month-day x)
     :cljs `(read-month-day x)))

(defn read-year-month [x] (jiffy.year-month/parse x))

(defn year-month [x]
  #?(:clj (read-year-month x)
     :cljs `(read-year-month x)))

(defn read-year [x] (jiffy.year/parse x))

(defn year [x]
  #?(:clj (read-year x)
     :cljs `(read-year x)))

(defn read-zone [x] (jiffy.zone-id/of x))

(defn zone [x]
  #?(:clj (read-zone x)
     :cljs `(read-zone x)))

(defn read-fixed-clock [x] (jiffy.clock/map->FixedClock x))

(defn fixed-clock [x]
  #?(:clj (read-fixed-clock x)
     :cljs `(read-fixed-clock x)))

(defn read-zone-rules [x] (jiffy.zone.zone-rules-impl/map->ZoneRules x))

(defn zone-rules [x]
  #?(:clj (read-zone-rules x)
     :cljs `(read-zone-rules x)))

(defn read-zone-rule [x] (jiffy.zone.zone-offset-transition-rule/map->ZoneOffsetTransitionRule x))

(defn zone-rule [x]
  #?(:clj (read-zone-rule x)
     :cljs `(read-zone-rule x)))

(defn read-zdt [x] (jiffy.zoned-date-time/parse x))

(defn zdt [x]
  #?(:clj (read-zdt x)
     :cljs `(read-zdt x)))

(defn read-odt [x] (jiffy.offset-date-time/parse x))

(defn odt [x]
  #?(:clj (read-odt x)
     :cljs `(read-odt x)))

(defn read-ot [x] (jiffy.offset-time/parse x))

(defn ot [x]
  #?(:clj (read-ot x)
     :cljs `(read-ot x)))

(defn read-era [x] (read-iso-era x))

(defn era [x]
  #?(:clj (read-era x)
     :cljs `(read-era x)))

(defn read-time [x] (read-time-definition x))

(defn time [x]
  #?(:clj (read-time x)
     :cljs `(read-time x)))

(defn read-chronology [_x] jiffy.chrono.iso-chronology/INSTANCE)

(defn chronology [x]
  #?(:clj (read-chronology x)
     :cljs `(read-chronology x)))

(defn read-transition [x] (jiffy.zone.zone-offset-transition-impl/map->ZoneOffsetTransition x))

(defn transition [x]
  #?(:clj (read-transition x)
     :cljs `(read-transition x)))

(def data-readers
  {'jiffy/instant read-instant
   'jiffy/duration read-duration
   'jiffy/query read-query
   'jiffy/field read-field
   'jiffy/unit read-unit
   'jiffy/range read-range
   'jiffy/period read-period
   'jiffy/day read-day
   'jiffy/ldt read-ldt
   'jiffy/lt read-lt
   'jiffy/ld read-ld
   'jiffy/month read-month
   'jiffy/month-day read-month-day
   'jiffy/year-month read-year-month
   'jiffy/year read-year
   'jiffy/zone read-zone
   'jiffy/fixed-clock read-fixed-clock
   'jiffy/zone-rules read-zone-rules
   'jiffy/zone-rule read-zone-rule
   'jiffy/zdt read-zdt
   'jiffy/odt read-odt
   'jiffy/ot read-ot
   'jiffy/era read-era
   'jiffy/time read-time
   'jiffy/chronology read-chronology
   'jiffy/transition read-transition})
