(ns jiffy.offset-date-time-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.zone-offset-impl :as zone-offset]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.local-date :as local-date]))

(def-record OffsetDateTime ::offset-date-time
  [date-time ::local-date-time/local-date-time
   offset ::zone-offset/zone-offset])

(def-constructor of ::offset-date-time
  ([date-time ::local-date-time/local-date-time
    offset ::zone-offset/zone-offset]
   (->OffsetDateTime date-time offset))

  ([date ::local-date/local-date
    time ::local-time/local-time
    offset ::zone-offset/zone-offset]
   (->OffsetDateTime (local-date-time-impl/of date time)
                     offset))

  ([year ::j/year
    month ::j/month
    day-of-month ::j/day
    hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    offset ::zone-offset/zone-offset]
   (->OffsetDateTime (local-date-time-impl/of year month day-of-month hour minute second nano-of-second)
                     offset)))
