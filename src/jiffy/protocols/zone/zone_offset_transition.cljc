(ns jiffy.protocols.zone.zone-offset-transition
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneOffsetTransition
  (get-instant [this])
  (to-epoch-second [this])
  (get-date-time-before [this])
  (get-date-time-after [this])
  (get-offset-before [this])
  (get-offset-after [this])
  (get-duration [this])
  (is-gap [this])
  (is-overlap [this])
  (is-valid-offset [this offset])
  (get-valid-offsets [this]))

(s/def ::zone-offset-transition #(satisfies? IZoneOffsetTransition %))
