(ns jiffy.parity-tests.support-cljs
  (:require [cljs.test :refer [deftest is testing]]
            [clojure.string :as str]
            [jiffy.edn-cljs]
            [jiffy.exception :refer [ex try*]]
            [jiffy.parity-tests.corpus :as corpus]))

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

(defn run-test [f {:keys [args result] :as data}]
  (let [actual (try-call f args)]
    (merge (dissoc data :result)
           {:same? (same? actual result)
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
        {:expr `(~'jiffy.parity-tests.support-cljs/same?
                 (~'jiffy.parity-tests.support-cljs/try-call ~(:fn failure) [~@(:args failure)])
                 ~(:expected failure))
         :failure failure}))))

(defn test-proto-fn! [_ & args] (apply test-fn! args))

(defn test-static-fn! [& args] (apply test-fn! args))

(defmacro test-proto-fn [jiffy-ns proto-fn]
  `(do
     (require '~(symbol jiffy-ns))
     (require '~(symbol (namespace proto-fn)))
     (deftest ~(gensym)
       (let [res# (test-fn! ~proto-fn)]
         (if (:failure (first res#))
           (throw (ex-info "Test failed" res#))
           (if-not (pos? (first res#))
             (throw (ex-info "No tests run" {:fn ~proto-fn}))
             (is (pos? (first res#)))))))))

(defmacro test-static-fn [fn]
  `(do
     (require '~(symbol (namespace fn)))
     (deftest ~(gensym)
       (let [res# (test-fn! ~fn)]
         (if (:failure (first res#))
           (throw (ex-info "Test failed" res#))
           (if-not (pos? (first res#))
             (throw (ex-info "No tests run" {:fn ~fn}))
             (is (pos? (first res#)))))))))
