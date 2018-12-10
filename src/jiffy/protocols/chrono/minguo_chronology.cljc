(ns jiffy.protocols.chrono.minguo-chronology
  (:require [clojure.spec.alpha :as s]))

(defprotocol IMinguoChronology)

(s/def ::minguo-chronology #(satisfies? IMinguoChronology %))
