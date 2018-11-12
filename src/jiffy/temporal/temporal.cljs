(ns jiffy.temporal.temporal
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java
(defprotocol ITemporal
  (with [this adjuster] [this field new-value])
  (plus [this amount] [this amount-to-add unit])
  (minus [this amount] [this amount-to-subtract unit])
  (until [this end-exclusive unit]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L189
(defn -with [this adjuster] (wip ::-with))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L261
(defn -plus [this amount] (wip ::-plus))

(defn -minus
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L333
  ([this amount] (wip ::-minus))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java#L369
  ([this amount-to-subtract unit] (wip ::-minus)))

