(ns jiffy.protocols.format.sign-style
  (:require [clojure.spec.alpha :as s]))

(defprotocol ISignStyle
  (parse [this positive strict fixed-width]))

(s/def ::sign-style #(satisfies? ISignStyle %))
