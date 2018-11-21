(ns jiffy.format.date-time-parse-context
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chronology :as Chronology]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.decimal-style :as DecimalStyle]
            [jiffy.format.parsed :as Parsed]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.zone-id :as ZoneId]))

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

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-parse-context (j/constructor-spec DateTimeParseContext create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-parse-context)

(defmacro args [& x] `(s/tuple ::date-time-parse-context ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L130
(s/def ::copy-args (args))
(defn -copy [this] (wip ::-copy))
(s/fdef -copy :args ::copy-args :ret ::date-time-parse-context)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L146
(s/def ::get-locale-args (args))
(defn -get-locale [this] (wip ::-get-locale))
(s/fdef -get-locale :args ::get-locale-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L157
(s/def ::get-decimal-style-args (args))
(defn -get-decimal-style [this] (wip ::-get-decimal-style))
(s/fdef -get-decimal-style :args ::get-decimal-style-args :ret ::DecimalStyle/decimal-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L166
(s/def ::get-effective-chronology-args (args))
(defn -get-effective-chronology [this] (wip ::-get-effective-chronology))
(s/fdef -get-effective-chronology :args ::get-effective-chronology-args :ret ::Chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L183
(s/def ::is-case-sensitive-args (args))
(defn -is-case-sensitive [this] (wip ::-is-case-sensitive))
(s/fdef -is-case-sensitive :args ::is-case-sensitive-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L192
(s/def ::set-case-sensitive-args (args ::j/boolean))
(defn -set-case-sensitive [this case-sensitive] (wip ::-set-case-sensitive))
(s/fdef -set-case-sensitive :args ::set-case-sensitive-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L208
(s/def ::sub-sequence-equals-args (args ::j/wip ::j/int ::j/wip ::j/int ::j/int))
(defn -sub-sequence-equals [this cs1 offset1 cs2 offset2 length] (wip ::-sub-sequence-equals))
(s/fdef -sub-sequence-equals :args ::sub-sequence-equals-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L241
(s/def ::char-equals-args (args ::j/char ::j/char))
(defn -char-equals [this ch1 ch2] (wip ::-char-equals))
(s/fdef -char-equals :args ::char-equals-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L269
(s/def ::is-strict-args (args))
(defn -is-strict [this] (wip ::-is-strict))
(s/fdef -is-strict :args ::is-strict-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L278
(s/def ::set-strict-args (args ::j/boolean))
(defn -set-strict [this strict] (wip ::-set-strict))
(s/fdef -set-strict :args ::set-strict-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L286
(s/def ::start-optional-args (args))
(defn -start-optional [this] (wip ::-start-optional))
(s/fdef -start-optional :args ::start-optional-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L295
(s/def ::end-optional-args (args ::j/boolean))
(defn -end-optional [this successful] (wip ::-end-optional))
(s/fdef -end-optional :args ::end-optional-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L318
(s/def ::to-unresolved-args (args))
(defn -to-unresolved [this] (wip ::-to-unresolved))
(s/fdef -to-unresolved :args ::to-unresolved-args :ret ::Parsed/parsed)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L327
(s/def ::to-resolved-args (args ::ResolverStyle/resolver-style ::j/wip))
(defn -to-resolved [this resolver-style resolver-fields] (wip ::-to-resolved))
(s/fdef -to-resolved :args ::to-resolved-args :ret ::TemporalAccessor/temporal-accessor)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L347
(s/def ::get-parsed-args (args ::TemporalField/temporal-field))
(defn -get-parsed [this field] (wip ::-get-parsed))
(s/fdef -get-parsed :args ::get-parsed-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L363
(s/def ::set-parsed-field-args (args ::TemporalField/temporal-field ::j/long ::j/int ::j/int))
(defn -set-parsed-field [this field value error-pos success-pos] (wip ::-set-parsed-field))
(s/fdef -set-parsed-field :args ::set-parsed-field-args :ret ::j/int)

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L381
(s/def ::set-parsed-args (args ::Chronology/chronology))
(defn -set-parsed [this set-parsed--overloaded-param] (wip ::-set-parsed))
(s/fdef -set-parsed :args ::set-parsed-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L400
(s/def ::add-chrono-changed-listener-args (args ::j/wip))
(defn -add-chrono-changed-listener [this listener] (wip ::-add-chrono-changed-listener))
(s/fdef -add-chrono-changed-listener :args ::add-chrono-changed-listener-args :ret ::j/void)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeParseContext.java#L423
(s/def ::set-parsed-leap-second-args (args))
(defn -set-parsed-leap-second [this] (wip ::-set-parsed-leap-second))
(s/fdef -set-parsed-leap-second :args ::set-parsed-leap-second-args :ret ::j/void)

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
(s/def ::char-equals-ignore-case-args (args ::j/char ::j/char))
(defn charEqualsIgnoreCase [c1 c2] (wip ::charEqualsIgnoreCase))
(s/fdef charEqualsIgnoreCase :args ::char-equals-ignore-case-args :ret ::j/boolean)
