(ns jiffy.specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/def ::int int?)
(s/def ::long int?)
(s/def ::boolean boolean?)
(s/def ::char char?)
(s/def ::void nil?)

;; TODO:
;; These specs are currently only used for generating data used in API
;; parity tests, and they are limited in ways to accomodate for testing.
;; We'll need to change these specs to define API constraints, and rather
;; override the specs during testing.

(s/def ::nano (s/int-in 100000 1000000000))  ;; include one decimal of milli
(s/def ::milli pos-int?)

;; divide by 1000 due to OpenJDK bug. toEpochMilli fails for MAX seconds
(s/def ::second (s/int-in (/ -31557014167219200 1000) (/ 31556889864403200 1000)))

(s/def ::nano-of-second (s/int-in 0 1000000000))
(s/def ::second-of-minute (s/int-in 0 60))
(s/def ::minute-of-hour (s/int-in 0 60))
(s/def ::hour-of-day (s/int-in 0 24))

(defn constructor-spec [record-type constructor param-spec]
  (s/with-gen #(instance? record-type %)
    (fn [] (gen/fmap #(apply constructor %) (s/gen param-spec)))))

;; Placeholder for incomplete specs
(s/def ::wip (s/and keyword? #(= % ::wip)))
