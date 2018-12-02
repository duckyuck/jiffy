(ns jiffy.specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/def ::int int?)
(s/def ::long int?)
(s/def ::boolean boolean?)
(s/def ::char char?)
(s/def ::void nil?)

(defn int-in [from-inclusive to-exclusive]
  (s/with-gen int?
    (fn [] (gen/fmap int (s/gen (s/int-in from-inclusive to-exclusive))))))

;; TODO:
;; These specs are currently only used for generating data used in API
;; parity tests, and they are limited in ways to accomodate for testing.
;; We'll need to change these specs to define API constraints, and rather
;; override the specs during testing.

(s/def ::nano (s/int-in 100000 1000000000))  ;; include one decimal of milli
(s/def ::milli pos-int?)

;; divide by 1000 due to OpenJDK bug. toEpochMilli fails for MAX seconds
(s/def ::second (s/int-in (/ -31557014167219200 1000) (/ 31556889864403200 1000)))
(s/def ::year (int-in -999999999 1000000000))

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

(defn- zone-id? [s]
  (re-find #"^[a-zA-Z][a-zA-Z/0-9~\._+-]+$" s))

(s/def ::zone-id (s/and string? zone-id?))

;; Placeholder for incomplete specs
(s/def ::wip (s/and keyword? #(= % ::wip)))
