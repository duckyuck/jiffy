(ns jiffy.local-time-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.local-time jiffy.protocols.local-time/to-nano-of-day)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/to-second-of-day)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/get-hour)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/get-minute)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/get-second)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/get-nano)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/with-hour)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/with-minute)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/with-second)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/with-nano)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/truncated-to)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/plus-hours)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/plus-minutes)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/plus-seconds)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/plus-nanos)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/minus-hours)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/minus-minutes)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/minus-seconds)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/minus-nanos)
;;(test-proto-fn! jiffy.local-time jiffy.protocols.local-time/format)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/at-date)

;; "not implemented: :jiffy.offset-time-impl/of"
;; (test-proto-fn! jiffy.local-time jiffy.protocols.local-time/at-offset)

(test-proto-fn jiffy.local-time jiffy.protocols.local-time/to-epoch-second)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/is-after)
(test-proto-fn jiffy.local-time jiffy.protocols.local-time/is-before)

(test-proto-fn jiffy.local-time jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal/until)

(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.local-time jiffy.protocols.temporal.temporal-adjuster/adjust-into)


