(ns jiffy.protocols.format.parsed
  (:refer-clojure :exclude [resolve ])
  (:require [clojure.spec.alpha :as s]))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/format/Parsed.java
(defprotocol IParsed
  (copy [this])
  (resolve [this resolver-style resolver-fields]))

(s/def ::parsed #(satisfies? IParsed %))