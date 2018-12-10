(ns jiffy.protocols.chrono.abstract-chronology
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/AbstractChronology.java
(defprotocol IAbstractChronology
  (resolve-proleptic-month [this field-values resolver-style])
  (resolve-year-of-era [this field-values resolver-style])
  (resolve-ymd [this field-values resolver-style])
  (resolve-yd [this field-values resolver-style])
  (resolve-ymaa [this field-values resolver-style])
  (resolve-ymad [this field-values resolver-style])
  (resolve-yaa [this field-values resolver-style])
  (resolve-yad [this field-values resolver-style])
  (resolve-aligned [this base months weeks dow])
  (add-field-value [this field-values field value]))

(s/def ::abstract-chronology #(satisfies? IAbstractChronology %))