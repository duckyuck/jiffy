(ns jiffy.protocols.format.resolver-style
  (:require [clojure.spec.alpha :as s]))

(defprotocol IResolverStyle)

(s/def ::resolver-style #(satisfies? IResolverStyle %))
