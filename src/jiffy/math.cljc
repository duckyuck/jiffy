(ns jiffy.math
  #?@(:clj
      [(:require [clojure.math :as math]
                 [jiffy.exception :refer [ex JavaArithmeticException try*]])]
      :cljs
      [(:require [clojure.math :as math]
                 [jiffy.exception :refer [ex JavaArithmeticException] :refer-macros [try*]])]))

;; public
;; (def long-max-value (mm/long-max-value))
;; (def long-min-value (mm/long-min-value))
;; (def integer-min-value (mm/integer-min-value))
;; (def integer-max-value (mm/integer-max-value))

(def long-max-value 9223372036854775807)
(def long-min-value -9223372036854775808)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(defn add-exact [x y]
  (let [r (try* (math/add-exact x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

(defn subtract-exact [x y]
  (let [r (try* (math/subtract-exact x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    (when (neg? (bit-and (bit-xor x y) (bit-xor x r)))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
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

(defn multiply-exact [x y]
  (let [r (try* (math/multiply-exact x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))
        ax (abs x)
        ay (abs y)]
    (when (and (not (zero? (unsigned-bit-shift-right (bit-or ax ay) 31)))
               (or (and (not (zero? y))
                        (not (= x (/ r y))))
                   (and (= x long-max-value)
                        (= y -1))))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

(defn floor-div [x y]
  (math/floor-div x y)
  ;; (let [r (long (/ x y))]
  ;;   (cond-> r
  ;;     (and (neg? (bit-xor x y))
  ;;          (not= (* r y) x))
  ;;     dec))
  )


(defn floor-mod [x y]
  (math/floor-mod x y)
  ;; (- x (*' y (math/floor-div x y)))
  )

(defn parse-int [s]
  (when (string? s)
    #?(:clj (Integer/parseInt s 10)
       :cljs (js/parseInt s 10))))

(defn parse-long [s]
  (when (string? s)
    #?(:clj (Long/parseLong s 10)
       :cljs (js/parseLong s 10))))
