(ns jiffy.require-jiffy-test
  (:require [clojure.test :refer [deftest is]]
            [jiffy.dev.main :as main]))

(deftest require-all-jiffy-nses
  (is (= (main/dummy)
         ::main/ok)))
