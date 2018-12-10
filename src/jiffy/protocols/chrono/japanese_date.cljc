(ns jiffy.protocols.chrono.japanese-date
  (:require [clojure.spec.alpha :as s]))

(defprotocol IJapaneseDate)

(s/def ::japanese-date #(satisfies? IJapaneseDate %))
