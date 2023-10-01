(ns jiffy.protocols.zone.zone-rules
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneRules
  (is-fixed-offset [this])
  (get-offset [this get-offset--overloaded-param])
  (get-valid-offsets [this local-date-time])
  (get-transition [this local-date-time])
  (get-standard-offset [this instant])
  (get-daylight-savings [this instant])
  (is-daylight-savings [this instant])
  (is-valid-offset [this local-date-time offset])
  (next-transition [this instant])
  (previous-transition [this instant])
  (get-transitions [this])
  (get-transition-rules [this]))

(s/def ::zone-rules #(satisfies? IZoneRules %))
