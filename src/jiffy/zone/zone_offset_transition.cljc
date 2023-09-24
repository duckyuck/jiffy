(ns jiffy.zone.zone-offset-transition
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.specs :as j]
            [jiffy.zone.zone-offset-transition-impl :refer [#?@(:cljs [ZoneOffsetTransition])] :as impl]
            [jiffy.math :as math]
            [jiffy.instant-impl :as instant-impl]
            [jiffy.duration-impl :as duration-impl])
  #?(:clj (:import [jiffy.zone.zone_offset_transition_impl ZoneOffsetTransition])))

(s/def ::zone-offset-transition ::impl/zone-offset-transition)

(def-method get-instant ::instant/instant
  [this ::zone-offset-transition]
  (-> this :transition (chrono-local-date-time/to-instant (:offset-before this))))

(def-method to-epoch-second ::j/long
  [this ::zone-offset-transition]
  (-> this :transition (chrono-local-date-time/to-epoch-second (:offset-before this))))

(def-method get-date-time-before ::local-date-time/local-date-time
  [this ::zone-offset-transition]
  (:transition this))

(defn --get-duration-seconds [this]
  (math/subtract-exact
   (-> this :offset-after zone-offset/get-total-seconds)
   (-> this :offset-before zone-offset/get-total-seconds)))

(def-method get-date-time-after ::local-date-time/local-date-time
  [this ::zone-offset-transition]
  (-> this :transition (local-date-time/plus-seconds (--get-duration-seconds this))))

(def-method get-offset-before ::zone-offset/zone-offset
  [this ::zone-offset-transition]
  (:offset-before this))

(def-method get-offset-after ::zone-offset/zone-offset
  [this ::zone-offset-transition]
  (:offset-after this))

(def-method get-duration ::duration/duration
  [this ::zone-offset-transition]
  (-> this --get-duration-seconds duration-impl/of-seconds))

(def-method is-gap ::j/boolean
  [this ::zone-offset-transition]
  (> (-> this :offset-after zone-offset/get-total-seconds)
     (-> this :offset-before zone-offset/get-total-seconds)))

(def-method is-overlap ::j/boolean
  [this ::zone-offset-transition]
  (< (-> this :offset-after zone-offset/get-total-seconds)
     (-> this :offset-before zone-offset/get-total-seconds)))

(def-method is-valid-offset ::j/boolean
  [this ::zone-offset-transition
   offset ::zone-offset/zone-offset]
  (if (is-gap this)
    false
    (or (= (get-offset-before this) offset)
        (= (get-offset-after this) offset))))

(def-method get-valid-offsets ::j/wip
  [this ::zone-offset-transition]
  (if (is-gap this)
    []
    [(get-offset-before this) (get-offset-after this)]))

(extend-type ZoneOffsetTransition
  zone-offset-transition/IZoneOffsetTransition
  (get-instant [this] (get-instant this))
  (to-epoch-second [this] (to-epoch-second this))
  (get-date-time-before [this] (get-date-time-before this))
  (get-date-time-after [this] (get-date-time-after this))
  (get-offset-before [this] (get-offset-before this))
  (get-offset-after [this] (get-offset-after this))
  (get-duration [this] (get-duration this))
  (is-gap [this] (is-gap this))
  (is-overlap [this] (is-overlap this))
  (is-valid-offset [this offset] (is-valid-offset this offset))
  (get-valid-offsets [this] (get-valid-offsets this)))

(def-method compare-to ::j/int
  [this ::zone-offset-transition
   other ::zone-offset-transition]
  (time-comparable/compare-to (get-instant this) (get-instant other)))

(extend-type ZoneOffsetTransition
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

(def-constructor of ::zone-offset-transition
  [transition ::local-date-time/local-date-time
   offset-before  ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (impl/of transition offset-before offset-after))
