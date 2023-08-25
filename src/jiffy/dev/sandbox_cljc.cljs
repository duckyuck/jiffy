(ns jiffy.dev.sandbox-cljc)

;; (ns jiffy.dev.sandbox-cljc
;;   (:require [cljs.spec.alpha :as s]
;;             [cljs.spec.gen.alpha :as gen]
;;             [jiffy.dev.defs-cljc :refer-macros [def-record def-method def-constructor]]
;;             [jiffy.specs :as j]))


;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ;;
;; ;; Spec'd record with generator

;; (def-record MyRecord ::my-record [a number? b keyword?])

;; #_(MyRecord. 1 :foo)

;; #_(gen/sample (s/gen ::my-record))


;; ;;;;;;;;;;;;;;;;;;;;;;;;
;; ;;
;; ;; "static" constructors

;; (def-constructor my-constructor ::my-record
;;   [a number?
;;    b keyword?]
;;   (->MyRecord a b))

;; #_(for [args (-> #'my-constructor s/get-spec :args s/gen gen/sample)]
;;     (apply my-constructor args))


;; ;;;;;;;;;;;;;;;;;;
;; ;;
;; ;; "class" methods

;; (def-method my-method map?
;;   [this ::my-record
;;    x string?
;;    y boolean?]
;;   {:this this
;;    :x (str "we got: '" x "'")
;;    :y y})

;; #_(for [args (-> #'my-method s/get-spec :args s/gen gen/sample)]
;;     (apply my-method args))



