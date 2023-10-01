(ns jiffy.protocols.chrono.japanese-chronology
  (:require [clojure.spec.alpha :as s]))

(defprotocol IJapaneseChronology
  (get-current-era [this]))

(s/def ::japanese-chronology #(satisfies? IJapaneseChronology %))
