(ns jiffy.protocols.year
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Year.java
(defprotocol IYear
  (get-value [this])
  (is-leap [this])
  (is-valid-month-day [this month-day])
  (length [this])
  (plus-years [this years-to-add])
  (minus-years [this years-to-subtract])
  (format [this formatter])
  (at-day [this day-of-year])
  (at-month [this at-month--overloaded-param])
  (at-month-day [this month-day])
  (is-after [this other])
  (is-before [this other]))

(s/def ::year #(satisfies? IYear %))