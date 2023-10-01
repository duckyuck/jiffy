(ns jiffy.protocols.chrono.hijrah-date
  (:require [clojure.spec.alpha :as s]))

(defprotocol IHijrahDate
  (with-variant [this chronology]))

(s/def ::hijrah-date #(satisfies? IHijrahDate %))
