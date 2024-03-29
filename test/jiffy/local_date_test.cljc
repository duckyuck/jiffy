(ns jiffy.local-date-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-month)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-day-of-week)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-day-of-year)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-year)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-month-value)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/get-day-of-month)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/with-year)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/with-month)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/with-day-of-month)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/with-day-of-year)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/plus-years)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/plus-months)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/plus-weeks)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/plus-days)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/minus-years)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/minus-months)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/minus-weeks)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/minus-days)

;; Package private method
;; (test-proto-fn! jiffy.local-date jiffy.protocols.local-date/days-until)

(test-proto-fn jiffy.local-date jiffy.protocols.local-date/dates-until)

(test-proto-fn jiffy.local-date jiffy.protocols.local-date/at-time)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/at-start-of-day)
(test-proto-fn jiffy.local-date jiffy.protocols.local-date/to-epoch-second)

(test-proto-fn jiffy.local-date jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/length-of-month)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/length-of-year)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/is-leap-year)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/to-epoch-day)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/get-chronology)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/get-era)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/until)

;; (test-proto-fn! jiffy.local-date jiffy.protocols.chrono.chrono-local-date/format)
(test-proto-fn jiffy.local-date/at-time--chrono jiffy.protocols.chrono.chrono-local-date/at-time)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/is-after)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/is-before)
(test-proto-fn jiffy.local-date jiffy.protocols.chrono.chrono-local-date/is-equal)

(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.local-date/until--temporal jiffy.protocols.temporal.temporal/until)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-accessor/is-supported)

(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-accessor/query)
(test-proto-fn jiffy.local-date jiffy.protocols.temporal.temporal-adjuster/adjust-into)

(test-proto-fn jiffy.local-date jiffy.protocols.string/to-string)

;; (test-static-fn! jiffy.local-date/now)
(test-static-fn jiffy.local-date/of)
(test-static-fn jiffy.local-date/of-year-day)
(test-static-fn jiffy.local-date/of-instant)
(test-static-fn jiffy.local-date/of-epoch-day)
(test-static-fn jiffy.local-date/from)
(test-static-fn jiffy.local-date/parse)
