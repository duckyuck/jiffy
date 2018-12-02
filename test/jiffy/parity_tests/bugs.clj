(ns jiffy.parity-tests.bugs
  (:require [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl])
  (:import [jiffy.instant_impl Instant]))

(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))
