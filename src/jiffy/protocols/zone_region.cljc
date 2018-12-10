(ns jiffy.protocols.zone-region
  (:require [clojure.spec.alpha :as s]))

(defprotocol IZoneRegion)

(s/def ::zone-region #(satisfies? IZoneRegion %))
