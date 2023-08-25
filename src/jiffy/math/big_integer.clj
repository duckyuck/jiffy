(ns jiffy.math.big-integer
  (:require [clojure.math :as math]
            [com.gfredericks.exact :as e]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException try*]]))

(defn value-of [val]
  (e/integer->native val))

(defn divide-and-reminder [this val]
  [(let [r (e// (bigint this) val)]
     (if (e/ratio? r)
       (bigint r)
       r))
   (e/rem this val)])

(defn bit-length [this] (.bitLength this))

(defn long-value [this]
  (e/integer->native this))

(defn int-value [this]
  (e/integer->native this))


;; Implementation based off `java.math.BigInteger`

;; (defn value-of [val] (BigInteger/valueOf val))

;; (defn divide-and-reminder [x y]
;;   (try*
;;    (.divideAndRemainder (biginteger x) (biginteger y))
;;    (catch :default e
;;      (throw (ex JavaArithmeticException "long overflow" {:x x :y y} e)))))

;; (defn bit-length [x] (.bitLength x))

;; (defn long-value [x] (.longValue x))

;; (defn int-value [x] (.intValue x))
