(ns jiffy.protocols.format.date-time-formatter
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java
(defprotocol IDateTimeFormatter
  (parse-best [this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2])
  (format [this formatter])
  (get-locale [this])
  (with-locale [this locale])
  (localized-by [this locale])
  (get-decimal-style [this])
  (with-decimal-style [this decimal-style])
  (get-chronology [this])
  (with-chronology [this chrono])
  (get-zone [this])
  (with-zone [this zone])
  (get-resolver-style [this])
  (with-resolver-style [this resolver-style])
  (get-resolver-fields [this])
  (with-resolver-fields [this with-resolver-fields--overloaded-param])
  (format-to [this temporal appendable])
  (parse [this text] [this parse--overloaded-param-1 parse--overloaded-param-2])
  (parse-unresolved [this text position])
  (to-printer-parser [this optional])
  (to-format [this] [this parse-query]))

(s/def ::date-time-formatter #(satisfies? IDateTimeFormatter %))