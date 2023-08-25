(ns jiffy.math-test
  (:require [clojure.math :as math]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.walk :as walk]
            [jiffy.math :as sut]
            [jiffy.math.support :refer [compare-math wrap unwrap]]
            [jiffy.specs :as j]))

(s/def ::floor-int int?)

(defspec floor-div-test 10000
  (prop/for-all [values (->> (s/tuple ::floor-int (s/and ::floor-int (comp not zero?)))
                             s/gen
                             (gen/fmap wrap))]
                (let [[x y] (unwrap values)]
                  (compare-math values
                                (sut/floor-div x y)
                                (math/floor-div x y)))))

(defspec floor-mod-test 10000
  (prop/for-all [values (->> (s/tuple ::floor-int (s/and ::floor-int (comp not zero?)))
                             s/gen
                             (gen/fmap wrap))]
                (let [[x y] (unwrap values)]
                  (compare-math values
                                (sut/floor-mod (long x) (long y))
                                (math/floor-mod x y)))))

(defspec add-exact-test 10000
  (prop/for-all [values (->> (s/or :ints (s/tuple ::j/int ::j/int)
                                   :longs (s/tuple ::j/long ::j/long))
                             s/gen
                             (gen/fmap wrap))]
                (let [[x y] (unwrap values)]
                  (compare-math values
                                (sut/add-exact (long x) (long y))
                                (math/add-exact x y)))))

(defspec subtract-exact-test 10000
  (prop/for-all [values (->> (s/tuple int? int?)
                             s/gen
                             (gen/fmap wrap))]
                (let [[x y] (unwrap values)]
                  (compare-math values
                                (sut/subtract-exact x y)
                                (math/subtract-exact x y)))))

(defspec multiply-exact-test 1000
  (prop/for-all [values (->> (s/tuple ::j/long
                                      (s/or :int ::j/int
                                            :long ::j/long))
                             s/gen
                             (gen/fmap wrap))]
                (let [[x y] (unwrap values)]
                  (compare-math values
                                (sut/multiply-exact x y)
                                (math/multiply-exact x y)))))
