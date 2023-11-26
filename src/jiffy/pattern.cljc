(ns jiffy.pattern)

(defn pattern->str [pattern]
  #?(:clj (str pattern)
     :cljs (if-not (regexp? pattern)
             pattern
             (let [s (str pattern)]
               (->> s
                    (drop 1)
                    (drop-last 1)
                    (apply str))))))

(defn combine [& patterns]
  (re-pattern (apply str (map pattern->str patterns))))
