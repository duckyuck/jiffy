(ns jiffy.math.big-decimal
  (:refer-clojure :exclude [divide])
  (:require [jiffy.dev.wip :refer [wip]]))

(defn add [x y]
  (+ x y))

(defn multiply [this big-decimal]
  (* this big-decimal))

(defn divide [this other rounding-mode]
  (.divide this other (case rounding-mode
                        :rounding.mode/down java.math.RoundingMode/DOWN)))

(defn divide-to-integral-value [this divisor]
  (.divideToIntegralValue this divisor))

(defn long-value-exact [this]
  (.longValueExact this))

(defn value-of
  ([unscaled-val scale]
   (BigDecimal/valueOf unscaled-val scale))
  ([val]
   (BigDecimal/valueOf val)))

(defn move-point-right [this points]
  (.movePointRight this points))

(defn to-big-integer-exact [this]
  (.toBigIntegerExact this))
