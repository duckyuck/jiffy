(ns jiffy.server
  (:require [org.httpkit.server :as http]))

(defn handler [_req]
  {:status 200
   :headers {"Content-Type" "application/edn"
             "Access-Control-Allow-Origin" "*"}
   :body (str "["
              (slurp "dev-resources/regression-corpus.edn")
              "]")})

(defn start-server []
  (http/run-server handler {:port 4444})
  (println "Started server on port 4444"))

(defn run [_opts]
  (start-server))
