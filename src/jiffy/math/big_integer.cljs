(ns jiffy.math.big-integer
  (:require [clojure.math :as math]
            [com.gfredericks.exact :as e]
            [decimal.core :as dc]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException] :refer-macros [try*]]))

(defn value-of [val]
  (e/native->integer val))

(defn long-value [this]
  (.toNumber this))

(defn int-value [this]
  (e/integer->native this))

(defn bit-length [n]
  (if (or (e/= n e/ONE)
          (e/zero? n))
    1
    (-> (Math/log (e/abs n))
        (/ (Math/log 2))
        Math/floor
        inc)))

(defn divide-and-reminder [this val]
  [(let [r (e// this val)]
     (if (e/ratio? r)
       (-> (dc/div' (e/integer->string (e/numerator r))
                    (e/integer->string (e/denominator r)))
           dc/to-string
           e/string->integer)
       r))
   (e/rem this val)])
