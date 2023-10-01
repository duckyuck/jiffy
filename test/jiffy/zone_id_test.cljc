(ns jiffy.zone-id-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

;; TODO: fix tests
;; (test-static-fn! jiffy.zone-id/get-available-zone-ids)
;; (test-static-fn! jiffy.zone-id/of)
(test-static-fn jiffy.zone-id/of-offset)
