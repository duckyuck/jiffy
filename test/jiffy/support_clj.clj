(ns jiffy.support-clj
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :as str]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java] :as conversion]
            [jiffy.edn-clj :include-macros true]
            [jiffy.exception :refer [try*]]
            [jiffy.math :as math]
            [jiffy.specs :as j]
            [jiffy.test-specs]))

:jiffy.edn-clj/keep

(defn partition-between
  [pred? coll]
  (->> (map pred? coll (rest coll))
       (reductions not= true)
       (map list coll)
       (partition-by second)
       (map (partial map first))))

(defn kebab-case [s]
  (->> (str s)
       (partition-between #(and (Character/isLowerCase %1)
                                (Character/isUpperCase %2)))
       (map #(apply str %))
       (map str/lower-case)
       (str/join "-")))

(defn camel-case [s]
  (->> (str s)
       (partition-by #(= % \-))
       (map #(apply str %))
       (remove #(str/starts-with? % "-"))
       (map str/capitalize)
       (apply str)))

(defmacro trycatch [& exprs]
  `(try* ~@exprs (catch :default e# e#)))

(defmacro matching-types? [jiffy-result java-result]
  `(not (and (not (ex-data ~jiffy-result))
             (instance? Throwable ~java-result))))

(defmacro same? [jiffy-expr java-expr]
  `(let [jiffy-result# (trycatch ~jiffy-expr)
         java-result# (trycatch ~java-expr)]
     (and (matching-types? jiffy-result# java-result#)
          (conversion/same? jiffy-result# java-result#))))

(defn invoke-java [f args {:keys [static?]}]
  (let [obj (when-not static? (first args))
        args (if static? args (rest args))
        method-name (name f)
        methods (->> (.getMethods (Class/forName (namespace f)))
                     (filter #(-> % .getName (= method-name)))
                     (filter #(-> % .getParameterCount (= (count args)))))]
    (when-not (seq methods)
      (throw (ex-info (str "Unable to find Java method " method-name " on object " obj)
                      {:method-name method-name :obj obj})))
    (let [results (for [method methods]
                    (try
                      [::result (.invoke method obj (into-array Object args))]
                      (catch java.lang.reflect.InvocationTargetException e
                        (if (and (.getMessage e) (str/starts-with? (.getMessage e) "java.lang.ClassCastException"))
                          [::exception (or (.getCause e) e)]
                          [::result (or (.getCause e) e)]))
                      (catch java.lang.IllegalArgumentException e
                        (if (or (and (.getMessage e) (or (str/starts-with? (.getMessage e) "java.lang.ClassCastException")
                                                         (str/starts-with? (.getMessage e) "argument type mismatch")))
                                (nil? (.getMessage e)))
                          [::exception (or (.getCause e) e)]
                          [::result (or (.getCause e) e)]))
                      (catch IllegalAccessException e
                        [::exception (or (.getCause e) e)])
                      (catch Throwable e
                        [::result e])))]
      (if-let [result (->> (filter #(-> % first (= ::result)) results)
                           (sort-by #(or (nil? (second %))
                                         (not (instance? Throwable %))))
                           first)]
        (second result)
        (throw (ex-info (str "Unable to invoke java method " method-name)
                        {:f f
                         :obj obj
                         :methods methods
                         :args args
                         :results results}))))))

(defn gen-test-name [f]
  (symbol (str (str/replace (namespace f) #"\." "-")
               "--"
               (name f))))

(defn get-defs-spec [f]
  (-> f
      s/get-spec
      :args))

(defn get-legacy-spec [f]
  (keyword (str (namespace f))
           (str (kebab-case (name f)) "-args")))

(defn get-spec [f]
  (or (get-defs-spec f)
      (get-legacy-spec f)))

(defn invoke-jiffy [f args]
  (apply f args))

(defmacro store-results [jiffy-fn args jiffy-expr]
  `(spit "dev-resources/regression-corpus.edn"
         (str
          (pr-str {:fn '~jiffy-fn
                   :args ~args
                   :result (let [res# (trycatch ~jiffy-expr)]
                             (cond
                               (ex-data res#)
                               (let [ex# (ex-data res#)]
                                 {:jiffy.exception/kind (:jiffy.exception/kind ex#)
                                  :message (.getMessage res#)})

                               (seq? res#)
                               (take conversion/max-coll-result-size res#)

                               :else
                               res#))})
          "\n")
         :append true))

(defn precision-exception? [jiffy-result]
  (or (some-> jiffy-result ex-data :jiffy.exception/kind (= :jiffy.precision/PrecisionException))
      (some-> jiffy-result ex-cause precision-exception?)))

(defmacro gen-prop [jiffy-fn java-fn spec & [{:keys [static?]}]]
  `(prop/for-all
    [args# (s/gen ~spec)]
    (let [jiffy-result# (trycatch (invoke-jiffy ~jiffy-fn args#))
          java-result# (trycatch (invoke-java ~java-fn
                                              (map conversion/jiffy->java args#)
                                              {:static? ~static?}))]
      (let [is-same?# (and (matching-types? jiffy-result# java-result#)
                           (conversion/same? jiffy-result# java-result#))
            ok?# (or is-same?#
                     (precision-exception? jiffy-result#))]
        (when ok?#
          (store-results ~jiffy-fn args# jiffy-result#))
        ok?#))))

(def default-num-tests 1000)

(def ns->class-anomalies
  {"jiffy.protocols.time-comparable" "java.lang.Comparable"
   "jiffy.protocols.string" "java.lang.Object"})

(defn jiffy-ns->java-class [jiffy-ns-str]
  (or (ns->class-anomalies jiffy-ns-str)
      (let [idx (inc (str/last-index-of jiffy-ns-str "."))]
        (str (-> jiffy-ns-str
                 (subs 0 idx)
                 (str/replace #"jiffy.protocols" "java.time")
                 (str/replace #"jiffy" "java.time"))
             (-> jiffy-ns-str
                 (subs idx (count jiffy-ns-str))
                 camel-case)))))

(defn jiffy-fn->java-method [s]
  (let [[first-char & rest] (camel-case s)]
    (str (str/lower-case (str first-char))
         (apply str rest))))

(defn symbol-ns [x]
  (symbol (or (namespace x)
              (name x))))

(defn jiffy-fn-sym->java-fn-sym
  ([jiffy-fn]
   (symbol (jiffy-ns->java-class (namespace jiffy-fn))
           (jiffy-fn->java-method (name jiffy-fn))))
  ([impl-ns proto-fn]
   (symbol (jiffy-ns->java-class (str impl-ns))
           (jiffy-fn->java-method (name proto-fn)))))

(defn resolve-impl-fn [impl-fn proto-fn]
  (if (namespace impl-fn)
    impl-fn
    (symbol (str impl-fn)
            (name proto-fn))))

(defmacro test-proto-fn [impl-ns proto-fn & [num-tests]]
  `(do
     (require 'jiffy.conversion)
     (require 'jiffy.test-specs)
     (require '~(symbol-ns proto-fn))
     (require '~(symbol-ns impl-ns))
     (defspec ~(gen-test-name proto-fn) {:num-tests ~(or num-tests default-num-tests)}
       (gen-prop ~proto-fn
                 '~(jiffy-fn-sym->java-fn-sym (symbol-ns impl-ns) proto-fn)
                 (get-spec '~(resolve-impl-fn impl-ns proto-fn))
                 {:static? false}))))

(defmacro test-static-fn [jiffy-fn & [num-tests]]
  `(do
     (require 'jiffy.conversion)
     (require 'jiffy.test-specs)
     (require '~(symbol (namespace jiffy-fn)))
     (defspec ~(gen-test-name jiffy-fn) ~(or num-tests default-num-tests)
       (gen-prop ~jiffy-fn
                 '~(jiffy-fn-sym->java-fn-sym jiffy-fn)
                 (get-spec '~jiffy-fn)
                 {:static? true}))))

(defmacro test-fn! [jiffy-fn java-fn args-samples & [{:keys [static?]}]]
  `(do
     (let [results#
           (for [args# ~args-samples]
             (let [jiffy-result# (trycatch (invoke-jiffy ~jiffy-fn args#))
                   java-args# (trycatch (mapv jiffy->java args#))
                   result# {:failed/jiffy-args args#
                            :failed/jiffy-result jiffy-result#
                            :failed/java-args java-args#}
                   java-result# (trycatch (invoke-java ~java-fn java-args# {:static? ~static?}))]
               (try
                 (when-not (or (precision-exception? jiffy-result#)
                               (and (matching-types? jiffy-result# java-result#)
                                    (or (conversion/same? jiffy-result# java-result#)
                                        (conversion/same? jiffy-result# java-result#))))
                   (assoc result# :failed/java-result java-result#))
                 (catch Exception e#
                   (assoc result# :failed/java-result e#)))))]
       (or (first (remove nil? results#))
           [(count results#) :success]))))

(def default-num-interactive-tests 1000)

(defmacro test-proto-fn! [impl-ns proto-fn & [num-tests]]
  `(do
     (require 'jiffy.conversion)
     (require 'jiffy.test-specs)
     (require '~(symbol-ns proto-fn))
     (require '~(symbol-ns impl-ns))
     (test-fn! ~proto-fn
               '~(jiffy-fn-sym->java-fn-sym (symbol-ns impl-ns) proto-fn)
               (gen/sample (s/gen (get-spec '~(resolve-impl-fn impl-ns proto-fn)))
                           ~(or num-tests default-num-interactive-tests))
               {:static? false})))

(defmacro test-static-fn! [jiffy-fn & [num-tests]]
  `(do
     (require 'jiffy.conversion)
     (require 'jiffy.test-specs)
     (require '~(symbol (namespace jiffy-fn)))
     (test-fn! ~jiffy-fn
               '~(jiffy-fn-sym->java-fn-sym jiffy-fn)
               (gen/sample (s/gen (get-spec '~jiffy-fn))
                           ~(or num-tests default-num-interactive-tests))
               {:static? true})))
