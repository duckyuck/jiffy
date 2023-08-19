(ns ^:focus jiffy.parity-tests.math-test
  (:require [clojure.math :as math]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.walk :as walk]
            [jiffy.conversion :as conversion]
            [jiffy.math :as sut]
            [jiffy.parity-tests.support :as support :refer [matching-types?]]
            [jiffy.specs :as j]))

(defn wrap [x]
  (walk/postwalk
   (fn [v]
     (if (number? v)
       [:typed (type v) v]
       v))
   x))

(defn typed [gen]
  (gen/fmap wrap gen))

(defn unwrap [x]
  (walk/postwalk
   (fn [v]
     (if (and (vector? v)
              (= :typed (first v)))
       (nth v 2)
       v))
   x))

;; Similar to `jiffy.parity-tests.support/same?` but disregarding type
(defmacro same-number? [values jiffy-expr java-expr]
  `(let [jiffy-result# (support/trycatch ~jiffy-expr)
         java-result# (support/trycatch ~java-expr)]
     (or (and (matching-types? jiffy-result# java-result#)
              (conversion/same? jiffy-result# java-result#))
         (throw (ex-info "Not same"
                         {:jiffy {:result jiffy-result#
                                  :expr '~jiffy-expr}
                          :java {:result java-result#
                                 :expr '~java-expr}
                          :same? (= jiffy-result# java-result#)
                          :values '~values})))))

(s/def ::floor-int int?)

(defspec floor-div-test 10000
  (prop/for-all [values (typed (s/gen (s/tuple ::floor-int (s/and ::floor-int (comp not zero?)))))]
                (let [[x y] (unwrap values)]
                  (same-number? values
                                (sut/floor-div x y)
                                (math/floor-div x y)))))

(defspec floor-mod-test 10000
  (prop/for-all [values (typed (s/gen (s/tuple ::floor-int (s/and ::floor-int (comp not zero?)))))]
                (let [[x y] (unwrap values)]
                  (same-number? values
                                (sut/floor-mod (long x) (long y))
                                (math/floor-mod x y)))))

(defspec add-exact-test 10000
  (prop/for-all [values (typed (s/gen (s/or :ints (s/tuple ::j/int ::j/int)
                                            :longs (s/tuple ::j/long ::j/long))))]
                (let [[x y] (unwrap values)]
                  (same-number? values
                                (sut/add-exact x y)
                                (math/add-exact x y)))))

(defspec subtract-exact-test 10000
  (prop/for-all [values (typed (s/gen (s/tuple int? int?)))]
                (let [[x y] (unwrap values)]
                  (same-number? values
                                (sut/subtract-exact x y)
                                (math/subtract-exact (long x) (long y))))))

(defspec multiply-exact-test 1000
  (prop/for-all [values (typed (s/gen (s/tuple ::j/long
                                               (s/or :int ::j/int :long ::j/long))))]
                (let [[x y] (unwrap values)]
                  (same-number? values
                                (sut/multiply-exact x y)
                                (math/multiply-exact x y)))))
