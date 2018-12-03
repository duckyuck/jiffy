(ns jiffy.conversion)

(defmulti jiffy->java type)
(defmethod jiffy->java :default
  [jiffy-object]
  (if (record? jiffy-object)
    (throw (ex-info (str "Shit! We're missing implementation of multimethod 'jiffy.conversion/jiffy->java' for record "
                         (type jiffy-object) ". Please do some more programming!") {:record jiffy-object}))
    jiffy-object))

(defmulti same? (fn [jiffy-object java-object] (type jiffy-object)))
(defmethod same? :default
  [jiffy-object java-object]
  (if (record? jiffy-object)
    (throw (ex-info (str "Darnit! We're missing implementation of multimethod 'jiffy.conversion/same?' for record "
                         (type jiffy-object) ". Please do some more programming!") {:record jiffy-object}))
    (= jiffy-object java-object)))

(defmethod jiffy->java clojure.lang.PersistentVector
  [coll]
  (mapv jiffy->java coll))

(defmethod same? clojure.lang.PersistentVector
  [jiffy-coll java-coll]
  (every? true? (map same? jiffy-coll java-coll)))
