(ns jiffy.parity-tests.value-range-test
  (:require [jiffy.parity-tests.support :refer [test-proto-fn test-static-fn]]))

(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/is-fixed)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/get-minimum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/get-largest-minimum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/get-smallest-maximum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/get-maximum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/is-int-value)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/is-valid-value)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/is-valid-int-value)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/check-valid-value)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/check-valid-int-value)
(test-static-fn jiffy.temporal.value-range/of)
