(ns jiffy.zone.zone-rules-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.protocols.zone.zone-offset-transition-rule :as transition-rule]
            [jiffy.protocols.zone.zone-offset-transition :as transition]
            [jiffy.asserts :as assert]))

(def-record ZoneRules ::zone-rules
  [standard-transitions (s/+ ::j/int)
   standard-offsets (s/+ ::zone-offset/zone-offset)
   savings-instant-transitions (s/* ::j/int)
   savings-local-transitions (s/* ::local-date-time/local-date-time)
   wall-offsets (s/* ::zone-offset/zone-offset)
   last-rules (s/* ::transition-rule/zone-offset-transition-rule)
   zone-id ::j/zone-id])

(def-constructor create ::zone-rules
  [standard-transitions (s/* ::j/int)
   standard-offsets (s/* ::zone-offset/zone-offset)
   savings-instant-transitions (s/* ::j/int)
   savings-local-transitions (s/* ::local-date-time/local-date-time)
   wall-offsets (s/* ::zone-offset/zone-offset)
   last-rules (s/* ::transition-rule/zone-offset-transition-rule)]
  (->ZoneRules standard-transitions
               standard-offsets
               savings-instant-transitions
               savings-local-transitions
               wall-offsets
               last-rules
               nil))

(def-constructor of ::zone-rules
  ([zone-offset ::zone-offset/zone-offset]
   (assert/require-non-nil zone-offset "zone-offset")
   (map->ZoneRules {:standard-offsets [zone-offset]
                    :wall-offsets [zone-offset]}))

  ;; TODO: maybe implement?
  ;; ([base-standard-offset
  ;;   base-wall-offset
  ;;   standard-offset-transition-list
  ;;   transition-list
  ;;   last-rules]
  ;;  (wip ::of))
  )
