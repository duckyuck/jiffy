(ns jiffy.protocols.format.date-time-parse-context
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java
(defprotocol IDateTimeParseContext
  (copy [this])
  (get-locale [this])
  (get-decimal-style [this])
  (get-effective-chronology [this])
  (is-case-sensitive [this])
  (set-case-sensitive [this case-sensitive])
  (sub-sequence-equals [this cs1 offset1 cs2 offset2 length])
  (char-equals [this ch1 ch2])
  (is-strict [this])
  (set-strict [this strict])
  (start-optional [this])
  (end-optional [this successful])
  (to-unresolved [this])
  (to-resolved [this resolver-style resolver-fields])
  (get-parsed [this field])
  (set-parsed-field [this field value error-pos success-pos])
  (set-parsed [this set-parsed--overloaded-param])
  (add-chrono-changed-listener [this listener])
  (set-parsed-leap-second [this]))

(s/def ::date-time-parse-context #(satisfies? IDateTimeParseContext %))