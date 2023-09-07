(ns jiffy.bugs-test
  (:require [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.instant-impl :as instant-impl]))

;; This test does not cover the `toEpochMilli` bug as `create` now longer
;; accepts greater than `9007199254740991` seconds as first param
;; (deftest java-toEpochMilli-bug
;;   (is (thrown-with-msg?
;;        java.lang.ArithmeticException #"long overflow"
;;        (.toEpochMilli (jiffy->java (instant-impl/create 9223372036854776 100000))))))
