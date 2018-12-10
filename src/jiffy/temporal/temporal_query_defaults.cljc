(ns jiffy.temporal.temporal-query-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.temporal.temporal-query :as temporal-query]
            [jiffy.protocols.temporal.temporal-accessor :as temporal-accessor]))

(s/def ::temporal-query ::temporal-query/temporal-query)
