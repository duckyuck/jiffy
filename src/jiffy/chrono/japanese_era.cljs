(ns jiffy.chrono.japanese-era
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.chrono.era :as Era]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java
(defprotocol IJapaneseEra
  (getPrivateEra [this])
  (getAbbreviation [this])
  (getName [this]))

(defrecord JapaneseEra [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L190
(defn -get-private-era [this] (wip ::-get-private-era))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L261
(defn -get-abbreviation [this] (wip ::-get-abbreviation))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L261
(defn -get-name [this] (wip ::-get-name))

(extend-type JapaneseEra
  IJapaneseEra
  (getPrivateEra [this] (-get-private-era this))
  (getAbbreviation [this] (-get-abbreviation this))
  (getName [this] (-get-name this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L105
(defn -get-value [this] (wip ::-get-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L256
(defn -get-display-name [this style locale] (wip ::-get-display-name))

(extend-type JapaneseEra
  Era/IEra
  (getValue [this] (-get-value this))
  (getDisplayName [this style locale] (-get-display-name this style locale)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L363
(defn -range [this field] (wip ::-range))

(extend-type JapaneseEra
  TemporalAccessor/ITemporalAccessor
  (range [this field] (-range this field)))

;; FIXME: no implementation found from inherited class interface java.time.temporal.TemporalAdjuster

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L206
(defn of [japanese-era] (wip ::of))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L224
(defn valueOf [japanese-era] (wip ::valueOf))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L245
(defn values [] (wip ::values))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L278
(defn from [date] (wip ::from))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L291
(defn toJapaneseEra [private-era] (wip ::toJapaneseEra))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/JapaneseEra.java#L300
(defn privateEraFrom [iso-date] (wip ::privateEraFrom))

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