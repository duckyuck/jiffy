(ns jiffy.math
  (:refer-clojure :exclude [abs parse-long])
  (:require [#?(:clj jiffy.math-clj :cljs jiffy.math-cljs) :as impl]
            [jiffy.exception :refer [ex]]
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

(defn subtract-exact [x y]
  (assert-precise-args! 'subtract-exact x y)
  (let [r (impl/subtract-exact x y)]
    (assert-precise-result! 'subtract-exact r x y)
    r))

(defn to-int-exact [x]
  (assert-precise-args! 'to-int-exact x)
  (let [r (impl/to-int-exact x)]
    (assert-precise-result! 'subtract-exact r x)
    r))

(defn multiply-exact [x y]
  (assert-precise-args! 'multiply-exact x y)
  (let [r (impl/multiply-exact x y)]
    (assert-precise-result! 'multiply-exact r x y)
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
    (assert-precise-result! 'parse-int r s)
    r))

(defn parse-long [s]
  (when-let [r (impl/parse-long s)]
    (assert-precise-result! 'parse-long r s)
    r))
