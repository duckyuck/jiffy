(ns jiffy.math.big-decimal
  (:refer-clojure :exclude [divide])
  (:require [jiffy.dev.wip :refer [wip]]))

(defn add [x y]
  (+ x y))

(defn multiply [this big-decimal]
  (* this big-decimal))

(defn divide [this other rounding-mode]
  (wip ::divide))

(defn divide-to-integral-value [this divisor]
  (wip ::divide-to-integral-value))

(defn long-value-exact [this]
  (wip ::long-value-exact))

(defn value-of
  ([unscaled-val scale]
   (wip ::value-of))
  ([val]
   (wip ::value-of)))

(defn move-point-right [this points]
  (wip ::move-point-right))

(defn to-big-integer-exact [this]
  (wip ::to-big-integer-exact))
