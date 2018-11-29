(ns jiffy.parity-tests.api-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl]
            [jiffy.parity-tests.support :refer [same? test-proto-fn test-static-fn trycatch]]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-field :as temporal-field]))


;; TODO: report bug
(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))

;; jiffy.zoned-date-time

;;(test-proto-fn jiffy.zoned-date-time jiffy.chrono.chrono-zoned-date-time/getOffset)

