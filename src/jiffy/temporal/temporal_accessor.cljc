(ns jiffy.temporal.temporal-accessor
  (:refer-clojure :exclude [get range])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.unsupported-temporal-type-exception :refer [unsupported-temporal-type-exception]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java
(defprotocol ITemporalAccessor
  (isSupported [this field])
  (range [this field])
  (get [this field])
  (getLong [this field])
  (query [this query]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L169
(defn -range [this field]
  (if (satisfies? ChronoField/IChronoField field)
    (if (isSupported this field)
      (range this field)
      (throw (unsupported-temporal-type-exception (str "Unsupported field: " field))))
    (TemporalField/rangeRefinedBy field this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L217
(defn -get [this field] (wip ::-get))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/TemporalAccessor.java#L308
(defn -query [this query] (wip ::-query))

