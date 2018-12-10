(ns jiffy.protocols.chrono.chrono-local-date-impl
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateImpl.java
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