(ns jiffy.chrono.chronology
  (:refer-clojure :exclude [range])
  (:require #?(:clj [clojure.spec.alpha :as s])
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [cljs.spec.alpha :as s])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chrono-local-date :as chrono-local-date]
            [jiffy.protocols.chrono.chrono-local-date-time :as chrono-local-date-time]
            [jiffy.protocols.chrono.chrono-period :as chrono-period]
            [jiffy.protocols.chrono.chrono-zoned-date-time :as chrono-zoned-date-time]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.clock :as clock]
            [jiffy.protocols.format.resolver-style :as resolver-style]
            [jiffy.protocols.format.text-style :as text-style]
            [jiffy.protocols.instant :as instant]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.time-comparable :as time-comparable]
            [jiffy.protocols.zone-id :as zone-id]
            [jiffy.protocols.zone-offset :as zone-offset]
            [jiffy.specs :as j]
            [jiffy.asserts :as asserts]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.chrono.iso-chronology :as iso-chronology]))

(s/def ::chronology ::chronology/chronology)

(def-constructor from ::chronology
  [temporal ::temporal-accessor/temporal-accessor]
  (asserts/require-non-nil temporal "temporal")
  (or (temporal-accessor/query temporal (temporal-queries/chronology))
      iso-chronology/INSTANCE))
