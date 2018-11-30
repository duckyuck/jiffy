(ns jiffy.local-date-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.local-date-impl :as local-date]
            [jiffy.local-time-impl :as local-time]
            [jiffy.specs :as j]))

(defrecord LocalDateTime [date time])

(s/def ::create-args (s/tuple ::local-date/local-date ::local-time/local-time))
(def create ->LocalDateTime)
(s/def ::local-date-time (j/constructor-spec LocalDateTime create ::create-args))
(s/fdef create :args ::create-args :ret ::local-date-time)

(defmacro args [& x] `(s/tuple ::local-date-time ~@x))

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L373
  ([date time] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L235
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L260
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5 of--overloaded-param-6] (wip ::of))

  ;; NB! This method is overloaded!
;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/LocalDateTime.java#L285
  ([of--overloaded-param-1 of--overloaded-param-2 of--overloaded-param-3 of--overloaded-param-4 of--overloaded-param-5 of--overloaded-param-6 of--overloaded-param-7] (wip ::of)))
(s/fdef of :args ::of-args :ret ::local-date-time)
