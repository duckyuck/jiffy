(ns jiffy.asserts
  (:require [jiffy.exception :refer [ex JavaNullPointerException]]))

(defn require-non-nil [value name]
  (when-not value
    (throw (ex JavaNullPointerException
               (str name " must be given")))))
