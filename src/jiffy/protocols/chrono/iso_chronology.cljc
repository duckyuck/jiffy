(ns jiffy.protocols.chrono.iso-chronology
  (:require [clojure.spec.alpha :as s]))

(defprotocol IIsoChronology)

(s/def ::iso-chronology #(satisfies? IIsoChronology %))
