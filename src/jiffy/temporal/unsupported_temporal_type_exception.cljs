(ns jiffy.temporal.unsupported-temporal-type-exception
  (:require [jiffy.date-time-exception :refer [IDateTimeException]]))

(defrecord UnsupportedTemporalTypeException [message data cause]
  IDateTimeException)

(defn unsupported-temporal-type-exception
  ([message] (unsupported-temporal-type-exception message {}))
  ([message data] (unsupported-temporal-type-exception message data nil))
  ([message data cause] (->UnsupportedTemporalTypeException message data cause)))

