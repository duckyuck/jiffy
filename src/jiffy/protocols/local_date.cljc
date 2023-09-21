(ns jiffy.protocols.local-date
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDate.java
(defprotocol ILocalDate
  (get-month [this])
  (get-day-of-week [this])
  (get-day-of-year [this])
  (get-year [this])
  (get-month-value [this])
  (get-day-of-month [this])
  (with-year [this year])
  (with-month [this month])
  (with-day-of-month [this day-of-month])
  (with-day-of-year [this day-of-year])
  (plus-years [this years-to-add])
  (plus-months [this months-to-add])
  (plus-weeks [this weeks-to-add])
  (plus-days [this days-to-add])
  (minus-years [this years-to-subtract])
  (minus-months [this months-to-subtract])
  (minus-weeks [this weeks-to-subtract])
  (minus-days [this days-to-subtract])
  (days-until [this end])
  (dates-until
    [this end-exclusive]
    [this end-exclusive step])
  (at-time
    [this offset-time]
    [this hour minute]
    [this hour minute second]
    [this hour minute second nano-of-second])
  (at-start-of-day [this] [this zone])
  (to-epoch-second [this time offset]))

(s/def ::local-date #(satisfies? ILocalDate %))
