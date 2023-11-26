(ns jiffy.zone.zone-rules-store)

(def db (atom {}))

(defn get-all-zone-ids []
  (set (keys @db)))

(defn get-all-rules []
  (set (vals @db)))

(defn find-rules [zone-id]
  (get @db zone-id))

(defn reset [zone-id->rules]
  (reset! db zone-id->rules)
  :ok)
