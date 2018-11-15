(ns jiffy.zone.zone-rules-exception
  (:require [jiffy.date-time-exception :refer [IDateTimeException]]))

(defrecord ZoneRulesException [message data cause]
  IDateTimeException)

(defn zone-rules-exception
  ([message] (zone-rules-exception message {}))
  ([message data] (zone-rules-exception message data nil))
  ([message data cause]
   #?(:clj (if cause
             (java.time.zone.ZoneRulesException. (str message data) cause)
             (java.time.zone.ZoneRulesException. (str message data)))
      :cljs (->ZoneRulesException message data cause))))
