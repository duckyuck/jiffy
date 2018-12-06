(ns jiffy.zone-region-impl
  (:require [jiffy.asserts :as assert]
            [jiffy.exception :refer [try* ex DateTimeException]]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]))

(defrecord ZoneRegion [id rules])

(defn create [id rules]
  (->ZoneRegion id rules))

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
