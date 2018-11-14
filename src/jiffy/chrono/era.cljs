(ns jiffy.chrono.era
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Era.java
(defprotocol IEra
  (getValue [this])
  (getDisplayName [this style locale]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/chrono/Era.java#L320
(defn -get-display-name [this style locale] (wip ::-get-display-name))

;; TODO: port default methods from interface and delegate in IEra records
