(ns jiffy.math-cljs
  (:refer-clojure :exclude [abs parse-long])
  (:require [clojure.math :as math]
            [jiffy.exception :refer [ex JavaArithmeticException] :refer-macros [try*]]
            [jiffy.precision :as precision]))

;; public
;; (def long-max-value (mm/long-max-value))
;; (def long-min-value (mm/long-min-value))
;; (def integer-min-value (mm/integer-min-value))
;; (def integer-max-value (mm/integer-max-value))

(def long-max-value precision/max-safe-integer)
(def long-min-value precision/min-safe-integer)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(defn add-exact [x y]
  (try*
   (math/add-exact x y)
   (catch :default e
     (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e)))))

(defn subtract-exact [x y]
  (let [r (try*
           (math/subtract-exact x y)
           (catch :default e
             (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    r))

(defn to-int-exact [x]
  (let [int-x (try*
               (int x)
               (catch :default e
                 (throw (ex JavaArithmeticException "integer overflow"))))]
    (if (= int-x x)
      int-x
      (throw (ex JavaArithmeticException "integer overflow")))))

(defn abs [x]
  (Math/abs x))

;; (defn multiply-exact [x y]
;;   (* x y))

(defn bit-length [n]
  (if (zero? n)
    1
    (inc (Math/floor (/ (Math/log (Math/abs n)) (Math/log 2))))))

(defn multiply-exact [x y]
  (try*
   (math/multiply-exact x y)
   (catch :default e
     (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e)))))

(defn floor-div [x y]
  (math/floor-div x y))

(defn floor-mod [x y]
  (math/floor-mod x y))

(defn parse-int [s]
  (when (string? s)
    (js/parseInt s 10)))

(defn parse-long [s]
  (when (string? s)
    (js/parseLong s 10)))
