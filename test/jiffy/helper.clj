(ns jiffy.helper
  (:require [clojure.string :as str]))

(defn var->symbol [v]
  (let [{:keys [ns name]} (meta v)]
    (symbol (str ns) (str name))))

(defn to-symbol [var-or-symbol]
  (if (symbol? var-or-symbol)
    var-or-symbol
    (var->symbol var-or-symbol)))

(defn resolve-filename [var-or-symbol lang]
  (let [sym (to-symbol var-or-symbol)]
    (-> (str/join (java.io.File/separator)
                  (flatten ["test-data"
                            (-> sym namespace (str/split #"\."))
                            (-> sym name (str "-result"
                                              (case lang
                                                :clj ".cljc"
                                                :cljs ".edn")))]))
        (str/replace "-" "_")
        (str/replace "test_data" "test-data"))))

(defn get-test-data-str [var-or-symbol lang]
  (-> var-or-symbol
      (resolve-filename lang)
      slurp))

