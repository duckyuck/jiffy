(ns jiffy.chrono.iso-era
  (:refer-clojure :exclude [range get])
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.enums #?@(:clj [:refer [defenum]]) #?@(:cljs [:refer-macros [defenum]])]
            [jiffy.protocols.chrono.iso-era :as iso-era]
            [jiffy.chrono.era-defaults :as era-defaults]
            [jiffy.specs :as j]
            [jiffy.protocols.chrono.era :as era]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.format.text-style :as text-style]))

(def-record IsoEra ::iso-era
  [ordinal ::j/long
   enum-name string?])

(defenum ->IsoEra
  [BCE []
   CE []])

(def-constructor values (s/coll-of (partial satisfies? iso-era/IIsoEra))
  []
  (sort-by :ordinal (vals @enums)))

(def-method get-value ::j/int
  [this ::iso-era]
  (:ordinal this))

(def-method is-supported ::j/boolean
  [this ::iso-era
   field ::temporal-field/temporal-field]
  (era-defaults/is-supported this field))

(def-method range ::value-range/value-range
  [this ::iso-era
   field ::temporal-field/temporal-field]
  (era-defaults/range this field))

(def-method get ::j/int
  [this ::iso-era
   field ::temporal-field/temporal-field]
  (era-defaults/get this field))

(def-method get-long ::j/long
  [this ::iso-era
   field ::temporal-field/temporal-field]
  (era-defaults/get-long this field))

(def-method query ::j/wip
  [this ::iso-era
   query ::temporal-query/temporal-query]
  (era-defaults/query this query))

(def-method adjust-into ::temporal/temporal
  [this ::iso-era
   temporal ::temporal/temporal]
  (era-defaults/adjust-into this temporal))

(def-method get-display-name string?
  [this ::iso-era
   style ::text-style/text-style
   locale ::j/locale]
  (era-defaults/get-display-name this style locale))

(extend-type IsoEra
  era/IEra
  (get-value [this] (get-value this))
  (get-display-name [this style locale] (get-display-name this style locale)))

(extend-type IsoEra
  temporal-accessor/ITemporalAccessor
  (is-supported [this field] (is-supported this field))
  (range [this field] (range this field))
  (get [this field] (get this field))
  (get-long [this field] (get-long this field))
  (query [this q] (query this q)))

(extend-type IsoEra
  temporal-adjuster/ITemporalAdjuster
  (adjust-into [this temporal] (adjust-into this temporal)))
