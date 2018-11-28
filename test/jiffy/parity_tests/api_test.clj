(ns jiffy.parity-tests.api-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl]
            [jiffy.parity-tests.support :refer [same? test-proto-fn test-static-fn trycatch]]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-field :as temporal-field]))



;; jiffy.temporal.value-range

(s/def ::temporal-field/temporal-field
  (s/with-gen ::temporal-field/temporal-field
    (fn [] (gen/one-of (for [enum (chrono-field/values)]
                         (gen/return enum))))))

(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/isFixed)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/getMinimum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/getLargestMinimum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/getSmallestMaximum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/getMaximum)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/isIntValue)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/isValidValue)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/isValidIntValue)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/checkValidValue)
(test-proto-fn jiffy.temporal.value-range jiffy.temporal.value-range/checkValidIntValue)
(test-static-fn jiffy.temporal.value-range/of)

;; TODO: report bug
(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))
