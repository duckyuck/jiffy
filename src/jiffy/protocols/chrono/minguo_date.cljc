(ns jiffy.protocols.chrono.minguo-date
  (:require [clojure.spec.alpha :as s]))

(defprotocol IMinguoDate)

(s/def ::minguo-date #(satisfies? IMinguoDate %))
