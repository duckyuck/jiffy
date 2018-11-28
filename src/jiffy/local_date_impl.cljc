(ns jiffy.local-date-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]))

(defrecord LocalDate [year month day])

(s/def ::create-args (s/tuple ::j/year ::j/month-of-year ::j/day-of-month))
(def create ->LocalDate)
(s/def ::local-date (j/constructor-spec LocalDate create ::create-args))
(s/fdef create :args ::create-args :ret ::local-date)
