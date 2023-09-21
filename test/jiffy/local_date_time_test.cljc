(ns jiffy.local-date-time-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-year)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-month-value)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-month)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-day-of-month)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-day-of-year)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-day-of-week)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-hour)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-minute)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-second)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/get-nano)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-year)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-month)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-day-of-month)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-day-of-year)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-hour)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-minute)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-second)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/with-nano)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/truncated-to)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-years)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-months)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-weeks)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-days)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-hours)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-minutes)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-seconds)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/plus-nanos)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-years)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-months)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-weeks)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-days)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-hours)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-minutes)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-seconds)
(test-proto-fn jiffy.local-date-time jiffy.protocols.local-date-time/minus-nanos)

;; Depends on OffsetDateTime impl
;; (test-proto-fn! jiffy.local-date-time jiffy.protocols.local-date-time/at-offset)

(test-proto-fn jiffy.local-date-time jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/to-local-date)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/to-local-time)
;; (test-proto-fn! jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/format)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/at-zone)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/is-after)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/is-before)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/is-equal)
(test-proto-fn jiffy.local-date-time jiffy.protocols.chrono.chrono-local-date-time/to-epoch-second)

(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal/until)

(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal-accessor/is-supported)

;; "not implemented: :jiffy.local-time/-range"
;; (test-proto-fn! jiffy.local-date-time jiffy.protocols.temporal.temporal-accessor/range)

;; "not implemented: :jiffy.local-time/-get"
;; (test-proto-fn! jiffy.local-date-time jiffy.protocols.temporal.temporal-accessor/get)

;; "not implemented: :jiffy.local-time/-get-long"
;; (test-proto-fn! jiffy.local-date-time jiffy.protocols.temporal.temporal-accessor/get-long)

(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal-accessor/query)
(test-proto-fn jiffy.local-date-time jiffy.protocols.temporal.temporal-adjuster/adjust-into)

;; (test-static-fn! jiffy.local-date-time/now)
;; (test-static-fn! jiffy.local-date-time/of)
;; (test-static-fn! jiffy.local-date-time/of-instant)
;; (test-static-fn! jiffy.local-date-time/of-epoch-second)
;; (test-static-fn! jiffy.local-date-time/from)
;; (test-static-fn! jiffy.local-date-time/parse)
