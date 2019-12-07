(ns jiffy.anders-test
  (:require #?(:clj [jiffy.anders-clj :as sut]
               :cljs [jiffy.anders-cljs :as sut :include-macros true])))

(defn cool-test []
  (sut/cool-macro anders-the-man))

(comment
  (def x (cool-test))

  (x)

  sut/the-answer
  )
