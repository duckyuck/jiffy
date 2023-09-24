(ns jiffy.zone.zone-rules-provider
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.specs :as j]
            [jiffy.zone.zone-rules-store :as store]))

(def-constructor get-available-zone-ids (s/coll-of ::j/zone-id)
  []
  (set (keys @store/zone-id->rules)))

(def-constructor get-rules ::zone-rules/zone-rules
  [zone-id ::j/zone-id
   for-caching ::j/boolean]
  (get @store/zone-id->rules zone-id))

(def-constructor get-versions (s/map-of string? ::zone-rules/zone-rules)
  [zone-id ::j/zone-id]
  {"1" (vals @store/zone-id->rules)})

(def-constructor refresh ::j/boolean
  []
  false)
