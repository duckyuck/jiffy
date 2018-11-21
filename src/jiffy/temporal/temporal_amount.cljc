(ns jiffy.temporal.temporal-amount
  (:refer-clojure :exclude [get ])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-unit :as TemporalUnit]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAmount.java
(defprotocol ITemporalAmount
  (get [this unit])
  (getUnits [this])
  (addTo [this temporal])
  (subtractFrom [this temporal]))


