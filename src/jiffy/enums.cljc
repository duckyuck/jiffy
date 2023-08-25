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

;; TODO - implement `value-of`, mimicing Java's Enum/valueOf, throwing exception
;; if given unknown id to resolve. Useful for enums that have an EDN representation that we
;; do not have control over. See e.g. `jiffy.edn` and `jiffy.temporal.chrono-field/valueOf`
