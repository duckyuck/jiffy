(ns jiffy.parity-tests.chrono-unit-test
  (:require [jiffy.parity-tests.support :refer [test-proto-fn test-proto-fn!]]))

(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/get-duration)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/is-duration-estimated)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/is-date-based)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/is-time-based)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/add-to)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/is-supported-by)
(test-proto-fn jiffy.temporal.chrono-unit jiffy.protocols.temporal.temporal-unit/between)
