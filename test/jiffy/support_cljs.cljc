(ns jiffy.support-cljs
  (:require [cljs.test :refer [deftest is testing]]
            [clojure.string :as str]
            [jiffy.edn.readers]
            [jiffy.edn.writers]
            [jiffy.exception :refer [ex try*]]
            [jiffy.corpus :as corpus]))

(defn try-call [f args]
  (try*
   (apply f args)
   (catch :default e
     e)))

(defn same? [actual expected]
  (or (= actual expected)
      (let [actual-data (ex-data actual)]
        (and actual-data
             (= (:jiffy.exception/kind actual-data)
                (:jiffy.exception/kind expected))))
      false))

(defn to-data [data]
  (clojure.walk/postwalk
   (fn [x]
     (if (record? x)
       (into {} x)
       x))
   data))

;; See jiffy.conversion/max-coll-result-size
(def max-coll-result-size 15)

(defn limit-actual [actual]
  (if (seq? actual)
    (take max-coll-result-size actual)
    actual))

(defn run-test [f {:keys [args result] :as data}]
  (let [actual (try-call f args)]
    (merge (dissoc data :result)
           {:same? (same? (limit-actual actual) result)
            :same-data? (= (to-data actual) (to-data result))
            :actual actual
            :expected result})))

(defn test-fn! [f & [num-tests]]
  (let [corpus (corpus/corpus-for-fn f)
        num-tests (or num-tests (count corpus))
        failures (->> corpus
                      (take num-tests)
                      (map #(run-test f %))
                      (remove :same?))]
    (if-not (seq failures)
      [num-tests :ok]
      (for [failure (take 3 failures)]
        {:expr `(~'jiffy.support-cljs/same?
                 (~'jiffy.support-cljs/try-call ~(:fn failure) [~@(:args failure)])
                 ~(:expected failure))
         :failure failure}))))

(defn test-proto-fn! [_ & args] (apply test-fn! args))

(defn test-static-fn! [& args] (apply test-fn! args))

(defn symbol-ns [x]
  (symbol (or (namespace x)
              (name x))))

(defn resolve-impl-fn [impl-fn proto-fn]
  (if (namespace impl-fn)
    impl-fn
    (symbol (str impl-fn)
            (name proto-fn))))


(defmacro test-proto-fn [impl-ns proto-fn & _rest]
  `(do
     (require 'jiffy.corpus)
     (require '~(symbol-ns proto-fn))
     (require '~(symbol-ns impl-ns))

     (deftest ~(gensym)
       (let [res# (test-fn! ~proto-fn)]
         (if (:failure (first res#))
           (throw (ex-info "Test failed" res#))
           (if-not (pos? (first res#))
             (throw (ex-info "No tests run" {:fn ~proto-fn}))
             (is (pos? (first res#)))))))))

(defmacro test-static-fn [fn & _rest]
  `(do
     (require 'jiffy.corpus)
     (require '~(symbol (namespace fn)))
     (deftest ~(gensym)
       (let [res# (test-fn! ~fn)]
         (if (:failure (first res#))
           (throw (ex-info "Test failed" res#))
           (if-not (pos? (first res#))
             (throw (ex-info "No tests run" {:fn ~fn}))
             (is (pos? (first res#)))))))))
