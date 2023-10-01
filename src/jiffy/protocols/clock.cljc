(ns jiffy.protocols.clock
  (:require [clojure.spec.alpha :as s]))

(defprotocol IClock
  (get-zone [this])
  (with-zone [this zone])
  (millis [this])
  (instant [this]))

(s/def ::clock #(satisfies? IClock %))
