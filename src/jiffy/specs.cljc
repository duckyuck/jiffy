(ns jiffy.specs
  (:require [jiffy.math :as math]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(defn int-in [from-inclusive to-exclusive]
  (s/with-gen int?
    (fn [] (gen/fmap int (s/gen (s/int-in from-inclusive to-exclusive))))))

(s/def ::int (int-in math/integer-min-value (inc math/integer-max-value)))
(s/def ::pos-int (int-in 0 (inc math/integer-max-value)))
(s/def ::long (s/int-in math/long-min-value math/long-max-value))
(s/def ::pos-long (s/int-in 0 math/long-max-value))
(s/def ::boolean boolean?)
(s/def ::char char?)
(s/def ::void nil?)
(s/def ::any any?)
(s/def ::char-sequence string?)

;; TODO:
;; These specs are currently only used for generating data used in API
;; parity tests, and they are limited in ways to accomodate for testing.
;; We'll need to change these specs to define API constraints, and rather
;; override the specs during testing.

(s/def ::nano (s/int-in 100000 1000000000))  ;; include one decimal of milli
(s/def ::milli pos-int?)

;; divide by 1000 due to OpenJDK bug. toEpochMilli fails for MAX seconds
(s/def ::second (s/int-in (/ -31557014167219200 1000) (/ 31556889864403200 1000)))
(s/def ::year (int-in math/integer-min-value math/integer-max-value))
(s/def ::month (int-in math/integer-min-value math/integer-max-value))
(s/def ::day (int-in math/integer-min-value math/integer-max-value))

(s/def ::nano-of-second (int-in 0 1000000000))
(s/def ::second-of-minute (int-in 0 60))
(s/def ::minute-of-hour (int-in 0 60))
(s/def ::hour-of-day (int-in 0 24))
(s/def ::day-of-year (int-in 1 367))
(s/def ::day-of-month (int-in 1 32))
(s/def ::month-of-year (int-in 1 13))

(defn constructor-spec [record-type constructor param-spec]
  (s/with-gen #(instance? record-type %)
    (fn [] (gen/fmap #(apply constructor %) (s/gen param-spec)))))

(defn constructor-spec2 [constructor-fn spec]
  (s/with-gen spec
    (fn [] (gen/fmap #(apply constructor-fn %) (s/gen (:args (s/get-spec constructor-fn)))))))

(defn constructor-spec3 [constructor-fn]
  (let [spec (s/get-spec constructor-fn)]
    (s/with-gen (:ret spec)
      (fn [] (gen/fmap #(apply constructor-fn %) (s/gen (:args spec)))))))

(defn- zone-id? [s]
  (re-find #"^[a-zA-Z][a-zA-Z/0-9~\._+-]+$" s))

(s/def ::zone-id (s/and string? zone-id?))

;; Placeholder for incomplete specs
(s/def ::wip (s/and keyword? #(= % ::wip)))
(s/def ::locale any?)
