(ns jiffy.offset-date-time
  (:refer-clojure :exclude [format range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeParseException DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-time :as local-date-time-impl]
            [jiffy.offset-date-time-impl :refer [#?@(:cljs [OffsetDateTime])] :as impl]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.protocols.offset-date-time :as offset-date-time]
            [jiffy.protocols.offset-time :as offset-time]
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
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.math :as math]
            [jiffy.zoned-date-time-impl :as zoned-date-time-impl]
            [jiffy.offset-time-impl :as offset-time-impl]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.temporal.chrono-unit :as chrono-unit]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.clock :as clock-impl]
            [jiffy.asserts :as asserts]
            [jiffy.protocols.string :as string])
  #?(:clj (:import [jiffy.offset_date_time_impl OffsetDateTime])))

(s/def ::offset-date-time ::impl/offset-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L602
(def-method get-offset ::zone-offset/zone-offset
  [this ::offset-date-time]
  (:offset this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L636
(def-method to-epoch-second ::j/long
  [{:keys [date-time offset]} ::offset-date-time]
  (chrono-local-date-time/to-epoch-second date-time offset))

(defn --with [this date-time offset]
  (if (and (= (:date-time this) date-time)
           (= (:offset this) offset))
    this
    (impl/of date-time offset)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L673
(def-method with-offset-same-local ::offset-date-time
  [this ::offset-date-time
   offset ::zone-offset/zone-offset]
  (--with this (:date-time this) offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L696
(def-method with-offset-same-instant ::offset-date-time
  [this ::offset-date-time
   offset ::zone-offset/zone-offset]
  (if (= (:offset this) offset)
    this
    (impl/of (local-date-time/plus-seconds
              (:date-time this)
              (math/subtract-exact (zone-offset/get-total-seconds offset)
                                   (zone-offset/get-total-seconds (:offset this))))
             offset)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L714
(def-method to-local-date-time ::local-date-time/local-date-time
  [this ::offset-date-time]
  (:date-time this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L727
(def-method to-local-date ::local-date/local-date
  [this ::offset-date-time]
  (-> this :date-time chrono-local-date-time/to-local-date))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L741
(def-method get-year ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L755
(def-method get-month-value ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-month-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L770
(def-method get-month ::month/month
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L781
(def-method get-day-of-month ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-day-of-month))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L792
(def-method get-day-of-year ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-day-of-year))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L809
(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-day-of-week))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L822
(def-method to-local-time ::local-time/local-time
  [this ::offset-date-time]
  (-> this :date-time chrono-local-date-time/to-local-time))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L831
(def-method get-hour ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-hour))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L840
(def-method get-minute ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-minute))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L849
(def-method get-second ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-second))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L858
(def-method get-nano ::j/int
  [this ::offset-date-time]
  (-> this :date-time local-date-time/get-nano))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L994
(def-method with-year ::offset-date-time
  [this ::offset-date-time
   year ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-year year))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1010
(def-method with-month ::offset-date-time
  [this ::offset-date-time
   month ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-month month))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1027
(def-method with-day-of-month ::offset-date-time
  [this ::offset-date-time
   day-of-month ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-day-of-month day-of-month))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1044
(def-method with-day-of-year ::offset-date-time
  [this ::offset-date-time
   day-of-year ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-day-of-year day-of-year))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1060
(def-method with-hour ::offset-date-time
  [this ::offset-date-time
   hour ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-hour hour))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1075
(def-method with-minute ::offset-date-time
  [this ::offset-date-time
   minute ::j/int]
  (--with this
          (local-date-time/with-minute (:date-time this) minute)
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1090
(def-method with-second ::offset-date-time
  [this ::offset-date-time
   second ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-second second))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1105
(def-method with-nano ::offset-date-time
  [this ::offset-date-time
   nano-of-second ::j/int]
  (--with this
          (-> this :date-time (local-date-time/with-nano nano-of-second))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1132
(def-method truncated-to ::offset-date-time
  [this ::offset-date-time
   unit ::temporal-unit/temporal-unit]
  (--with this
          (-> this :date-time (local-date-time/truncated-to unit))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1216
(def-method plus-years ::offset-date-time
  [this ::offset-date-time
   years ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-years years))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1240
(def-method plus-months ::offset-date-time
  [this ::offset-date-time
   months ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-months months))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1259
(def-method plus-weeks ::offset-date-time
  [this ::offset-date-time
   weeks ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-weeks weeks))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1278
(def-method plus-days ::offset-date-time
  [this ::offset-date-time
   days ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-days days))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1291
(def-method plus-hours ::offset-date-time
  [this ::offset-date-time
   hours ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-hours hours))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1304
(def-method plus-minutes ::offset-date-time
  [this ::offset-date-time
   minutes ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-minutes minutes))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1317
(def-method plus-seconds ::offset-date-time
  [this ::offset-date-time
   seconds ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-seconds seconds))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1330
(def-method plus-nanos ::offset-date-time
  [this ::offset-date-time
   nanos ::j/long]
  (--with this
          (-> this :date-time (local-date-time/plus-nanos nanos))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1405
(def-method minus-years ::offset-date-time
  [this ::offset-date-time
   years ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-years years))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1429
(def-method minus-months ::offset-date-time
  [this ::offset-date-time
   months ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-months months))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1448
(def-method minus-weeks ::offset-date-time
  [this ::offset-date-time
   weeks ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-weeks weeks))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1467
(def-method minus-days ::offset-date-time
  [this ::offset-date-time
   days ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-days days))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1480
(def-method minus-hours ::offset-date-time
  [this ::offset-date-time
   hours ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-hours hours))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1493
(def-method minus-minutes ::offset-date-time
  [this ::offset-date-time
   minutes ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-minutes minutes))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1506
(def-method minus-seconds ::offset-date-time
  [this ::offset-date-time
   seconds ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-seconds seconds))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1519
(def-method minus-nanos ::offset-date-time
  [this ::offset-date-time
   nanos ::j/long]
  (--with this
          (-> this :date-time (local-date-time/minus-nanos nanos))
          (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1672
(def-method format string?
  [this ::offset-date-time
   formatter ::date-time-formatter/date-time-formatter]
  (wip ::format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1693
(def-method at-zone-same-instant ::zoned-date-time/zoned-date-time
  [this ::offset-date-time
   zone ::zone-id/zone-id]
  (zoned-date-time-impl/of-instant (:date-time this) (:offset this) zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1721
(def-method at-zone-similar-local ::zoned-date-time/zoned-date-time
  [this ::offset-date-time
   zone ::zone-id/zone-id]
  (zoned-date-time-impl/of-local (:date-time this) zone (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1733
(def-method to-offset-time ::offset-time/offset-time
  [this ::offset-date-time]
  (offset-time-impl/of (-> this :date-time chrono-local-date-time/to-local-time)
                       (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1748
(def-method to-zoned-date-time ::zoned-date-time/zoned-date-time
  [this ::offset-date-time]
  (zoned-date-time-impl/of (:date-time this) (:offset this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1760
(def-method to-instant ::instant/instant
  [this ::offset-date-time]
  (-> this :date-time (chrono-local-date-time/to-instant (:offset this))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1821
(def-method is-after ::j/boolean
  [this ::offset-date-time
   other ::offset-date-time]
  (let [this-epoch-sec (to-epoch-second this)
        other-epoch-sec (to-epoch-second other)]
    (or (> this-epoch-sec other-epoch-sec)
        (and (= this-epoch-sec other-epoch-sec)
             (> (-> this to-local-time local-time/get-nano)
                (-> other to-local-time local-time/get-nano))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1838
(def-method is-before ::j/boolean
  [this ::offset-date-time
   other ::offset-date-time]
  (let [this-epoch-sec (to-epoch-second this)
        other-epoch-sec (to-epoch-second other)]
    (or (< this-epoch-sec other-epoch-sec)
        (and (= this-epoch-sec other-epoch-sec)
             (< (-> this to-local-time local-time/get-nano)
                (-> other to-local-time local-time/get-nano))))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1855
(def-method is-equal ::j/boolean
  [this ::offset-date-time
   other ::offset-date-time]
  (and (= (to-epoch-second this)
          (to-epoch-second other))
       (= (-> this to-local-time local-time/get-nano)
          (-> other to-local-time local-time/get-nano))))

(extend-type OffsetDateTime
  offset-date-time/IOffsetDateTime
  (get-offset [this] (get-offset this))
  (to-epoch-second [this] (to-epoch-second this))
  (with-offset-same-local [this offset] (with-offset-same-local this offset))
  (with-offset-same-instant [this offset] (with-offset-same-instant this offset))
  (to-local-date-time [this] (to-local-date-time this))
  (to-local-date [this] (to-local-date this))
  (get-year [this] (get-year this))
  (get-month-value [this] (get-month-value this))
  (get-month [this] (get-month this))
  (get-day-of-month [this] (get-day-of-month this))
  (get-day-of-year [this] (get-day-of-year this))
  (get-day-of-week [this] (get-day-of-week this))
  (to-local-time [this] (to-local-time this))
  (get-hour [this] (get-hour this))
  (get-minute [this] (get-minute this))
  (get-second [this] (get-second this))
  (get-nano [this] (get-nano this))
  (with-year [this year] (with-year this year))
  (with-month [this month] (with-month this month))
  (with-day-of-month [this day-of-month] (with-day-of-month this day-of-month))
  (with-day-of-year [this day-of-year] (with-day-of-year this day-of-year))
  (with-hour [this hour] (with-hour this hour))
  (with-minute [this minute] (with-minute this minute))
  (with-second [this second] (with-second this second))
  (with-nano [this nano-of-second] (with-nano this nano-of-second))
  (truncated-to [this unit] (truncated-to this unit))
  (plus-years [this years] (plus-years this years))
  (plus-months [this months] (plus-months this months))
  (plus-weeks [this weeks] (plus-weeks this weeks))
  (plus-days [this days] (plus-days this days))
  (plus-hours [this hours] (plus-hours this hours))
  (plus-minutes [this minutes] (plus-minutes this minutes))
  (plus-seconds [this seconds] (plus-seconds this seconds))
  (plus-nanos [this nanos] (plus-nanos this nanos))
  (minus-years [this years] (minus-years this years))
  (minus-months [this months] (minus-months this months))
  (minus-weeks [this weeks] (minus-weeks this weeks))
  (minus-days [this days] (minus-days this days))
  (minus-hours [this hours] (minus-hours this hours))
  (minus-minutes [this minutes] (minus-minutes this minutes))
  (minus-seconds [this seconds] (minus-seconds this seconds))
  (minus-nanos [this nanos] (minus-nanos this nanos))
  (format [this formatter] (format this formatter))
  (at-zone-same-instant [this zone] (at-zone-same-instant this zone))
  (at-zone-similar-local [this zone] (at-zone-similar-local this zone))
  (to-offset-time [this] (to-offset-time this))
  (to-zoned-date-time [this] (to-zoned-date-time this))
  (to-instant [this] (to-instant this))
  (is-after [this other] (is-after this other))
  (is-before [this other] (is-before this other))
  (is-equal [this other] (is-equal this other)))

(defn --compare-instant [this other]
  (if (= (get-offset this) (get-offset other))
    (time-comparable/compare-to (to-local-date-time this)
                                (to-local-date-time other))
    (let [cmp (math/compare (to-epoch-second this)
                            (to-epoch-second other))]
      (if (zero? cmp)
        (math/subtract-exact (-> this
                                 to-local-time
                                 local-time/get-nano)
                             (-> other
                                 to-local-time
                                 local-time/get-nano))
        cmp))))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1802
(def-method compare-to ::j/int
  [this ::offset-date-time
   other ::offset-date-time]
  (let [cmp (--compare-instant this other)]
    (if (zero? cmp)
      (time-comparable/compare-to (to-local-date-time this)
                                  (to-local-date-time other))
      cmp)))

(extend-type OffsetDateTime
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(declare of-instant)
(def-method with ::offset-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L908
  ([{:keys [date-time offset] :as this} ::offset-date-time
    adjuster ::temporal-adjuster/temporal-adjuster]
   (cond
     (or (satisfies? local-date/ILocalDate adjuster)
         (satisfies? local-time/ILocalTime adjuster)
         (satisfies? local-date-time/ILocalDateTime adjuster))
     (--with this (temporal/with date-time adjuster) offset)

     (satisfies? instant/IInstant adjuster)
     (of-instant adjuster offset)

     (satisfies? zone-offset/IZoneOffset adjuster)
     (--with this date-time adjuster)

     (satisfies? offset-date-time/IOffsetDateTime adjuster)
     adjuster

     :else
     (temporal-adjuster/adjust-into adjuster this)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L423
  ([{:keys [offset date-time] :as this} ::offset-date-time
    field ::temporal-field/temporal-field
    new-value ::j/long]
   (if-not (chrono-field/chrono-field? field)
     (temporal-field/adjust-into field this new-value)
     (condp = field
       chrono-field/INSTANT_SECONDS
       (of-instant (instant-impl/of-epoch-second new-value (get-nano this))
                   offset)

       chrono-field/OFFSET_SECONDS
       (--with this
               date-time
               (zone-offset-impl/of-total-seconds (chrono-field/check-valid-int-value field new-value)))

       (--with this
               (temporal/with date-time field new-value)
               offset)))))

(def-method plus ::offset-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1158
  ([this ::offset-date-time
    amount-to-add ::temporal-amount/temporal-amount]
   (temporal-amount/add-to amount-to-add this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1188
  ([this ::offset-date-time
    amount-to-add ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (chrono-unit/chrono-unit? unit)
     (--with this
             (temporal/plus (:date-time this) amount-to-add unit)
             (:offset this))
     (temporal-unit/add-to unit this amount-to-add))))

(def-method minus ::offset-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1356
  ([this ::offset-date-time
    amount-to-subtract ::temporal-amount/temporal-amount]
   (temporal-amount/subtract-from amount-to-subtract this))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1380
  ([this ::offset-date-time
    amount-to-subtract ::j/long
    unit ::temporal-unit/temporal-unit]
   (if (= amount-to-subtract math/long-max-value)
     (-> this
         (plus math/long-max-value unit)
         (plus 1 unit))
     (plus this (- amount-to-subtract) unit))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1654
(declare from)
(def-method until ::j/long
  [this ::offset-date-time
   end-exclusive ::temporal/temporal
   unit ::temporal-unit/temporal-unit]
  (let [end (from end-exclusive)]
    (if-not (chrono-unit/chrono-unit? unit)
      (temporal-unit/between unit this end)
      (temporal/until (:date-time this)
                      (-> this
                          (with-offset-same-instant (:offset this))
                          :date-time)
                      unit))))

(extend-type OffsetDateTime
  temporal/ITemporal
  (with
    ([this adjuster] (with this adjuster))
    ([this date-time offset] (with this date-time offset)))
  (plus
    ([this amount-to-add] (plus this amount-to-add))
    ([this amount-to-add unit] (plus this amount-to-add unit)))
  (minus
    ([this amount-to-subtract] (minus this amount-to-subtract))
    ([this amount-to-subtract unit] (minus this amount-to-subtract unit)))
  (until [this end-exclusive unit] (until this end-exclusive unit)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L484
(def-method is-supported ::j/boolean
  [this ::offset-date-time
   field-or-unit (s/or :field ::temporal-field/temporal-field
                       :unit ::temporal-unit/temporal-unit)]
  (condp satisfies? field-or-unit
    temporal-field/ITemporalField
    (or (chrono-field/chrono-field? field-or-unit)
        (and field-or-unit (temporal-field/is-supported-by field-or-unit this)))

    temporal-unit/ITemporalUnit
    (if (chrono-unit/chrono-unit? field-or-unit)
      (not= field-or-unit chrono-unit/FOREVER)
      (and field-or-unit (temporal-unit/is-supported-by field-or-unit this)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L557
(def-method range ::value-range/value-range
  [this ::offset-date-time
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (if (or (= field chrono-field/INSTANT_SECONDS)
            (= field chrono-field/OFFSET_SECONDS))
      (temporal-field/range field)
      (temporal-accessor/range (:date-time this) field))
    (temporal-field/range-refined-by field this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L596
(def-method get ::j/int
  [this ::offset-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-accessor-defaults/-get this field)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (throw (ex UnsupportedTemporalTypeException (str "Invalid field 'InstantSeconds' for get() method, use getLong() instead")))

      (= chrono-field/OFFSET_SECONDS field)
      (-> this get-offset zone-offset/get-total-seconds)

      :else
      (temporal-accessor/get (:date-time this) field))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L633
(def-method get-long ::j/long
  [this ::offset-date-time
   field ::temporal-field/temporal-field]
  (if-not (chrono-field/chrono-field? field)
    (temporal-field/get-from field this)
    (cond
      (= chrono-field/INSTANT_SECONDS field)
      (to-epoch-second this)

      (= chrono-field/OFFSET_SECONDS field)
      (-> this get-offset zone-offset/get-total-seconds)

      :else
      (temporal-accessor/get-long (:date-time this) field))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1544
(def-method query ::temporal-query/result
  [this ::offset-date-time
   query ::temporal-query/temporal-query]
  (cond
    (#{(temporal-queries/zone)
       (temporal-queries/offset)} query)
    (get-offset this)

    (= query (temporal-queries/zone-id))
    nil

    (= query (temporal-queries/local-date))
    (to-local-date this)

    (= query (temporal-queries/local-time))
    (to-local-time this)

    (= query (temporal-queries/chronology))
    iso-chronology/INSTANCE

    (= query (temporal-queries/precision))
    chrono-unit/NANOS

    :else
    (temporal-query/query-from query this)))

(extend-type OffsetDateTime
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L1590
(def-method adjust-into ::temporal/temporal
  [this ::offset-date-time
   temporal ::temporal/temporal]
  (-> temporal
      (temporal/with chrono-field/EPOCH_DAY (-> this to-local-date chrono-local-date/to-epoch-day))
      (temporal/with chrono-field/NANO_OF_DAY (-> this to-local-time local-time/to-nano-of-day))
      (temporal/with chrono-field/OFFSET_SECONDS (-> this get-offset zone-offset/get-total-seconds))))

(extend-type OffsetDateTime
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L161
(defn time-line-order [] (wip ::time-line-order))
(s/fdef time-line-order :ret ::j/wip)

(def-constructor now ::offset-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L211
  ([]
   (now (clock-impl/system-default-zone)))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L228
  ([clock-or-zone-id (s/or :clock ::clock/clock
                           :zone-id ::zone-id/zone-id)]
   (condp satisfies? clock-or-zone-id
     zone-id/IZoneId (now (clock-impl/system clock-or-zone-id))
     clock/IClock
     (do
       (asserts/require-non-nil clock-or-zone-id "clock")
       (let [now (clock/instant clock-or-zone-id)]
         (of-instant now
                     (-> clock-or-zone-id clock/get-zone zone-id/get-rules (zone-rules/get-offset now))))))))

(def-constructor of ::offset-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L275
  ([date-time ::local-date-time/local-date-time
    offset ::zone-offset/zone-offset]
   (impl/of date-time offset))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L261
  ([date ::local-date/local-date
    time ::local-time/local-time
    offset ::zone-offset/zone-offset]
   (impl/of date time offset))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L303
  ([year ::j/year
    month ::j/month
    day-of-month ::j/day
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    offset ::zone-offset/zone-offset]
   (impl/of year month day-of-month hour minute second nano-of-second offset)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L323
(def-constructor of-instant ::offset-date-time
  [instant ::instant/instant
   zone ::zone-id/zone-id]
  (let [rules (zone-id/get-rules zone)
        offset (zone-rules/get-offset rules instant)
        ldt (local-date-time-impl/of-epoch-second (instant/get-epoch-second instant)
                                                  (instant/get-nano instant)
                                                  offset)]
    (impl/->OffsetDateTime ldt offset)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L354
(def-constructor from ::offset-date-time
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? offset-date-time/IOffsetDateTime temporal)
    temporal
    (try*
     (let [offset (zone-offset-impl/from temporal)
           date (temporal-accessor/query temporal (temporal-queries/local-date))
           time (temporal-accessor/query temporal (temporal-queries/local-time))]
       (if (and date time)
         (impl/of date time offset)
         (of-instant (instant-impl/from temporal) offset)))
     (catch :default e
       (throw (ex DateTimeException (str "Unable to obtain OffsetDateTime from TemporalAccessor: "
                                         temporal " of type "
                                         (type temporal)
                                         e)))))))

(s/def ::string string?)

(def PATTERN
  (delay (re-pattern (str "(" @local-date-time-impl/PATTERN ")"
                          (str #"(([+-][\d:]*)|(Z))")))))

(def-constructor parse ::offset-date-time
  ([text ::string]
   (if-let [[date-time _ _ _ _ _ _ _ _ _ _ _ offset zulu-offset]
            (some->> (re-matches @PATTERN text)
                     rest)]
     (of (local-date-time-impl/parse date-time)
         (zone-offset-impl/of (or offset zulu-offset)))
     (throw (ex DateTimeParseException (str "Failed to parse OffsetDateTime: '" text "'")))))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetDateTime.java#L400
  ;; ([text ::j/char-sequence
  ;;   formatter ::date-time-formatter/date-time-formatter]
  ;;  (wip ::parse))
  )

(def MIN (local-date-time-impl/at-offset local-date-time-impl/MIN zone-offset-impl/MAX))
(def MAX (local-date-time-impl/at-offset local-date-time-impl/MAX zone-offset-impl/MIN))

(def-method to-string string?
  [{:keys [date-time offset]} ::offset-date-time]
  (str (string/to-string date-time)
       (string/to-string offset)))

(extend-type OffsetDateTime
  string/IString
  (to-string [this] (to-string this)))
