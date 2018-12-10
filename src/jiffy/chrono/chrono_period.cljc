(ns jiffy.chrono.chrono-period
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]
            [jiffy.specs :as j]))

(s/def ::chrono-period ::chrono-period/chrono-period)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/ChronoPeriod.java#L116
(s/def ::between-args (s/tuple ::chrono-local-date/chrono-local-date ::chrono-local-date/chrono-local-date))
(defn between [start-date-inclusive end-date-exclusive]
  (chrono-local-date/until start-date-inclusive end-date-exclusive))
(s/fdef between :args ::between-args :ret ::chrono-period)
