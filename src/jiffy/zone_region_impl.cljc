(ns jiffy.zone-region-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.specs :as j]
            [jiffy.asserts :as assert]
            [jiffy.exception :refer [try* ex DateTimeException]]
            [jiffy.zone.zone-rules-provider :as zone-rules-provider]
            [jiffy.protocols.zone.zone-rules :as zone-rules]))

(def-record ZoneRegion ::zone-region
  [id ::j/zone-id
   rules ::zone-rules/zone-rules])

(defn- check-name [zone-id]
  (when (< (count zone-id) 2)
    (throw (ex DateTimeException (str "Invalid ID for region-based ZoneId, invalid format: " zone-id))))
  (when-not (re-find #"^[a-zA-Z][a-zA-Z/0-9~\._+-]+$" zone-id)
    (throw (ex DateTimeException (str "Invalid ID for region-based ZoneId, invalid format: "  zone-id)))))

(def-constructor of-id ::zone-region
  [zone-id ::j/zone-id
   check-available ::j/boolean]
  (assert/require-non-nil zone-id "zone-id")
  (check-name zone-id)
  (try*
   (->ZoneRegion zone-id (zone-rules-provider/get-rules zone-id true))
   (catch :default e
     (when check-available
       (throw e)))))
