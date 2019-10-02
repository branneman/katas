(ns katas.main-test
  (:require
    [clojure.test :refer :all]
    [katas.main :refer :all]))

(deftest test-kata
  (is (thrown? Exception
    (kata [])))
  (is (thrown? Exception
    (kata ["unknown"])))
  (is (= "fizzbuzz"
    (:name (kata ["fizzbuzz" "1" "100"])))))

(deftest test-assert-arity
  (is (thrown? Exception (assert-arity 2 [])))
  (is (thrown? Exception (assert-arity 2 [1])))
  (is (thrown? Exception (assert-arity 2 [1 2 3])))
  (is (nil? (assert-arity 2 [1 2])))
  (is (nil? (assert-arity ##Inf [])))
  (is (nil? (assert-arity ##Inf [1])))
  (is (nil? (assert-arity ##Inf [1 2 3]))))
