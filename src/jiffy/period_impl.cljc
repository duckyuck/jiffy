(ns jiffy.period-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(def-record Period ::period
  [years ::j/year
   months ::j/month
   days ::j/day])

(def-constructor create ::period
  [years ::j/year
   months ::j/month
   days ::j/day]
  (->Period years months days))

(def of create)
