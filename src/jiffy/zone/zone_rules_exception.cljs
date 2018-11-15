(ns jiffy.zone.zone-rules-exception
  (:require [jiffy.date-time-exception :refer [IDateTimeException]]))

(defrecord ZoneRulesException [message data cause]
  IDateTimeException)

(defn unsupported-temporal-type-exception
  ([message] (unsupported-temporal-type-exception message {}))
  ([message data] (unsupported-temporal-type-exception message data nil))
  ([message data cause] (->ZoneRulesException message data cause)))

(defn ZoneRulesException
  ([s] (ZoneRulesException s nil nil))
  ([s m] (ZoneRulesException s m nil))
  ([s m e] (ex-info s (or m {}))))
