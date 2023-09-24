(ns jiffy.zone.zone-rules-provider-test
  #?(:clj (:require [clojure.test :refer [deftest is]]
                    [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

#?(:clj
   (deftest get-available-zone-ids-test
     (is (= (jiffy.zone.zone-rules-provider/get-available-zone-ids)
            (java.time.zone.ZoneRulesProvider/getAvailableZoneIds)))))

(test-static-fn! jiffy.zone.zone-rules-provider/get-rules)
