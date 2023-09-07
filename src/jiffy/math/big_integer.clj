(ns jiffy.math.big-integer
  (:require [clojure.math :as math]
            [com.gfredericks.exact :as e]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException try*]])
  (:import java.math.BigDecimal
           java.math.BigInteger))

(defn value-of [val]
  (BigInteger/valueOf val))

(defn divide-and-reminder [this val]
  (.divideAndRemainder this val))

(defn bit-length [this]
  (.bitLength this))

(defn long-value [this]
  (.longValue this))

(defn int-value [this]
  (.intValue this))
