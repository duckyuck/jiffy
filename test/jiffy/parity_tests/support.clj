(ns jiffy.parity-tests.support
  (:require [clojure.string :as str]
            [jiffy.conversion :as conversion]
            [jiffy.exception :refer [try*]]))

(defn partition-between
  [pred? coll]
  (->> (map pred? coll (rest coll))
       (reductions not= true)
       (map list coll)
       (partition-by second)
       (map (partial map first))))

(defn split-at-java-case [s]
  (->> s
       (partition-between #(and (Character/isLowerCase %1)
                                (Character/isUpperCase %2)))
       (map #(apply str %))))

(defn kebab-case [s]
  (->> (split-at-java-case (str s))
       (map str/lower-case)
       (str/join "-")))

(defmacro same? [call-jiffy-form call-java-form]
  `(conversion/same?
    (try* ~call-jiffy-form (catch :default e# e#))
    (try* ~call-java-form (catch :default e# e#))))
