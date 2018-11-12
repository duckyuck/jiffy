(ns jiffy.format.date-time-parse-context
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java
(defprotocol IDateTimeParseContext
  (copy [this])
  (getLocale [this])
  (getDecimalStyle [this])
  (getEffectiveChronology [this])
  (isCaseSensitive [this])
  (setCaseSensitive [this case-sensitive])
  (subSequenceEquals [this cs1 offset1 cs2 offset2 length])
  (charEquals [this ch1 ch2])
  (isStrict [this])
  (setStrict [this strict])
  (startOptional [this])
  (endOptional [this successful])
  (toUnresolved [this])
  (toResolved [this resolver-style resolver-fields])
  (getParsed [this field])
  (setParsedField [this field value error-pos success-pos])
  (setParsed [this set-parsed--overloaded-param])
  (addChronoChangedListener [this listener])
  (setParsedLeapSecond [this]))

(defrecord DateTimeParseContext [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L130
(defn -copy [this] (wip ::-copy))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L146
(defn -get-locale [this] (wip ::-get-locale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L157
(defn -get-decimal-style [this] (wip ::-get-decimal-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L166
(defn -get-effective-chronology [this] (wip ::-get-effective-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L183
(defn -is-case-sensitive [this] (wip ::-is-case-sensitive))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L192
(defn -set-case-sensitive [this case-sensitive] (wip ::-set-case-sensitive))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L208
(defn -sub-sequence-equals [this cs1 offset1 cs2 offset2 length] (wip ::-sub-sequence-equals))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L241
(defn -char-equals [this ch1 ch2] (wip ::-char-equals))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L269
(defn -is-strict [this] (wip ::-is-strict))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L278
(defn -set-strict [this strict] (wip ::-set-strict))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L286
(defn -start-optional [this] (wip ::-start-optional))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L295
(defn -end-optional [this successful] (wip ::-end-optional))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L318
(defn -to-unresolved [this] (wip ::-to-unresolved))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L327
(defn -to-resolved [this resolver-style resolver-fields] (wip ::-to-resolved))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L347
(defn -get-parsed [this field] (wip ::-get-parsed))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L363
(defn -set-parsed-field [this field value error-pos success-pos] (wip ::-set-parsed-field))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L381
(defn -set-parsed [this set-parsed--overloaded-param] (wip ::-set-parsed))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L400
(defn -add-chrono-changed-listener [this listener] (wip ::-add-chrono-changed-listener))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L423
(defn -set-parsed-leap-second [this] (wip ::-set-parsed-leap-second))

(extend-type DateTimeParseContext
  IDateTimeParseContext
  (copy [this] (-copy this))
  (getLocale [this] (-get-locale this))
  (getDecimalStyle [this] (-get-decimal-style this))
  (getEffectiveChronology [this] (-get-effective-chronology this))
  (isCaseSensitive [this] (-is-case-sensitive this))
  (setCaseSensitive [this case-sensitive] (-set-case-sensitive this case-sensitive))
  (subSequenceEquals [this cs1 offset1 cs2 offset2 length] (-sub-sequence-equals this cs1 offset1 cs2 offset2 length))
  (charEquals [this ch1 ch2] (-char-equals this ch1 ch2))
  (isStrict [this] (-is-strict this))
  (setStrict [this strict] (-set-strict this strict))
  (startOptional [this] (-start-optional this))
  (endOptional [this successful] (-end-optional this successful))
  (toUnresolved [this] (-to-unresolved this))
  (toResolved [this resolver-style resolver-fields] (-to-resolved this resolver-style resolver-fields))
  (getParsed [this field] (-get-parsed this field))
  (setParsedField [this field value error-pos success-pos] (-set-parsed-field this field value error-pos success-pos))
  (setParsed [this set-parsed--overloaded-param] (-set-parsed this set-parsed--overloaded-param))
  (addChronoChangedListener [this listener] (-add-chrono-changed-listener this listener))
  (setParsedLeapSecond [this] (-set-parsed-leap-second this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L255
(defn charEqualsIgnoreCase [c1 c2] (wip ::charEqualsIgnoreCase))
