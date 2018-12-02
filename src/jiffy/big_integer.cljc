(ns jiffy.big-integer
  (:require [jiffy.dev.wip :refer [wip]]))

(defn value-of [val]
  #?(:clj (BigInteger/valueOf val)
     :cljs val))

(defn divide-and-reminder [this val]
  #?(:clj
     (.divideAndRemainder this val)
     :cljs (wip ::divide-and-reminder)))

(defn bit-length [this]
  #?(:clj (.bitLength this)
     :cljs (wip ::bit-length)))

(defn long-value [this]
  #?(:clj (.longValue this)
     :cljs (wip ::long-value)))

(defn int-value [this]
  #?(:clj (.intValue this)
     :cljs (wip ::int-value)))
