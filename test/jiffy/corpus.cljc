(ns jiffy.corpus
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [jiffy.all]
            [jiffy.edn.readers]
            [jiffy.edn.writers]
            [jiffy.helper.macros :refer [load-edn-file] :refer-macros [load-edn-file] :include-macros true]))

#?(:cljs
   (defn fetch-corpus [fn-sym]
     (let [xhr (js/XMLHttpRequest.)]
       (.open xhr "GET" (str "http://localhost:4444/?fn=" fn-sym) false)
       (.send xhr nil)
       (when (= 200 (.-status xhr))
         (let [body (.-responseText xhr)]
           (edn/read-string
            {:readers jiffy.edn.readers/data-readers}
            body)))))
   :clj
   (defn fetch-corpus [fn-sym]
     (throw (ex-info "Function only supported in cljs" {}))))

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
    (println (str "Loading corpus for fn " fn-sym))
    (fetch-corpus fn-sym)))
