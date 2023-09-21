(ns jiffy.conversion
  (:require [jiffy.chrono.iso-chronology]
            [jiffy.chrono.iso-era]
            [jiffy.clock]
            [jiffy.day-of-week]
            [jiffy.duration-impl]
            [jiffy.exception :as exception]
            [jiffy.instant-impl]
            [jiffy.local-date-impl]
            [jiffy.local-date-time-impl]
            [jiffy.local-time-impl]
            [jiffy.month]
            [jiffy.month-day]
            [jiffy.offset-date-time-impl]
            [jiffy.offset-time-impl]
            [jiffy.period]
            [jiffy.precision :as precision]
            [jiffy.temporal.chrono-field]
            [jiffy.temporal.chrono-unit]
            [jiffy.temporal.temporal-query]
            [jiffy.temporal.value-range]
            [jiffy.year-month]
            [jiffy.zoned-date-time-impl]
            [jiffy.zone-offset-impl]
            [jiffy.zone.zone-offset-transition-rule]
            [jiffy.zone.zone-rules])
  (:import java.lang.reflect.Constructor
           (jiffy.chrono.iso_chronology IsoChronology)
           (jiffy.chrono.iso_era IsoEra)
           (jiffy.clock FixedClock SystemClock)
           (jiffy.day_of_week DayOfWeek)
           (jiffy.duration_impl Duration)
           (jiffy.instant_impl Instant)
           (jiffy.local_date_impl LocalDate)
           (jiffy.local_date_time_impl LocalDateTime)
           (jiffy.local_time_impl LocalTime)
           (jiffy.month Month)
           (jiffy.month_day MonthDay)
           (jiffy.offset_date_time_impl OffsetDateTime)
           (jiffy.offset_time_impl OffsetTime)
           (jiffy.period Period)
           (jiffy.temporal.chrono_field ChronoField)
           (jiffy.temporal.chrono_unit ChronoUnit)
           (jiffy.temporal.temporal_query TemporalQuery)
           (jiffy.temporal.value_range ValueRange)
           (jiffy.year_month YearMonth)
           (jiffy.zoned_date_time_impl ZonedDateTime)
           (jiffy.zone_offset_impl ZoneOffset)
           (jiffy.zone.zone_offset_transition_rule ZoneOffsetTransitionRule)
           (jiffy.zone.zone_rules_impl ZoneRules)))

(defmulti jiffy->java* type)

(defn jiffy->java [jiffy-object]
  (jiffy->java* jiffy-object))

(defmethod jiffy->java* :default
  [object]
  (when-not (nil? object)
    (throw (ex-info (str "Shit! We're missing implementation of multimethod 'jiffy.conversion/jiffy->java' for type "
                         (type object) ". Please do some more programming!") {:object object}))))

(defmulti same?* (fn [jiffy-object java-object] (type jiffy-object)))

(defn same? [jiffy-object java-object]
  (same?* jiffy-object java-object))

(defn stream-seq [java-stream]
  (-> java-stream .iterator iterator-seq))

(defn to-seq [java-object]
  (if (instance? java.util.stream.BaseStream java-object)
    (stream-seq java-object)
    java-object))

(def max-coll-result-size 10)

(defn same-coll? [jiffy-coll java-coll]
  (->> (map same?
            (take max-coll-result-size jiffy-coll)
            (take max-coll-result-size (to-seq java-coll)))
       (every? true?)))

(defmethod same?* :default
  [jiffy-object java-object]
  (cond
    (instance? java.util.stream.BaseStream java-object)
    (same-coll? jiffy-object (to-seq java-object))

    :else
    (= (jiffy->java jiffy-object)
       java-object)))

(defmethod same?* clojure.lang.PersistentVector [& args] (apply same-coll? args))
(defmethod same?* clojure.lang.ArraySeq [& args] (apply same-coll? args))
(defmethod same?* (Class/forName "[Ljava.math.BigInteger;") [& args] (apply same-coll? args))

(let [kind->class {exception/JavaException java.lang.Exception
                   exception/JavaRuntimeException java.lang.RuntimeException
                   exception/JavaNullPointerException java.lang.NullPointerException
                   exception/DateTimeException java.time.DateTimeException
                   exception/UnsupportedTemporalTypeException java.time.temporal.UnsupportedTemporalTypeException
                   exception/ZoneRulesException java.time.zone.ZoneRulesException
                   exception/DateTimeParseException java.time.format.DateTimeParseException
                   exception/JavaArithmeticException java.lang.ArithmeticException
                   exception/JavaClassCastException java.lang.ClassCastException
                   exception/JavaIllegalArgumentException java.lang.IllegalArgumentException
                   exception/JavaIllegalStateException java.lang.IllegalStateException
                   exception/JavaParseException java.text.ParseException
                   exception/JavaIndexOutOfBoundsException java.lang.IndexOutOfBoundsException
                   exception/JavaThrowable java.lang.Throwable}]
  ;; TODO: include exception message in check. needs polishing of exception error messages
  (defmethod same?* clojure.lang.ExceptionInfo
    [ex java-object]
    (= (kind->class (::exception/kind (ex-data ex)))
       (type java-object))))

(doseq [class [java.lang.Integer
               java.lang.Long
               java.lang.Boolean
               java.lang.String
               java.math.BigInteger
               java.math.BigDecimal]]
  (defmethod jiffy->java* class [x] x))

(doseq [class [clojure.lang.ArraySeq
               clojure.lang.PersistentVector
               clojure.lang.LazySeq]]
  (defmethod jiffy->java* class [coll] (mapv jiffy->java coll)))

(defmethod jiffy->java* Instant [{:keys [seconds nanos]}]
  (.plusNanos (java.time.Instant/ofEpochSecond seconds) nanos))

(defmethod jiffy->java* ChronoField [chrono-field]
  (java.time.temporal.ChronoField/valueOf (:enum-name chrono-field)))

(defmethod jiffy->java* TemporalQuery [{:keys [name]}]
  (case name
    "ZoneId" (java.time.temporal.TemporalQueries/zoneId)
    "Chronology" (java.time.temporal.TemporalQueries/chronology)
    "Precision" (java.time.temporal.TemporalQueries/precision)
    "ZoneOffset" (java.time.temporal.TemporalQueries/offset)
    "Zone" (java.time.temporal.TemporalQueries/zone)
    "LocalDate" (java.time.temporal.TemporalQueries/localDate)
    "LocalTime" (java.time.temporal.TemporalQueries/localTime)))

(defmethod jiffy->java* ChronoUnit [{:keys [enum-name]}]
  (java.time.temporal.ChronoUnit/valueOf enum-name))

(defmethod jiffy->java* ValueRange [{:keys [min-smallest min-largest max-smallest max-largest]}]
  (java.time.temporal.ValueRange/of min-smallest min-largest max-smallest max-largest))

(defmethod same?* ValueRange [jiffy-object java-object]
  (and (or (= (:min-smallest jiffy-object) (.getMinimum java-object))
           (and (= (:min-smallest jiffy-object) precision/min-safe-integer)
                (> (:min-smallest jiffy-object) (.getMinimum java-object))))
       (or (= (:min-largest jiffy-object) (.getLargestMinimum java-object))
           (and (= (:min-largest jiffy-object) precision/min-safe-integer)
                (> (:min-largest jiffy-object) (.getLargestMinimum java-object))))
       (or (= (:max-smallest jiffy-object) (.getSmallestMaximum java-object))
           (and (= (:max-smallest jiffy-object) precision/max-safe-integer)
                (< (:max-smallest jiffy-object) (.getSmallestMaximum java-object))))
       (or (= (:max-largest jiffy-object) (.getMaximum java-object))
           (and (= (:max-largest jiffy-object) precision/max-safe-integer)
                (< (:max-largest jiffy-object) (.getMaximum java-object))))))

(defmethod jiffy->java* Duration [{:keys [seconds nanos]}]
  (.withNanos (java.time.Duration/ofSeconds seconds) nanos))

(defmethod same?* Duration [jiffy-object java-object]
  (and (or (= (:seconds jiffy-object) (.getSeconds java-object))
           (and (= (:seconds jiffy-object) precision/min-safe-integer)
                (> (:seconds jiffy-object) (.getSeconds java-object)))
           (and (= (:seconds jiffy-object) precision/max-safe-integer)
                (< (:seconds jiffy-object) (.getSeconds java-object))))))

(defmethod jiffy->java* DayOfWeek [{:keys [enum-name]}]
  (java.time.DayOfWeek/valueOf enum-name))

(defmethod jiffy->java* ZoneOffset [{:keys [total-seconds]}]
  (java.time.ZoneOffset/ofTotalSeconds total-seconds))

(defn jiffy->month [{:keys [enum-name]}]
  (java.time.Month/valueOf enum-name))

(defmethod jiffy->java* Month [arg]
  (jiffy->month arg))

(defmethod jiffy->java* MonthDay [{:keys [month day]}]
  (java.time.MonthDay/of month day))

(defmethod jiffy->java* YearMonth [{:keys [year month]}]
  (java.time.YearMonth/of year month))

(defmethod jiffy->java* Period [{:keys [years months days]}]
  (java.time.Period/of years months days))

(defmethod jiffy->java* LocalTime [{:keys [hour minute second nano]}]
  (java.time.LocalTime/of hour minute second nano))

(defmethod jiffy->java* LocalDate [{:keys [year month day]}]
  (java.time.LocalDate/of year month day))

(defmethod jiffy->java* LocalDateTime [{:keys [date time]}]
  (java.time.LocalDateTime/of (jiffy->java date)
                              (jiffy->java time)))

(defn zdt->java [{:keys [date-time zone offset]}]
  (java.time.ZonedDateTime/ofLocal (jiffy->java date-time)
                                   (jiffy->java zone)
                                   (jiffy->java offset)))

(defmethod jiffy->java* ZonedDateTime [{:keys [date-time zone offset] :as x}]
  (zdt->java x))

(defmethod jiffy->java* OffsetDateTime [{:keys [date-time offset]}]
  (java.time.OffsetDateTime/of (jiffy->java date-time)
                               (jiffy->java offset)))

(defmethod jiffy->java* OffsetTime [{:keys [time offset]}]
  (java.time.OffsetTime/of (jiffy->java time)
                           (jiffy->java offset)))

(defmethod jiffy->java* ValueRange [{:keys [min-smallest min-largest max-smallest max-largest]}]
  (java.time.temporal.ValueRange/of min-smallest min-largest max-smallest max-largest))

(defmethod jiffy->java* SystemClock [{:keys [zone]}]
  (java.time.Clock/system (jiffy->java zone)))

(defmethod jiffy->java* FixedClock [{:keys [instant zone]}]
  (java.time.Clock/fixed (jiffy->java instant) (jiffy->java zone)))

(defn set-private-property [obj property-name new-value]
  (let [field (.getDeclaredField (class obj) property-name)]
    (.setAccessible field true)
    (.set field obj new-value)
    obj))

(defn create-zone-rules [zone-offset]
  (let [cls (Class/forName "java.time.zone.ZoneRules")
        constructor (.getDeclaredConstructor cls (into-array Class [java.time.ZoneOffset]))]
    (.setAccessible constructor true)
    (.newInstance constructor (into-array Object [zone-offset]))))

(defn jiffy->zone-rules [{:keys [standard-transitions
                                 standard-offsets
                                 savings-instant-transitions
                                 savings-local-transitions
                                 wall-offsets
                                 last-rules] :as jiffy}]
  (let [zone-rules (create-zone-rules (java.time.ZoneOffset/of "Z"))]
    jiffy
    (cond-> zone-rules
      standard-transitions (set-private-property "standardTransitions" (long-array (jiffy->java standard-transitions)))
      standard-offsets (set-private-property "standardOffsets" (into-array java.time.ZoneOffset (jiffy->java standard-offsets)))
      savings-instant-transitions (set-private-property "savingsInstantTransitions" (long-array (jiffy->java savings-instant-transitions)))
      savings-local-transitions (set-private-property "savingsLocalTransitions" (into-array java.time.LocalDateTime (jiffy->java savings-local-transitions)))
      wall-offsets (set-private-property "wallOffsets" (into-array java.time.ZoneOffset (jiffy->java wall-offsets)))
      last-rules (set-private-property "lastRules" (into-array java.time.zone.ZoneOffsetTransitionRule (jiffy->java last-rules))))))

(defmethod jiffy->java* ZoneRules [arg]
  (jiffy->zone-rules arg))

(defn jiffy->zone-offset-transition-rule [{:keys [time-definition
                                                  midnight-end-of-day
                                                  local-time
                                                  month
                                                  day-of-month-indicator
                                                  day-of-week
                                                  standard-offset
                                                  offset-after
                                                  offset-before]}]
  (java.time.zone.ZoneOffsetTransitionRule/of
   (jiffy->java month)
   (jiffy->java day-of-month-indicator)
   (jiffy->java day-of-week)
   (jiffy->java local-time)
   (jiffy->java midnight-end-of-day)
   (java.time.zone.ZoneOffsetTransitionRule$TimeDefinition/valueOf (name time-definition))
   (jiffy->java standard-offset)
   (jiffy->java offset-after)
   (jiffy->java offset-before)))

(defmethod jiffy->java* ZoneOffsetTransitionRule [arg]
  (jiffy->zone-offset-transition-rule arg))

(defmethod jiffy->java* IsoChronology [iso-chronology]
  java.time.chrono.IsoChronology/INSTANCE)

(defmethod jiffy->java* IsoEra [{:keys [enum-name]}]
  (java.time.chrono.IsoEra/valueOf enum-name))
