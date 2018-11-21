(ns jiffy.conversion)

(defmulti jiffy->java type)
(defmethod jiffy->java :default [jiffy-object] jiffy-object)

(defmulti same? (fn [jiffy-object java-object] (type jiffy-object)))
(defmethod same? :default [jiffy-object java-object] (= jiffy-object java-object))
