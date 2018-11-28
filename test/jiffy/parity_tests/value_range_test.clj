(ns jiffy.parity-tests.value-range-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.parity-tests.support :refer [test-proto-fn test-static-fn]]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-field :as TemporalField]))

(s/def ::TemporalField/temporal-field
  (s/with-gen ::TemporalField/temporal-field
    (fn [] (gen/one-of (for [enum (ChronoField/values)]
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
