(ns jiffy.zone.zone-rules
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
            [jiffy.protocols.zone.zone-offset-transition :as transition]))

(def-record ZoneRules ::zone-rules
  [fixed-offset ::j/boolean
   transition-rules (s/+ ::transition-rule/zone-offset-transition-rule)
   transitions (s/+ ::transition/zone-offset-transition)])

(def-constructor create ::zone-rules
  [fixed-offset ::j/boolean
   transition-rules ::transition-rule/zone-offset-transition-rule
   transitions ::transition/zone-offset-transition]
  (->ZoneRules fixed-offset transition-rules transitions))

(defmacro args [& x] `(s/tuple ::zone-rules ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L463
(s/def ::is-fixed-offset-args (args))
(defn -is-fixed-offset [this] (wip ::-is-fixed-offset))
(s/fdef -is-fixed-offset :args ::is-fixed-offset-args :ret ::j/boolean)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L478
(s/def ::get-offset-args (args ::local-date-time/local-date-time))
(defn -get-offset [this get-offset--overloaded-param] (wip ::-get-offset))
(s/fdef -get-offset :args ::get-offset-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L585
(s/def ::get-valid-offsets-args (args ::local-date-time/local-date-time))
(defn -get-valid-offsets [this local-date-time] (wip ::-get-valid-offsets))
(s/fdef -get-valid-offsets :args ::get-valid-offsets-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L628
(s/def ::get-transition-args (args ::local-date-time/local-date-time))
(defn -get-transition [this local-date-time] (wip ::-get-transition))
(s/fdef -get-transition :args ::get-transition-args :ret ::zone-offset-transition/zone-offset-transition)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L749
(s/def ::get-standard-offset-args (args ::instant/instant))
(defn -get-standard-offset [this instant] (wip ::-get-standard-offset))
(s/fdef -get-standard-offset :args ::get-standard-offset-args :ret ::zone-offset/zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L779
(s/def ::get-daylight-savings-args (args ::instant/instant))
(defn -get-daylight-savings [this instant] (wip ::-get-daylight-savings))
(s/fdef -get-daylight-savings :args ::get-daylight-savings-args :ret ::duration/duration)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L802
(s/def ::is-daylight-savings-args (args ::instant/instant))
(defn -is-daylight-savings [this instant] (wip ::-is-daylight-savings))
(s/fdef -is-daylight-savings :args ::is-daylight-savings-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L820
(s/def ::is-valid-offset-args (args ::local-date-time/local-date-time ::zone-offset/zone-offset))
(defn -is-valid-offset [this local-date-time offset] (wip ::-is-valid-offset))
(s/fdef -is-valid-offset :args ::is-valid-offset-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L835
(s/def ::next-transition-args (args ::instant/instant))
(defn -next-transition [this instant] (wip ::-next-transition))
(s/fdef -next-transition :args ::next-transition-args :ret ::zone-offset-transition/zone-offset-transition)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L882
(s/def ::previous-transition-args (args ::instant/instant))
(defn -previous-transition [this instant] (wip ::-previous-transition))
(s/fdef -previous-transition :args ::previous-transition-args :ret ::zone-offset-transition/zone-offset-transition)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L942
(s/def ::get-transitions-args (args))
(defn -get-transitions [this] (wip ::-get-transitions))
(s/fdef -get-transitions :args ::get-transitions-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L971
(s/def ::get-transition-rules-args (args))
(defn -get-transition-rules [this] (wip ::-get-transition-rules))
(s/fdef -get-transition-rules :args ::get-transition-rules-args :ret ::j/wip)

(extend-type ZoneRules
  zone-rules/IZoneRules
  (is-fixed-offset [this] (-is-fixed-offset this))
  (get-offset [this get-offset--overloaded-param] (-get-offset this get-offset--overloaded-param))
  (get-valid-offsets [this local-date-time] (-get-valid-offsets this local-date-time))
  (get-transition [this local-date-time] (-get-transition this local-date-time))
  (get-standard-offset [this instant] (-get-standard-offset this instant))
  (get-daylight-savings [this instant] (-get-daylight-savings this instant))
  (is-daylight-savings [this instant] (-is-daylight-savings this instant))
  (is-valid-offset [this local-date-time offset] (-is-valid-offset this local-date-time offset))
  (next-transition [this instant] (-next-transition this instant))
  (previous-transition [this instant] (-previous-transition this instant))
  (get-transitions [this] (-get-transitions this))
  (get-transition-rules [this] (-get-transition-rules this)))

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L197
  ([offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L176
  ([base-standard-offset base-wall-offset standard-offset-transition-list transition-list last-rules] (wip ::of)))
(s/fdef of :args ::of-args :ret ::zone-rules)
