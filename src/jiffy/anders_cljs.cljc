(ns jiffy.anders-cljs
  (:require [jiffy.anders :as anders]))

(defmacro cool-macro [x]
  `(defn ~x []
     (anders/cool (quote ~x))))

(defmacro def-42 [x]
  `(def ~x 42))

#?(:clj (def-42 the-answer))
