(ns jiffy.parity-tests.clock-test
  #?(:clj (:require [jiffy.parity-tests.support-2 :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.parity-tests.support-2-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-static-fn jiffy.clock/fixed)
