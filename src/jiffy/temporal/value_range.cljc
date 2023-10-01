(ns jiffy.temporal.value-range
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
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
  ([min max]
   (if (> min max)
     (throw (ex JavaIllegalArgumentException "Minimum value must be less than maximum value" {:min min :max max}))
     (create min min max max)))

  ([min max-smallest max-largest]
   (create min min max-smallest max-largest))

  ([min-smallest min-largest max-smallest max-largest]
   (cond
     (> min-smallest min-largest)
     (throw (ex JavaIllegalArgumentException "Smallest minimum value must be less than largest minimum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))

     (> max-smallest max-largest)
     (throw (ex JavaIllegalArgumentException "Smallest maximum value must be less than largest maximum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))

     (> min-largest max-largest)
     (throw (ex JavaIllegalArgumentException "Minimum value must be less than maximum value" {:min-smallest min-smallest :min-largest min-largest :max-smallest max-smallest :max-largest max-largest}))

     :else
     (->ValueRange min-smallest min-largest max-smallest max-largest))))
(s/def ::value-range (j/constructor-spec ValueRange create ::create-args))
(s/fdef create :args ::create-args :ret ::value-range)

(defmacro args [& x] `(s/tuple ::value-range ~@x))

(def-method is-fixed ::j/boolean
  [this ::value-range]
  (and (= (:min-smallest this) (:min-largest this))
       (= (:max-smallest this) (:max-largest this))))

(def-method get-minimum ::j/long
  [this ::value-range]
  (:min-smallest this))

(def-method get-largest-minimum ::j/long
  [this ::value-range]
  (:min-largest this))

(def-method get-smallest-maximum ::j/long
  [this ::value-range]
  (:max-smallest this))

(def-method get-maximum ::j/long
  [this ::value-range]
  (:max-largest this))

(def-method is-int-value ::j/boolean
  [this ::value-range]
  (and (>= (get-minimum this) math/integer-min-value)
       (<= (get-maximum this) math/integer-max-value)))

(def-method is-valid-value ::j/boolean
  [this ::value-range
   value ::j/long]
  (and (>= value (get-minimum this))
       (<= value (get-maximum this))))

(def-method is-valid-int-value ::j/boolean
  [this ::value-range
   value ::j/long]
  (and (is-int-value this)
       (is-valid-value this value)))

;; TODO: improve error message:
;; Jiffy example: "Invalid value for -1 (valid values jiffy.temporal.value_range.ValueRange@55c26df2): jiffy.temporal.chrono_field.ChronoField@7858bcad"
;; Java example: "Invalid value for NanoOfSecond (valid values 0 - -1/0): -1"
(defn --gen-invalid-field-message [this field value]
  (if field
    (str "Invalid value for " (pr-str field) " (valid values " (pr-str this) "): " (pr-str value))
    (str "Invalid value (valid values " (pr-str this) "): " (pr-str value))))

(def-method check-valid-value ::j/long
  [this ::value-range
   value ::j/long
   field ::temporal-field/temporal-field]
  (when-not (is-valid-value this value)
    (throw (ex DateTimeException (--gen-invalid-field-message this value field))))
  value)

(s/def ::check-valid-int-value-args (args ))
(def-method check-valid-int-value ::j/int
  [this ::value-range
   value ::j/long
   field ::temporal-field/temporal-field]
  (when-not (is-valid-int-value this value)
    (throw (ex DateTimeException (--gen-invalid-field-message this value field))))
  (int value))

(extend-type ValueRange
  value-range/IValueRange
  (is-fixed [this] (is-fixed this))
  (get-minimum [this] (get-minimum this))
  (get-largest-minimum [this] (get-largest-minimum this))
  (get-smallest-maximum [this] (get-smallest-maximum this))
  (get-maximum [this] (get-maximum this))
  (is-int-value [this] (is-int-value this))
  (is-valid-value [this value] (is-valid-value this value))
  (is-valid-int-value [this value] (is-valid-int-value this value))
  (check-valid-value [this value field] (check-valid-value this value field))
  (check-valid-int-value [this value field] (check-valid-int-value this value field)))

(s/def ::of-args ::create-args)
(defn of [& args] (apply create args))
(s/fdef of :args ::of-args :ret ::value-range)
