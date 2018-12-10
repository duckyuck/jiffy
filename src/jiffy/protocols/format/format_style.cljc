(ns jiffy.protocols.format.format-style
  (:require [clojure.spec.alpha :as s]))

(defprotocol IFormatStyle)

(s/def ::format-style #(satisfies? IFormatStyle %))
