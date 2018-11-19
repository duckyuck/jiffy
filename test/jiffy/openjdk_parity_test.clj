(ns jiffy.openjdk-parity-test
  (:require [clojure.reflect :as cr]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as spec-gen]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [jiffy.instant :as instant]
            [jiffy.instant-impl :as instant-impl])
  (:import java.time.Instant))

(defn invoke-static-method
  [clazz method-name params & args]
  (-> clazz
      (.getDeclaredMethod (name method-name) (into-array Class params))
      (doto (.setAccessible true))
      (.invoke nil (into-array Object args))))

(defn create-instant [secs nanos]
  (invoke-static-method java.time.Instant
                        :create
                        [java.lang.Long/TYPE java.lang.Integer/TYPE]
                        (long secs) (int nanos)))

(s/def :jiffy.instant/Instant #(satisfies? instant/IInstant %))
(s/def :java.time/Instant #(-> % type (= java.time.Instant)))
(s/def ::instants (s/tuple :jiffy.instant/Instant
                           :java.time/Instant))

(s/def ::nanos (s/int-in 100000 1000000000))
(s/def ::secs (s/int-in (/ -31557014167219200 2) (/ 31556889864403200 2)))

(s/def ::instants-gen
  (s/with-gen ::instants
    #(spec-gen/fmap (fn [[secs nanos]] [(instant-impl/create secs nanos)
                                        (create-instant secs nanos)])
                    (spec-gen/tuple (s/gen ::secs) (s/gen ::nanos)))))

(defn same-instant? [jiffy-instant jdk-instant]
  (= ((juxt instant/getEpochSecond instant/getNano) jiffy-instant)
     ((juxt (memfn getEpochSecond) (memfn getNano)) jdk-instant)))

(def plus-seconds-prop
  (prop/for-all
   [[jiffy-instant jdk-instant] (s/gen ::instants-gen)
    sec (s/gen ::secs)]
   (same-instant? (instant/plusSeconds jiffy-instant sec)
                  (.plusSeconds jdk-instant sec))))

(comment

  (gen/sample (s/gen ::instants-gen))

  (tc/quick-check 100000 plus-seconds-prop)

  (gen/generate (s/gen ::instants-gen) 10 1)

  (spec-gen/generate (s/gen int?))

  (gen/generate (s/gen ::hello) 10 1)


)
