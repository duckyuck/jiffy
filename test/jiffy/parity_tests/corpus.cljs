(ns jiffy.parity-tests.corpus
  (:require [jiffy.edn-cljs]
            [jiffy.helper.macros :refer-macros [load-edn-file]]
            [clojure.string :as str]))

(def corpus (load-edn-file "regression-corpus.edn"))

;; TODO: replace this with something that allows testing with advanced compilation
(defn fn->symbol [f]
  (when-let [js-name (some->> f
                              pr-str
                              (re-find #".*\[(.*)\]")
                              second)]
    (let [parts (-> js-name
                    (str/replace "_" "-")
                    (str/replace "-GT-" ">")
                    (str/split #"\$"))]
      (symbol (str/join "." (butlast parts))
              (last parts)))))

(defn corpus-for-fn [f]
  (let [fn-sym (fn->symbol f)]
    (filter #(= (:fn %) fn-sym) corpus)))

(comment
  
  (let [f jiffy.protocols.temporal.temporal-adjuster/adjust-into
        corpus (first (corpus-for-fn f))]
    (apply f (:args corpus)))

  )



