(ns jiffy.protocols.chrono.japanese-era
  (:require [clojure.spec.alpha :as s]))

(defprotocol IJapaneseEra
  (get-private-era [this])
  (get-abbreviation [this])
  (get-name [this]))

(s/def ::japanese-era #(satisfies? IJapaneseEra %))
