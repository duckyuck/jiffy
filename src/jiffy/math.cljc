(ns jiffy.math
  (:refer-clojure :exclude [abs parse-long])
  (:require [#?(:clj jiffy.math-clj :cljs jiffy.math-cljs) :as impl]))

(def long-max-value 9223372036854775807)
(def long-min-value -9223372036854775808)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(def abs impl/abs)
(def add-exact impl/add-exact)
(def subtract-exact impl/subtract-exact)
(def to-int-exact impl/to-int-exact)
(def multiply-exact impl/multiply-exact)
(def floor-div impl/floor-div)
(def floor-mod impl/floor-mod)
(def parse-int impl/parse-int)
(def parse-long impl/parse-long)
