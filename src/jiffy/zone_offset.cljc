(ns jiffy.zone-offset
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]
            [jiffy.temporal.temporal :as Temporal]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]
            [jiffy.temporal.temporal-adjuster :as TemporalAdjuster]
            [jiffy.temporal.temporal-field :as TemporalField]
            [jiffy.temporal.temporal-query :as TemporalQuery]
            [jiffy.temporal.value-range :as ValueRange]
            [jiffy.time-comparable :as TimeComparable]
            [jiffy.zone-id :as ZoneId]
            [jiffy.zone-offset-impl :refer [create #?@(:cljs [ZoneOffset])] :as impl]
            [jiffy.zone.zone-rules-impl :as ZoneRules])
  #?(:clj (:import [jiffy.zone_offset_impl ZoneOffset])))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java
(defprotocol IZoneOffset
  (getTotalSeconds [this]))

(s/def ::zone-offset ::impl/zone-offset)

(defmacro args [& x] `(s/tuple ::zone-offset ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L473
(s/def ::get-total-seconds-args (args))
(defn -get-total-seconds [this] (wip ::-get-total-seconds))
(s/fdef -get-total-seconds :args ::get-total-seconds-args :ret ::j/int)

(extend-type ZoneOffset
  IZoneOffset
  (getTotalSeconds [this] (-get-total-seconds this)))

;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L703
(s/def ::compare-to-args (args ::zone-offset))
(defn -compare-to [this compare-to--overloaded-param] (wip ::-compare-to))
(s/fdef -compare-to :args ::compare-to-args :ret ::j/int)

(extend-type ZoneOffset
  TimeComparable/ITimeComparable
  (compareTo [this compare-to--overloaded-param] (-compare-to this compare-to--overloaded-param)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L491
(s/def ::get-id-args (args))
(defn -get-id [this] (wip ::-get-id))
(s/fdef -get-id :args ::get-id-args :ret string?)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L504
(s/def ::get-rules-args (args))
(defn -get-rules [this] (wip ::-get-rules))
(s/fdef -get-rules :args ::get-rules-args :ret ::ZoneRules/zone-rules)

(extend-type ZoneOffset
  ZoneId/IZoneId
  (getId [this] (-get-id this))
  (getRules [this] (-get-rules this)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L529
(s/def ::is-supported-args (args ::TemporalField/temporal-field))
(defn -is-supported [this field] (wip ::-is-supported))
(s/fdef -is-supported :args ::is-supported-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L560
(s/def ::range-args (args ::TemporalField/temporal-field))
(defn -range [this field] (wip ::-range))
(s/fdef -range :args ::range-args :ret ::ValueRange/value-range)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L590
(s/def ::get-args (args ::TemporalField/temporal-field))
(defn -get [this field] (wip ::-get))
(s/fdef -get :args ::get-args :ret ::j/int)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L622
(s/def ::get-long-args (args ::TemporalField/temporal-field))
(defn -get-long [this field] (wip ::-get-long))
(s/fdef -get-long :args ::get-long-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L652
(s/def ::query-args (args ::TemporalQuery/temporal-query))
(defn -query [this query] (wip ::-query))
(s/fdef -query :args ::query-args :ret ::j/wip)

(extend-type ZoneOffset
  TemporalAccessor/ITemporalAccessor
  (isSupported [this field] (-is-supported this field))
  (range [this field] (-range this field))
  (get [this field] (-get this field))
  (getLong [this field] (-get-long this field))
  (query [this query] (-query this query)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L684
(s/def ::adjust-into-args (args ::Temporal/temporal))
(defn -adjust-into [this temporal] (wip ::-adjust-into))
(s/fdef -adjust-into :args ::adjust-into-args :ret ::Temporal/temporal)

(extend-type ZoneOffset
  TemporalAdjuster/ITemporalAdjuster
  (adjustInto [this temporal] (-adjust-into this temporal)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L202
(s/def ::of-args (args string?))
(defn of [offset-id] (wip ::of))
(s/fdef of :args ::of-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L282
(s/def ::of-hours-args (args ::j/int))
(defn ofHours [hours] (wip ::ofHours))
(s/fdef ofHours :args ::of-hours-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L299
(s/def ::of-hours-minutes-args (args ::j/int ::j/int))
(defn ofHoursMinutes [hours minutes] (wip ::ofHoursMinutes))
(s/fdef ofHoursMinutes :args ::of-hours-minutes-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L316
(s/def ::of-hours-minutes-seconds-args (args ::j/int ::j/int ::j/int))
(defn ofHoursMinutesSeconds [hours minutes seconds] (wip ::ofHoursMinutesSeconds))
(s/fdef ofHoursMinutesSeconds :args ::of-hours-minutes-seconds-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L343
(s/def ::from-args (args ::TemporalAccessor/temporal-accessor))
(defn from [temporal] (wip ::from))
(s/fdef from :args ::from-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L413
(s/def ::of-total-seconds-args (args ::j/int))
(defn ofTotalSeconds [total-seconds] (wip ::ofTotalSeconds))
(s/fdef ofTotalSeconds :args ::of-total-seconds-args :ret ::zone-offset)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L151
(def UTC ::UTC--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L155
(def MIN ::MIN--not-implemented)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/ZoneOffset.java#L159
(def MAX ::MAX--not-implemented)
