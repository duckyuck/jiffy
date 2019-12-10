(ns jiffy.parity-tests.math-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [jiffy.math :as sut]
            [jiffy.parity-tests.support :refer [same?]]
            [jiffy.specs :as j]))

;; FIXME: need to narrow this to int as we're unable to replicate implementation in java.lang.Math.
;; Change ::floor-int to int? to see the results.
;; If this discrepancy is relevant for the implementation of Jiffy it should
;; manifest itself in failing API parity tests.
(s/def ::floor-int (s/int-in -2147483647 2147483647))

(defspec floor-div-test 10000
  (prop/for-all [[x y] (s/gen (s/tuple ::floor-int (s/and ::floor-int (comp not zero?))))]
                (same? (sut/floor-div x y)
                       (Math/floorDiv x y))))

(defspec floor-mod-test 10000
  (prop/for-all [[x y] (s/gen (s/tuple ::floor-int (s/and ::floor-int (comp not zero?))))]
                (same? (sut/floor-mod x y)
                       (Math/floorMod x y))))

(defspec add-exact-test 10000
  (prop/for-all [[x y] (s/gen (s/or :ints (s/tuple ::j/int ::j/int)
                                    :longs (s/tuple ::j/long ::j/long)))]
                (same? (sut/add-exact x y)
                       (Math/addExact x y))))

(defspec subtract-exact-test 10000
  (prop/for-all [[x y] (s/gen (s/tuple int? int?))]
                (same? (sut/subtract-exact x y)
                       (Math/subtractExact (long x) (long y)))))

(defspec multiply-exact-test 1000
  (prop/for-all [[x y] (s/gen (s/tuple ::j/long
                                       (s/or :int ::j/int :long ::j/long)))]
                (same? (sut/multiply-exact x y)
                       (Math/multiplyExact x y))))
