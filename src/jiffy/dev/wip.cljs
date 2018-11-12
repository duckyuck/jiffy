(ns jiffy.dev.wip)

(defn wip [where]
  (throw (ex-info (str "not implemented: " where) {})))
