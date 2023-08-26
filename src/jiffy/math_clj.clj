(ns jiffy.math-clj
  (:refer-clojure :exclude [abs parse-long])
  (:require [clojure.math :as math]
            [jiffy.exception :refer [ex JavaArithmeticException try*]]))

(def long-max-value 9223372036854775807)
(def long-min-value -9223372036854775808)
(def integer-min-value -2147483648)
(def integer-max-value 2147483647)

(defn bit-length [n]
  (if (zero? n)
    1
    (inc (int (/ (Math/log (Math/abs n))
                 (Math/log 2))))))

(defmulti add-exact* (fn [x y] [(type x) (type y)]))
(defn add-exact [x y]
  (add-exact* x y))

(defmethod add-exact* [java.lang.Integer java.lang.Integer]
  [x y]
  (let [r (try* (+ x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "integer overflow" {:x x :y y} e))))]
    (when (>= (bit-length r) 32)
      (throw (ex JavaArithmeticException "integer overflow" {:x x :y y})))
    r))

(defmethod add-exact* [java.lang.Long java.lang.Long]
  [x y]
  (let [r (try* (math/add-exact x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
      (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
    r))

;; (defn add-exact [x y]
;;   (let [r (try* (math/add-exact x y)
;;                 (catch :default e
;;                   (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
;;     (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
;;       (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
;;     r))

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

(defmulti multiply-exact* (fn [x y] [(type x) (type y)]))
(defn multiply-exact [x y]
  (multiply-exact* x y))

(defmethod multiply-exact* [java.lang.Integer java.lang.Integer]
   [x y]
   (let [r (* (long x) (long y))]
     (if (not= r (try*
                  (int r)
                  (catch :default e
                    (throw (ex JavaArithmeticException "integer overflow")))))
       (throw (ex JavaArithmeticException "integer overflow"))
       (int r))))

(defmethod multiply-exact* [java.lang.Long java.lang.Long]
   [x y]
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

(defmethod multiply-exact* [java.lang.Long java.lang.Integer]
   [x y]
   (multiply-exact* x (long y)))

;; (defn multiply-exact [x y]
;;   (let [r (try* (math/multiply-exact x y)
;;                 (catch :default e
;;                   (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))
;;         ax (abs x)
;;         ay (abs y)]
;;     (when (and (not (zero? (unsigned-bit-shift-right (bit-or ax ay) 31)))
;;                (or (and (not (zero? y))
;;                         (not (= x (/ r y))))
;;                    (and (= x long-max-value)
;;                         (= y -1))))
;;       (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
;;     r))

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
    (Integer/parseInt s 10)))

(defn parse-long [s]
  (when (string? s)
    (Long/parseLong s 10)))
