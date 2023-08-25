(ns jiffy.instant-2-impl
  (:require #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.exception :refer [DateTimeException UnsupportedTemporalTypeException ex #?(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(def-record Instant ::instant [seconds ::j/second nanos ::j/nano])

(def EPOCH (->Instant 0 0))
(def MAX_SECOND 31556889864403199)
(def MIN_SECOND -31557014167219200)

(def-constructor create ::instant
  [seconds ::j/second
   nano-of-second ::j/nano]
  (cond
    (zero? (bit-or seconds nano-of-second))
    EPOCH

    (or (< seconds MIN_SECOND) (> seconds MAX_SECOND))
    (throw (ex DateTimeException
               "Instant exceeds minimum or maximum instant"
               {:max-second MAX_SECOND
                :min-second MIN_SECOND
                :seconds seconds}))

    :else
    (->Instant seconds nano-of-second)))

(def-constructor of-epoch-milli ::instant
  [epoch-milli ::j/milli]
  (create (math/floor-div epoch-milli 1000)
          (int (* (math/floor-mod epoch-milli 1000)
                  1000000))))
