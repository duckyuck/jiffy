(ns jiffy.protocols.chrono.chrono-zoned-date-time-impl
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoZonedDateTimeImpl)

(s/def ::chrono-zoned-date-time-impl #(satisfies? IChronoZonedDateTimeImpl %))
