(ns jiffy.zone-region
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.asserts :as assert]
            [jiffy.exception :refer [ex DateTimeException #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.specs :as j]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.zone-region-impl :as impl :refer [#?@(:cljs [ZoneRegion])]]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]
            [jiffy.protocols.string :as string])
  #?(:clj (:import [jiffy.zone_region_impl ZoneRegion])))

(s/def ::zone-region ::impl/zone-region)

(def-method -get-id string?
  [this ::zone-region]
  (:id this))

(def-method -get-rules ::zone-rules/zone-rules
  [this ::zone-region]
  (if (not (nil? (:rules this)))
    (:rules this)
    (zone-rules-provider/get-rules (:id this) false)))

(extend-type ZoneRegion
  zone-id/IZoneId
  (get-id [this] (-get-id this))
  (get-rules [this] (-get-rules this)))

(def-constructor of-id ::zone-region
  [zone-id ::j/zone-id
   check-available ::j/boolean]
  (impl/of-id zone-id check-available))

(def-method to-string string?
  [this ::zone-region]
  (:id this))

(extend-type ZoneRegion
  string/IString
  (to-string [this] (to-string this)))
