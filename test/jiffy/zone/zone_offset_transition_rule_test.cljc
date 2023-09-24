(ns jiffy.zone.zone-offset-transition-rule-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-month)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-day-of-month-indicator)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-day-of-week)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-local-time)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/is-midnight-end-of-day)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-time-definition)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-standard-offset)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-offset-before)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/get-offset-after)
(test-proto-fn jiffy.zone.zone-offset-transition-rule jiffy.protocols.zone.zone-offset-transition-rule/create-transition)

(test-static-fn jiffy.zone.zone-offset-transition-rule/of)
