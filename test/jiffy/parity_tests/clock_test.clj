(ns jiffy.parity-tests.clock-test
  (:require [jiffy.parity-tests.support :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support]))

(test-static-fn jiffy.clock/fixed)
