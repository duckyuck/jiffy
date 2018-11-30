(ns jiffy.chrono.japanese-era
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.era :as era]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as text-style]
            [jiffy.local-date :as local-date]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.value-range :as value-range]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java
(defprotocol IJapaneseEra
  (get-private-era [this])
  (get-abbreviation [this])
  (get-name [this]))

(defrecord JapaneseEra [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::japanese-era (j/constructor-spec JapaneseEra create ::create-args))
(s/fdef create :args ::create-args :ret ::japanese-era)

(defmacro args [& x] `(s/tuple ::japanese-era ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L190
(s/def ::get-private-era-args (args))
(defn -get-private-era [this] (wip ::-get-private-era))
(s/fdef -get-private-era :args ::get-private-era-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L261
(s/def ::get-abbreviation-args (args))
(defn -get-abbreviation [this] (wip ::-get-abbreviation))
(s/fdef -get-abbreviation :args ::get-abbreviation-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L261
(s/def ::get-name-args (args))
(defn -get-name [this] (wip ::-get-name))
(s/fdef -get-name :args ::get-name-args :ret string?)

(extend-type JapaneseEra
  IJapaneseEra
  (get-private-era [this] (-get-private-era this))
  (get-abbreviation [this] (-get-abbreviation this))
  (get-name [this] (-get-name this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L105
(s/def ::get-value-args (args))
(defn -get-value [this] (wip ::-get-value))
(s/fdef -get-value :args ::get-value-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L256
(s/def ::get-display-name-args (args ::text-style/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

(extend-type JapaneseEra
  era/IEra
  (get-value [this] (-get-value this))
  (get-display-name [this style locale] (-get-display-name this style locale)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L363
(s/def ::range-args (args ::temporal-field/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::value-range/value-range)

(extend-type JapaneseEra
  temporal-accessor/ITemporalAccessor
  (range [this field] (-range this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L206
(s/def ::of-args (args ::j/int))
(defn of [japanese-era] (wip ::of))
(s/fdef of :args ::of-args :ret ::japanese-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L224
(s/def ::value-of-args (args string?))
(defn value-of [japanese-era] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::japanese-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L245
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L278
(s/def ::from-args (args ::local-date/local-date))
(defn from [date] (wip ::from))
(s/fdef from :args ::from-args :ret ::japanese-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L291
(s/def ::to-japanese-era-args (args ::j/wip))
(defn to-japanese-era [private-era] (wip ::to-japanese-era))
(s/fdef to-japanese-era :args ::to-japanese-era-args :ret ::japanese-era)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L300
(s/def ::private-era-from-args (args ::local-date/local-date))
(defn private-era-from [iso-date] (wip ::private-era-from))
(s/fdef private-era-from :args ::private-era-from-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L106
(def ERA_OFFSET ::ERA_OFFSET--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L108
(def ERA_CONFIG ::ERA_CONFIG--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L114
(def MEIJI ::MEIJI--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L119
(def TAISHO ::TAISHO--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L124
(def SHOWA ::SHOWA--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L129
(def HEISEI ::HEISEI--not-implemented)