(ns jiffy.protocols.zone-offset
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneOffset
  (get-total-seconds [this]))

(s/def ::zone-offset #(satisfies? IZoneOffset %))
