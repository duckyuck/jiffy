(ns jiffy.zone-id-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.format.text-style :as text-style]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-accessor :as temporal-acccessor]
            [jiffy.zone.zone-rules :as zone-rules]))

(defmacro args [& x] `(s/tuple ::j/wip ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L125
(s/def ::normalized-args (args))
(defn -normalized [this] (wip ::-normalized))
(s/fdef -normalized :args ::normalized-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L487
(s/def ::get-id-args (args))
(defn -getn-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L504
(s/def ::get-display-name-args (args ::text-style/text-style ::j/wip))
(defn -get-display-name [this style locale] (wip ::-get-display-name))
(s/fdef -get-display-name :args ::get-display-name-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L562
(s/def ::get-rules-args (args))
(defn -get-rules [this] (wip ::-get-rules))
(s/fdef -get-rules :args ::get-rules-args :ret ::zone-rules/zone-rules)
