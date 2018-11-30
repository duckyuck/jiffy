(ns jiffy.chrono.chronology-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.chrono.chrono-local-date-defaults :as chrono-local-date]
            [jiffy.chrono.chrono-local-date-time-defaults :as chrono-local-date-time]
            [jiffy.chrono.chrono-period :as chrono-period]
            [jiffy.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.chrono.era :as era]
            [jiffy.clock :as clock]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as resolver-style]
            [jiffy.format.text-style :as text-style]
            [jiffy.instant :as instant]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.value-range :as value-range]
            [jiffy.time-comparable :as time-comparable]
            [jiffy.zone-id :as zone-id]
            [jiffy.zone-offset :as zone-offset]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L316
(s/def ::date-args ::j/wip)
(defn -date [this era year-of-era month day-of-month] (wip ::-date))
(s/fdef -date :args ::date-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L347
(s/def ::date-year-day-args ::j/wip)
(defn -date-year-day [this era year-of-era day-of-year] (wip ::-date-year-day))
(s/fdef -date-year-day :args ::date-year-day-args :ret ::chrono-local-date/chrono-local-date)

(s/def ::date-now-args ::j/wip)
(defn -date-now
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L126
  ([this] (wip ::-date-now))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L410
  ([this date-now--overloaded-param] (wip ::-date-now)))
(s/fdef -date-now :args ::date-now-args :ret ::chrono-local-date/chrono-local-date)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L475
(s/def ::local-date-time-args ::j/wip)
(defn -local-date-time [this temporal] (wip ::-local-date-time))
(s/fdef -local-date-time :args ::local-date-time-args :ret ::chrono-local-date-time/chrono-local-date-time)

(s/def ::zoned-date-time-args ::j/wip)
(defn -zoned-date-time
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L507
  ([this temporal] (wip ::-zoned-date-time))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L533
  ([this instant zone] (wip ::-zoned-date-time)))
(s/fdef -zoned-date-time :args ::zoned-date-time-args :ret ::chrono-zoned-date-time/chrono-zoned-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L645
(s/def ::get-display-name-args ::j/wip)
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L716
(s/def ::period-args ::j/wip)
(defn -period [this years months days] (wip ::-period))
(s/fdef -period :args ::period-args :ret ::chrono-period/chrono-period)

(s/def ::epoch-second-args ::j/wip)
(defn -epoch-second
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L739
  ([this proleptic-year month day-of-month hour minute second zone-offset] (wip ::-epoch-second))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Chronology.java#L768
  ([this era year-of-era month day-of-month hour minute second zone-offset] (wip ::-epoch-second)))
(s/fdef -epoch-second :args ::epoch-second-args :ret ::j/long)
