(ns jiffy.math.big-integer-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer [deftest is testing]]
            [com.gfredericks.exact :as exact]
            [jiffy.math.big-integer :as sut]
            [jiffy.math.support :refer [compare-math unwrap wrap]]
            [jiffy.parity-tests.support-2 :as support]))

(def digits [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \a \b \c \d \e \f])

(defn gen-integer-with-digits
  [base]
  (gen/let [digit-count (gen/scale #(* 2 %) gen/nat)
            [f the-digits]
            (gen/tuple (gen/elements [exact/+ exact/-])
                       (gen/vector (gen/elements (take base digits))
                                   (inc digit-count)))]
    (f (exact/string->integer (apply str the-digits) base))))

(def word-max (apply * (repeat 32 2)))

(def gen-word
  (gen/let [[f x]
            (gen/tuple (gen/elements [identity
                                      #(- word-max % 1)])
                       (gen/large-integer* {:min 0 :max (dec word-max)}))]
    (exact/native->integer (f x))))

;; generating with 32 bit words to make it more likely to catch
;; js impl edge cases
(def gen-integer-with-words*
  (let [word-max (exact/native->integer word-max)]
    (gen/let [word-count (gen/scale #(/ % 5) gen/nat)
              words (gen/vector gen-word word-count)]
      (->> words
           (reduce (fn [acc word]
                     (-> acc
                         (exact/* word-max)
                         (exact/+ word)))
                   exact/ZERO)))))

(def gen-integer-with-words
  (gen/let [[x f] (gen/tuple gen-integer-with-words*
                             (gen/elements [exact/+ exact/-]))]
    (f x)))

(def gen-integer
  (gen/one-of [(gen-integer-with-digits 10)
               (gen-integer-with-digits 16)
               gen-integer-with-words]))

(defspec divide-and-reminder 1000
  (prop/for-all [values (gen/fmap wrap (gen/tuple gen-integer (gen/such-that pos?
                                                                             gen-integer
                                                                             1000)))]
                (let [[x y] (unwrap values)]
                  (compare-math values                                
                                (sut/divide-and-reminder x y)
                                (.divideAndRemainder (biginteger x) (biginteger y))))))

(defspec bit-length 1000
  (prop/for-all [values (gen/fmap wrap (gen/tuple gen-integer))]
                (let [[x] (unwrap values)]
                  (compare-math values                                
                                (sut/bit-length (bigint x))
                                (.bitLength (biginteger x))))))

(defspec long-value 1000
  (prop/for-all [values (gen/fmap wrap (gen/tuple gen/int))]
                (let [[x] (unwrap values)]
                  (compare-math values                                
                                (sut/long-value (bigint x))
                                (.longValue (biginteger x))))))
(defspec int-value 1000
  (prop/for-all [values (gen/fmap wrap (gen/tuple gen/int))]
                (let [[x] (unwrap values)]
                  (compare-math values                                
                                (sut/int-value (bigint x))
                                (.intValue (biginteger x))))))
