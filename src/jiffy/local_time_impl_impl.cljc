(ns jiffy.local-time-impl-impl
  (:require [jiffy.asserts :as asserts]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.temporal.temporal-queries :as temporal-queries]))

(def-constructor from :jiffy.local-time-impl/local-time
  [temporal ::temporal-accessor/temporal-accessor]
  (asserts/require-non-nil temporal "temporal")
  (if-let [time (temporal-accessor/query temporal (temporal-queries/local-time))]
    time
    (throw (ex DateTimeException
               (str "Unable to obtain LocalTime from TemporalAccessor: " temporal " of type " (type temporal))
               {:temporal temporal}))))
