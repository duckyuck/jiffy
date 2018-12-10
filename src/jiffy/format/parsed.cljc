(ns jiffy.format.parsed
  (:refer-clojure :exclude [resolve ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.format.parsed :as parsed]
            [jiffy.protocols.format.resolver-style :as resolver-style]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.specs :as j]))

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
(s/def ::resolve-args (args ::resolver-style/resolver-style ::j/wip))
(defn -resolve [this resolver-style resolver-fields] (wip ::-resolve))
(s/fdef -resolve :args ::resolve-args :ret ::temporal-accessor/temporal-accessor)

(extend-type Parsed
  parsed/IParsed
  (copy [this] (-copy this))
  (resolve [this resolver-style resolver-fields] (-resolve this resolver-style resolver-fields)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L180
(s/def ::is-supported-args (args ::temporal-field/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L190
(s/def ::get-long-args (args ::temporal-field/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java#L210
(s/def ::query-args (args ::temporal-query/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type Parsed
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (-is-supported this field))
  (get-long [this field] (-get-long this field))
  (query [this query] (-query this query)))

