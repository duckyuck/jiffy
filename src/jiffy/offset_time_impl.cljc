(ns jiffy.offset-time-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.local-time-impl :as local-time-impl]))

(def-record OffsetTime ::offset-time
  [time ::local-time/local-time
   offset ::zone-offset/zone-offset])

(def-constructor of ::offset-time
  ([time ::local-time/local-time
    offset ::zone-offset/zone-offset]
   (->OffsetTime time offset))

  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    offset ::zone-offset/zone-offset]
   (->OffsetTime (local-time-impl/of hour minute second nano-of-second)
                 offset)))
