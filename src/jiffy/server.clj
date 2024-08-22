(ns jiffy.server
  (:require [clojure.edn :as edn]
            [jiffy.dev.main]
            [jiffy.edn.readers]
            [jiffy.edn.writers]
            [jiffy.zone.zone-rules-loader]
            [org.httpkit.server :as http]
            [ring.middleware.params]))

:jiffy.dev.main/keep
:jiffy.edn.writers/keep

(defn load-corpus []
  (->> (str "["
            (slurp "dev-resources/regression-corpus.edn")
            "]")
       (edn/read-string {:readers jiffy.edn.readers/data-readers})
       (group-by :fn)))


(def fn->corpus
  (do
    (println "Loading corpus...")
    (let [corpus (load-corpus)]
      (println "Loaded corpus for" (count corpus) "functions")
      (atom corpus))))

(defn get-corpus-by-fn [fn-str]
  (@fn->corpus (symbol fn-str)))

(defn handler [req]
  (let [fn-str (get (:query-params req) "fn")
        corpus (get-corpus-by-fn fn-str)]
    (println "Returning" (count corpus) "tests for fn" fn-str)
    {:status 200
     :headers {"Content-Type" "application/edn"
               "Access-Control-Allow-Origin" "*"}
     :body (pr-str corpus)}))

(defn create-handler [handler]
  (-> handler
      (ring.middleware.params/wrap-params)))

(defn start-server []
  (http/run-server (create-handler #'handler) {:port 4444})
  (println "Started server on port 4444"))

(defn run [_opts]
  (start-server))
