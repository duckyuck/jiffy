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
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L212
  ([time ::local-time/local-time
    offset ::zone-offset/zone-offset]
   (->OffsetTime time offset))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L235
  ([hour ::j/hour-of-day
    minute ::j/minute-of-hour
    second ::j/second-of-minute
    nano-of-second ::j/nano-of-second
    offset ::zone-offset/zone-offset]
   (->OffsetTime (local-time-impl/of hour minute second nano-of-second)
                 offset)))
