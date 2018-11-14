(ns java-time-clj.main
  (:require [jiffy.duration :as Duration]
            [jiffy.instant :as Instant]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-accessor :as TemporalAccessor]))

(enable-console-print!)

(let [a (Instant/now)
      b (Instant/plusSeconds a 60)]
  (Duration/between a b))

(TemporalAccessor/getLong (Instant/now) ChronoField/INSTANT_SECONDS)

