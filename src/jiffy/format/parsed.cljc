(ns jiffy.format.parsed
  (:refer-clojure :exclude [resolve ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.resolver-style :as ResolverStyle]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java
(defprotocol IParsed
  (copy [this])
  (resolve [this resolver-style resolver-fields]))

(defrecord Parsed [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::parsed (j/constructor-spec Parsed create ::create-args))
(s/fdef create :args ::create-args :ret ::parsed)

(defmacro args [& x] `(s/tuple ::parsed ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L168
(s/def ::copy-args (args))
(defn -copy [this] (wip ::-copy))
(s/fdef -copy :args ::copy-args :ret ::parsed)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L248
(s/def ::resolve-args (args ::ResolverStyle/resolver-style ::j/wip))
(defn -resolve [this resolver-style resolver-fields] (wip ::-resolve))
(s/fdef -resolve :args ::resolve-args :ret ::TemporalAccessor/temporal-accessor)

(extend-type Parsed
  IParsed
  (copy [this] (-copy this))
  (resolve [this resolver-style resolver-fields] (-resolve this resolver-style resolver-fields)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L180
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L190
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L210
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Parsed
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

