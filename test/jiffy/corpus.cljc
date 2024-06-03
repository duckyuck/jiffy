(ns jiffy.corpus
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [jiffy.all]
            [jiffy.edn.readers]
            [jiffy.helper.macros :refer [load-edn-file] :refer-macros [load-edn-file] :include-macros true]
            [jiffy.specs :as j]
            [cljs.core.async :refer [go]]
            [cljs.core.async.interop :refer-macros [<p!]]))

#_(def corpus #?(:cljs (load-edn-file "dev-resources/regression-corpus.edn")))

(def corpus (atom nil))

(defn get-corpus []
  @corpus)

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
    (let [data (filter #(= (:fn %) fn-sym) @corpus)]
      ;; (println "found" (count data) "corpus for fn-sym" fn-sym)
      data)))

#?(:cljs
   (defn load-corpus []
     (let [xhr (js/XMLHttpRequest.)]
       (.open xhr "GET" "http://localhost:4444" false)
       (.send xhr nil)
       (when (= 200 (.-status xhr))
         (reset! corpus
                 (edn/read-string
                  {:readers jiffy.edn.readers/data-readers}
                  (.-responseText xhr)))))))

#?(:cljs
   (do
     (prn "loading corpus...")
     (load-corpus)
     (prn (str "loaded " (count (get-corpus)) " corpi"))))

(comment

  (def corpus (atom nil))

  (count @corpus)



  (future )
  (reset! corpus nil)

  (-> (js/fetch "http://localhost:4444")
      (.then (fn [r]
               (when-not (.-ok r)
                 (throw (js/Error. "Could not fetch corpus data")))
               (.text r)))
      (.then (fn [text]
               (reset! corpus
                       (edn/read-string
                        {:readers jiffy.edn.readers/data-readers}
                        text)))))
  
  (edn/read-string
   {:readers jiffy.edn.readers/data-readers}
   "(1 2 3)")
  (count corpus)
  
  (let [f jiffy.protocols.temporal.temporal-adjuster/adjust-into
        corpus (first (corpus-for-fn f))]
    {:cljs (apply f (:args corpus))
     :clj (:result corpus)})

  (clojure.edn/read-string
   {:readers jiffy.edn.readers/data-readers}
   (str "[" (slurp "dev-resources/regression-corpus.edn") "]"))
  
  )
