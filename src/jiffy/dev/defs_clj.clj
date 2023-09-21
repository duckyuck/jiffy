(ns jiffy.dev.defs-clj
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.protocols.time-comparable :as time-comparable]
            [orchestra.core :refer [defn-spec]]
            ;; [orchestra.spec.test :refer [instrument unstrument]]
            [clojure.string :as str]))

(defmacro def-record [record spec-name fields+specs & args]
  (let [fields (partition 2 fields+specs)
        field-names (map first fields)
        fields-spec (map second fields)
        constructor-args (vec (repeatedly (count fields) gensym))
        tag (str (clojure.string/replace (str (ns-name *ns*)) #"-" "_")
                 "." record)
        tag-reader-fn (symbol (str 'map-> record))]
    `(do
       (defrecord ~record ~(vec field-names)
         java.lang.Comparable
         (compareTo [this# other#]
           (if (satisfies? time-comparable/ITimeComparable this#)
             (time-comparable/compare-to this# other#)
             (throw (ex-info (str "Object does not implement ITimeComparable: " this#)
                             {:this this# :other other#})))))
       (letfn [(constructor# [~@field-names]
                 ~(if (seq args)
                   `(do ~@args)
                   `(new ~record ~@field-names)))]
         (s/def ~spec-name
           (s/with-gen #(instance? ~record %)
             (fn [] (->> (s/tuple ~@fields-spec)
                         s/gen
                         (gen/fmap #(apply constructor# %)))))))
       ~record)))

;; (defmacro def-record [record spec-name fields+specs & args]
;;   (let [fields (partition 2 fields+specs)
;;         field-names (map first fields)
;;         fields-spec (map second fields)
;;         constructor-args (vec (repeatedly (count fields) gensym))
;;         cljs? (cljs-env? &env)]
;;     `(do
;;        (defrecord ~record ~(vec field-names))
;;        (when ~cljs?
;;          #?(:clj
;;             (letfn [(constructor# ~constructor-args
;;                       (new ~record ~@constructor-args))]
;;               (s/def ~spec-name
;;                 (s/with-gen #(instance? ~record %)
;;                   (fn [] (->> (s/tuple ~@fields-spec)
;;                               s/gen
;;                               (gen/such-that (fn [~(vec field-names)]
;;                                                (if (seq ~args)
;;                                                  (do
;;                                                    ~@args)
;;                                                  true)))
;;                               (gen/fmap #(apply constructor# %)))))))))
;;        ~record)))

(defmacro def-method [& args]
  `(defn-spec ~@args))

(defmacro def-constructor [& args]
  `(defn-spec ~@args))

(comment

  (gen/sample (s/gen integer?))

  (def-record MyRecord ::my-record [a number? b keyword?])

  (extend-type MyRecord
    time-comparable/ITimeComparable
    (compare-to [this other]
      (compare (:a this) (:a other))))

  (= (MyRecord. 1 :foo)
     (MyRecord. 2 :bar))

  [(compare (MyRecord. 1 :foo)
            (MyRecord. 2 :bar))

   (compare (MyRecord. 1 :bar)
            (MyRecord. 1 :foo))

   (compare (MyRecord. 2 :bar)
            (MyRecord. 1 :foo))]



  )
