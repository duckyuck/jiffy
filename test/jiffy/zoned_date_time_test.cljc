(ns jiffy.zoned-date-time-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-fixed-offset-zone)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-year)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-month-value)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-month)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-day-of-month)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-day-of-year)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-day-of-week)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-hour)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-minute)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-second)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/get-nano)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-year)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-month)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-day-of-month)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-day-of-year)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-hour)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-minute)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-second)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/with-nano)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/truncated-to)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-years)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-months)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-weeks)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-days)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-hours)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-minutes)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-seconds)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/plus-nanos)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-years)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-months)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-weeks)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-days)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-hours)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-minutes)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-seconds)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/minus-nanos)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.zoned-date-time/to-offset-date-time)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.time-comparable/compare-to)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/to-epoch-second)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/get-offset)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/with-earlier-offset-at-overlap)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/get-zone)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/with-later-offset-at-overlap)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/with-zone-same-local)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/with-zone-same-instant)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/to-local-date-time)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/to-local-date)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/to-local-time)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/is-before)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/is-after)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/is-equal)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/get-chronology)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/to-instant)
;; (test-proto-fn! jiffy.zoned-date-time jiffy.protocols.chrono.chrono-zoned-date-time/format)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal/until)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.temporal.temporal-accessor/query)
(test-proto-fn jiffy.zoned-date-time jiffy.protocols.string/to-string)

;; (test-static-fn! jiffy.zoned-date-time/now)
(test-static-fn jiffy.zoned-date-time/of)
(test-static-fn jiffy.zoned-date-time/of-local)
(test-static-fn jiffy.zoned-date-time/of-instant)
(test-static-fn jiffy.zoned-date-time/of-strict)
(test-static-fn jiffy.zoned-date-time/from)
(test-static-fn jiffy.zoned-date-time/parse)
