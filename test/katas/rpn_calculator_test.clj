(ns katas.rpn-calculator-test
  (:require
    [clojure.test :refer :all]
    [katas.rpn-calculator :refer :all]))

;;
;; Operators
;;
(deftest test-token->operator
  (is (= "+"
    (get (token->operator
      {:type "operator", :value "+"})
      :match)))
  (is (= "/"
    (get (token->operator
      {:type "operator", :value "/"})
      :match)))
  (is (= "SQ"
    (get (token->operator
      {:type "operator", :value "SQ"})
      :match))))

(deftest test-operator-addition
  (is (= 3
    (let [
      func (get (token->operator
        {:type "operator", :value "+"})
        :func)
    ] (func 1 2)))))

(deftest test-operator-subtraction
  (is (= 8
    (let [
      func (get (token->operator
        {:type "operator", :value "-"})
        :func)
    ] (func 10 2)))))

(deftest test-operator-multiplication
  (is (= 6
    (let [
      func (get (token->operator
        {:type "operator", :value "*"})
        :func)
    ] (func 3 2)))))

(deftest test-operator-division
  (is (= 4
    (let [
      func (get (token->operator
        {:type "operator", :value "/"})
        :func)
    ] (func 20 5)))))

(deftest test-operator-square
  (is (= 64
    (let [
      func (get (token->operator
        {:type "operator", :value "SQ"})
        :func)
    ] (func 8)))))

(deftest test-operator-min
  (is (= 5
    (let [
      func (get (token->operator
        {:type "operator", :value "MIN"})
        :func)
    ] (func 20 42 5 13 37)))))

(deftest test-operator-max
  (is (= 42
    (let [
      func (get (token->operator
        {:type "operator", :value "MAX"})
        :func)
    ] (func 20 42 5 13 37)))))

;;
;; Lexer
;;
(deftest test-parse-token-type
  (is (= "operator" (parse-token-type "+")))
  (is (= "operator" (parse-token-type "*")))
  (is (= "number" (parse-token-type "42")))
  (is (= "number" (parse-token-type "!@#$%"))))

(deftest test-parse-token-value
  (is (= 42 (parse-token-value "number" "42")))
  (is (= "+" (parse-token-value "operator" "+")))
  (is (= "SQ" (parse-token-value "operator" "SQ")))
  (is (thrown? Exception
    (parse-token-value "number" "not a number"))))

(deftest test-literal->token-number
  (is (=
    {:type "number", :value 42}
    (literal->token "42")))
  (is (=
    {:type "operator", :value "+"}
    (literal->token "+")))
  (is (=
    {:type "operator", :value "/"}
    (literal->token "/"))))

(deftest test-lexer
  (is (=
    '(
      {:type "number", :value 1},
      {:type "number", :value 2},
      {:type "operator", :value "+"}
    )
    (lexer "1 2 +")))
  (is (=
    '(
      {:type "number", :value 3},
      {:type "number", :value 5},
      {:type "number", :value 8},
      {:type "operator", :value "*"},
      {:type "number", :value 7},
      {:type "operator", :value "+"},
      {:type "operator", :value "*"},
    )
    (lexer "3 5 8 * 7 + *"))))

;;
;; Evaluator
;;
(deftest test-literal-numbers
  (is (= 1 (rpn-evaluate "1")))
  (is (= 1337 (rpn-evaluate "1337"))))

(deftest test-basic-addition
  (is (= 3 (rpn-evaluate "1 2 +")))
  (is (= 1337 (rpn-evaluate "1300 37 +"))))

(deftest test-basic-subtraction
  (is (= 2 (rpn-evaluate "3 1 -")))
  (is (= 42 (rpn-evaluate "48 6 -"))))

(deftest test-basic-multiplication
  (is (= 6 (rpn-evaluate "3 2 *")))
  (is (= 56 (rpn-evaluate "8 7 *"))))

(deftest test-basic-division
  (is (= 4 (rpn-evaluate "20 5 /")))
  (is (= 8 (rpn-evaluate "48 6 /"))))

(deftest test-basic-square
  (is (= 2304 (rpn-evaluate "48 SQ"))))

(deftest test-basic-square-root
  (is (= 8 (rpn-evaluate "64 SQRT"))))

(deftest test-basic-min-max
  (is (= 1 (rpn-evaluate "5 8 1 4 2 MIN")))
  (is (= 8 (rpn-evaluate "5 8 1 4 2 MAX"))))

(deftest test-arithmetic-multiple-operations
  (is (= 3 (rpn-evaluate "4 2 + 3 -")))
  (is (= 141 (rpn-evaluate "3 5 8 * 7 + *")))
  (is (= 5 (rpn-evaluate "15 7 1 1 + - / 3 * 2 1 1 + + -"))))
