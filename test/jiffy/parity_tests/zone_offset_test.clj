(ns jiffy.parity-tests.zone-offset-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [jiffy.conversion :as conversion]
            [jiffy.parity-tests.support :refer [test-proto-fn test-static-fn]]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as ChronoField]
            [jiffy.temporal.temporal-field :as TemporalField]))

(s/def ::TemporalField/temporal-field
  (s/with-gen ::TemporalField/temporal-field
    (fn [] (gen/one-of (for [enum (ChronoField/values)]
                         (gen/return enum))))))

(test-proto-fn jiffy.zone-offset jiffy.zone-offset/getTotalSeconds)
(test-proto-fn jiffy.zone-offset jiffy.time-comparable/compareTo)
(test-proto-fn jiffy.zone-offset jiffy.zone-id/getId)
;; (test-proto-fn jiffy.zone-offset jiffy.zone-id/getRules)
(test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-accessor/isSupported)
(test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-accessor/range)
(test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-accessor/get)
(test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-accessor/getLong)
;; (test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-accessor/query)
;; (test-proto-fn jiffy.zone-offset jiffy.temporal.temporal-adjuster/adjustInto)

;; TODO: these fail due to spec generator producing Long, not Integer as required by
;; call to java. Coerce from Long to Integer in `jiffy.parity-tests.support/invoke-java`

;; (test-static-fn jiffy.zone-offset/ofHours)
;; (test-static-fn jiffy.zone-offset/ofHoursMinutes)
;; (test-static-fn jiffy.zone-offset/ofHoursMinutesSeconds)
;; (test-static-fn jiffy.zone-offset/ofTotalSeconds)
;; (test-static-fn jiffy.zone-offset/of)
;; (test-static-fn jiffy.zone-offset/from)
