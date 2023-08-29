(ns jiffy.zone.zone-offset-transition-rule
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.month :as month]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-offset-transition-rule :as zone-offset-transition-rule]
            [jiffy.specs :as j]))

(s/def ::time-definition #{::UTC ::WALL ::STANDARD})

(def-record ZoneOffsetTransitionRule ::zone-offset-transition-rule
  [time-definition ::time-definition
   midnight-end-of-day ::j/boolean
   local-time ::local-time/local-time
   month ::month/month
   day-of-month-indicator ::j/int
   day-of-week ::day-of-week/day-of-week
   standard-offset ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset
   offset-before ::zone-offset/zone-offset])

(def-constructor create ::zone-offset-transition-rule
  [time-definition ::time-definition
   midnight-end-of-day ::j/boolean
   local-time ::local-time/local-time
   month ::month/month
   day-of-month-indicator ::j/int
   day-of-week ::day-of-week/day-of-week
   standard-offset ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset
   offset-before ::zone-offset/zone-offset]
  (->ZoneOffsetTransitionRule
   time-definition midnight-end-of-day local-time month day-of-month-indicator
   day-of-week standard-offset offset-after offset-before))

(defmacro args [& x] `(s/tuple ::zone-offset-transition-rule ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L374
(s/def ::get-month-args (args))
(defn -get-month [this] (wip ::-get-month))
(s/fdef -get-month :args ::get-month-args :ret ::month/month)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L396
(s/def ::get-day-of-month-indicator-args (args))
(defn -get-day-of-month-indicator [this] (wip ::-get-day-of-month-indicator))
(s/fdef -get-day-of-month-indicator :args ::get-day-of-month-indicator-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L412
(s/def ::get-day-of-week-args (args))
(defn -get-day-of-week [this] (wip ::-get-day-of-week))
(s/fdef -get-day-of-week :args ::get-day-of-week-args :ret ::day-of-week/day-of-week)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L424
(s/def ::get-local-time-args (args))
(defn -get-local-time [this] (wip ::-get-local-time))
(s/fdef -get-local-time :args ::get-local-time-args :ret ::local-time/local-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L435
(s/def ::is-midnight-end-of-day-args (args))
(defn -is-midnight-end-of-day [this] (wip ::-is-midnight-end-of-day))
(s/fdef -is-midnight-end-of-day :args ::is-midnight-end-of-day-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L447
(s/def ::get-time-definition-args (args))
(defn -get-time-definition [this] (wip ::-get-time-definition))
(s/fdef -get-time-definition :args ::get-time-definition-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L456
(s/def ::get-standard-offset-args (args))
(defn -get-standard-offset [this] (wip ::-get-standard-offset))
(s/fdef -get-standard-offset :args ::get-standard-offset-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L465
(s/def ::get-offset-before-args (args))
(defn -get-offset-before [this] (wip ::-get-offset-before))
(s/fdef -get-offset-before :args ::get-offset-before-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L474
(s/def ::get-offset-after-args (args))
(defn -get-offset-after [this] (wip ::-get-offset-after))
(s/fdef -get-offset-after :args ::get-offset-after-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L487
(s/def ::create-transition-args (args ::j/int))
(defn -create-transition [this year] (wip ::-create-transition))
(s/fdef -create-transition :args ::create-transition-args :ret ::zone-offset-transition/zone-offset-transition)

(extend-type ZoneOffsetTransitionRule
  zone-offset-transition-rule/IZoneOffsetTransitionRule
  (get-month [this] (-get-month this))
  (get-day-of-month-indicator [this] (-get-day-of-month-indicator this))
  (get-day-of-week [this] (-get-day-of-week this))
  (get-local-time [this] (-get-local-time this))
  (is-midnight-end-of-day [this] (-is-midnight-end-of-day this))
  (get-time-definition [this] (-get-time-definition this))
  (get-standard-offset [this] (-get-standard-offset this))
  (get-offset-before [this] (-get-offset-before this))
  (get-offset-after [this] (-get-offset-after this))
  (create-transition [this year] (-create-transition this year)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L172
(s/def ::of-args (args ::month/month ::j/int ::day-of-week/day-of-week ::local-time/local-time ::j/boolean ::j/wip ::zone-offset/zone-offset ::zone-offset/zone-offset ::zone-offset/zone-offset))
(defn of [month day-of-month-indicator day-of-week time time-end-of-day time-defnition standard-offset offset-before offset-after] (wip ::of))
(s/fdef of :args ::of-args :ret ::zone-offset-transition-rule)
