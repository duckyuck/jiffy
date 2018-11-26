(ns jiffy.parity-tests.api-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :as str]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.exception :refer [try*]]
            [jiffy.math :as math]
            [jiffy.parity-tests.support :refer [test-static-fn test-proto-fn same?]]
            [jiffy.specs :as j]))

;; (test-proto-fn jiffy.instant jiffy.instant/getEpochSecond)
;; (test-proto-fn jiffy.instant jiffy.instant/getNano)
;; (test-proto-fn jiffy.instant jiffy.instant/truncatedTo)
;; (test-proto-fn jiffy.instant jiffy.instant/plusSeconds)
;; (test-proto-fn jiffy.instant jiffy.instant/plusMillis)
;; (test-proto-fn jiffy.instant jiffy.instant/plusNanos)
;; (test-proto-fn jiffy.instant jiffy.instant/minusSeconds)
;; (test-proto-fn jiffy.instant jiffy.instant/minusMillis)
;; (test-proto-fn jiffy.instant jiffy.instant/minusNanos)
;; (test-proto-fn jiffy.instant jiffy.instant/toEpochMilli)
(test-proto-fn jiffy.instant jiffy.instant/atOffset)
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
(test-static-fn jiffy.instant/from jiffy.instant.Instant/from)

(test-static-fn jiffy.local-date/ofEpochDay java.time.LocalDate/ofEpochDay)

(jiffy.local-date/ofEpochDay 1)
(gen/sample (s/gen :jiffy.local-date/of-epoch-day-args))

;; TODO: report bug
(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))
