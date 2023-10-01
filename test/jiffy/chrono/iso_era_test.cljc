(ns jiffy.chrono.iso-era-test
  #?(:clj (:require [jiffy.support-clj :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support])
     :cljs (:require [jiffy.support-cljs :refer [test-proto-fn test-proto-fn! test-static-fn test-static-fn!] :as support :include-macros true])))

(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.chrono.era/get-value)
;; (test-proto-fn! jiffy.chrono.iso-era jiffy.protocols.chrono.era/get-display-name)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-accessor/is-supported)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-accessor/range)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-accessor/get)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-accessor/get-long)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-accessor/query)
(test-proto-fn jiffy.chrono.iso-era jiffy.protocols.temporal.temporal-adjuster/adjust-into)

(test-static-fn jiffy.chrono.iso-era/values)
