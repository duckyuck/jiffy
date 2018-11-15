(ns jiffy.date-time-exception)

(defprotocol IDateTimeException)

(defrecord DateTimeException [message data cause]
  IDateTimeException)

(defn date-time-exception
  ([message] (date-time-exception message {}))
  ([message data] (date-time-exception message data nil))
  ([message data cause]
   #?(:clj (if cause
             (java.time.DateTimeException. (str message data) cause)
             (java.time.DateTimeException. (str message data)))
      :cljs (->DateTimeException message data cause))))
