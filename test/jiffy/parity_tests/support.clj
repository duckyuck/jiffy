(ns jiffy.parity-tests.support
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.string :as str]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer [deftest is]]
            [jiffy.conversion :refer [jiffy->java] :as conversion]
            [jiffy.exception :refer [try*]]
            [jiffy.math :as math]
            [jiffy.specs :as j]))

(defn partition-between
  [pred? coll]
  (->> (map pred? coll (rest coll))
       (reductions not= true)
       (map list coll)
       (partition-by second)
       (map (partial map first))))

(defn split-at-java-case [s]
  (->> s
       (partition-between #(and (Character/isLowerCase %1)
                                (Character/isUpperCase %2)))
       (map #(apply str %))))

(defn kebab-case [s]
  (->> (split-at-java-case (str s))
       (map str/lower-case)
       (str/join "-")))

(defmacro trycatch [& exprs]
  `(try* ~@exprs (catch :default e# e#)))

(defmacro same? [call-jiffy-form call-java-form]
  `(conversion/same?
    (trycatch ~call-jiffy-form)
    (trycatch ~call-java-form)))

(defn invoke-java [f args {:keys [static?]}]
  (let [obj (when-not static? (first args))
        method-name (name f)
        method (->> (.getDeclaredMethods (Class/forName (namespace f)))
                    (filter #(-> % .getName (= method-name)))
                    (filter #(-> % .getParameterCount (= (count (if static? args (rest args))))))
                    first)]
    (try
      (if static?
        (.invoke method obj (into-array Object args))
        (.invoke method obj (into-array Object (rest args))))
      (catch java.lang.reflect.InvocationTargetException ite
          (throw (.getCause ite))))))

(defn jiffy-fn->java-class [jiffy-method]
  (condp = (symbol (namespace jiffy-method))
    'jiffy.instant 'java.time.Instant
    'jiffy.temporal.value-range 'java.time.temporal.ValueRange
    'jiffy.zoned-date-time 'java.time.ZonedDateTime
    'jiffy.chrono.chrono-zoned-date-time 'java.time.chrono.ChronoZonedDateTime
    (throw (ex-info "Unable to resolve java class of jiffy method " jiffy-method {}))))

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

(defmacro gen-protocol-method-prop [protocol-ns f]
  `(prop/for-all
    [args# (s/gen ~(get-spec (symbol (str protocol-ns) (name f))))]
    (same? (invoke-jiffy ~f args#)
           (invoke-java '~(symbol (str (jiffy-fn->java-class f))
                                  (name f))
                        (map jiffy->java args#)
                        {:static? false}))))

(defn gen-static-method-prop [jiffy-fn java-fn]
  `(prop/for-all
    [args# (s/gen ~(get-spec jiffy-fn))]
    (same? (invoke-jiffy ~jiffy-fn args#)
           (invoke-java '~java-fn
                        (map jiffy->java args#)
                        {:static? true}))))

(def default-num-tests 1000)

(defmacro test-proto-fn [protocol-ns f & [num-tests]]
  `(do
     (require '~(symbol (namespace f)))
     (defspec ~(gen-test-name f) ~(or num-tests default-num-tests)
       (gen-protocol-method-prop ~protocol-ns ~f))))

(defmacro test-static-fn [jiffy-fn java-fn & [num-tests]]
  `(do
     (require '~(symbol (namespace jiffy-fn)))
     (defspec ~(gen-test-name jiffy-fn) ~(or num-tests default-num-tests)
       ~(gen-static-method-prop jiffy-fn java-fn))))
