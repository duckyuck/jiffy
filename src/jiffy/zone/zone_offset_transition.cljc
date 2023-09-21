(ns jiffy.zone.zone-offset-transition
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.duration :as duration]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.specs :as j]
            [jiffy.zone.zone-offset-transition-impl :refer [#?@(:cljs [ZoneOffsetTransition])] :as impl])
  #?(:clj (:import [jiffy.zone.zone_offset_transition_impl ZoneOffsetTransition])))

(s/def ::zone-offset-transition ::impl/zone-offset-transition)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L253
(def-method get-instant ::instant/instant
  [this ::zone-offset-transition]
  (wip ::-get-instant))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L262
(def-method to-epoch-second ::j/long
  [this ::zone-offset-transition]
  (:epoch-second this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L279
(def-method get-date-time-before ::local-date-time/local-date-time
  [this ::zone-offset-transition]
  (wip ::-get-date-time-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L293
(def-method get-date-time-after ::local-date-time/local-date-time
  [this ::zone-offset-transition]
  (wip ::-get-date-time-after))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L304
(def-method get-offset-before ::zone-offset/zone-offset
  [this ::zone-offset-transition]
  (:offset-before this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L315
(def-method get-offset-after ::zone-offset/zone-offset
  [this ::zone-offset-transition]
  (:offset-after this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L328
(def-method get-duration ::duration/duration
  [this ::zone-offset-transition]
  (wip ::-get-duration))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L350
(def-method is-gap ::j/boolean
  [this ::zone-offset-transition]
  (wip ::-is-gap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L363
(def-method is-overlap ::j/boolean
  [this ::zone-offset-transition]
  (wip ::-is-overlap))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L377
(def-method is-valid-offset ::j/boolean
  [this ::zone-offset-transition
   offset ::zone-offset/zone-offset]
  (wip ::-is-valid-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L388
(def-method get-valid-offsets ::j/wip
  [this ::zone-offset-transition]
  (wip ::-get-valid-offsets))

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

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L406
(def-method compare-to ::j/int
  [this ::zone-offset-transition
   other ::zone-offset-transition]
  (wip ::-compare-to))

(extend-type ZoneOffsetTransition
  time-comparable/ITimeComparable
  (compare-to [this other] (compare-to this other)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransition.java#L138
(def-method of ::zone-offset-transition
  [transition ::local-date-time/local-date-time
   offset-before  ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (impl/of transition offset-before offset-after))
