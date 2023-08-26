(ns jiffy.parity-tests.support-2-cljs
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
                (:jiffy.exception/kind expected))))))

(defn run-test [f {:keys [args result] :as data}]
  (let [actual (try-call f args)]
    (merge (dissoc data :result)
           {:same? (same? actual result)
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
        {:expr `(~'jiffy.parity-tests.support-2-cljs/same?
                 (~'jiffy.parity-tests.support-2-cljs/try-call ~(:fn failure) [~@(:args failure)])
                 ~(:expected failure))
         :failure failure}))))

(defn test-proto-fn! [_ & args] (apply test-fn! args))

(defn test-static-fn! [& args] (apply test-fn! args))

(defmacro test-proto-fn [_ proto-fn]
  `(do
     (let [v# (var ~proto-fn)]
       (deftest ~(gensym)
         (let [res# (test-fn! v#)]
           (if (:failure (first res#))
             (throw (ex-info "Test failed" res#))
             (if-not (pos? (first res#))
               (throw (ex-info "No tests run" {:fn v#}))
               (is (pos? (first res#))))))))))

(defmacro test-static-fn [fn]
  `(do
     (let [v# (var ~fn)]
       (deftest ~(gensym)
         (let [res# (test-fn! v#)]
           (if (:failure (first res#))
             (throw (ex-info "Test failed" res#))
             (if-not (pos? (first res#))
               (throw (ex-info "No tests run" {:fn v#}))
               (is (pos? (first res#))))))))))
