(ns jiffy.protocols.year-month
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/YearMonth.java
(defprotocol IYearMonth
  (get-year [this])
  (get-month-value [this])
  (get-month [this])
  (is-leap-year [this])
  (is-valid-day [this day-of-month])
  (length-of-month [this])
  (length-of-year [this])
  (with-year [this year])
  (with-month [this month])
  (plus-years [this years-to-add])
  (plus-months [this months-to-add])
  (minus-years [this years-to-subtract])
  (minus-months [this months-to-subtract])
  (format [this formatter])
  (at-day [this day-of-month])
  (at-end-of-month [this])
  (is-after [this other])
  (is-before [this other]))

(s/def ::year-month #(satisfies? IYearMonth %))