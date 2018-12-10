(ns jiffy.protocols.temporal.julian-fields
  (:require [clojure.spec.alpha :as s]))

(defprotocol IJulianFields)

(s/def ::julian-fields #(satisfies? IJulianFields %))
