(ns jiffy.temporal.temporal-amount-defaults
  (:refer-clojure :exclude [get ])
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.temporal.temporal-amount :as temporal-amount]
            [jiffy.protocols.temporal.temporal :as temporal]
            [jiffy.protocols.temporal.temporal-unit :as temporal-unit]))

(s/def ::temporal-amount ::temporal-amount/temporal-amount)
