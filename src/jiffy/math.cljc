(ns jiffy.math
  (:refer-clojure :exclude [abs parse-long])
  (:require [#?(:clj jiffy.math-clj :cljs jiffy.math-cljs) :as impl]
            [jiffy.exception :refer [ex JavaArithmeticException]]
            [jiffy.precision :as precision]))

(def long-max-value precision/max-safe-integer)
(def long-min-value precision/min-safe-integer)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(defn assert-precise-args! [description & arguments]
  (when-not (every? precision/precise? arguments)
    (throw (ex precision/PrecisionException
               (str description " failed. Arguments cannot be precisly represented")
               {:arguments (vec arguments)
                :precise (mapv precision/precise? arguments)}))))

(defn assert-precise-result! [description result & arguments]
  (when-not (precision/precise? result)
    (throw (ex precision/PrecisionException
               (str description " failed. Result cannot be precisly represented")
               {:result result
                :precise (precision/precise? result)
                :arguments (vec arguments)}))))

(defn assert-int-args! [description & arguments]
  (when-not (every? precision/integer? arguments)
    (throw (ex JavaArithmeticException
               (str "integer overflow. " description " failed. Arguments cannot be precisly represented")
               {:arguments (vec arguments)
                :integer? (mapv precision/integer? arguments)}))))

(defn assert-int-result! [description result & arguments]
  (when-not (precision/integer? result)
    (throw (ex JavaArithmeticException
               (str "integer overflow. "description " failed. Result cannot be precisly represented")
               {:result result
                :integer? (precision/integer? result)
                :arguments (vec arguments)}))))

(defn assert-int! [description ])

(defn abs [x]
  (assert-precise-args! 'abs x)
  (let [r (impl/abs x)]
    (assert-precise-result! 'abs r x)
    r))

(defn add-exact [x y]
  (assert-precise-args! 'add-exact x y)
  (let [r (impl/add-exact x y)]
    (assert-precise-result! 'add-exact r x y)
    r))

(defn add-exact-int [x y]
  (assert-int-args! 'add-exact-in x y)
  (let [r (impl/add-exact x y)]
    (assert-int-result! 'add-exact r x y)
    r))

(defn subtract-exact [x y]
  (assert-precise-args! 'subtract-exact x y)
  (let [r (impl/subtract-exact x y)]
    (assert-precise-result! 'subtract-exact r x y)
    r))

(defn subtract-exact-int [x y]
  (assert-int-args! 'subtract-exact x y)
  (let [r (impl/subtract-exact x y)]
    (assert-int-result! 'subtract-exact-int r x y)
    r))

(defn to-int-exact [x]
  (assert-int-args! 'to-int-exact x)
  (let [r (impl/to-int-exact x)]
    (assert-int-result! 'subtract-exact r x)
    r))

(defn multiply-exact [x y]
  (assert-precise-args! 'multiply-exact x y)
  (let [r (impl/multiply-exact x y)]
    (assert-precise-result! 'multiply-exact r x y)
    r))

(defn multiply-exact-int [x y]
  (assert-int-args! 'multiply-exact-int x y)
  (let [r (impl/multiply-exact x y)]
    (assert-int-result! 'multiply-exact r x y)
    r))

(defn floor-div [x y]
  (assert-precise-args! 'floor-div x y)
  (let [r (impl/floor-div x y)]
    (assert-precise-result! 'floor-div r x y)
    r))

(defn floor-mod [x y]
  (assert-precise-args! 'floor-mod x y)
  (let [r (impl/floor-mod x y)]
    (assert-precise-result! 'floor-mod r x y)
    r))

(defn parse-int [s]
  (when-let [r (impl/parse-int s)]
    (assert-int-result! 'parse-int r s)
    r))

(defn parse-long [s]
  (when-let [r (impl/parse-long s)]
    (assert-precise-result! 'parse-long r s)
    r))

(defn compare [x y]
  (assert-precise-args! 'compare x y)
  (cond
    (= x y) 0
    (< x y) -1
    :else 1))
