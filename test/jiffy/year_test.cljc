(ns jiffy.year-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.year jiffy.protocols.year/get-value)
(test-proto-fn jiffy.year/is-leap--proto jiffy.protocols.year/is-leap)
(test-proto-fn jiffy.year jiffy.protocols.year/is-valid-month-day)
(test-proto-fn jiffy.year jiffy.protocols.year/length)
(test-proto-fn jiffy.year jiffy.protocols.year/plus-years)
(test-proto-fn jiffy.year jiffy.protocols.year/minus-years)
;; (test-proto-fn! jiffy.year jiffy.protocols.year/format)
(test-proto-fn jiffy.year jiffy.protocols.year/at-day)
(test-proto-fn jiffy.year jiffy.protocols.year/at-month)
(test-proto-fn jiffy.year jiffy.protocols.year/at-month-day)
(test-proto-fn jiffy.year jiffy.protocols.year/is-after)
(test-proto-fn jiffy.year jiffy.protocols.year/is-before)

(test-proto-fn jiffy.year jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal/until)

(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.year jiffy.protocols.temporal.temporal-adjuster/adjust-into)

;; (test-static-fn! jiffy.year/now)
(test-static-fn jiffy.year/of)
(test-static-fn jiffy.year/from)
(test-static-fn jiffy.year/is-leap)
;; (test-static-fn! jiffy.year/parse)
