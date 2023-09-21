(ns jiffy.month-day-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.month-day jiffy.protocols.month-day/get-month)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/get-month-value)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/get-day-of-month)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/is-valid-year)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/with-month)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/with)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/with-day-of-month)
;; (test-proto-fn! jiffy.month-day jiffy.protocols.month-day/format)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/at-year)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/is-after)
(test-proto-fn jiffy.month-day jiffy.protocols.month-day/is-before)

(test-proto-fn jiffy.month-day jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.month-day jiffy.protocols.temporal.temporal-adjuster/adjust-into)

;; (test-static-fn jiffy.month-day/now)
(test-static-fn jiffy.month-day/of)
(test-static-fn jiffy.month-day/from)
;; (test-static-fn! jiffy.month-day/parse)

