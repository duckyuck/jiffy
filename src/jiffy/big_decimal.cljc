(ns jiffy.big-decimal
  (:refer-clojure :exclude [divide])
  (:require [jiffy.dev.wip :refer [wip]]))

(defn add [x y]
  (+ x y))

(defn multiply [this big-decimal]
  (* this big-decimal))

(defn divide [this other rounding-mode]
  #?(:clj (.divide this other (case rounding-mode
                                :rounding.mode/down java.math.RoundingMode/DOWN))
     :cljs (wip ::divide)))

(defn divide-to-integral-value [this divisor]
  #?(:clj (.divideToIntegralValue this divisor)
     :cljs (wip ::divide-to-integral-value)))

(defn long-value-exact [this]
  #?(:clj (.longValueExact this)
     :cljs (wip ::long-value-exact)))

(defn value-of
  ([unscaled-val scale]
   #?(:clj (BigDecimal/valueOf unscaled-val scale)
      :cljs (wip ::value-of)))
  ([val]
   #?(:clj (BigDecimal/valueOf val)
      :cljs (wip ::value-of))))

(defn move-point-right [this points]
  #?(:clj (.movePointRight this points)
     :cljs (wip ::move-point-right)))

(defn to-big-integer-exact [this]
  #?(:clj (.toBigIntegerExact this)
     :cljs (wip ::to-big-integer-exact)))
