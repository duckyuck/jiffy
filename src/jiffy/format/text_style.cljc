(ns jiffy.format.text-style
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defprotocol ITextStyle
  (is-standalone [this])
  (as-standalone [this])
  (as-normal [this])
  (to-calendar-style [this])
  (zone-name-style-index [this]))

(defrecord TextStyle [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::text-style (j/constructor-spec TextStyle create ::create-args))
(s/fdef create :args ::create-args :ret ::text-style)

(defmacro args [& x] `(s/tuple ::text-style ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L135
(s/def ::is-standalone-args (args))
(defn -is-standalone [this] (wip ::-is-standalone))
(s/fdef -is-standalone :args ::is-standalone-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L143
(s/def ::as-standalone-args (args))
(defn -as-standalone [this] (wip ::-as-standalone))
(s/fdef -as-standalone :args ::as-standalone-args :ret ::text-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L152
(s/def ::as-normal-args (args))
(defn -as-normal [this] (wip ::-as-normal))
(s/fdef -as-normal :args ::as-normal-args :ret ::text-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L161
(s/def ::to-calendar-style-args (args))
(defn -to-calendar-style [this] (wip ::-to-calendar-style))
(s/fdef -to-calendar-style :args ::to-calendar-style-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java#L174
(s/def ::zone-name-style-index-args (args))
(defn -zone-name-style-index [this] (wip ::-zone-name-style-index))
(s/fdef -zone-name-style-index :args ::zone-name-style-index-args :ret ::j/int)

(extend-type TextStyle
  ITextStyle
  (is-standalone [this] (-is-standalone this))
  (as-standalone [this] (-as-standalone this))
  (as-normal [this] (-as-normal this))
  (to-calendar-style [this] (-to-calendar-style this))
  (zone-name-style-index [this] (-zone-name-style-index this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::text-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/TextStyle.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

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