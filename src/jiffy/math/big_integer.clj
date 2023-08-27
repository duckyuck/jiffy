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
