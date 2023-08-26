(ns jiffy.parity-tests.day-of-week-test
  #?(:clj (:require [jiffy.parity-tests.support-2 :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.parity-tests.support-2-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.day-of-week jiffy.day-of-week/get-value)
;; (test-proto-fn! jiffy.day-of-week jiffy.day-of-week/get-display-name)
(test-proto-fn jiffy.day-of-week jiffy.day-of-week/plus)
(test-proto-fn jiffy.day-of-week jiffy.day-of-week/minus)

(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.day-of-week jiffy.protocols.temporal.temporal-adjuster/adjust-into)

(test-static-fn jiffy.day-of-week/of)
;; (test-static-fn! jiffy.day-of-week/from)
(test-static-fn jiffy.day-of-week/value-of)
(test-static-fn jiffy.day-of-week/values)
