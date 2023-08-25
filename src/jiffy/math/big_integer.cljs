(ns jiffy.math.big-integer
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException] :refer-macros [try*]]))

(defn value-of [val] val)

(defn divide-and-reminder [x y] (wip ::divide-and-reminder))

(defn bit-length [x]
  ;; (wip ::bit-length)
  ;; TODO fix me
  32)

(defn long-value [x] (wip ::long-value))

(defn int-value [x] (wip ::int-value))
