(ns jiffy.zone.zone-rules
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java
(defprotocol IZoneRules
  (isFixedOffset [this])
  (getOffset [this get-offset--overloaded-param])
  (getValidOffsets [this local-date-time])
  (getTransition [this local-date-time])
  (getStandardOffset [this instant])
  (getDaylightSavings [this instant])
  (isDaylightSavings [this instant])
  (isValidOffset [this local-date-time offset])
  (nextTransition [this instant])
  (previousTransition [this instant])
  (getTransitions [this])
  (getTransitionRules [this]))

(defrecord ZoneRules [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L463
(defn -is-fixed-offset [this] (wip ::-is-fixed-offset))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L478
(defn -get-offset [this get-offset--overloaded-param] (wip ::-get-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L585
(defn -get-valid-offsets [this local-date-time] (wip ::-get-valid-offsets))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L628
(defn -get-transition [this local-date-time] (wip ::-get-transition))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L749
(defn -get-standard-offset [this instant] (wip ::-get-standard-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L779
(defn -get-daylight-savings [this instant] (wip ::-get-daylight-savings))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L802
(defn -is-daylight-savings [this instant] (wip ::-is-daylight-savings))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L820
(defn -is-valid-offset [this local-date-time offset] (wip ::-is-valid-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L835
(defn -next-transition [this instant] (wip ::-next-transition))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L882
(defn -previous-transition [this instant] (wip ::-previous-transition))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L942
(defn -get-transitions [this] (wip ::-get-transitions))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L971
(defn -get-transition-rules [this] (wip ::-get-transition-rules))

(extend-type ZoneRules
  IZoneRules
  (isFixedOffset [this] (-is-fixed-offset this))
  (getOffset [this get-offset--overloaded-param] (-get-offset this get-offset--overloaded-param))
  (getValidOffsets [this local-date-time] (-get-valid-offsets this local-date-time))
  (getTransition [this local-date-time] (-get-transition this local-date-time))
  (getStandardOffset [this instant] (-get-standard-offset this instant))
  (getDaylightSavings [this instant] (-get-daylight-savings this instant))
  (isDaylightSavings [this instant] (-is-daylight-savings this instant))
  (isValidOffset [this local-date-time offset] (-is-valid-offset this local-date-time offset))
  (nextTransition [this instant] (-next-transition this instant))
  (previousTransition [this instant] (-previous-transition this instant))
  (getTransitions [this] (-get-transitions this))
  (getTransitionRules [this] (-get-transition-rules this)))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L197
  ([offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L176
  ([base-standard-offset base-wall-offset standard-offset-transition-list transition-list last-rules] (wip ::of)))
