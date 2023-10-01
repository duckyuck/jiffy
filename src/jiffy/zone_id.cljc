(ns jiffy.zone-id
  (:require [clojure.spec.alpha :as s]
            [jiffy.asserts :as assert]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [try* ex DateTimeException JavaNullPointerException JavaIllegalArgumentException]]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.zone-offset-impl :as zone-offset]
            [jiffy.zone-region-impl :as zone-region]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]))

(s/def ::zone-id ::zone-id/zone-id)

(def-constructor get-available-zone-ids (s/coll-of ::j/zone-id)
  []
  (zone-rules-provider/get-available-zone-ids))

(declare of-offset)

(defn- of-with-prefix [zone-id prefix-length check-available]
  (let [prefix (.substring zone-id 0 prefix-length)]
    (cond
      (= (count zone-id) prefix-length)
      (of-offset prefix zone-offset/UTC)

      (and (not= (.charAt zone-id prefix-length) "+")
           (not= (.charAt zone-id prefix-length) "-"))
      (zone-region/of-id zone-id check-available)

      :default
      (try*
       (of-offset prefix (zone-offset/of (.substring zone-id prefix-length)))
       (catch DateTimeException e
         (throw (ex DateTimeException (str "Invalid ID for offset-based ZoneId: " zone-id) ex)))))))

(def-constructor of ::zone-id
  ([zone-id ::j/zone-id]
   (of zone-id true))

  ([zone-id ::j/zone-id
    arg (s/or :check-available ::j/boolean
              :alias-map (s/map-of string? string?))]
   (cond
     (boolean? arg)
     (do
       (assert/require-non-nil zone-id "zone-id")
       (cond
         (or (<= (count zone-id) 1)
              (.startsWith zone-id "+")
              (.startsWith zone-id "-"))
         (zone-offset/of zone-id)

         (or (.startsWith zone-id "UTC") (.startsWith zone-id "GMT"))
         (of-with-prefix zone-id 3 arg)

         (.startsWith zone-id "UT")
         (of-with-prefix zone-id 2 arg)

         :default
         (zone-region/of-id zone-id arg)))

     (map? arg)
     (do
       (assert/require-non-nil zone-id "zone-id")
       (of (assert/require-non-nil-else (get arg zone-id) zone-id)))

     :default
     (throw (ex JavaNullPointerException "Second argument to of should not be null")))))

(def-constructor of-offset ::zone-id
  [prefix string?
   offset ::zone-offset/zone-offset]
  (assert/require-non-nil prefix "prefix")
  (assert/require-non-nil offset "offset")
  (cond
    (= 0 (count prefix))
    offset

    (and (not= prefix "GMT")
         (not= prefix "UTC")
         (not= prefix "UT"))
    (throw (ex JavaIllegalArgumentException (str "prefix should be GMT, UTC or UT, is: " prefix)))

    (not= 0 (:total-seconds offset))
    (zone-region/->ZoneRegion (str prefix (zone-id/get-id offset)) (zone-id/get-rules offset))

    :default
    (zone-region/->ZoneRegion prefix (zone-id/get-rules offset))))

(def-constructor from ::zone-id
  [temporal ::temporal-accessor/temporal-accessor]
  (let [obj (temporal-accessor/query temporal (temporal-queries/zone))]
    (if (nil? obj)
      (throw (ex DateTimeException (str "Unable to obtain ZoneId from TemporalAccessor: "
                                        temporal " of type " (type temporal))))
      obj)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L224
(def SHORT_IDS ::SHORT_IDS--not-implemented)

(def-constructor system-default ::zone-id
  []
  #?(:clj
     (-> (java.util.TimeZone/getDefault)
         .getID
         of))
  #?(:cljs
     (-> (js/Intl.DateTimeFormat)
         (.resolvedOptions)
         .-timeZone
         of)))

