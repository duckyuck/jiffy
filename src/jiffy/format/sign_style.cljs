(ns jiffy.format.sign-style
  (:require [jiffy.dev.wip :refer [wip]]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defprotocol ISignStyle
  (parse [this positive strict fixed-width]))

(defrecord SignStyle [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java#L125
(defn -parse [this positive strict fixed-width] (wip ::-parse))

(extend-type SignStyle
  ISignStyle
  (parse [this positive strict fixed-width] (-parse this positive strict fixed-width)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defn valueOf [value-of--unknown-param-name] (wip ::valueOf))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defn values [] (wip ::values))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(def EXCEEDS_PAD ::EXCEEDS_PAD--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(def NORMAL ::NORMAL--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(def ALWAYS ::ALWAYS--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(def NEVER ::NEVER--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(def NOT_NEGATIVE ::NOT_NEGATIVE--not-implemented)