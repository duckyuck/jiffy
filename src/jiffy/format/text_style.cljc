(ns jiffy.format.text-style
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defprotocol ITextStyle
  (isStandalone [this])
  (asStandalone [this])
  (asNormal [this])
  (toCalendarStyle [this])
  (zoneNameStyleIndex [this]))

(defrecord TextStyle [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L135
(defn -is-standalone [this] (wip ::-is-standalone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L143
(defn -as-standalone [this] (wip ::-as-standalone))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L152
(defn -as-normal [this] (wip ::-as-normal))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L161
(defn -to-calendar-style [this] (wip ::-to-calendar-style))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L174
(defn -zone-name-style-index [this] (wip ::-zone-name-style-index))

(extend-type TextStyle
  ITextStyle
  (isStandalone [this] (-is-standalone this))
  (asStandalone [this] (-as-standalone this))
  (asNormal [this] (-as-normal this))
  (toCalendarStyle [this] (-to-calendar-style this))
  (zoneNameStyleIndex [this] (-zone-name-style-index this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defn values [] (wip ::values))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def SHORT ::SHORT--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def FULL_STANDALONE ::FULL_STANDALONE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def FULL ::FULL--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def SHORT_STANDALONE ::SHORT_STANDALONE--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def NARROW ::NARROW--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(def NARROW_STANDALONE ::NARROW_STANDALONE--not-implemented)