(ns jiffy.zone.zone-offset-transition-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]))

(def-record ZoneOffsetTransition ::zone-offset-transition
  [epoch-second ::j/pos-int
   transition ::local-date-time/local-date-time
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset])

(def-constructor create ::zone-offset-transition
  [transition ::local-date-time/local-date-time
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (->ZoneOffsetTransition
   (chrono-local-date-time/to-epoch-second transition offset-before)
   transition
   offset-before
   offset-after))

(defn of [transition offset-before offset-after]
  (create transition offset-before offset-after))
