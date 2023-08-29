(ns jiffy.chrono.chrono-local-date-time-defaults
  (:refer-clojure :exclude [format ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.format.date-time-formatter :as date-time-formatter]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.local-time :as local-time]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.math :as math]))

(s/def ::chrono-local-date-time ::chrono-local-date-time/chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L192
(s/def ::get-chronology-args ::j/wip)
(defn -get-chronology [this] (wip ::-get-chronology))
(s/fdef -get-chronology :args ::get-chronology-args :ret ::chronology/chronology)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L236
(s/def ::is-supported-args ::j/wip)
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L272
(s/def ::with-args ::j/wip)
(defn -with [this adjuster] (wip ::-with))
(s/fdef -with :args ::with-args :ret ::chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L290
(s/def ::plus-args ::j/wip)
(defn -plus [this amount] (wip ::-plus))
(s/fdef -plus :args ::plus-args :ret ::chrono-local-date-time)

(s/def ::minus-args ::j/wip)
(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L308
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L318
  ([this amount-to-subtract unit] (wip ::-minus)))
(s/fdef -minus :args ::minus-args :ret ::chrono-local-date-time)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L343
(s/def ::query-args ::j/wip)
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L384
(s/def ::adjust-into-args ::j/wip)
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::temporal/temporal)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L404
(s/def ::format-args ::j/wip)
(defn -format [this formatter] (wip ::-format))
(s/fdef -format :args ::format-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L452
(s/def ::to-instant-args ::j/wip)
(defn -to-instant [this offset] (wip ::-to-instant))
(s/fdef -to-instant :args ::to-instant-args :ret ::instant/instant)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L470
(s/def ::to-epoch-second-args ::j/wip)
(defn -to-epoch-second [this offset]
  (let [epoch-day (-> this chrono-local-date-time/to-local-date chrono-local-date/to-epoch-day)
        secs (->> this
                  chrono-local-date-time/to-local-time
                  local-time/to-second-of-day
                  (math/add-exact (math/multiply-exact epoch-day 86400)))]
    (->> offset zone-offset/get-total-seconds (math/subtract-exact secs))))
(s/fdef -to-epoch-second :args ::to-epoch-second-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L506
(s/def ::compare-to-args ::j/wip)
(defn -compare-to [this other] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L531
(s/def ::is-after-args ::j/wip)
(defn -is-after [this other] (wip ::-is-after))
(s/fdef -is-after :args ::is-after-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L552
(s/def ::is-before-args ::j/wip)
(defn -is-before [this other] (wip ::-is-before))
(s/fdef -is-before :args ::is-before-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoLocalDateTime.java#L573
(s/def ::is-equal-args ::j/wip)
(defn -is-equal [this other] (wip ::-is-equal))
(s/fdef -is-equal :args ::is-equal-args :ret ::j/boolean)
