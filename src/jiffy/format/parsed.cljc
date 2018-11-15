(ns jiffy.format.parsed
  (:refer-clojure :exclude [resolve ])
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java
(defprotocol IParsed
  (copy [this])
  (resolve [this resolver-style resolver-fields]))

(defrecord Parsed [])

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L168
(defn -copy [this] (wip ::-copy))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L248
(defn -resolve [this resolver-style resolver-fields] (wip ::-resolve))

(extend-type Parsed
  IParsed
  (copy [this] (-copy this))
  (resolve [this resolver-style resolver-fields] (-resolve this resolver-style resolver-fields)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L180
(defn -is-supported [this field] (wip ::-is-supported))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L190
(defn -get-long [this field] (wip ::-get-long))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L210
(defn -query [this query] (wip ::-query))

(extend-type Parsed
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

