(ns jiffy.format.sign-style
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defprotocol ISignStyle
  (parse [this positive strict fixed-width]))

(defrecord SignStyle [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::sign-style (j/constructor-spec SignStyle create ::create-args))
(s/fdef create :args ::create-args :ret ::sign-style)

(defmacro args [& x] `(s/tuple ::sign-style ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java#L125
(s/def ::parse-args (args ::j/boolean ::j/boolean ::j/boolean))
(defn -parse [this positive strict fixed-width] (wip ::-parse))
(s/fdef -parse :args ::parse-args :ret ::j/boolean)

(extend-type SignStyle
  ISignStyle
  (parse [this positive strict fixed-width] (-parse this positive strict fixed-width)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::sign-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/SignStyle.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

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