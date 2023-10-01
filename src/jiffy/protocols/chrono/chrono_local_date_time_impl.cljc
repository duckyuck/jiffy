(ns jiffy.protocols.chrono.chrono-local-date-time-impl
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoLocalDateTimeImpl
  (plus-seconds [this seconds]))

(s/def ::chrono-local-date-time-impl #(satisfies? IChronoLocalDateTimeImpl %))
