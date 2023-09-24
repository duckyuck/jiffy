(ns jiffy.zone.zone-offset-transition-rule
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.exception :refer [JavaIllegalArgumentException ex #?(:clj try*)]  #?@(:cljs [:refer-macros [try*]])]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.day-of-week :as day-of-week]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.month :as month]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.local-date-impl :as local-date-impl]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.local-date :as local-date]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.protocols.zone.zone-offset-transition :as zone-offset-transition]
            [jiffy.protocols.zone.zone-offset-transition-rule :as zone-offset-transition-rule]
            [jiffy.specs :as j]
            [jiffy.chrono.iso-chronology :as iso-chronology]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.local-date-time-impl :as local-date-time-impl]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-adjusters :as temporal-adjusters]
            [jiffy.math :as math]
            [jiffy.zone-offset :as zone-offset-impl]
            [jiffy.protocols.local-date-time :as local-date-time]
            [jiffy.zone.zone-offset-transition-impl :as zone-offset-transition-impl]
            [jiffy.asserts :as asserts]
            [clojure.test.check.generators :as gen]))

(def-record TimeDefinition ::time-definition
  [ordinal ::j/long
   enum-name string?])

(def-constructor create-time-definition ::time-definition
  [ordinal ::j/long
   enum-name string?]
  (->TimeDefinition ordinal enum-name))

(defenum create-time-definition
  [UTC []
   STANDARD []
   WALL []])

(def-constructor value-of ::time-definition
  [enum-name string?]
  (or (@enums enum-name)
      (throw (ex JavaIllegalArgumentException
                 (str "no enum constant " (symbol (str *ns*) (str enum-name)))))))

(defn create-date-time [time-definition local-date-time standard-offset wall-offset]
  (condp = time-definition
    UTC (->> (math/subtract-exact (:total-seconds wall-offset)
                                  (:total-seconds zone-offset-impl/UTC))
             (local-date-time/plus-seconds local-date-time))
    STANDARD (->> (math/subtract-exact (:total-seconds wall-offset)
                                       (:total-seconds standard-offset))
                  (local-date-time/plus-seconds local-date-time))
    WALL local-date-time))

(def-record ZoneOffsetTransitionRule ::zone-offset-transition-rule
  [time-definition ::time-definition
   midnight-end-of-day ::j/boolean
   local-time (s/and ::local-time/local-time (comp zero? :nano))
   month ::month/month
   day-of-month-indicator (s/and (s/int-in -28 31) (complement zero?))
   day-of-week ::day-of-week/day-of-week
   standard-offset ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset
   offset-before ::zone-offset/zone-offset])

(def-constructor create ::zone-offset-transition-rule
  [month ::month/month
   day-of-month-indicator ::j/int
   day-of-week ::day-of-week/day-of-week
   local-time ::local-time/local-time
   time-end-of-day ::j/boolean
   time-definition ::time-definition
   standard-offset ::zone-offset/zone-offset
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (->ZoneOffsetTransitionRule time-definition
                              time-end-of-day
                              local-time
                              month
                              day-of-month-indicator
                              day-of-week
                              standard-offset
                              offset-after
                              offset-before))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L374
(def-method get-month ::month/month
  [this ::zone-offset-transition-rule]
  (:month this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L396
(def-method get-day-of-month-indicator ::j/int
  [this ::zone-offset-transition-rule]
  (:day-of-month-indicator this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L412
(def-method get-day-of-week ::day-of-week/day-of-week
  [this ::zone-offset-transition-rule]
  (:day-of-week this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L424
(def-method get-local-time ::local-time/local-time
  [this ::zone-offset-transition-rule]
  (:local-time this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L435
(def-method is-midnight-end-of-day ::j/boolean
  [this ::zone-offset-transition-rule]
  (:time-end-of-day this)
  (:midnight-end-of-day this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L447
(def-method get-time-definition ::time-definition
  [this ::zone-offset-transition-rule]
  (:time-definition this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L456
(def-method get-standard-offset ::zone-offset/zone-offset
  [this ::zone-offset-transition-rule]
  (:standard-offset this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L465
(def-method get-offset-before ::zone-offset/zone-offset
  [this ::zone-offset-transition-rule]
  (:offset-before this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L474
(def-method get-offset-after ::zone-offset/zone-offset
  [this ::zone-offset-transition-rule]
  (:offset-after this))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L487
(def-method create-transition ::zone-offset-transition/zone-offset-transition
  [{:keys [day-of-month-indicator month day-of-week midnight-end-of-day local-time time-definition standard-offset offset-before offset-after] :as this} ::zone-offset-transition-rule
   year ::j/int]
  (let [leap-year? (chronology/is-leap-year iso-chronology/INSTANCE year)
        date (if (neg? day-of-month-indicator)
               (let [date (local-date-impl/of year month (+ 1 (month/length month leap-year?) day-of-month-indicator))]
                 (if day-of-week
                   (temporal/with date (temporal-adjusters/previous-or-same day-of-week))
                   date))
               (let [date (local-date-impl/of year month day-of-month-indicator)]
                 (if day-of-week
                   (temporal/with date (temporal-adjusters/next-or-same day-of-week))
                   date)))
        date (if midnight-end-of-day
               (local-date/plus-days date 1)
               date)
        local-dt (local-date-time-impl/of date local-time)
        transition (create-date-time time-definition local-dt standard-offset offset-before)]
    (zone-offset-transition-impl/of transition offset-before offset-after)))

(extend-type ZoneOffsetTransitionRule
  zone-offset-transition-rule/IZoneOffsetTransitionRule
  (get-month [this] (get-month this))
  (get-day-of-month-indicator [this] (get-day-of-month-indicator this))
  (get-day-of-week [this] (get-day-of-week this))
  (get-local-time [this] (get-local-time this))
  (is-midnight-end-of-day [this] (is-midnight-end-of-day this))
  (get-time-definition [this] (get-time-definition this))
  (get-standard-offset [this] (get-standard-offset this))
  (get-offset-before [this] (get-offset-before this))
  (get-offset-after [this] (get-offset-after this))
  (create-transition [this year] (create-transition this year)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneOffsetTransitionRule.java#L172
(def-constructor of ::zone-offset-transition-rule
  [month ::month/month
   day-of-month-indicator ::j/int
   day-of-week ::day-of-week/day-of-week
   time ::local-time/local-time
   time-end-of-day ::j/boolean
   time-definition ::time-definition
   standard-offset ::zone-offset/zone-offset
   offset-before ::zone-offset/zone-offset
   offset-after ::zone-offset/zone-offset]
  (asserts/require-non-nil month "month")
  (asserts/require-non-nil time "time")
  (asserts/require-non-nil time-definition "time-definition")
  (asserts/require-non-nil standard-offset "standard-offset")
  (asserts/require-non-nil offset-before "offset-before")
  (asserts/require-non-nil offset-after "offset-after")

  (when (or (< day-of-month-indicator -28)
            (> day-of-month-indicator 31)
            (zero? day-of-month-indicator))
    (throw (ex JavaIllegalArgumentException "Day of month indicator must be between -28 and 31 inclusive excluding zero")))

  (when (and time-end-of-day (not (= time local-time-impl/MIDNIGHT)))
    (throw (ex JavaIllegalArgumentException "Time must be midnight when end of day flag is true")))

  (when-not (zero? (local-time/get-nano time))
    (throw (ex JavaIllegalArgumentException "Time's nano-of-second must be zero")))

  (->ZoneOffsetTransitionRule time-definition
                              time-end-of-day
                              time
                              month
                              day-of-month-indicator
                              day-of-week
                              standard-offset
                              offset-after
                              offset-before))
