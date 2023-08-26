(ns jiffy.math.support
  (:require [clojure.spec.gen.alpha :as gen]
            [clojure.walk :as walk]
            [jiffy.conversion :as conversion]
            [jiffy.parity-tests.support :as support]))

(defn wrap [x]
  (walk/postwalk
   (fn [v]
     (if (number? v)
       [:typed (type v) v]
       v))
   x))

(defn typed [gen]
  (gen/fmap wrap gen))

(defn unwrap [x]
  (walk/postwalk
   (fn [v]
     (if (and (vector? v)
              (= :typed (first v)))
       (nth v 2)
       v))
   x))

(defmacro compare-math [values jiffy-expr java-expr]
  `(let [jiffy-result# (support/trycatch ~jiffy-expr)
         java-result# (support/trycatch ~java-expr)]
     (or (support/ignore-result? jiffy-result#)
         (and (support/matching-types? jiffy-result# java-result#)
              (conversion/same? jiffy-result# java-result#))
         (throw (ex-info "Not same"
                         {:jiffy {:result jiffy-result#
                                  :expr '~jiffy-expr}
                          :java {:result java-result#
                                 :expr '~java-expr}
                          :same? (= jiffy-result# java-result#)
                          :values ~values})))))
