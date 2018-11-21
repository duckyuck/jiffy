(ns jiffy.zone.zone-rules-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

(defrecord ZoneRules [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::zone-rules (j/constructor-spec ZoneRules create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-rules)
