(ns jiffy.chrono.chronology
  (:refer-clojure :exclude [range])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java
(defprotocol IChronology
  (get-id [this])
  (get-calendar-type [this])
  (date [this temporal] [this proleptic-year month day-of-month] [this era year-of-era month day-of-month])
  (date-year-day [this proleptic-year day-of-year] [this era year-of-era day-of-year])
  (date-epoch-day [this epoch-day])
  (date-now [this] [this date-now--overloaded-param])
  (local-date-time [this temporal])
  (zoned-date-time [this temporal] [this instant zone])
  (is-leap-year [this proleptic-year])
  (proleptic-year [this era year-of-era])
  (era-of [this era-value])
  (eras [this])
  (range [this field])
  (get-display-name [this style locale])
  (resolve-date [this field-values resolver-style])
  (period [this years months days])
  (epoch-second [this proleptic-year month day-of-month hour minute second zone-offset] [this era year-of-era month day-of-month hour minute second zone-offset]))

(s/def ::chronology #(satisfies? IChronology %))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L182
(s/def ::from-args ::j/wip)
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L229
(s/def ::of-locale-args ::j/wip)
(defn of-locale [locale] (wip ::of-locale))
(s/fdef of-locale :args ::of-locale-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L254
(s/def ::of-args ::j/wip)
(defn of [id] (wip ::of))
(s/fdef of :args ::of-args :ret ::chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L268
(defn get-available-chronologies [] (wip ::get-available-chronologies))
(s/fdef get-available-chronologies :ret ::j/wip)
