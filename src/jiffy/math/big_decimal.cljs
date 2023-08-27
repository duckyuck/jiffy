(ns jiffy.math.big-decimal
  (:refer-clojure :exclude [divide])
  (:require [com.gfredericks.exact :as e]
            [decimal.core :as dc :include-macros true]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [ex JavaArithmeticException]]
            [jiffy.precision :as precision]))

(dc/config!
 {:rounding :round-down
  :precision 1000
  :to-exp-neg -100
  :to-exp-pos 100})

(defn add [x y]
  (dc/plus x y))

(defn multiply [this big-decimal]
  (dc/mul this big-decimal))

(defn to-rounding [rounding]
  (or ({:rounding.mode/down :round-down} rounding)
      (throw (ex ::illegal-argument "Unsupported rounding mode" {:rounding-mode rounding}))))

(defn divide [this other rounding-mode]
 (dc/with-config {:rounding (to-rounding rounding-mode)}
   (dc/div this other)))

(defn divide-to-integral-value [this divisor]
  (let [r (dc/div' this divisor)]
    (when-not (dc/finite? r)
      (throw (ex JavaArithmeticException "divide-to-integral-value failed"
                 {:this this :divisor divisor} e)))
    r))

(defn long-value-exact [this]
  (let [r (dc/to-number (dc/to-nearest this 1))]
    (when-not (precision/precise? r)
      (throw (ex JavaArithmeticException
                 "long-value-exact failed. Result cannot be precisly represented"
                 {:result r
                  :precise (precision/precise? r)
                  :arguments [this]})))
    r))

(defn value-of
  ([unscaled-val scale]
   (dc/with-config {:precision 9 :rounding :round-down}
     (dc/div unscaled-val (dc/pow 10 scale))))
  ([val]
   (dc/*decimal*. val)))

(defn move-point-right [this points]
  (dc/mul this (dc/pow 10 points)))

(defn to-big-integer-exact [this]
  (-> this
      (dc/to-nearest 1)
      dc/to-string
      e/string->integer))

(defn to-decimal-places [this places]
  (dc/to-decimal-places this places))
