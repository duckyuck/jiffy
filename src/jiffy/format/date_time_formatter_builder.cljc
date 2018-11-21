(ns jiffy.format.date-time-formatter-builder
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date :as ChronoLocalDate]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.date-time-formatter :as DateTimeFormatter]
            [jiffy.format.format-style :as FormatStyle]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.format.sign-style :as SignStyle]
            [jiffy.format.text-style :as TextStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-field :as TemporalField]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-formatter-builder (j/constructor-spec DateTimeFormatterBuilder create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-formatter-builder)

(defmacro args [& x] `(s/tuple ::date-time-formatter-builder ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L282
(s/def ::parse-case-sensitive-args (args))
(defn -parse-case-sensitive [this] (wip ::-parse-case-sensitive))
(s/fdef -parse-case-sensitive :args ::parse-case-sensitive-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L302
(s/def ::parse-case-insensitive-args (args))
(defn -parse-case-insensitive [this] (wip ::-parse-case-insensitive))
(s/fdef -parse-case-insensitive :args ::parse-case-insensitive-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L321
(s/def ::parse-strict-args (args))
(defn -parse-strict [this] (wip ::-parse-strict))
(s/fdef -parse-strict :args ::parse-strict-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L340
(s/def ::parse-lenient-args (args))
(defn -parse-lenient [this] (wip ::-parse-lenient))
(s/fdef -parse-lenient :args ::parse-lenient-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L373
(s/def ::parse-defaulting-args (args ::TemporalField/temporal-field ::j/long))
(defn -parse-defaulting [this field value] (wip ::-parse-defaulting))
(s/fdef -parse-defaulting :args ::parse-defaulting-args :ret ::date-time-formatter-builder)

(s/def ::append-value-args (args ::j/wip))
(defn -append-value
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L398
  ([this field] (wip ::-append-value))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L452
  ([this field width] (wip ::-append-value))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L493
  ([this field min-width max-width sign-style] (wip ::-append-value)))
(s/fdef -append-value :args ::append-value-args :ret ::date-time-formatter-builder)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L554
(s/def ::append-value-reduced-args (args ::TemporalField/temporal-field ::j/int ::j/int ::j/int))
(defn -append-value-reduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4] (wip ::-append-value-reduced))
(s/fdef -append-value-reduced :args ::append-value-reduced-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L701
(s/def ::append-fraction-args (args ::TemporalField/temporal-field ::j/int ::j/int ::j/boolean))
(defn -append-fraction [this field min-width max-width decimal-point] (wip ::-append-fraction))
(s/fdef -append-fraction :args ::append-fraction-args :ret ::date-time-formatter-builder)

(s/def ::append-text-args (args ::j/wip))
(defn -append-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L728
  ([this field] (wip ::-append-text))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L747
  ([this append-text--overloaded-param-1 append-text--overloaded-param-2] (wip ::-append-text)))
(s/fdef -append-text :args ::append-text-args :ret ::date-time-formatter-builder)

(s/def ::append-instant-args (args ::j/wip))
(defn -append-instant
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L839
  ([this] (wip ::-append-instant))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L880
  ([this fractional-digits] (wip ::-append-instant)))
(s/fdef -append-instant :args ::append-instant-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L899
(s/def ::append-offset-id-args (args))
(defn -append-offset-id [this] (wip ::-append-offset-id))
(s/fdef -append-offset-id :args ::append-offset-id-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L973
(s/def ::append-offset-args (args string? string?))
(defn -append-offset [this pattern no-offset-text] (wip ::-append-offset))
(s/fdef -append-offset :args ::append-offset-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1007
(s/def ::append-localized-offset-args (args ::TextStyle/text-style))
(defn -append-localized-offset [this style] (wip ::-append-localized-offset))
(s/fdef -append-localized-offset :args ::append-localized-offset-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1063
(s/def ::append-zone-id-args (args))
(defn -append-zone-id [this] (wip ::-append-zone-id))
(s/fdef -append-zone-id :args ::append-zone-id-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1119
(s/def ::append-zone-region-id-args (args))
(defn -append-zone-region-id [this] (wip ::-append-zone-region-id))
(s/fdef -append-zone-region-id :args ::append-zone-region-id-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1175
(s/def ::append-zone-or-offset-id-args (args))
(defn -append-zone-or-offset-id [this] (wip ::-append-zone-or-offset-id))
(s/fdef -append-zone-or-offset-id :args ::append-zone-or-offset-id-args :ret ::date-time-formatter-builder)

(s/def ::append-zone-text-args (args ::j/wip))
(defn -append-zone-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1212
  ([this text-style] (wip ::-append-zone-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1254
  ([this text-style preferred-zones] (wip ::-append-zone-text)))
(s/fdef -append-zone-text :args ::append-zone-text-args :ret ::date-time-formatter-builder)

(s/def ::append-generic-zone-text-args (args ::j/wip))
(defn -append-generic-zone-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1294
  ([this text-style] (wip ::-append-generic-zone-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1320
  ([this text-style preferred-zones] (wip ::-append-generic-zone-text)))
(s/fdef -append-generic-zone-text :args ::append-generic-zone-text-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1346
(s/def ::append-chronology-id-args (args))
(defn -append-chronology-id [this] (wip ::-append-chronology-id))
(s/fdef -append-chronology-id :args ::append-chronology-id-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1360
(s/def ::append-chronology-text-args (args ::TextStyle/text-style))
(defn -append-chronology-text [this text-style] (wip ::-append-chronology-text))
(s/fdef -append-chronology-text :args ::append-chronology-text-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1398
(s/def ::append-localized-args (args ::FormatStyle/format-style ::FormatStyle/format-style))
(defn -append-localized [this date-style time-style] (wip ::-append-localized))
(s/fdef -append-localized :args ::append-localized-args :ret ::date-time-formatter-builder)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1415
(s/def ::append-literal-args (args ::j/char))
(defn -append-literal [this append-literal--overloaded-param] (wip ::-append-literal))
(s/fdef -append-literal :args ::append-literal-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1452
(s/def ::append-args (args ::DateTimeFormatter/date-time-formatter))
(defn -append [this formatter] (wip ::-append))
(s/fdef -append :args ::append-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1471
(s/def ::append-optional-args (args ::DateTimeFormatter/date-time-formatter))
(defn -append-optional [this formatter] (wip ::-append-optional))
(s/fdef -append-optional :args ::append-optional-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1675
(s/def ::optional-start-args (args))
(defn -optional-start [this] (wip ::-optional-start))
(s/fdef -optional-start :args ::optional-start-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1676
(s/def ::optional-end-args (args))
(defn -optional-end [this] (wip ::-optional-end))
(s/fdef -optional-end :args ::optional-end-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1700
(s/def ::append-pattern-args (args string?))
(defn -append-pattern [this pattern] (wip ::-append-pattern))
(s/fdef -append-pattern :args ::append-pattern-args :ret ::date-time-formatter-builder)

(s/def ::pad-next-args (args ::j/wip))
(defn -pad-next
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2044
  ([this pad-width] (wip ::-pad-next))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2069
  ([this pad-width pad-char] (wip ::-pad-next)))
(s/fdef -pad-next :args ::pad-next-args :ret ::date-time-formatter-builder)

(s/def ::to-formatter-args (args ::j/wip))
(defn -to-formatter
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2183
  ([this] (wip ::-to-formatter))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2204
  ([this locale] (wip ::-to-formatter))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L2215
  ([this resolver-style chrono] (wip ::-to-formatter)))
(s/fdef -to-formatter :args ::to-formatter-args :ret ::DateTimeFormatter/date-time-formatter)

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
(s/def ::get-localized-date-time-pattern-args (args ::FormatStyle/format-style ::FormatStyle/format-style ::Chronology/chronology ::j/wip))
(defn getLocalizedDateTimePattern [date-style time-style chrono locale] (wip ::getLocalizedDateTimePattern))
(s/fdef getLocalizedDateTimePattern :args ::get-localized-date-time-pattern-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L4942
(def LENGTH_SORT ::LENGTH_SORT--not-implemented)
