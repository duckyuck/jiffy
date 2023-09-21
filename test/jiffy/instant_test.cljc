(ns jiffy.instant-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.instant jiffy.protocols.instant/get-epoch-second)
(test-proto-fn jiffy.instant jiffy.protocols.instant/get-nano)
(test-proto-fn jiffy.instant jiffy.protocols.instant/truncated-to)
(test-proto-fn jiffy.instant jiffy.protocols.instant/plus-seconds)
(test-proto-fn jiffy.instant jiffy.protocols.instant/plus-millis)
(test-proto-fn jiffy.instant jiffy.protocols.instant/plus-nanos)
(test-proto-fn jiffy.instant jiffy.protocols.instant/minus-seconds)
(test-proto-fn jiffy.instant jiffy.protocols.instant/minus-millis)
(test-proto-fn jiffy.instant jiffy.protocols.instant/minus-nanos)
(test-proto-fn jiffy.instant jiffy.protocols.instant/to-epoch-milli)
(test-proto-fn jiffy.instant jiffy.protocols.instant/at-offset)
(test-proto-fn jiffy.instant jiffy.protocols.instant/at-zone)
(test-proto-fn jiffy.instant jiffy.protocols.instant/is-after)
(test-proto-fn jiffy.instant jiffy.protocols.instant/is-before)

(test-proto-fn jiffy.instant jiffy.protocols.time-comparable/compare-to)

(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal/with)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal/plus)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal/minus)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal/until)

(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-accessor/query)

(test-proto-fn jiffy.instant jiffy.protocols.temporal.temporal-adjuster/adjust-into)

#_(test-static-fn! jiffy.instant/now)
(test-static-fn jiffy.instant/of-epoch-second)
(test-static-fn jiffy.instant/of-epoch-milli)
(test-static-fn jiffy.instant/from)
;; (test-static-fn! jiffy.instant/parse)
