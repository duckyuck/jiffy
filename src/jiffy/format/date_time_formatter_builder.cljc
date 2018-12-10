(ns jiffy.format.date-time-formatter-builder
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.format.date-time-formatter-builder :as date-time-formatter-builder]
            [jiffy.protocols.format.format-style :as format-style]
            [jiffy.protocols.format.resolver-style :as resolver-style]
            [jiffy.protocols.format.sign-style :as sign-style]
            [jiffy.protocols.format.text-style :as text-style]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.specs :as j]))

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
(s/def ::parse-defaulting-args (args ::temporal-field/temporal-field ::j/long))
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
(s/def ::append-value-reduced-args (args ::temporal-field/temporal-field ::j/int ::j/int ::j/int))
(defn -append-value-reduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4] (wip ::-append-value-reduced))
(s/fdef -append-value-reduced :args ::append-value-reduced-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L701
(s/def ::append-fraction-args (args ::temporal-field/temporal-field ::j/int ::j/int ::j/boolean))
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
(s/def ::append-localized-offset-args (args ::text-style/text-style))
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
(s/def ::append-chronology-text-args (args ::text-style/text-style))
(defn -append-chronology-text [this text-style] (wip ::-append-chronology-text))
(s/fdef -append-chronology-text :args ::append-chronology-text-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1398
(s/def ::append-localized-args (args ::format-style/format-style ::format-style/format-style))
(defn -append-localized [this date-style time-style] (wip ::-append-localized))
(s/fdef -append-localized :args ::append-localized-args :ret ::date-time-formatter-builder)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1415
(s/def ::append-literal-args (args ::j/char))
(defn -append-literal [this append-literal--overloaded-param] (wip ::-append-literal))
(s/fdef -append-literal :args ::append-literal-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1452
(s/def ::append-args (args ::date-time-formatter/date-time-formatter))
(defn -append [this formatter] (wip ::-append))
(s/fdef -append :args ::append-args :ret ::date-time-formatter-builder)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L1471
(s/def ::append-optional-args (args ::date-time-formatter/date-time-formatter))
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
(s/fdef -to-formatter :args ::to-formatter-args :ret ::date-time-formatter/date-time-formatter)

(extend-type DateTimeFormatterBuilder
  date-time-formatter-builder/IDateTimeFormatterBuilder
  (parse-case-sensitive [this] (-parse-case-sensitive this))
  (parse-case-insensitive [this] (-parse-case-insensitive this))
  (parse-strict [this] (-parse-strict this))
  (parse-lenient [this] (-parse-lenient this))
  (parse-defaulting [this field value] (-parse-defaulting this field value))
  (append-value
    ([this field] (-append-value this field))
    ([this field width] (-append-value this field width))
    ([this field min-width max-width sign-style] (-append-value this field min-width max-width sign-style)))
  (append-value-reduced [this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4] (-append-value-reduced this append-value-reduced--overloaded-param-1 append-value-reduced--overloaded-param-2 append-value-reduced--overloaded-param-3 append-value-reduced--overloaded-param-4))
  (append-fraction [this field min-width max-width decimal-point] (-append-fraction this field min-width max-width decimal-point))
  (append-text
    ([this field] (-append-text this field))
    ([this append-text--overloaded-param-1 append-text--overloaded-param-2] (-append-text this append-text--overloaded-param-1 append-text--overloaded-param-2)))
  (append-instant
    ([this] (-append-instant this))
    ([this fractional-digits] (-append-instant this fractional-digits)))
  (append-offset-id [this] (-append-offset-id this))
  (append-offset [this pattern no-offset-text] (-append-offset this pattern no-offset-text))
  (append-localized-offset [this style] (-append-localized-offset this style))
  (append-zone-id [this] (-append-zone-id this))
  (append-zone-region-id [this] (-append-zone-region-id this))
  (append-zone-or-offset-id [this] (-append-zone-or-offset-id this))
  (append-zone-text
    ([this text-style] (-append-zone-text this text-style))
    ([this text-style preferred-zones] (-append-zone-text this text-style preferred-zones)))
  (append-generic-zone-text
    ([this text-style] (-append-generic-zone-text this text-style))
    ([this text-style preferred-zones] (-append-generic-zone-text this text-style preferred-zones)))
  (append-chronology-id [this] (-append-chronology-id this))
  (append-chronology-text [this text-style] (-append-chronology-text this text-style))
  (append-localized [this date-style time-style] (-append-localized this date-style time-style))
  (append-literal [this append-literal--overloaded-param] (-append-literal this append-literal--overloaded-param))
  (append [this formatter] (-append this formatter))
  (append-optional [this formatter] (-append-optional this formatter))
  (optional-start [this] (-optional-start this))
  (optional-end [this] (-optional-end this))
  (append-pattern [this pattern] (-append-pattern this pattern))
  (pad-next
    ([this pad-width] (-pad-next this pad-width))
    ([this pad-width pad-char] (-pad-next this pad-width pad-char)))
  (to-formatter
    ([this] (-to-formatter this))
    ([this locale] (-to-formatter this locale))
    ([this resolver-style chrono] (-to-formatter this resolver-style chrono))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L214
(s/def ::get-localized-date-time-pattern-args (args ::format-style/format-style ::format-style/format-style ::chronology/chronology ::j/wip))
(defn get-localized-date-time-pattern [date-style time-style chrono locale] (wip ::get-localized-date-time-pattern))
(s/fdef get-localized-date-time-pattern :args ::get-localized-date-time-pattern-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeFormatterBuilder.java#L4942
(def LENGTH_SORT ::LENGTH_SORT--not-implemented)