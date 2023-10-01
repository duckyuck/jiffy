(ns jiffy.protocols.year
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

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
