(ns jiffy.zone.zone-offset-transition-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-instant)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/to-epoch-second)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-date-time-before)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-date-time-after)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-offset-before)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-offset-after)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-duration)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/is-gap)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/is-overlap)
(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/is-valid-offset)

;; Package private method in java.time. Tested indirectly by `jiffy.zone.zone-rules/get-valid-offsets`
;; (test-proto-fn! jiffy.zone.zone-offset-transition jiffy.protocols.zone.zone-offset-transition/get-valid-offsets)

(test-proto-fn jiffy.zone.zone-offset-transition jiffy.protocols.time-comparable/compare-to)

(test-static-fn jiffy.zone.zone-offset-transition/of)
