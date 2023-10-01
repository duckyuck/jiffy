(ns jiffy.protocols.zone-id
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneId
  (normalized [this])
  (get-id [this])
  (get-display-name [this style locale])
  (get-rules [this]))

(s/def ::zone-id #(satisfies? IZoneId %))
