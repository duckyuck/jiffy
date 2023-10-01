(ns jiffy.protocols.format.date-time-text-provider
  (:require [clojure.spec.alpha :as s]))

(defprotocol IDateTimeTextProvider
  (get-text [this field value style locale] [this chrono field value style locale])
  (get-text-iterator [this field style locale] [this chrono field style locale]))

(s/def ::date-time-text-provider #(satisfies? IDateTimeTextProvider %))
