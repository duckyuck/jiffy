(ns jiffy.temporal.chrono-field)

(defprotocol IChronoField
  (checkValidValue [this value]))

(def INSTANT_SECONDS ::INSTANT_SECONDS--not-implemented)
(def NANO_OF_SECOND ::NANO_OF_SECOND--not-implemented)
(def MICRO_OF_SECOND ::MICRO_OF_SECOND--not-implemented)
(def MILLI_OF_SECOND ::MILLI_OF_SECOND--not-implemented)
