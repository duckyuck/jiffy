(ns jiffy.temporal.unsupported-temporal-type-exception)

;; FIXME: no implementation found from inherited class class java.time.DateTimeException
(defn UnsupportedTemporalTypeException
  ([s] (UnsupportedTemporalTypeException s nil nil))
  ([s m] (UnsupportedTemporalTypeException s m nil))
  ([s m e] (ex-info s (or m {}))))
