(ns jiffy.protocols.format.date-time-text-provider
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java
(defprotocol IDateTimeTextProvider
  (get-text [this field value style locale] [this chrono field value style locale])
  (get-text-iterator [this field style locale] [this chrono field style locale]))

(s/def ::date-time-text-provider #(satisfies? IDateTimeTextProvider %))