(ns jiffy.zone-offset-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.zone-offset jiffy.protocols.zone-offset/get-total-seconds)

(test-proto-fn jiffy.zone-offset jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.zone-offset jiffy.protocols.zone-id/get-id)
(test-proto-fn jiffy.zone-offset jiffy.protocols.zone-id/get-rules)

(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-accessor/query)
(test-proto-fn jiffy.zone-offset jiffy.protocols.temporal.temporal-adjuster/adjust-into)

(test-static-fn jiffy.zone-offset/of-hours)
(test-static-fn jiffy.zone-offset/of-hours-minutes)
(test-static-fn jiffy.zone-offset/of-hours-minutes-seconds)
(test-static-fn jiffy.zone-offset/of-total-seconds)
;; (test-static-fn! jiffy.zone-offset/of)
(test-static-fn jiffy.zone-offset/from)
