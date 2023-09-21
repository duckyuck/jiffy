(ns jiffy.zone.zone-rules-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true]))
  )

(test-proto-fn jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/is-fixed-offset)

;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-offset)

;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-valid-offsets)
(test-proto-fn jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-transition)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-standard-offset)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-daylight-savings)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/is-daylight-savings)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/is-valid-offset)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/next-transition)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/previous-transition)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-transitions)
;; (test-proto-fn! jiffy.zone.zone-rules jiffy.protocols.zone.zone-rules/get-transition-rules)

;; (test-static-fn! jiffy.zone.zone-rules/of)
