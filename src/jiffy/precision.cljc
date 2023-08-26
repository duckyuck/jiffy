(ns jiffy.precision
"The precision of native numbers on the Javascript platform is more limited than that
of the JVM platform. The Javascript platform can natively represent precise numbers of up/down
to `max-safe-integer` / `min-safe-integer` (2^53 - 1). This is less than the 64-bit precision
afforded by the JVM platform.

This limitation causes a divergence with regards to parity with `java.time`, but should
generally not be an issue. "
  (:require [jiffy.exception :refer [ex]]))

(def max-safe-integer 9007199254740991)
(def min-safe-integer -9007199254740991)

(def PrecisionException ::PrecisionException)

(defn precise? [x]
  (<= min-safe-integer x max-safe-integer))

(defn assert-precise! [n]
  (when-not (precise? n)
    (throw (ex PrecisionException
               "Number cannot be precisly represented"
               {:number n}))))

