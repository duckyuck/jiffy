(ns jiffy.zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.asserts :as asserts]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.specs :as j]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.instant-impl :as instant-impl]))

(declare of-local)

(def-record ZonedDateTime ::zoned-date-time
  [date-time ::local-date-time/local-date-time
   offset ::zone-offset/zone-offset
   zone ::zone-id/zone-id]
  (of-local date-time zone offset))

(defn create [epoch-second nano-of-second zone]
  (let [rules (zone-id/get-rules zone)
        instant (instant-impl/of-epoch-second epoch-second nano-of-second)
        offset (zone-rules/get-offset rules instant)]
    (->ZonedDateTime (local-date-time-impl/of-epoch-second epoch-second nano-of-second offset)
                     offset
                     zone)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L366
(def-constructor of-local ::zoned-date-time
  [local-date-time ::local-date-time/local-date-time
   zone ::zone-id/zone-id
   preferred-offset ::zone-offset/zone-offset]
  (asserts/require-non-nil local-date-time "LocalDateTime")
  (asserts/require-non-nil zone "zone")
  (if (satisfies? zone-offset/IZoneOffset zone)
    (->ZonedDateTime local-date-time zone zone)
    (let [rules (zone-id/get-rules zone)
          valid-offsets (zone-rules/get-valid-offsets rules local-date-time)]
      (cond
        (= (count valid-offsets) 1)
        (->ZonedDateTime local-date-time (first valid-offsets) zone)

        (= (count valid-offsets) 0)
        (let [trans (zone-rules/get-transition rules local-date-time)]
          (->ZonedDateTime
           (local-date-time/plus-seconds
            local-date-time
            (-> trans zone-offset-transition/get-duration duration/get-seconds))
           (zone-offset-transition/get-offset-after trans)
           zone))

        :default (if (and (not (nil? preferred-offset))
                          ((set valid-offsets) preferred-offset))
                   (->ZonedDateTime local-date-time preferred-offset zone)
                   (do
                     (asserts/require-non-nil (first valid-offsets) "offset")
                     (->ZonedDateTime local-date-time (first valid-offsets) zone)))))))

(def-constructor of ::zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L292
  ([local-date-time ::local-date-time/local-date-time
    zone ::zone-id/zone-id]
   (of-local local-date-time zone nil))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L264
  ([date ::local-date/local-date
    time ::local-time/local-time
    zone ::zone-id/zone-id]
   (of (local-date-time-impl/of date time) zone))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L336
  ([year ::j/year
    month ::j/month
    day-of-month ::j/day
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    zone ::zone-id/zone-id]
   (of-local (local-date-time-impl/of year month day-of-month hour minute second nano-of-second) zone nil)))

(def-constructor of-instant ::zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L406
  ([instant ::instant/instant
    zone ::zone-id/zone-id]
   (asserts/require-non-nil instant "instant")
   (asserts/require-non-nil zone "zone")
   (create (instant/get-epoch-second instant) (instant/get-nano instant) zone))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZonedDateTime.java#L432
  ([local-date-time ::local-date-time/local-date-time
    offset ::zone-offset/zone-offset
    zone ::zone-id/zone-id]
   (asserts/require-non-nil local-date-time "LocalDateTime")
   (asserts/require-non-nil offset "offset")
   (asserts/require-non-nil zone "zone")
   (if (-> zone zone-id/get-rules (zone-rules/is-valid-offset local-date-time offset))
     (->ZonedDateTime local-date-time offset zone)
     (create (chrono-local-date-time/to-epoch-second local-date-time offset)
             (local-date-time/get-nano local-date-time)
             zone))))
