(ns jiffy.chrono.era-defaults
  (:refer-clojure :exclude [range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.format.text-style :as text-style]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.temporal.chrono-field :as chrono-field]
            [jiffy.temporal.temporal-accessor-defaults :as temporal-accessor-defaults]
            [jiffy.temporal.temporal-queries :as temporal-queries]
            [jiffy.temporal.chrono-unit :as chrono-unit]))

(s/def ::era ::era/era)

(def-method is-supported ::j/boolean
  [this ::era
   field ::temporal-field/temporal-field]
  (if (chrono-field/chrono-field? field)
    (= field chrono-field/ERA)
    (and field (temporal-field/is-supported-by field this))))

(def-method range ::value-range/value-range
  [this ::era
   field ::temporal-field/temporal-field]
  (temporal-accessor-defaults/-range this field))

(def-method get ::j/int
  [this ::era
   field ::temporal-field/temporal-field]
  (if (= field chrono-field/ERA)
    (era/get-value this)
    (temporal-accessor-defaults/-get this field)))

(def-method get-long ::j/long
  [this ::era
   field ::temporal-field/temporal-field]
  (cond
    (= field chrono-field/ERA)
    (era/get-value this)

    (chrono-field/chrono-field? field)
    (throw (ex UnsupportedTemporalTypeException (str "Unsupported field: " field)
               {:this this :field field}))

    :default
    (temporal-field/get-from field this)))

(def-method query ::j/wip
  [this ::era
   query ::temporal-query/temporal-query]
  (if (= query (temporal-queries/precision))
    chrono-unit/ERAS
    (temporal-accessor-defaults/-query this query)))

(def-method adjust-into ::temporal/temporal
  [this ::era
   temporal ::temporal/temporal]
  (temporal/with temporal chrono-field/ERA (era/get-value this)))

(def-method get-display-name string?
  [this ::era
   style ::text-style/text-style
   locale ::j/locale]
  (wip ::-get-display-name))
