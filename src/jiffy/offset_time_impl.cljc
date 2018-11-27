(ns jiffy.offset-time-impl
  (:require [clojure.spec.alpha :as s]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.specs :as j]))

(defrecord OffsetTime [])

(s/def ::create-args ::j/wip)
(defn create [])
(s/def ::offset-time (j/constructor-spec OffsetTime create ::create-args))
(s/fdef create :args ::create-args :ret ::offset-time)

(defmacro args [& x] `(s/tuple ::offset-time ~@x))

(s/def ::of-args (args ::j/wip))
(defn of
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L212
  ([time offset] (wip ::of))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/OffsetTime.java#L235
  ([hour minute second nano-of-second offset] (wip ::of)))
(s/fdef of :args ::of-args :ret ::offset-time)
