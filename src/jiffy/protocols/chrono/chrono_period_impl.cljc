(ns jiffy.protocols.chrono.chrono-period-impl
  (:require [clojure.spec.alpha :as s]))

(defprotocol IChronoPeriodImpl)

(s/def ::chrono-period-impl #(satisfies? IChronoPeriodImpl %))
