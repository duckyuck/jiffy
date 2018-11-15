(ns jiffy.format.date-time-formatter
  (:refer-clojure :exclude [format])
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java
(defprotocol IDateTimeFormatter
  (parseBest [this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2])
  (format [this formatter])
  (getLocale [this])
  (withLocale [this locale])
  (localizedBy [this locale])
  (getDecimalStyle [this])
  (withDecimalStyle [this decimal-style])
  (getChronology [this])
  (withChronology [this chrono])
  (getZone [this])
  (withZone [this zone])
  (getResolverStyle [this])
  (withResolverStyle [this resolver-style])
  (getResolverFields [this])
  (withResolverFields [this with-resolver-fields--overloaded-param])
  (formatTo [this temporal appendable])
  (parse [this text] [this parse--overloaded-param-1 parse--overloaded-param-2])
  (parseUnresolved [this text position])
  (toPrinterParser [this optional])
  (toFormat [this] [this parse-query]))

(defrecord DateTimeFormatter [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java
(defn -parse-best [this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2] (wip ::-parse-best))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L118
(defn -format [this formatter] (wip ::-format))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1437
(defn -get-locale [this] (wip ::-get-locale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1459
(defn -with-locale [this locale] (wip ::-with-locale))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1493
(defn -localized-by [this locale] (wip ::-localized-by))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1518
(defn -get-decimal-style [this] (wip ::-get-decimal-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1530
(defn -with-decimal-style [this decimal-style] (wip ::-with-decimal-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1547
(defn -get-chronology [this] (wip ::-get-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1584
(defn -with-chronology [this chrono] (wip ::-with-chronology))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1601
(defn -get-zone [this] (wip ::-get-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1641
(defn -with-zone [this zone] (wip ::-with-zone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1659
(defn -get-resolver-style [this] (wip ::-get-resolver-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1682
(defn -with-resolver-style [this resolver-style] (wip ::-with-resolver-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1701
(defn -get-resolver-fields [this] (wip ::-get-resolver-fields))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1744
(defn -with-resolver-fields [this with-resolver-fields--overloaded-param] (wip ::-with-resolver-fields))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1837
(defn -format-to [this temporal appendable] (wip ::-format-to))

(defn -parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1871
  ([this text] (wip ::-parse))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L119
  ([this parse--overloaded-param-1 parse--overloaded-param-2] (wip ::-parse)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2094
(defn -parse-unresolved [this text position] (wip ::-parse-unresolved))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2123
(defn -to-printer-parser [this optional] (wip ::-to-printer-parser))

(defn -to-format
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2140
  ([this] (wip ::-to-format))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2160
  ([this parse-query] (wip ::-to-format)))

(extend-type DateTimeFormatter
  IDateTimeFormatter
  (parseBest [this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2] (-parse-best this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2))
  (format [this formatter] (-format this formatter))
  (getLocale [this] (-get-locale this))
  (withLocale [this locale] (-with-locale this locale))
  (localizedBy [this locale] (-localized-by this locale))
  (getDecimalStyle [this] (-get-decimal-style this))
  (withDecimalStyle [this decimal-style] (-with-decimal-style this decimal-style))
  (getChronology [this] (-get-chronology this))
  (withChronology [this chrono] (-with-chronology this chrono))
  (getZone [this] (-get-zone this))
  (withZone [this zone] (-with-zone this zone))
  (getResolverStyle [this] (-get-resolver-style this))
  (withResolverStyle [this resolver-style] (-with-resolver-style this resolver-style))
  (getResolverFields [this] (-get-resolver-fields this))
  (withResolverFields [this with-resolver-fields--overloaded-param] (-with-resolver-fields this with-resolver-fields--overloaded-param))
  (formatTo [this temporal appendable] (-format-to this temporal appendable))
  (parse
    ([this text] (-parse this text))
    ([this parse--overloaded-param-1 parse--overloaded-param-2] (-parse this parse--overloaded-param-1 parse--overloaded-param-2)))
  (parseUnresolved [this text position] (-parse-unresolved this text position))
  (toPrinterParser [this optional] (-to-printer-parser this optional))
  (toFormat
    ([this] (-to-format this))
    ([this parse-query] (-to-format this parse-query))))

(defn ofPattern
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L563
  ([pattern] (wip ::ofPattern))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L587
  ([pattern locale] (wip ::ofPattern)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L614
(defn ofLocalizedDate [date-style] (wip ::ofLocalizedDate))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L645
(defn ofLocalizedTime [time-style] (wip ::ofLocalizedTime))

(defn ofLocalizedDateTime
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L676
  ([date-time-style] (wip ::ofLocalizedDateTime))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L708
  ([date-style time-style] (wip ::ofLocalizedDateTime)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1351
(defn parsedExcessDays [] (wip ::parsedExcessDays))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1392
(defn parsedLeapSecond [] (wip ::parsedLeapSecond))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L739
(def ISO_LOCAL_DATE ::ISO_LOCAL_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L769
(def ISO_OFFSET_DATE ::ISO_OFFSET_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L801
(def ISO_DATE ::ISO_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L838
(def ISO_LOCAL_TIME ::ISO_LOCAL_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L870
(def ISO_OFFSET_TIME ::ISO_OFFSET_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L901
(def ISO_TIME ::ISO_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L929
(def ISO_LOCAL_DATE_TIME ::ISO_LOCAL_DATE_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L959
(def ISO_OFFSET_DATE_TIME ::ISO_OFFSET_DATE_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L993
(def ISO_ZONED_DATE_TIME ::ISO_ZONED_DATE_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1034
(def ISO_DATE_TIME ::ISO_DATE_TIME--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1076
(def ISO_ORDINAL_DATE ::ISO_ORDINAL_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1120
(def ISO_WEEK_DATE ::ISO_WEEK_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1165
(def ISO_INSTANT ::ISO_INSTANT--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1202
(def BASIC_ISO_DATE ::BASIC_ISO_DATE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1260
(def RFC_1123_DATE_TIME ::RFC_1123_DATE_TIME--not-implemented)
