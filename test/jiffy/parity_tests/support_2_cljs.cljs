(ns jiffy.parity-tests.support-2-cljs
  (:require [jiffy.edn-cljs]
            [jiffy.exception :refer [ex] :refer-macros [try*]]
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
                 ~(:result failure))
         :failure failure}))))

(defn test-proto-fn! [_ & args] (apply test-fn! args))

(defn test-static-fn! [& args] (apply test-fn! args))

(def test-proto-fn test-proto-fn!)

(def test-static-fn test-static-fn!)


