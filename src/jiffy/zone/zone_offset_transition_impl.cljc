(ns jiffy.zone.zone-offset-transition-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.asserts :as asserts]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.local-date-time-impl :as local-date-time-impl]))

(def-record ZoneOffsetTransition ::zone-offset-transition
  [transition ::local-date-time/local-date-time
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset])

(defn create [epoch-sec offset-before offset-after]
  (->ZoneOffsetTransition
   (local-date-time-impl/of-epoch-second epoch-sec 0 offset-before)
   offset-before
   offset-after))

(def-constructor of ::zone-offset-transition
  [transition ::local-date-time/local-date-time
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (asserts/require-non-nil transition "transition")
  (asserts/require-non-nil offset-before "offset-before")
  (asserts/require-non-nil offset-after "offset-after")

  (when (= offset-before offset-after)
    (throw (ex JavaIllegalArgumentException "Offsets must not be equal")))

  (when-not (zero? (local-date-time/get-nano transition))
    (throw (ex JavaIllegalArgumentException "Nano-of-second must be zero")))

  (->ZoneOffsetTransition
   transition
   offset-before
   offset-after))
