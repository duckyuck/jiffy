(ns jiffy.dev.defs
  ;; (:require #?(:clj [clojure.spec.alpha :as s])
  ;;           #?(:clj [clojure.spec.gen.alpha :as gen])
  ;;           #?(:cljs [cljs.spec.alpha :as s])
  ;;           #?(:cljs [cljs.spec.gen.alpha :as gen])
  ;;           #?(:cljs [cljs.reader])
  ;;           [orchestra.core :refer [defn-spec]]
  ;;           ;; [orchestra.spec.test :refer [instrument unstrument]]
  ;;           [clojure.string :as str])
  )

;; (defn cljs-env? [env]
;;   (boolean (:ns env)))

;; (defmacro def-record [record spec-name fields+specs & args]
;;   (let [fields (partition 2 fields+specs)
;;         field-names (map first fields)
;;         fields-spec (map second fields)
;;         constructor-args (vec (repeatedly (count fields) gensym))
;;         cljs? (cljs-env? &env)
;;         tag (str (clojure.string/replace (str (ns-name *ns*)) #"-" "_")
;;                  "." record)
;;         tag-reader-fn (symbol (str 'map-> record))]
;;     `(do
;;        (defrecord ~record ~(vec field-names))
;;        (cljs.reader/register-tag-parser! (symbol ~tag) ~tag-reader-fn)
;;        (letfn [(constructor# ~constructor-args
;;                  (new ~record ~@constructor-args))]
;;          (if (cljs-env? &env))
;;          (s/def ~spec-name any?
;;            ;; (s/with-gen #(instance? ~record %)
;;            ;;   (fn [] (->> (s/tuple ~@fields-spec)
;;            ;;               s/gen
;;            ;;               (gen/such-that (fn [~(vec field-names)]
;;            ;;                                (if (seq ~args)
;;            ;;                                  (do
;;            ;;                                    ~@args)
;;            ;;                                  true)))
;;            ;;               (gen/fmap #(apply constructor# %)))))
;;            ))
;;        ~record)))

;; (def-record MyRecord ::my-record [a number? b keyword?]
;;   (> a 0))


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

;; (defmacro def-method [& args]
;;   `(defn-spec ~@args))

;; (defmacro def-constructor [& args]
;;   `(defn-spec ~@args))

;; (comment



;;   (def-record MyRecord ::my-record [a number? b keyword?]
;;     (> a 0))

;;   (MyRecord. 1 :foo)

;;   ;; (gen/sample (s/gen ::my-record))



;;   )
