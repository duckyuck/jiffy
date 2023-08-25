(ns jiffy.edn
  (:require [clojure.edn :as edn]
            #?(:cljs [cljs.reader :as reader])
            ;; [jiffy.instant-2 #?@(:cljs [:refer [Instant]])]
            [jiffy.instant-impl #?@(:cljs [:refer [Instant]])]
            [jiffy.duration-impl #?@(:cljs [:refer [Duration]])]
            [jiffy.temporal.temporal-queries]
            [jiffy.temporal.value-range #?@(:cljs [:refer [ValueRange]])]
            [jiffy.temporal.temporal-query #?@(:cljs [:refer [TemporalQuery]])]
            [jiffy.temporal.chrono-field #?@(:cljs [:refer [ChronoField]])]
            [jiffy.temporal.chrono-unit #?@(:cljs [:refer [ChronoUnit]])])
  #?(:clj (:import [java.io Writer]
                   ;; [jiffy.instant_2 Instant]
                   [jiffy.instant_impl Instant]
                   [jiffy.duration_impl Duration]
                   [jiffy.temporal.value_range ValueRange]
                   [jiffy.temporal.temporal_query TemporalQuery]
                   [jiffy.temporal.chrono_field ChronoField]
                   [jiffy.temporal.chrono_unit ChronoUnit])))

(defn to-string [tag f x]
  (let [x (f x)]
    (str "#jiffy/" tag (when (string? x) " ") (pr-str x))))

(defn ->map [x] (into {} x))

(def tags
  `{;; :instant-2 {:record Instant
    ;;             :read-fn 'jiffy.instant-2/map->Instant
    ;;             :write-fn '->map}
    :instant {:record Instant
              :read-fn 'jiffy.instant-impl/map->Instant
              :write-fn '->map}
    :duration {:record Duration
               :read-fn 'jiffy.duration-impl/map->Duration
               :write-fn '->map}
    :query {:record TemporalQuery
            :read-fn 'jiffy.temporal.temporal-queries/name->query
            :write-fn :name}
    :field {:record ChronoField
            :read-fn 'jiffy.temporal.chrono-field/valueOf
            :write-fn :enum-name}
    :unit {:record ChronoUnit
           :read-fn 'jiffy.temporal.chrono-unit/value-of
           :write-fn :enum-name}})
