(ns jiffy.parity-tests.api-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl]
            [jiffy.parity-tests.support :refer [same? test-proto-fn test-static-fn trycatch]]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-field :as temporal-field]))


;; jiffy.instant

(test-proto-fn jiffy.instant jiffy.instant/getEpochSecond)
(test-proto-fn jiffy.instant jiffy.instant/getNano)
;; (test-proto-fn jiffy.instant jiffy.instant/truncatedTo)
(test-proto-fn jiffy.instant jiffy.instant/plusSeconds)
(test-proto-fn jiffy.instant jiffy.instant/plusMillis)
(test-proto-fn jiffy.instant jiffy.instant/plusNanos)
(test-proto-fn jiffy.instant jiffy.instant/minusSeconds)
(test-proto-fn jiffy.instant jiffy.instant/minusMillis)
(test-proto-fn jiffy.instant jiffy.instant/minusNanos)
(test-proto-fn jiffy.instant jiffy.instant/toEpochMilli)
;; (test-proto-fn jiffy.instant jiffy.instant/atOffset)



;; (test-proto-fn jiffy.instant jiffy.instant/atZone)
;; (test-proto-fn jiffy.instant jiffy.instant/isAfter)
;; (test-proto-fn jiffy.instant jiffy.instant/isBefore)
;; (test-proto-fn jiffy.time-comparable jiffy.instant/compareTo)
;; (test-proto-fn jiffy.temporal.temporal jiffy.instant/with)
;; (test-proto-fn jiffy.temporal.temporal jiffy.instant/plus)
;; (test-proto-fn jiffy.temporal.temporal jiffy.instant/minus)
;; (test-proto-fn jiffy.temporal.temporal jiffy.instant/until)
;; (test-proto-fn jiffy.temporal.temporal-accessor jiffy.instant/isSupported)
;; (test-proto-fn jiffy.temporal.temporal-accessor jiffy.instant/range)
;; (test-proto-fn jiffy.temporal.temporal-accessor jiffy.instant/get)
;; (test-proto-fn jiffy.temporal.temporal-accessor jiffy.instant/getLong)
;; (test-proto-fn jiffy.temporal.temporal-accessor jiffy.instant/query)
;; (test-proto-fn jiffy.temporal.temporal-adjuster jiffy.instant/adjustInto)
;; (test-static-fn jiffy.instant/ofEpochSecond java.time.Instant/ofEpochSecond)
;; (test-static-fn jiffy.instant/now jiffy.instant.Instant/now)
;; (test-static-fn jiffy.instant/ofEpochMilli jiffy.instant.Instant/ofEpochMilli)
;;(test-static-fn jiffy.instant/from jiffy.instant.Instant/from)

;;(test-static-fn jiffy.local-date/ofEpochDay java.time.LocalDate/ofEpochDay)


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
(test-static-fn jiffy.temporal.value-range/of java.time.temporal.ValueRange/of)

;; TODO: report bug
(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))
