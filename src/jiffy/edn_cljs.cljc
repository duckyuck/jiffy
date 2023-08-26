(ns jiffy.edn-cljs
  (:require [cljs.reader :as reader]
            [jiffy.edn :as edn :include-macros true]))

(defn gen-tag-writer [[tag {:keys [record write-fn]}]]
  (let [t (str (name tag))
        fn (if (seq? write-fn)
             (second write-fn)
             write-fn)]
    `(~record
      (~'-pr-writer [~'x ~'w ~'_]
       (~'-write ~'w (edn/to-string ~t ~fn ~'x))))))

(defn gen-tag-parser [[tag {:keys [read-fn]}]]
  (let [fn (if (seq? read-fn)
             (second read-fn)
             read-fn)]
    `(reader/register-tag-parser! (symbol "jiffy" ~(name tag)) ~fn)))

(defn gen-converters [tags]
  #?(:cljs
     `(do
        (extend-protocol IPrintWithWriter
          ~@(mapcat gen-tag-writer tags))
        ~@(map gen-tag-parser tags))))

;; (defmacro init-converters! []
;;   `(gen-converters edn/tags))

;; (init-converters!)

;; (gen-converters edn/tags)

#?(:cljs
   (do
 (cljs.core/extend-protocol
  cljs.core/IPrintWithWriter
  jiffy.zone-offset-impl/ZoneOffset
  (-pr-writer [x w _] (-write w (jiffy.edn/to-string "zone-offset" :id x)))
  jiffy.instant-2-impl/Instant
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "instant-2" jiffy.edn/->map x)))
  jiffy.local-time-impl/LocalTime
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "local-time" jiffy.edn/->map x)))
  jiffy.temporal.chrono-unit/ChronoUnit
  (-pr-writer [x w _] (-write w (jiffy.edn/to-string "unit" :enum-name x)))
  jiffy.temporal.value-range/ValueRange
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "value-range" jiffy.edn/->map x)))
  jiffy.day-of-week/DayOfWeek
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "day-of-week" :enum-name x)))
  jiffy.month/Month
  (-pr-writer [x w _] (-write w (jiffy.edn/to-string "month" :enum-name x)))
  jiffy.temporal.chrono-field/ChronoField
  (-pr-writer [x w _] (-write w (jiffy.edn/to-string "field" :enum-name x)))
  jiffy.duration-impl/Duration
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "duration" jiffy.edn/->map x)))
  jiffy.clock/FixedClock
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "fixed-clock" jiffy.edn/->map x)))
  jiffy.period/Period
  (-pr-writer
   [x w _]
   (-write w (jiffy.edn/to-string "period" jiffy.edn/->map x)))
  jiffy.temporal.temporal-query/TemporalQuery
  (-pr-writer [x w _] (-write w (jiffy.edn/to-string "query" :name x))))
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "zone-offset")
  jiffy.zone-offset/of)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "instant-2")
  jiffy.instant-2-impl/map->Instant)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "local-time")
  jiffy.local-time-impl/map->LocalTime)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "unit")
  jiffy.temporal.chrono-unit/value-of)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "value-range")
  jiffy.temporal.value-range/map->ValueRange)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "day-of-week")
  jiffy.day-of-week/value-of)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "month")
  jiffy.month/value-of)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "field")
  jiffy.temporal.chrono-field/valueOf)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "duration")
  jiffy.duration-impl/map->Duration)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "fixed-clock")
  jiffy.clock/map->FixedClock)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "period")
  jiffy.period/map->Period)
 (cljs.reader/register-tag-parser!
  (cljs.core/symbol "jiffy" "query")
  jiffy.temporal.temporal-queries/name->query))
)


(comment

  ;; (gen-converters edn/tags)

  ;; (reader/read-string "#jiffy/instant{:seconds 0, :nanos 0}")
  ;; (pr-str #jiffy/instant{:seconds 0, :nanos 0})

  ;; (pr-str #jiffy/duration{:seconds 1071594177785110233, :nanos 31100157})
  ;; (reader/read-string "#jiffy/duration{:seconds 1071594177785110233, :nanos 31100157}")
  ;; (reader/read-string "#jiffy/value-range{:min-smallest 0, :min-largest 0, :max-smallest 999999999, :max-largest 999999999}")




  ;; (-> #jiffy/instant-2{:seconds 0, :nanos 0}
  ;;     (jiffy.instant-2/plus-seconds 10))

  )
