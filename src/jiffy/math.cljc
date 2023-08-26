(ns jiffy.math
  (:refer-clojure :exclude [abs parse-long])
  (:require [#?(:clj jiffy.math-clj :cljs jiffy.math-cljs) :as impl]
            [jiffy.precision :as precision :refer [assert-precise!]]))

(def long-max-value precision/max-safe-integer)
(def long-min-value precision/min-safe-integer)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(defn abs [x]
  (assert-precise! x)
  (impl/abs x))

(defn add-exact [x y]
  (assert-precise! x)
  (assert-precise! y)
  (let [r (impl/add-exact x y)]
    (assert-precise! r)
    r))

(defn subtract-exact [x y]
  (assert-precise! x)
  (assert-precise! y)
  (let [r (impl/subtract-exact x y)]
    (assert-precise! r)
    r))

(defn to-int-exact [x]
  (assert-precise! x)
  (let [r (impl/to-int-exact x)]
    (assert-precise! r)
    r))

(defn multiply-exact [x y]
  (assert-precise! x)
  (assert-precise! y)
  (let [r (impl/multiply-exact x y)]
    (assert-precise! r)
    r))

(def floor-div impl/floor-div)
(def floor-mod impl/floor-mod)
(def parse-int impl/parse-int)
(def parse-long impl/parse-long)
