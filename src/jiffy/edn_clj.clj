(ns jiffy.edn-clj
  (:refer-clojure :exclude [range])
  (:require [jiffy.edn :as edn])
  (:import [java.io Writer]))

(defmacro defconverter! [data]
  (let [{:keys [tag record write-fn read-fn]} data]
    `(do
       (defmethod print-method ~record [x# ^Writer w#]
         (.write w# ^String (edn/to-string ~(name tag) ~write-fn x#)))
       (defmethod print-dup ~record [x# ^Writer w#]
         (.write w# ^String (edn/to-string ~(name tag) ~write-fn x#)))
       (defn ~(symbol (name tag)) [x#] (list '~read-fn x#)))))

(defmacro tags [] `~edn/tags)

(defmacro defconverters! []
  `(do
     ~@(for [data# (tags)]
         `(defconverter! ~data#))))

(defconverters!)
