(ns jiffy.parity-tests.api-test
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :as str]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java]]
            [jiffy.exception :refer [try*]]
            [jiffy.math :as math]
            [jiffy.parity-tests.support :refer [same? kebab-case]]
            [jiffy.specs :as j]))

(defn invoke-java [f args {:keys [static?]}]
  (let [obj (when-not static? (first args))
        method-name (name f)
        method (->> (.getDeclaredMethods (Class/forName (namespace f)))
                    (filter #(-> % .getName (= method-name)))
                    (filter #(-> % .getParameterCount (= (count (if static? args (rest args))))))
                    first)]
    (if static?
      (.invoke method obj (into-array Object args))
      (.invoke method obj (into-array Object (rest args))))))

(defn jiffy-fn->java-class [jiffy-method]
  (case (symbol (namespace jiffy-method))
    'jiffy.instant 'java.time.Instant))

(defn gen-test-name [f]
  (symbol (str (str/replace (namespace f) #"\." "-")
               "--"
               (name f)
               "-test")))

(defn get-spec [f]
  (keyword (str (namespace f))
           (str (kebab-case (name f)) "-args")))

(defn invoke-jiffy [f args]
  (apply f args))

(defn gen-protocol-method-prop [protocol-ns f]
  `(prop/for-all
    [args# (s/gen ~(get-spec f))]
    (same? (invoke-jiffy ~(symbol (str protocol-ns) (name f)) args#)
           (invoke-java '~(symbol (str (jiffy-fn->java-class f)) (name f))
                        (map jiffy->java args#)
                        {:static? false}))))

(defn gen-static-method-prop [jiffy-fn java-fn]
  `(prop/for-all
    [args# (s/gen ~(get-spec jiffy-fn))]
    (same? (invoke-jiffy ~jiffy-fn args#)
           (invoke-java '~java-fn
                        (map jiffy->java args#)
                        {:static? true}))))

(def default-num-tests 10000)

(defmacro test-proto-fn [protocol-ns f & [num-tests]]
  `(do
     (require '~(symbol (namespace f)))
     (defspec ~(gen-test-name f) ~(or num-tests default-num-tests)
       ~(gen-protocol-method-prop protocol-ns f))))

(defmacro test-static-fn [jiffy-fn java-fn & [num-tests]]
  `(do
     (require '~(symbol (namespace jiffy-fn)))
     (defspec ~(gen-test-name jiffy-fn) ~(or num-tests default-num-tests)
       ~(gen-static-method-prop jiffy-fn java-fn))))

(test-static-fn jiffy.instant/ofEpochSecond java.time.Instant/ofEpochSecond)
(test-proto-fn jiffy.instant jiffy.instant/plusSeconds)
(test-proto-fn jiffy.instant jiffy.instant/plusMillis)
(test-proto-fn jiffy.instant jiffy.instant/plusNanos)
(test-proto-fn jiffy.instant jiffy.instant/minusSeconds)
(test-proto-fn jiffy.instant jiffy.instant/minusMillis)
(test-proto-fn jiffy.instant jiffy.instant/minusNanos)
(test-proto-fn jiffy.instant jiffy.instant/toEpochMilli)
(test-proto-fn jiffy.instant jiffy.instant/isAfter)
(test-proto-fn jiffy.instant jiffy.instant/isBefore)
(test-proto-fn jiffy.time-comparable jiffy.instant/compareTo)

;; TODO: report bug
(deftest java-toEpochMilli-bug
  (is (thrown-with-msg?
       java.lang.ArithmeticException #"long overflow"
       (.toEpochMilli (jiffy->java #jiffy.instant_impl.Instant{:seconds 9223372036854776, :nanos 100000})))))
