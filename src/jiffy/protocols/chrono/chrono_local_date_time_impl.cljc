(ns jiffy.protocols.chrono.chrono-local-date-time-impl
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTimeImpl.java
(defprotocol IChronoLocalDateTimeImpl
  (plus-seconds [this seconds]))

(s/def ::chrono-local-date-time-impl #(satisfies? IChronoLocalDateTimeImpl %))