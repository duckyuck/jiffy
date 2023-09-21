(ns jiffy.local-date-time-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.local-date-impl :as local-date-impl]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.math :as math]
            [jiffy.asserts :as asserts]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.month :as month]))

(def-record LocalDateTime ::local-date-time
  [date ::local-date/local-date
   time ::local-time/local-time])

(def-constructor create ::local-date-time
  [date ::local-date/local-date
   time ::local-time/local-time]
  (->LocalDateTime date time))

(def-constructor of-epoch-second ::local-date-time
  [epoch-second ::j/long
   nano-of-second ::j/int
   offset ::zone-offset/zone-offset]
  (asserts/require-non-nil offset "offset")
  (chrono-field/check-valid-value chrono-field/NANO_OF_SECOND nano-of-second)
  (let [local-second (math/add-exact epoch-second (zone-offset/get-total-seconds offset))
        local-epoch-day (math/floor-div local-second local-time-impl/SECONDS_PER_DAY)
        secs-of-day (math/floor-mod local-second local-time-impl/SECONDS_PER_DAY)]
    (create (local-date-impl/of-epoch-day local-epoch-day)
            (local-time-impl/of-nano-of-day (-> secs-of-day
                                                (math/multiply-exact local-time-impl/NANOS_PER_SECOND)
                                                (math/add-exact nano-of-second))))))

(def-constructor of ::local-date-time
  ([date ::local-date/local-date
    time ::local-time/local-time]
   (create date time))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour]
   (of (local-date-impl/of year month day-of-month)
            (local-time-impl/of hour minute)))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute]
   (of (local-date-impl/of year month day-of-month)
       (local-time-impl/of hour minute second)))

  ([year ::j/year
    month (s/or :number ::j/month-of-year
                :month ::month/month)
    day-of-month ::j/day-of-month
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second]
   (of (local-date-impl/of year month day-of-month)
       (local-time-impl/of hour minute second nano-of-second))))
