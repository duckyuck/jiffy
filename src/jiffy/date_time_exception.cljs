(ns jiffy.date-time-exception)

(defn DateTimeException
  ([s] (DateTimeException s nil nil))
  ([s m] (DateTimeException s m nil))
  ([s m e] (ex-info s (or m {}))))
