(ns jiffy.zone-region-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.asserts :as assert]
            [jiffy.exception :refer [try* ex DateTimeException]]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]
            [jiffy.protocols.zone.zone-rules :as zone-rules]))

(defrecord ZoneRegion [id rules])

(s/def ::create-args (s/tuple ::j/zone-id ::zone-rules/zone-rules))
(defn create [id rules]
  (->ZoneRegion id rules))
(s/def ::zone-region (j/constructor-spec ZoneRegion create ::create-args))
(s/fdef create :args ::create-args :ret ::zone-region)

(defn- check-name [zone-id]
  (when (< (count zone-id) 2)
    (throw (ex DateTimeException (str "Invalid ID for region-based ZoneId, invalid format: " zone-id))))
  (when-not (re-find #"^[a-zA-Z][a-zA-Z/0-9~\._+-]+$" zone-id)
    (throw (ex DateTimeException (str "Invalid ID for region-based ZoneId, invalid format: "  zone-id)))))

(defn of-id [zone-id check-available]
  (assert/require-non-nil zone-id "zone-id")
  (check-name zone-id)
  (try*
   (create zone-id (zone-rules-provider/get-rules zone-id true))
   (catch :default e
     (when check-available
       (throw e)))))
