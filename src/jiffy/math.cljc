(ns jiffy.math
  (:require [jiffy.exception :refer [JavaArithmeticException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]))

;; TODO: find proper value for min and max

(def long-max-value 9223372036854775807)
(def long-min-value -9223372036854775808)
(def integer-min-value -0x7fffffff)
(def integer-max-value 0x7fffffff)

;; TODO: decide whether to deviate from java.time and use arbitrary precision
;; math operations instead (clojure.core's +' and *')

(defn add-exact [x y]
  (let [r (try* (+ x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    ;; TODO: find out if this is nessecary on cljs.
    ;; This case seems to be handled by Clojure itself (see try* above)
    (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

(defn subtract-exact [x y]
  (let [r (try* (- x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    ;; TODO: find out if this is nessecary on cljs.
    ;; This case seems to be handled by Clojure itself (see try* above)
    (when (neg? (bit-and (bit-xor x y) (bit-xor x r)))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

(defn abs [x]
  (Math/abs x))

(defn multiply-exact [x y]
  (let [r (try* (* x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))
        ax (abs x)
        ay (abs y)]
    ;; TODO: find out if this is nessecary on cljs.
    ;; This case seems to be handled by Clojure itself (see try* above)
    (when (and (not (zero? (unsigned-bit-shift-right (bit-or ax ay) 31)))
               (or (and (not (zero? y))
                        (not (= x (/ r y))))
                   (and (= x long-max-value)
                        (= y -1))))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

(defn floor-div [x y]
  (let [r (long (/ x y))]
    (cond-> r
      (and (neg? (bit-xor x y))
           (not= (* r y) x))
      dec)))

(defn floor-mod [x y]
  (- x (* (floor-div x y) y)))
