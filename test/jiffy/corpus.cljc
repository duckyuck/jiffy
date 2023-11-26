(ns jiffy.corpus
  (:require [clojure.string :as str]
            [jiffy.all]
            [jiffy.helper.macros :refer [load-edn-file] :refer-macros [load-edn-file] :include-macros true]))

(def corpus #?(:cljs (load-edn-file "dev-resources/regression-corpus.edn")))

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
  (let [fn-sym (if (var? f)
                 (symbol f)
                 (fn->symbol f))]
    (let [data (filter #(= (:fn %) fn-sym) corpus)]
      ;; (println "found" (count data) "corpus for fn-sym" fn-sym)
      data)))


(comment

  (count corpus)
  
  (let [f jiffy.protocols.temporal.temporal-adjuster/adjust-into
        corpus (first (corpus-for-fn f))]
    {:cljs (apply f (:args corpus))
     :clj (:result corpus)})

  (clojure.edn/read-string
   {:readers jiffy.edn.readers/data-readers}
   (str "[" (slurp "dev-resources/regression-corpus.edn") "]"))
  
  )



