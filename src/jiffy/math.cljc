(ns jiffy.math
  (:require [jiffy.exception :refer [JavaArithmeticException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            #?(:clj [jiffy.math-macros :as mm]))
  #?(:cljs (:require-macros [jiffy.math-macros :as mm])))

(def long-max-value (mm/long-max-value))
(def long-min-value (mm/long-min-value))
(def integer-min-value (mm/integer-min-value))
(def integer-max-value (mm/integer-max-value))

(defmulti add-exact* (fn [x y] [(type x) (type y)]))
(defn add-exact [x y] (add-exact* x y))

#?(:cljs
   (defmethod add-exact* :default [x y] (+ x y)))

#?(:clj
   (defmethod add-exact* [java.lang.Integer java.lang.Integer]
     [x y]
     (let [r (try* (int (+' x y))
                   (catch :default e
                     (throw (ex JavaArithmeticException "integer overflow" {:x x :y y} e))))]
       ;; TODO: find out if this is nessecary on cljs.
       ;; This case seems to be handled by Clojure itself (see try* above)
       (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
         (throw (ex JavaArithmeticException "integer overflow" {:x x :y y})))
       r)))

#?(:clj
   (defmethod add-exact* [java.lang.Long java.lang.Long]
     [x y]
     (let [r (try* (long (+' x y))
                   (catch :default e
                     (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
       ;; TODO: find out if this is nessecary on cljs.
       ;; This case seems to be handled by Clojure itself (see try* above)
       (when (neg? (bit-and (bit-xor x r) (bit-xor y r)))
         (throw (ex JavaArithmeticException "long overflow" {:x x :y y})))
       r)))

;; #?(:clj
;;    (defmethod add-exact* [java.lang.Integer java.lang.Long]
;;      [x y]
;;      (add-exact*
;;       x
;;       (try* (int y) (catch :default e (throw (ex JavaArithmeticException "integer overflow" {:x x :y y} e)))))))

;; #?(:clj
;;    (defmethod add-exact* [java.lang.Long java.lang.Integer]
;;      [x y]
;;      (add-exact*
;;       (try* (int x) (catch :default e (throw (ex JavaArithmeticException "integer overflow" {:x x :y y} e))))
;;       y)))

(defn subtract-exact [x y]
  (let [r (try* (- x y)
                (catch :default e
                  (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e))))]
    ;; TODO: find out if this is nessecary on cljs.
    ;; This case seems to be handled by Clojure itself (see try* above)
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
(defn multiply-exact [x y] (multiply-exact* x y))

#?(:cljs
   (defmethod multiply-exact* :default [x y] (* x y)))

#?(:clj
   (defmethod multiply-exact* [java.lang.Integer java.lang.Integer]
     [x y]
     (let [r (* (long x) (long y))]
       (if (not= r (try*
                    (int r)
                    (catch :default e
                      (throw (ex JavaArithmeticException "integer overflow")))))
         (throw (ex JavaArithmeticException "integer overflow"))
         (int r)))))

#?(:clj
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
       r)))

#?(:clj
   (defmethod multiply-exact* [java.lang.Long java.lang.Integer]
     [x y]
     (multiply-exact* x (long y))))

(defn floor-div [x y]
  (let [r (long (/ x y))]
    (cond-> r
      (and (neg? (bit-xor x y))
           (not= (* r y) x))
      dec)))

(defn floor-mod [x y]
  (- x (* (floor-div x y) y)))

(defn parse-int [s]
  (when (string? s)
    #?(:clj (Integer/parseInt s 10)
       :cljs (js/parseInt s 10))))

(defn parse-long [s]
  (when (string? s)
    #?(:clj (Long/parseLong s 10)
       :cljs (js/parseLong s 10))))
