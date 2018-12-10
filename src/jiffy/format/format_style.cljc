(ns jiffy.format.format-style
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.format.format-style :as format-style]
            [jiffy.specs :as j]))

(defrecord FormatStyle [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::format-style (j/constructor-spec FormatStyle create ::create-args))
(s/fdef create :args ::create-args :ret ::format-style)

(defmacro args [& x] `(s/tuple ::format-style ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(s/def ::value-of-args (args string?))
(defn value-of [value-of--unknown-param-name] (wip ::value-of))
(s/fdef value-of :args ::value-of-args :ret ::format-style)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(defn values [] (wip ::values))
(s/fdef values :ret ::j/wip)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(def LONG ::LONG--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(def MEDIUM ::MEDIUM--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(def SHORT ::SHORT--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/FormatStyle.java
(def FULL ::FULL--not-implemented)