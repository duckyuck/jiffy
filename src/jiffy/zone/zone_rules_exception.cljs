(ns jiffy.zone.zone-rules-exception)

;; FIXME: no implementation found from inherited class class java.time.DateTimeException
(defn ZoneRulesException
  ([s] (ZoneRulesException s nil nil))
  ([s m] (ZoneRulesException s m nil))
  ([s m e] (ex-info s (or m {}))))
