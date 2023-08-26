(ns jiffy.edn-clj
  (:require [jiffy.edn :as edn])
  (:import [java.io Writer]))

(defmacro defconverter! [tag data]
  (let [{:keys [record write-fn read-fn]} data]
    `(do
       (defmethod print-method ~record [x# ^Writer w#]
         (.write w# ^String (edn/to-string ~(str tag) ~write-fn x#)))
       (defmethod print-dup ~record [x# ^Writer w#]
         (.write w# ^String (edn/to-string ~(str tag) ~write-fn x#)))
       (defn ~tag [x#] (list '~read-fn x#)))))

(defmacro tags [] `~edn/tags)

(defmacro defconverters! []
  `(do
     ~@(for [[tag# data#] (tags)]
         `(defconverter! ~(symbol tag#) ~data#))))

(defconverters!)


(comment

  ;; (jiffy.duration-impl/of-seconds 1)

  ;; #jiffy/duration{:seconds 31556952, :nanos 0}

  ;; (-> #jiffy/instant-2{:seconds 0, :nanos 0}
  ;;     (jiffy.instant-2/plus-seconds 10)
  ;;     (jiffy.instant-2/plus-nanos 10))

  )
