(ns jiffy.format.date-time-formatter
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.decimal-style :as DecimalStyle]
            [jiffy.format.format-style :as FormatStyle]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.zone-id :as ZoneId]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-formatter (j/constructor-spec DateTimeFormatter create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-formatter)

(defmacro args [& x] `(s/tuple ::date-time-formatter ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java
(s/def ::parse-best-args (args ::j/wip ::j/wip))
(defn -parse-best [this parse-best--unknown-param-name-1 parse-best--unknown-param-name-2] (wip ::-parse-best))
(s/fdef -parse-best :args ::parse-best-args :ret ::TemporalAccessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L118
(s/def ::format-args (args ::TemporalAccessor/temporal-accessor))
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1437
(s/def ::get-locale-args (args))
(defn -get-locale [this] (wip ::-get-locale))
(s/fdef -get-locale :args ::get-locale-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1459
(s/def ::with-locale-args (args ::j/wip))
(defn -with-locale [this locale] (wip ::-with-locale))
(s/fdef -with-locale :args ::with-locale-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1493
(s/def ::localized-by-args (args ::j/wip))
(defn -localized-by [this locale] (wip ::-localized-by))
(s/fdef -localized-by :args ::localized-by-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1518
(s/def ::get-decimal-style-args (args))
(defn -get-decimal-style [this] (wip ::-get-decimal-style))
(s/fdef -get-decimal-style :args ::get-decimal-style-args :ret ::DecimalStyle/decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1530
(s/def ::with-decimal-style-args (args ::DecimalStyle/decimal-style))
(defn -with-decimal-style [this decimal-style] (wip ::-with-decimal-style))
(s/fdef -with-decimal-style :args ::with-decimal-style-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1547
(s/def ::get-chronology-args (args))
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1584
(s/def ::with-chronology-args (args ::Chronology/chronology))
(defn -with-chronology [this chrono] (wip ::-with-chronology))
(s/fdef -with-chronology :args ::with-chronology-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1601
(s/def ::get-zone-args (args))
(defn -get-zone [this] (wip ::-get-zone))
(s/fdef -get-zone :args ::get-zone-args :ret ::ZoneId/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1641
(s/def ::with-zone-args (args ::ZoneId/zone-id))
(defn -with-zone [this zone] (wip ::-with-zone))
(s/fdef -with-zone :args ::with-zone-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1659
(s/def ::get-resolver-style-args (args))
(defn -get-resolver-style [this] (wip ::-get-resolver-style))
(s/fdef -get-resolver-style :args ::get-resolver-style-args :ret ::ResolverStyle/resolver-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1682
(s/def ::with-resolver-style-args (args ::ResolverStyle/resolver-style))
(defn -with-resolver-style [this resolver-style] (wip ::-with-resolver-style))
(s/fdef -with-resolver-style :args ::with-resolver-style-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1701
(s/def ::get-resolver-fields-args (args))
(defn -get-resolver-fields [this] (wip ::-get-resolver-fields))
(s/fdef -get-resolver-fields :args ::get-resolver-fields-args :ret ::j/wip)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1744
(s/def ::with-resolver-fields-args (args ::j/wip))
(defn -with-resolver-fields [this with-resolver-fields--overloaded-param] (wip ::-with-resolver-fields))
(s/fdef -with-resolver-fields :args ::with-resolver-fields-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1837
(s/def ::format-to-args (args ::TemporalAccessor/temporal-accessor ::j/wip))
(defn -format-to [this temporal appendable] (wip ::-format-to))
(s/fdef -format-to :args ::format-to-args :ret ::j/void)

(s/def ::parse-args (args ::j/wip))
(defn -parse
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1871
  ([this text] (wip ::-parse))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L119
  ([this parse--overloaded-param-1 parse--overloaded-param-2] (wip ::-parse)))
(s/fdef -parse :args ::parse-args :ret ::TemporalAccessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2094
(s/def ::parse-unresolved-args (args ::j/wip ::j/wip))
(defn -parse-unresolved [this text position] (wip ::-parse-unresolved))
(s/fdef -parse-unresolved :args ::parse-unresolved-args :ret ::TemporalAccessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2123
(s/def ::to-printer-parser-args (args ::j/boolean))
(defn -to-printer-parser [this optional] (wip ::-to-printer-parser))
(s/fdef -to-printer-parser :args ::to-printer-parser-args :ret ::j/wip)

(s/def ::to-format-args (args ::j/wip))
(defn -to-format
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2140
  ([this] (wip ::-to-format))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L2160
  ([this parse-query] (wip ::-to-format)))
(s/fdef -to-format :args ::to-format-args :ret ::j/wip)

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

(s/def ::of-pattern-args (args ::j/wip))
(defn ofPattern
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L563
  ([pattern] (wip ::ofPattern))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L587
  ([pattern locale] (wip ::ofPattern)))
(s/fdef ofPattern :args ::of-pattern-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L614
(s/def ::of-localized-date-args (args ::FormatStyle/format-style))
(defn ofLocalizedDate [date-style] (wip ::ofLocalizedDate))
(s/fdef ofLocalizedDate :args ::of-localized-date-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L645
(s/def ::of-localized-time-args (args ::FormatStyle/format-style))
(defn ofLocalizedTime [time-style] (wip ::ofLocalizedTime))
(s/fdef ofLocalizedTime :args ::of-localized-time-args :ret ::date-time-formatter)

(s/def ::of-localized-date-time-args (args ::j/wip))
(defn ofLocalizedDateTime
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L676
  ([date-time-style] (wip ::ofLocalizedDateTime))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L708
  ([date-style time-style] (wip ::ofLocalizedDateTime)))
(s/fdef ofLocalizedDateTime :args ::of-localized-date-time-args :ret ::date-time-formatter)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1351
(defn parsedExcessDays [] (wip ::parsedExcessDays))
(s/fdef parsedExcessDays :ret ::TemporalQuery/temporal-query)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatter.java#L1392
(defn parsedLeapSecond [] (wip ::parsedLeapSecond))
(s/fdef parsedLeapSecond :ret ::TemporalQuery/temporal-query)

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
