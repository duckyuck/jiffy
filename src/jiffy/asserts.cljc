(ns jiffy.asserts
  (:require [jiffy.exception :refer [ex JavaNullPointerException]]))

(defn require-non-nil [value name]
  (when-not value
    (throw (ex JavaNullPointerException
               (str name " must be given")))))

(defn require-non-nil-else [& args]
  (if-let [val (first (drop-while nil? args))]
    val
    (throw (ex JavaNullPointerException "Expected at least one non-nil"))))
