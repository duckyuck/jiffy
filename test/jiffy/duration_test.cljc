(ns jiffy.duration-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.duration jiffy.protocols.duration/is-zero)
(test-proto-fn jiffy.duration jiffy.protocols.duration/is-negative)
(test-proto-fn jiffy.duration jiffy.protocols.duration/get-seconds)
(test-proto-fn jiffy.duration jiffy.protocols.duration/get-nano)
(test-proto-fn jiffy.duration jiffy.protocols.duration/with-seconds)
(test-proto-fn jiffy.duration jiffy.protocols.duration/with-nanos)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-days)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-hours)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-minutes)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-seconds)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-millis)
(test-proto-fn jiffy.duration jiffy.protocols.duration/plus-nanos)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-days)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-hours)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-minutes)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-seconds)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-millis)
(test-proto-fn jiffy.duration jiffy.protocols.duration/minus-nanos)
(test-proto-fn jiffy.duration jiffy.protocols.duration/multiplied-by)
(test-proto-fn jiffy.duration jiffy.protocols.duration/divided-by)
(test-proto-fn jiffy.duration jiffy.protocols.duration/negated)
(test-proto-fn jiffy.duration jiffy.protocols.duration/abs)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-days)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-hours)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-minutes)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-seconds)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-millis)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-nanos)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-days-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-hours-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-minutes-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-seconds-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-millis-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/to-nanos-part)
(test-proto-fn jiffy.duration jiffy.protocols.duration/truncated-to)
(test-proto-fn jiffy.duration jiffy.protocols.time-comparable/compare-to)
(test-proto-fn jiffy.duration jiffy.protocols.temporal.temporal-amount/get)
(test-proto-fn jiffy.duration jiffy.protocols.temporal.temporal-amount/get-units)
(test-proto-fn jiffy.duration jiffy.protocols.temporal.temporal-amount/add-to)
(test-proto-fn jiffy.duration jiffy.protocols.temporal.temporal-amount/subtract-from)
(test-proto-fn jiffy.duration jiffy.protocols.string/to-string)

(test-static-fn jiffy.duration/between)
(test-static-fn jiffy.duration/parse)
(test-static-fn jiffy.duration/from)
(test-static-fn jiffy.duration/of)
(test-static-fn jiffy.duration/of-nanos)
(test-static-fn jiffy.duration/of-millis)
(test-static-fn jiffy.duration/of-seconds)
(test-static-fn jiffy.duration/of-minutes)
(test-static-fn jiffy.duration/of-hours)
(test-static-fn jiffy.duration/of-days)
