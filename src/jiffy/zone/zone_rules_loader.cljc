(ns jiffy.zone.zone-rules-loader
  (:require #?(:clj [jiffy.zone.zone-rules-conversion :as conversion])
            [jiffy.zone.zone-rules-store :as store]
            [jiffy.edn.readers]
            [jiffy.helper.macros :refer [load-edn-file] :refer-macros [load-edn-file] :include-macros true]))

#?(:clj
   (do
     (defn load-zone-rules []
       (let [zone-ids #{"Europe/Oslo"}
             ;; repl times out if we load all rules in cljs
             ;; zone-ids (java.time.zone.ZoneRulesProvider/getAvailableZoneIds)
             ]
         (zipmap zone-ids
                 (map #(-> (java.time.zone.ZoneRulesProvider/getRules % false)
                           conversion/java->jiffy
                           (assoc :zone-id %))
                      zone-ids))))

     (spit "dev-resources/zone-rules.edn" (load-zone-rules)))
   )

(def zone-id->rules
  #?(:clj (load-zone-rules)
     :cljs (first (load-edn-file "dev-resources/zone-rules.edn"))))

(store/reset zone-id->rules)
