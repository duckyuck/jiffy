(ns jiffy.month-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

;; TODO - complete implementation
(test-proto-fn jiffy.month jiffy.protocols.month/get-value)
;; (test-proto-fn! jiffy.month jiffy.protocols.month/get-display-name)
(test-proto-fn jiffy.month jiffy.protocols.month/plus)
(test-proto-fn jiffy.month jiffy.protocols.month/minus)
(test-proto-fn jiffy.month jiffy.protocols.month/length)
(test-proto-fn jiffy.month jiffy.protocols.month/min-length)
(test-proto-fn jiffy.month jiffy.protocols.month/max-length)
(test-proto-fn jiffy.month jiffy.protocols.month/first-day-of-year)
(test-proto-fn jiffy.month jiffy.protocols.month/first-month-of-quarter)

(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.month jiffy.protocols.temporal.temporal-adjuster/adjust-into)

;; TODO: fix test of value-of. currently tests invalid values (i.e. random strings)
(test-static-fn jiffy.month/value-of)
(test-static-fn jiffy.month/values)
(test-static-fn jiffy.month/of)
(test-static-fn jiffy.month/from)
