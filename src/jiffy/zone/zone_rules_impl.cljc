(ns jiffy.zone.zone-rules-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

(defrecord ZoneRules [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-rules (j/constructor-spec ZoneRules create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-rules)

(defmacro args [& x] `(s/tuple ::zone-rules ~@x))

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L197
  ([offset]  (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/zone/ZoneRules.java#L176
  ([base-standard-offset base-wall-offset standard-offset-transition-list transition-list last-rules] (wip ::of)))
(s/fdef of :args ::of-args :ret ::zone-rules)
