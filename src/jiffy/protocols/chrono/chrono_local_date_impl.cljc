(ns jiffy.protocols.chrono.chrono-local-date-impl
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoLocalDateImpl
  (plus-years [this years-to-add])
  (plus-months [this months-to-add])
  (plus-weeks [this weeks-to-add])
  (plus-days [this days-to-add])
  (minus-years [this years-to-subtract])
  (minus-months [this months-to-subtract])
  (minus-weeks [this weeks-to-subtract])
  (minus-days [this days-to-subtract]))

(s/def ::chrono-local-date-impl #(satisfies? IChronoLocalDateImpl %))
