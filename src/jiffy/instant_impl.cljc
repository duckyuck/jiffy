(ns jiffy.instant-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.local-time-impl :as local-time-impl]
            [jiffy.math :as math]
            [jiffy.precision :as precision]
            [jiffy.protocols.instant :as instant]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]
            [jiffy.specs :as j]
            [jiffy.temporal.chrono-field :as chrono-field]))

(def-record Instant ::instant [seconds ::j/second nanos ::j/nano])

(def EPOCH (->Instant 0 0))
(def MAX_SECOND precision/max-safe-integer)
(def MIN_SECOND precision/min-safe-integer)

(defn instant? [x] (instance? Instant x))

(def-constructor create ::instant
  [seconds ::j/second
   nano-of-second ::j/nano]
  (cond
    (zero? (bit-or seconds nano-of-second))
    EPOCH

    (or (< seconds MIN_SECOND) (> seconds MAX_SECOND))
    (throw (ex :jiffy.precision/PrecisionException
               "Instant exceeds minimum or maximum instant"
               {:max-second MAX_SECOND
                :min-second MIN_SECOND
                :seconds seconds}))

    :else
    (->Instant seconds nano-of-second)))

(def get-epoch-second :seconds)
(def get-nano :nanos)

(defn of-epoch-milli [epoch-milli]
  (create (math/floor-div epoch-milli 1000)
          (int (* (math/floor-mod epoch-milli 1000)
                  1000000))))

(defn of-epoch-second
  ([epoch-second]
   (create epoch-second 0))

  ([epoch-second nano-adjustment]
   (create
    (math/add-exact epoch-second (math/floor-div nano-adjustment local-time-impl/NANOS_PER_SECOND))
    (math/floor-mod nano-adjustment local-time-impl/NANOS_PER_SECOND))))

(def-constructor from ::instant
  [temporal ::temporal-accessor/temporal-accessor]
  (if (satisfies? instant/IInstant temporal)
    temporal
    (try*
     (of-epoch-second
      (temporal-accessor/get-long temporal chrono-field/INSTANT_SECONDS)
      (temporal-accessor/get temporal chrono-field/NANO_OF_SECOND))
     (catch :default e
       (throw (ex DateTimeException
                  (str "Unable to obtain Instant from TemporalAccessor: "
                       temporal " of type " (type temporal))
                  {:temporal temporal
                   :type (type temporal)}
                  e))))))
