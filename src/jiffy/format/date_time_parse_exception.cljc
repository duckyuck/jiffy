(ns jiffy.format.date-time-parse-exception
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.date-time-exception :as DateTimeException]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseException.java
(defprotocol IDateTimeParseException
  (getParsedString [this])
  (getErrorIndex [this]))

(defrecord DateTimeParseException [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseException.java#L125
(defn -get-parsed-string [this] (wip ::-get-parsed-string))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseException.java#L134
(defn -get-error-index [this] (wip ::-get-error-index))

(extend-type DateTimeParseException
  IDateTimeParseException
  (getParsedString [this] (-get-parsed-string this))
  (getErrorIndex [this] (-get-error-index this)))

;; FIXME: no implementation found from inherited class class java.time.DateTimeException

