(ns jiffy.parity-tests.clock-test
  #?(:clj (:require [jiffy.parity-tests.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.parity-tests.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-static-fn jiffy.clock/fixed)
