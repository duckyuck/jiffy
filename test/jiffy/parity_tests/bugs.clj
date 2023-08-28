(ns jiffy.parity-tests.bugs
  (:require [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl :as instant-impl]))

(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java (instant-impl/create 9223372036854776 100000))))))
