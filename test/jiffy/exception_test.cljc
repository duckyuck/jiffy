(ns jiffy.exception-test
  (:require [clojure.test :refer [deftest testing is]]
            [jiffy.exception :refer [ex ex-msg #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]))

(def Foo ::foo)
(def Bar ::bar)
(derive Bar Foo)

(deftest try-test
  (testing "simple case"
    (is (= (try*
            (throw (ex Foo "Foo"))
            (catch Foo f
              [Foo (ex-msg f)]))
           [Foo "Foo: {}"])))

  (testing "considers exception hierarchy, catching Bar with Foo"
    (is (= (try*
            (throw (ex Bar "Bar"))
            (catch Foo f
              [Foo (ex-msg f)]))
           [Foo "Bar: {}"])))

  (testing "supports :default"
    (is (= (try*
            (throw (ex Foo "Foo"))
            (catch :default f
              [Foo (ex-msg f)]))
           [Foo "Foo: {}"])))

  (testing "multiple catch clauses"
    (is (= (try*
            (throw (ex Bar "Bar"))
            (catch Bar b
              [Bar (ex-msg b)])
            (catch Foo f
              [Foo (ex-msg f)]))
           [Bar "Bar: {}"])))

  (testing "multiple catch clauses, catching Bar with Foo"
    (is (= (try*
            (throw (ex Bar "Bar"))
            (catch Foo f
              [Foo (ex-msg f)])
            (catch Bar b
              [Bar (ex-msg b)]))
           [Foo "Bar: {}"]))))
