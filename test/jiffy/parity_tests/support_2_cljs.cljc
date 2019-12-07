(ns jiffy.parity-tests.support-2-cljs
  (:require [jiffy.parity-tests.corpus :as corpus]
            [jiffy.edn-cljs]
            [jiffy.exception :refer [ex] :refer-macros [try*]]))

(defn try-call [f args]
  (try*
   (apply f args)
   (catch :default e
     e)))

(defn same? [actual expected]
  (let [res (or (= actual expected)
            (let [actual-data (ex-data actual)]
              (and actual-data
                   (= (:jiffy.exception/kind actual-data)
                      (:jiffy.exception/kind expected))))
            false)]
    ;; (prn [res
    ;;       (-equiv actual expected)
    ;;       (= (into {} actual) (into {} expected))
    ;;       (type actual) actual
    ;;       (type expected) expected])
    res))

(defn run-test [f {:keys [args result] :as data}]
  (let [cljs-result (try-call f args)]
    (merge data
           {:cljs-result cljs-result
            :same? (same? cljs-result result)})))

(defn test-fn! [f & [num-tests]]
  (let [corpus (corpus/corpus-for-fn f)
        num-tests (or num-tests (count corpus))
        failures (->> corpus
                      (take num-tests)
                      (map #(run-test f %))
                      (remove :same?))]
    (if-let [failure (first failures)]
      `(~'= (~(:fn failure) ~@(:args failure))
          ~(:result failure))
      ;; [(:result failure) (:cljs-result failure)]
      [num-tests :ok])))

(defn test-proto-fn! [_ & args] (apply test-fn! args))

(defn test-static-fn! [& args] (apply test-fn! args))

(def test-proto-fn test-proto-fn!)

(def test-static-fn test-static-fn!)


