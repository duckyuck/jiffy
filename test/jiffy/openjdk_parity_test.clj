(ns jiffy.openjdk-parity-test
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [jiffy.instant :as instant])
  (:import java.time.Instant))

(defn same-instant? [jiffy-instant jdk-instant]
  (= ((juxt instant/getEpochSecond instant/getNano) jiffy-instant)
     ((juxt (memfn getEpochSecond) (memfn getNano)) jdk-instant)))

(def of-epoch-second-prop
  (prop/for-all
   [v gen/int]
   (same-instant? (instant/ofEpochSecond v) (Instant/ofEpochSecond v))))

(tc/quick-check 10000 of-epoch-second-prop)
