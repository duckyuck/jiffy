(ns jiffy.protocols.temporal.iso-fields
  (:require [clojure.spec.alpha :as s]))

(defprotocol IIsoFields)

(s/def ::iso-fields #(satisfies? IIsoFields %))
