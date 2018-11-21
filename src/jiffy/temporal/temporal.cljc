(ns jiffy.temporal.temporal
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/Temporal.java
(defprotocol ITemporal
  (with [this adjuster] [this field new-value])
  (plus [this amount] [this amount-to-add unit])
  (minus [this amount] [this amount-to-subtract unit])
  (until [this end-exclusive unit]))

(s/def ::temporal ::j/wip)
