(ns katas.rpn-calculator
  (:require
    [clojure.string :refer [split]]))

;;
;; Operators
;;

(def operators [
  { :match "+" :arity 2 :func + }
  { :match "-" :arity 2 :func - }
  { :match "*" :arity 2 :func * }
  { :match "/" :arity 2 :func / }
  { :match "SQ" :arity 1 :func #(* % %) }
  { :match "SQRT" :arity 1 :func #(int (Math/sqrt %)) }
  { :match "MIN" :arity ##Inf :func min }
  { :match "MAX" :arity ##Inf :func max }
])

;; token->operator :: map -> map
(defn token->operator [token]
  (first (filter
    #(= (:match %) (:value token))
    operators)))

;;
;; Lexer
;;

;; parse-token-type :: str -> str
(defn parse-token-type [str]
  (if
    (seq (filter #(= (:match %) str) operators))
    "operator"
    "number"))

;; parse-token-value :: (str str) -> *
(defn parse-token-value [type str]
  (if (= type "number")
    (Integer/parseInt str)
    str))

;; literal->token :: str -> map
(defn literal->token [str]
  (let [
    type (parse-token-type str)
    value (parse-token-value type str)
  ]
  {:type type :value value}))

;; lexer :: str -> list map
(def lexer (comp
  #(map literal->token %)
  #(split % #" ")))

;;
;; Evaluator
;;  operands are pushed to stack
;;  operators applied to items popped from stack
;;  result pushed onto stack
;;

;; reducer :: (list map) -> list
(defn reducer [stack token]
  (if (= "number" (:type token))
    (conj stack (:value token))
    (let [
      operator (token->operator token)
      arity (:arity operator)
      func (:func operator)
      operands (reverse (take arity stack))
      stack (drop arity stack)
    ] (conj stack (apply func operands)))))

;; rpn-evaluate :: str -> num
(def rpn-evaluate (comp
  first
  #(reduce reducer '() %)
  lexer))
