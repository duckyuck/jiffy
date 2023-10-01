(ns jiffy.protocols.format.parsed
  (:refer-clojure :exclude [resolve ])
  (:require [clojure.spec.alpha :as s]))

(defprotocol IParsed
  (copy [this])
  (resolve [this resolver-style resolver-fields]))

(s/def ::parsed #(satisfies? IParsed %))
