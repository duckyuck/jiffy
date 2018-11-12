(ns jiffy.format.date-time-text-provider
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java
(defprotocol IDateTimeTextProvider
  (getText [this field value style locale] [this chrono field value style locale])
  (getTextIterator [this field style locale] [this chrono field style locale]))

(defrecord DateTimeTextProvider [])

(defn -get-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L143
  ([this field value style locale] (wip ::-get-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L166
  ([this chrono field value style locale] (wip ::-get-text)))

(defn -get-text-iterator
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L221
  ([this field style locale] (wip ::-get-text-iterator))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L246
  ([this chrono field style locale] (wip ::-get-text-iterator)))

(extend-type DateTimeTextProvider
  IDateTimeTextProvider
  (getText
    ([this field value style locale] (-get-text this field value style locale))
    ([this chrono field value style locale] (-get-text this chrono field value style locale)))
  (getTextIterator
    ([this field style locale] (-get-text-iterator this field style locale))
    ([this chrono field style locale] (-get-text-iterator this chrono field style locale))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L125
(defn getInstance [] (wip ::getInstance))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L511
(defn getLocalizedResource [key locale] (wip ::getLocalizedResource))
