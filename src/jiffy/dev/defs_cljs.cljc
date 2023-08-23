(ns jiffy.dev.defs-cljs
  (:require ;; [cljs.reader :as reader]
            [cljs.spec.alpha :as s]
            [cljs.spec.gen.alpha :as gen]
            [clojure.string :as str]
            #?(:cljs [orchestra.core :refer-macros [defn-spec]])
            #?(:clj [orchestra.core :refer [defn-spec]])
            ))

(defmacro def-record [record spec-name fields+specs & args]
  (let [fields (partition 2 fields+specs)
        field-names (map first fields)
        fields-spec (map second fields)
        constructor-args (vec (repeatedly (count fields) gensym))]
    `(do
       (defrecord ~record ~(vec field-names))
       ;; (letfn [(constructor# ~constructor-args
       ;;           (new ~record ~@constructor-args))]
       ;;   (s/def ~spec-name
       ;;     (s/with-gen #(instance? ~record %)
       ;;       (fn [] (->> (s/tuple ~@fields-spec)
       ;;                   s/gen
       ;;                   (gen/such-that (fn [~(vec field-names)]
       ;;                                    (if (seq ~args)
       ;;                                      (do
       ;;                                        ~@args)
       ;;                                      true)))
       ;;                   (gen/fmap #(apply constructor# %)))))))
       (letfn [(constructor# ~constructor-args
                 (new ~record ~@constructor-args))]
         (s/def ~spec-name
           (s/with-gen #(instance? ~record %)
             (fn [] (->> (s/tuple ~@fields-spec)
                         s/gen
                         (gen/such-that (fn [~(vec field-names)]
                                          (if (seq ~args)
                                            (do
                                              ~@args)
                                            true)))
                         (gen/fmap #(apply constructor# %)))))))
       ~record)))

(defmacro def-method [& args]
  `(defn-spec ~@args))

(defmacro def-constructor [& args]
  `(defn-spec ~@args))

(comment

  (gen/sample (s/gen integer?))

  (def-record MyRecord ::my-record [a number? b keyword?])

  )
