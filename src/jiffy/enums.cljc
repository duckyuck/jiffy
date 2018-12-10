(ns jiffy.enums)

(defmacro defenum [constructor-fn enums]
  (let [ordinal (atom -1)
        next-ordinal #(swap! ordinal inc)]
    `(do
       (def ~'enums (atom {}))
       ~@(for [[sym args] (partition 2 enums)]
           `(do
              (def ~sym (~constructor-fn ~(next-ordinal) ~(str sym) ~@args))
              (swap! ~'enums assoc ~(str sym) ~sym))))))
