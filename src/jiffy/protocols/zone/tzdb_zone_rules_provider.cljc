(ns jiffy.protocols.zone.tzdb-zone-rules-provider
  (:require [clojure.spec.alpha :as s]))

(defprotocol ITzdbZoneRulesProvider)

(s/def ::tzdb-zone-rules-provider #(satisfies? ITzdbZoneRulesProvider %))
