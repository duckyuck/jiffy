(ns jiffy.protocols.string)

(defprotocol IString
  (to-string [this]))

(defn delete-char-at [s idx]
  (let [[a b] (split-at idx s)]
    (str (apply str a) (apply str (rest b)))))

(defn substring [s idx]
  (->> (split-at idx s)
       second
       (apply str)))
