(ns jiffy.temporal.temporal-adjuster-defaults
  (:require [clojure.spec.alpha :as s]
            [jiffy.specs :as j]
            [jiffy.dev.wip :refer [wip]]
            [jiffy.protocols.temporal.temporal-adjuster :as temporal-adjuster]
            [jiffy.protocols.temporal.temporal :as temporal]))

(s/def ::temporal-adjuster ::temporal-adjuster/temporal-adjuster)
