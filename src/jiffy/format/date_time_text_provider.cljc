(ns jiffy.format.date-time-text-provider
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.chrono.chronology :as chronology]
            [jiffy.protocols.format.date-time-text-provider :as date-time-text-provider]
            [jiffy.protocols.format.text-style :as text-style]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.specs :as j]))

(defrecord DateTimeTextProvider [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::date-time-text-provider (j/constructor-spec DateTimeTextProvider create ::create-args))
(s/fdef create :args ::create-args :ret ::date-time-text-provider)

(defmacro args [& x] `(s/tuple ::date-time-text-provider ~@x))

(s/def ::get-text-args (args ::j/wip))
(defn -get-text
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L143
  ([this field value style locale] (wip ::-get-text))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L166
  ([this chrono field value style locale] (wip ::-get-text)))
(s/fdef -get-text :args ::get-text-args :ret string?)

(s/def ::get-text-iterator-args (args ::j/wip))
(defn -get-text-iterator
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L221
  ([this field style locale] (wip ::-get-text-iterator))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L246
  ([this chrono field style locale] (wip ::-get-text-iterator)))
(s/fdef -get-text-iterator :args ::get-text-iterator-args :ret ::j/wip)

(extend-type DateTimeTextProvider
  date-time-text-provider/IDateTimeTextProvider
  (get-text
    ([this field value style locale] (-get-text this field value style locale))
    ([this chrono field value style locale] (-get-text this chrono field value style locale)))
  (get-text-iterator
    ([this field style locale] (-get-text-iterator this field style locale))
    ([this chrono field style locale] (-get-text-iterator this chrono field style locale))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L125
(defn get-instance [] (wip ::get-instance))
(s/fdef get-instance :ret ::date-time-text-provider)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/DateTimeTextProvider.java#L511
(s/def ::get-localized-resource-args (args string? ::j/wip))
(defn get-localized-resource [key locale] (wip ::get-localized-resource))
(s/fdef get-localized-resource :args ::get-localized-resource-args :ret ::j/wip)
