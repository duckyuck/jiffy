(ns jiffy.temporal.value-range
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.exception :refer [DateTimeException JavaIllegalArgumentException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.protocols.temporal.value-range :as value-range]
            [jiffy.protocols.temporal.temporal-field :as temporal-field]
            [jiffy.math :as math]))

(defrecord ValueRange [min-smallest min-largest max-smallest max-largest])

(s/def ::create-args
  (s/or :arity-2 (s/and (s/cat :min ::j/long
                               :max ::j/long)
                        #(<= (:min %) (:max %)))
        :arity-3 (s/and (s/cat :min ::j/long
                               :max-smallest ::j/long
                               :max-largest ::j/long)
                        #(<= (:max-smallest %) (:max-largest %))
                        #(<= (:min %) (:max-largest %)))
        :arity-4 (s/and (s/cat :min-smallest ::j/long
                               :min-largest ::j/long
                               :max-smallest ::j/long
                               :max-largest ::j/long)
                        #(<= (:min-smallest %) (:min-largest %))
                        #(<= (:max-smallest %) (:max-largest %))
                        #(<= (:min-largest %) (:max-largest %)))))
(defn create
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L125
  ([min max]
   (if (> min max)
     (throw (ex JavaIllegalArgumentException "Minimum value must be less than maximum value" {:min min :max max}))
     (create min min max max)))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L146
  ([min max-smallest max-largest]
   (create min min max-smallest max-largest))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L165
  ([min-smallest min-largest max-smallest max-largest]
   (cond
     (> min-smallest min-largest)
     (throw (ex JavaIllegalArgumentException "Smallest minimum value must be less than largest minimum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))

     (> max-smallest max-largest)
     (throw (ex JavaIllegalArgumentException "Smallest maximum value must be less than largest maximum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))
;; [jiffy.dev.wip :refer [wip]]
;; [jiffy.protocols.temporal.temporal-field :as temporal-field]
;; [jiffy.protocols.temporal.value-range :as value-range]
;; [jiffy.specs :as j]

     (> min-largest max-largest)
     (throw (ex JavaIllegalArgumentException "Minimum value must be less than maximum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))

     :else
     (->ValueRange min-smallest min-largest max-smallest max-largest))))
(s/def ::value-range (j/constructor-spec ValueRange create ::create-args))
(s/fdef create :args ::create-args :ret ::value-range)

(defmacro args [& x] `(s/tuple ::value-range ~@x))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L203
(s/def ::is-fixed-args (args))
(defn -is-fixed [this]
  (and (= (:min-smallest this) (:min-largest this))
       (= (:max-smallest this) (:max-largest this))))
(s/fdef -is-fixed :args ::is-fixed-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L216
(s/def ::get-minimum-args (args))
(defn -get-minimum [this] (:min-smallest this))
(s/fdef -get-minimum :args ::get-minimum-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L228
(s/def ::get-largest-minimum-args (args))
(defn -get-largest-minimum [this] (:min-largest this))
(s/fdef -get-largest-minimum :args ::get-largest-minimum-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L240
(s/def ::get-smallest-maximum-args (args))
(defn -get-smallest-maximum [this] (:max-smallest this))
(s/fdef -get-smallest-maximum :args ::get-smallest-maximum-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L252
(s/def ::get-maximum-args (args))
(defn -get-maximum [this] (:max-largest this))
(s/fdef -get-maximum :args ::get-maximum-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L269
(s/def ::is-int-value-args (args))
(defn -is-int-value [this]
  (and (>= (-get-minimum this) math/integer-min-value)
       (<= (-get-maximum this) math/integer-max-value)))
(s/fdef -is-int-value :args ::is-int-value-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L281
(s/def ::is-valid-value-args (args ::j/long))
(defn -is-valid-value [this value]
  (and (>= value (-get-minimum this))
       (<= value (-get-maximum this))))
(s/fdef -is-valid-value :args ::is-valid-value-args :ret ::j/boolean)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L294
(s/def ::is-valid-int-value-args (args ::j/long))
(defn -is-valid-int-value [this value]
  (and (-is-int-value this)
       (-is-valid-value this value)))
(s/fdef -is-valid-int-value :args ::is-valid-int-value-args :ret ::j/boolean)

;; TODO: improve error message:
;; Jiffy example: "Invalid value for -1 (valid values jiffy.temporal.value_range.ValueRange@55c26df2): jiffy.temporal.chrono_field.ChronoField@7858bcad"
;; Java example: "Invalid value for NanoOfSecond (valid values 0 - -1/0): -1"
(defn --gen-invalid-field-message [this field value]
  (if field
    (str "Invalid value for " field " (valid values " this "): " value)
    (str "Invalid value (valid values " this "): " value)))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L309
(s/def ::check-valid-value-args (args ::j/long ::temporal-field/temporal-field))
(defn -check-valid-value [this value field]
  (when-not (-is-valid-value this value)
    (throw (ex DateTimeException (--gen-invalid-field-message this value field))))
  value)
(s/fdef -check-valid-value :args ::check-valid-value-args :ret ::j/long)

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/temporal/ValueRange.java#L328
(s/def ::check-valid-int-value-args (args ::j/long ::temporal-field/temporal-field))
(defn -check-valid-int-value [this value field]
  (when-not (-is-valid-int-value this value)
    (throw (ex DateTimeException (--gen-invalid-field-message this value field))))
  (int value))
(s/fdef -check-valid-int-value :args ::check-valid-int-value-args :ret ::j/int)

(extend-type ValueRange
  value-range/IValueRange
  (is-fixed [this] (-is-fixed this))
  (get-minimum [this] (-get-minimum this))
  (get-largest-minimum [this] (-get-largest-minimum this))
  (get-smallest-maximum [this] (-get-smallest-maximum this))
  (get-maximum [this] (-get-maximum this))
  (is-int-value [this] (-is-int-value this))
  (is-valid-value [this value] (-is-valid-value this value))
  (is-valid-int-value [this value] (-is-valid-int-value this value))
  (check-valid-value [this value field] (-check-valid-value this value field))
  (check-valid-int-value [this value field] (-check-valid-int-value this value field)))

(s/def ::of-args ::create-args)
(defn of [& args] (apply create args))
(s/fdef of :args ::of-args :ret ::value-range)
