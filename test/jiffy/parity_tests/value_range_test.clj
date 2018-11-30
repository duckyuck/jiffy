(ns jiffy.parity-tests.value-range-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.parity-tests.support :refer [test-proto-fn test-static-fn]]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-field :as temporal-field]))

(s/def ::temporal-field/temporal-field
  (s/with-gen ::temporal-field/temporal-field
    (fn [] (gen/one-of (for [enum (chrono-field/values)]
                         (gen/return enum))))))

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
