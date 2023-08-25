(ns jiffy.zone-id
  (:require [clojure.spec.alpha :as s]
            [jiffy.asserts :as assert]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.exception :refer [try* ex DateTimeException JavaNullPointerException JavaIllegalArgumentException]]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.zone-offset-impl :as zone-offset]
            [jiffy.zone-region-impl :as zone-region]))

(s/def ::zone-id ::zone-id/zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L287
(defn get-available-zone-ids [] (wip ::get-available-zone-ids))
(s/fdef get-available-zone-ids :ret ::j/wip)

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

(s/def ::of-args (s/tuple ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L355
  ([zone-id]
   (of zone-id true))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L308
  ([zone-id arg]
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
(s/fdef of :args ::of-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L372
(s/def ::of-offset-args (s/tuple string? :ijffy.zone-offset/zone-offset))
(defn of-offset [prefix offset]
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
    (zone-region/create (str prefix (zone-id/get-id offset)) (zone-id/get-rules offset))

    :default
    (zone-region/create prefix (zone-id/get-rules offset))))
(s/fdef of-offset :args ::of-offset-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L459
(s/def ::from-args (s/tuple ::temporal-accessor/temporal-accessor))
(defn from [temporal]
  (let [obj (temporal-accessor/query temporal (temporal-queries/zone))]
    (if (nil? obj)
      (throw (ex DateTimeException (str "Unable to obtain ZoneId from TemporalAccessor: "
                                        temporal " of type " (type temporal))))
      obj)))
(s/fdef from :args ::from-args :ret ::zone-id)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L224
(def SHORT_IDS ::SHORT_IDS--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneId.java#L271
(defn system-default []
  #?(:clj (.toZoneId (java.util.TimeZone/getDefault))))
(s/fdef system-default :ret ::zone-id)
