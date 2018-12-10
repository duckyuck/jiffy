(ns jiffy.protocols.chrono.hijrah-date
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/HijrahDate.java
(defprotocol IHijrahDate
  (with-variant [this chronology]))

(s/def ::hijrah-date #(satisfies? IHijrahDate %))