(ns jiffy.zone-region
  (:require [clojure.spec.alpha :as s]
            [jiffy.asserts :as assert]
            [jiffy.exception :refer [ex DateTimeException #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.specs :as j]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.zone-region-impl :as impl :refer [#?@(:cljs [ZoneRegion])]]
            [jiffy.protocols.zone.zone-rules :as zone-rules]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider])
  #?(:clj (:import [jiffy.zone_region_impl ZoneRegion])))

(s/def ::zone-region ::impl/zone-region)

(defmacro args [& x] `(s/tuple ::zone-region ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L169
(s/def ::get-id-args (args))
(defn -get-id [this]
  (:id this))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L174
(s/def ::get-rules-args (args))
(defn -get-rules [this]
  (if (not (nil? (:rules this)))
    (:rules this)
    (zone-rules-provider/get-rules (:id this) false)))
(s/fdef -get-rules :args ::get-rules-args :ret ::zone-rules/zone-rules)

(extend-type ZoneRegion
  zone-id/IZoneId
  (get-id [this] (-get-id this))
  (get-rules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneRegion.java#L114
(s/def ::of-id-args (args string? ::j/boolean))
(defn of-id [zone-id check-available]
  (impl/of-id zone-id check-available))
(s/fdef of-id :args ::of-id-args :ret ::zone-region)
