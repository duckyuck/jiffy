(ns jiffy.parity-tests.jiffy-parity-test
  (:require [cljs.reader :as reader]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            jiffy.chrono.abstract-chronology
            jiffy.chrono.chrono-local-date-impl
            jiffy.chrono.chrono-local-date-time-impl
            jiffy.chrono.chrono-period-impl
            jiffy.chrono.chrono-zoned-date-time-impl
            jiffy.chrono.hijrah-chronology
            jiffy.chrono.hijrah-chronology-impl
            jiffy.chrono.hijrah-date
            jiffy.chrono.hijrah-era
            jiffy.chrono.iso-chronology
            jiffy.chrono.iso-chronology-impl
            jiffy.chrono.iso-era
            jiffy.chrono.japanese-chronology
            jiffy.chrono.japanese-chronology-impl
            jiffy.chrono.japanese-date
            jiffy.chrono.japanese-era
            jiffy.chrono.minguo-chronology
            jiffy.chrono.minguo-chronology-impl
            jiffy.chrono.minguo-date
            jiffy.chrono.minguo-era
            jiffy.chrono.thai-buddhist-chronology
            jiffy.chrono.thai-buddhist-chronology-impl
            jiffy.chrono.thai-buddhist-date
            jiffy.chrono.thai-buddhist-era
            jiffy.clock
            jiffy.day-of-week
            ;; [jiffy.dev.defs-cljs :refer [def-record]]
            [jiffy.edn-cljs :include-macros true]
            jiffy.duration-impl
            jiffy.format.date-time-formatter
            jiffy.format.date-time-formatter-builder
            jiffy.format.date-time-parse-context
            jiffy.format.date-time-print-context
            jiffy.format.date-time-text-provider
            jiffy.format.decimal-style
            jiffy.format.format-style
            jiffy.format.parsed
            jiffy.format.resolver-style
            jiffy.format.sign-style
            jiffy.format.text-style
            [jiffy.helper.macros :refer-macros [load-regression-corpus-str]]
            ;; [jiffy.instant-2 :refer [Instant]]
            [jiffy.instant :refer [Instant]]
            jiffy.instant-impl
            jiffy.local-date-impl
            jiffy.local-date-time
            jiffy.local-date-time-impl
            jiffy.local-time-impl
            jiffy.month
            jiffy.month-day
            jiffy.offset-date-time-impl
            jiffy.offset-time-impl
            jiffy.period
            [jiffy.protocols.instant]
            jiffy.protocols.temporal.temporal-accessor
            jiffy.temporal.chrono-field
            jiffy.temporal.chrono-unit
            jiffy.temporal.iso-fields
            jiffy.temporal.julian-fields
            jiffy.temporal.temporal-query
            jiffy.temporal.value-range
            jiffy.temporal.week-fields
            jiffy.year
            jiffy.year-month
            jiffy.zoned-date-time-impl
            jiffy.zone-offset-impl
            jiffy.zone-region-impl
            jiffy.zone.tzdb-zone-rules-provider
            jiffy.zone.zone-offset-transition
            jiffy.zone.zone-offset-transition-rule
            jiffy.zone.zone-rules
            jiffy.zone.zone-rules-impl
            jiffy.zone.zone-rules-provider))

(comment

  :jiffy.edn/keep

  )

(defn load-corpus [] (cljs.reader/read-string (load-regression-corpus-str)))

;; (def-record Foo ::foo [a any? b any?])

;; (deftest duration
;;   ;; (cljs.reader/register-tag-parser! 'jiffy.duration_impl.Duration 'jiffy.duration-impl/map->Duration)
;;   (= (:seconds (cljs.reader/read-string "#jiffy.duration_impl.Duration{:seconds 3600, :nanos 0}"))
;;      3600))

;; (deftest foo
;;   (is (= (cljs.reader/read-string "#jiffy.parity_tests.jiffy_parity_test.Foo{:a 1, :b 2}")
;;          (Foo. 1 2))))


;; (deftest instant-2-reader-test
;;   (is (= (cljs.reader/read-string "#jiffy/instant-2 {:seconds 0, :nanos 100000}")
;;          (jiffy.instant-2/Instant. 0 100000))))

;; (deftest test-client
;;   (is (= (first (load-corpus))
;;          {:args [(jiffy.instant-2/->Instant 0 100000)
;;                  (jiffy.instant-2/->Instant 0 100001)]
;;           :fn 'jiffy.protocols.instant/is-before
;;           :result true})))



;; ((resolve 'jiffy.protocols.instant/get-nano) (jiffy.instant-2/->Instant 1 2))
(comment

  (doseq [{:keys [fn args result] :as test} (load-corpus)]
    (let [cljs-result (call fn args)]
      (when-not (= cljs-result result)
        (throw (ex-info "Fuckin fail!" {:test test :cljs-result cljs-result})))))

  ;; (jiffy.edn/instant-2 {:a 1})


  ;; (jiffy.instant-2/->Instant 0 100000)

  ;; (cljs.reader/register-tag-parser! 'jiffy/instant-2 jiffy.instant-2/map->Instant)

  ;; (cljs.reader/deregister-tag-parser! 'jiffy/instant-2)

  ;; (first (load-corpus))

  ;; (require 'cljs.reader)

  ;; (def a (defrecord Foo [a b]))
  ;; (pr-str (js/Date.))

  ;; (cljs.reader/read-string "#jiffy.parity_tests.jiffy_parity_test.Foo{:a 1, :b 2}")

  )
