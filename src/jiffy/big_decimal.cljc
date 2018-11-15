(ns jiffy.big-decimal
  (:refer-clojure :exclude [divide])
  (:require [jiffy.dev.wip :refer [wip]]))

(defn add [x y] (wip ::add--not-implemented))

(defn value-of [unscaled-val & [scale]] (wip ::value-of--not-implemented))

(defn multiply [this big-decimal] (wip ::multiply--not-implemented))

(defn divide [this other rounding-mode] (wip ::divide--not-implemented))

(defn divide-to-integral-value [this divisor] (wip ::divide-to-integral-value--not-implemented))

(defn long-value-exact [this] (wip ::long-value-exact--not-implemented))
