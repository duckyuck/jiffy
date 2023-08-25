(ns jiffy.conversion-2
  (:require [jiffy.exception :as exception]
            ;; [jiffy.instant-2]
            [jiffy.instant-impl])
  (:import jiffy.day_of_week.DayOfWeek
           jiffy.duration_impl.Duration
           ;; jiffy.instant_2.Instant
           jiffy.instant_impl.Instant
           jiffy.local_time_impl.LocalTime
           jiffy.month.Month
           jiffy.period.Period
           jiffy.temporal.chrono_field.ChronoField
           jiffy.temporal.chrono_unit.ChronoUnit
           jiffy.temporal.temporal_query.TemporalQuery
           jiffy.temporal.value_range.ValueRange
           jiffy.zoned_date_time_impl.ZonedDateTime
           jiffy.zone_offset_impl.ZoneOffset))

(defmulti jiffy->java type)

(defmethod jiffy->java :default
  [jiffy-object]
  (if (record? jiffy-object)
    (throw (ex-info (str "Shit! We're missing implementation of multimethod 'jiffy.conversion-2/jiffy->java' for record "
                         (type jiffy-object) ". Please do some more programming!") {:record jiffy-object}))
    jiffy-object))

(defmulti same? (fn [jiffy-object java-object] (type jiffy-object)))

(defmethod same? :default
  [jiffy-object java-object]
  (= (jiffy->java jiffy-object)
     java-object))

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
  (defmethod same? clojure.lang.ExceptionInfo
    [ex java-object]
    (= (kind->class (::exception/kind (ex-data ex)))
       (type java-object))))

(defmethod jiffy->java Instant [{:keys [seconds nanos]}]
  (.plusNanos (java.time.Instant/ofEpochSecond seconds) nanos))

(defmethod jiffy->java ChronoField [chrono-field]
  (java.time.temporal.ChronoField/valueOf (:enum-name chrono-field)))

(defmethod jiffy->java TemporalQuery [{:keys [name]}]
  (case name
    "ZoneId" (java.time.temporal.TemporalQueries/zoneId)
    "Chronology" (java.time.temporal.TemporalQueries/chronology)
    "Precision" (java.time.temporal.TemporalQueries/precision)
    "ZoneOffset" (java.time.temporal.TemporalQueries/offset)
    "Zone" (java.time.temporal.TemporalQueries/zone)
    "LocalDate" (java.time.temporal.TemporalQueries/localDate)
    "LocalTime" (java.time.temporal.TemporalQueries/localTime)))

(defmethod jiffy->java ChronoUnit [{:keys [enum-name]}]
  (java.time.temporal.ChronoUnit/valueOf enum-name))

(defmethod jiffy->java ValueRange [{:keys [min-smallest min-largest max-smallest max-largest]}]
  (java.time.temporal.ValueRange/of min-smallest min-largest max-smallest max-largest))

(defmethod jiffy->java Duration [{:keys [seconds nanos]}]
  (.withNanos (java.time.Duration/ofSeconds seconds) nanos))

(defmethod jiffy->java DayOfWeek [{:keys [enum-name]}]
  (java.time.DayOfWeek/valueOf enum-name))

(defmethod jiffy->java ZoneOffset [{:keys [total-seconds]}]
  (java.time.ZoneOffset/ofTotalSeconds total-seconds))

(defmethod jiffy->java Month [{:keys [enum-name]}]
  (java.time.Month/valueOf enum-name))

(defmethod jiffy->java Period [{:keys [years months days]}]
  (java.time.Period/of years months days))

(defmethod jiffy->java LocalTime [{:keys [hour minute second nano]}]
  (java.time.LocalTime/of hour minute second nano))

(defmethod jiffy->java ZonedDateTime [{:keys [local-date zone offset]}]
  (java.time.ZonedDateTime/ofLocal (jiffy->java local-date)
                                   (jiffy->java zone)
                                   (jiffy->java offset)))

(comment
  
  (methods jiffy->java)

  (methods same?)

  )
