(ns jiffy.protocols.day-of-week
  (:require [clojure.spec.alpha :as s]))

(defprotocol IDayOfWeek
  (get-value [this])
  (get-display-name [this style locale])
  (plus [this days])
  (minus [this days]))

(s/def ::day-of-week #(satisfies? IDayOfWeek %))
