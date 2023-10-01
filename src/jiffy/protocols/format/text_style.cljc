(ns jiffy.protocols.format.text-style
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITextStyle
  (is-standalone [this])
  (as-standalone [this])
  (as-normal [this])
  (to-calendar-style [this])
  (zone-name-style-index [this]))

(s/def ::text-style #(satisfies? ITextStyle %))
