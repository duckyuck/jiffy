(ns jiffy.temporal.value-range
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java
(defprotocol IValueRange
  (isFixed [this])
  (getMinimum [this])
  (getLargestMinimum [this])
  (getSmallestMaximum [this])
  (getMaximum [this])
  (isIntValue [this])
  (isValidValue [this value])
  (isValidIntValue [this value])
  (checkValidValue [this value field])
  (checkValidIntValue [this value field]))

(defrecord ValueRange [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L203
(defn -is-fixed [this] (wip ::-is-fixed))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L216
(defn -get-minimum [this] (wip ::-get-minimum))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L228
(defn -get-largest-minimum [this] (wip ::-get-largest-minimum))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L240
(defn -get-smallest-maximum [this] (wip ::-get-smallest-maximum))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L252
(defn -get-maximum [this] (wip ::-get-maximum))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L269
(defn -is-int-value [this] (wip ::-is-int-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L281
(defn -is-valid-value [this value] (wip ::-is-valid-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L294
(defn -is-valid-int-value [this value] (wip ::-is-valid-int-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L309
(defn -check-valid-value [this value field] (wip ::-check-valid-value))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L328
(defn -check-valid-int-value [this value field] (wip ::-check-valid-int-value))

(extend-type ValueRange
  IValueRange
  (isFixed [this] (-is-fixed this))
  (getMinimum [this] (-get-minimum this))
  (getLargestMinimum [this] (-get-largest-minimum this))
  (getSmallestMaximum [this] (-get-smallest-maximum this))
  (getMaximum [this] (-get-maximum this))
  (isIntValue [this] (-is-int-value this))
  (isValidValue [this value] (-is-valid-value this value))
  (isValidIntValue [this value] (-is-valid-int-value this value))
  (checkValidValue [this value field] (-check-valid-value this value field))
  (checkValidIntValue [this value field] (-check-valid-int-value this value field)))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L125
  ([min max] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L146
  ([min max-smallest max-largest] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L165
  ([min-smallest min-largest max-smallest max-largest] (wip ::of)))
