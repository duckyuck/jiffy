(ns jiffy.temporal.unsupported-temporal-type-exception
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.date-time-exception :as DateTimeException]))

(defrecord UnsupportedTemporalTypeException [])

;; FIXME: no implementation found from inherited class class java.time.DateTimeException

