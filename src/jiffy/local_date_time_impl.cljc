(ns jiffy.local-date-time-impl
  (:require [clojure.spec.alpha :as s]
            #?(:clj [jiffy.dev.defs-clj :refer [def-record def-method def-constructor]])
            #?(:cljs [jiffy.dev.defs-cljs :refer-macros [def-record def-method def-constructor]])
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :as local-date]
            [jiffy.local-time-impl :as local-time]
            [jiffy.specs :as j]))

(def-record LocalDateTime ::local-date-time
  [date ::local-date/local-date
   time ::local-time/local-time])

(def-constructor create ::local-date-time
  [date ::local-date/local-date
   time ::local-time/local-time]
  (->LocalDateTime date time))

(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L373
  ([date time] (create date time))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L235
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L260
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5 of--overloaded-param-6] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L285
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5 of--overloaded-param-6 of--overloaded-param-7] (wip ::of)))

