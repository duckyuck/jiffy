(ns jiffy.protocols.zone.zone-offset-transition-rule
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneOffsetTransitionRule
  (get-month [this])
  (get-day-of-month-indicator [this])
  (get-day-of-week [this])
  (get-local-time [this])
  (is-midnight-end-of-day [this])
  (get-time-definition [this])
  (get-standard-offset [this])
  (get-offset-before [this])
  (get-offset-after [this])
  (create-transition [this year]))

(s/def ::zone-offset-transition-rule #(satisfies? IZoneOffsetTransitionRule %))
