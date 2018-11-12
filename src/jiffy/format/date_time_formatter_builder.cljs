(ns jiffy.format.date-time-formatter-builder
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java
(defprotocol IDateTimeFormatterBuilder
  (parseCaseSensitive [this])
  (parseCaseInsensitive [this])
  (parseStrict [this])
  (parseLenient [this])
  (parseDefaulting [this field value])
  (appendValue [this field] [this field width] [this field min-width max-width sign-style])
  (appendValueReduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4])
  (appendFraction [this field min-width max-width decimal-point])
  (appendText [this field] [this append-text--overloaded-param-1 append-text--overloaded-param-2])
  (appendInstant [this] [this fractional-digits])
  (appendOffsetId [this])
  (appendOffset [this pattern no-offset-text])
  (appendLocalizedOffset [this style])
  (appendZoneId [this])
  (appendZoneRegionId [this])
  (appendZoneOrOffsetId [this])
  (appendZoneText [this text-style] [this text-style preferred-zones])
  (appendGenericZoneText [this text-style] [this text-style preferred-zones])
  (appendChronologyId [this])
  (appendChronologyText [this text-style])
  (appendLocalized [this date-style time-style])
  (appendLiteral [this append-literal--overloaded-param])
  (append [this formatter])
  (appendOptional [this formatter])
  (optionalStart [this])
  (optionalEnd [this])
  (appendPattern [this pattern])
  (padNext [this pad-width] [this pad-width pad-char])
  (toFormatter [this] [this locale] [this resolver-style chrono]))

(defrecord DateTimeFormatterBuilder [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L282
(defn -parse-case-sensitive [this] (wip ::-parse-case-sensitive))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L302
(defn -parse-case-insensitive [this] (wip ::-parse-case-insensitive))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L321
(defn -parse-strict [this] (wip ::-parse-strict))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L340
(defn -parse-lenient [this] (wip ::-parse-lenient))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L373
(defn -parse-defaulting [this field value] (wip ::-parse-defaulting))

(defn -append-value
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L398
  ([this field] (wip ::-append-value))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L452
  ([this field width] (wip ::-append-value))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L493
  ([this field min-width max-width sign-style] (wip ::-append-value)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L554
(defn -append-value-reduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4] (wip ::-append-value-reduced))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L701
(defn -append-fraction [this field min-width max-width decimal-point] (wip ::-append-fraction))

(defn -append-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L728
  ([this field] (wip ::-append-text))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L747
  ([this append-text--overloaded-param-1 append-text--overloaded-param-2] (wip ::-append-text)))

(defn -append-instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L839
  ([this] (wip ::-append-instant))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L880
  ([this fractional-digits] (wip ::-append-instant)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L899
(defn -append-offset-id [this] (wip ::-append-offset-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L973
(defn -append-offset [this pattern no-offset-text] (wip ::-append-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1007
(defn -append-localized-offset [this style] (wip ::-append-localized-offset))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1063
(defn -append-zone-id [this] (wip ::-append-zone-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1119
(defn -append-zone-region-id [this] (wip ::-append-zone-region-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1175
(defn -append-zone-or-offset-id [this] (wip ::-append-zone-or-offset-id))

(defn -append-zone-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1212
  ([this text-style] (wip ::-append-zone-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1254
  ([this text-style preferred-zones] (wip ::-append-zone-text)))

(defn -append-generic-zone-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1294
  ([this text-style] (wip ::-append-generic-zone-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1320
  ([this text-style preferred-zones] (wip ::-append-generic-zone-text)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1346
(defn -append-chronology-id [this] (wip ::-append-chronology-id))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1360
(defn -append-chronology-text [this text-style] (wip ::-append-chronology-text))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1398
(defn -append-localized [this date-style time-style] (wip ::-append-localized))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1415
(defn -append-literal [this append-literal--overloaded-param] (wip ::-append-literal))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1452
(defn -append [this formatter] (wip ::-append))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1471
(defn -append-optional [this formatter] (wip ::-append-optional))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1675
(defn -optional-start [this] (wip ::-optional-start))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1676
(defn -optional-end [this] (wip ::-optional-end))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1700
(defn -append-pattern [this pattern] (wip ::-append-pattern))

(defn -pad-next
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2044
  ([this pad-width] (wip ::-pad-next))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2069
  ([this pad-width pad-char] (wip ::-pad-next)))

(defn -to-formatter
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2183
  ([this] (wip ::-to-formatter))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2204
  ([this locale] (wip ::-to-formatter))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2215
  ([this resolver-style chrono] (wip ::-to-formatter)))

(extend-type DateTimeFormatterBuilder
  IDateTimeFormatterBuilder
  (parseCaseSensitive [this] (-parse-case-sensitive this))
  (parseCaseInsensitive [this] (-parse-case-insensitive this))
  (parseStrict [this] (-parse-strict this))
  (parseLenient [this] (-parse-lenient this))
  (parseDefaulting [this field value] (-parse-defaulting this field value))
  (appendValue
    ([this field] (-append-value this field))
    ([this field width] (-append-value this field width))
    ([this field min-width max-width sign-style] (-append-value this field min-width max-width sign-style)))
  (appendValueReduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4] (-append-value-reduced this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4))
  (appendFraction [this field min-width max-width decimal-point] (-append-fraction this field min-width max-width decimal-point))
  (appendText
    ([this field] (-append-text this field))
    ([this append-text--overloaded-param-1 append-text--overloaded-param-2] (-append-text this append-text--overloaded-param-1 append-text--overloaded-param-2)))
  (appendInstant
    ([this] (-append-instant this))
    ([this fractional-digits] (-append-instant this fractional-digits)))
  (appendOffsetId [this] (-append-offset-id this))
  (appendOffset [this pattern no-offset-text] (-append-offset this pattern no-offset-text))
  (appendLocalizedOffset [this style] (-append-localized-offset this style))
  (appendZoneId [this] (-append-zone-id this))
  (appendZoneRegionId [this] (-append-zone-region-id this))
  (appendZoneOrOffsetId [this] (-append-zone-or-offset-id this))
  (appendZoneText
    ([this text-style] (-append-zone-text this text-style))
    ([this text-style preferred-zones] (-append-zone-text this text-style preferred-zones)))
  (appendGenericZoneText
    ([this text-style] (-append-generic-zone-text this text-style))
    ([this text-style preferred-zones] (-append-generic-zone-text this text-style preferred-zones)))
  (appendChronologyId [this] (-append-chronology-id this))
  (appendChronologyText [this text-style] (-append-chronology-text this text-style))
  (appendLocalized [this date-style time-style] (-append-localized this date-style time-style))
  (appendLiteral [this append-literal--overloaded-param] (-append-literal this append-literal--overloaded-param))
  (append [this formatter] (-append this formatter))
  (appendOptional [this formatter] (-append-optional this formatter))
  (optionalStart [this] (-optional-start this))
  (optionalEnd [this] (-optional-end this))
  (appendPattern [this pattern] (-append-pattern this pattern))
  (padNext
    ([this pad-width] (-pad-next this pad-width))
    ([this pad-width pad-char] (-pad-next this pad-width pad-char)))
  (toFormatter
    ([this] (-to-formatter this))
    ([this locale] (-to-formatter this locale))
    ([this resolver-style chrono] (-to-formatter this resolver-style chrono))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L214
(defn getLocalizedDateTimePattern [date-style time-style chrono locale] (wip ::getLocalizedDateTimePattern))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L4942
(def LENGTH_SORT ::LENGTH_SORT--not-implemented)