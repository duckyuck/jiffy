(ns jiffy.zone.zone-rules-store
  (:require [jiffy.zone.zone-rules-conversion :as conversion]))

(def zone-id->rules (atom {}))

#?(:clj
   (defn load-zone-rules []
     (let [zone-ids (java.time.zone.ZoneRulesProvider/getAvailableZoneIds)]
       (zipmap zone-ids
               (map #(conversion/convert-zone-rules (java.time.zone.ZoneRulesProvider/getRules % false)) zone-ids)))))

#?(:clj
   (do
     (reset! zone-id->rules (load-zone-rules))
     nil))

