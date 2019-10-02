(ns katas.roman-numerals-test
  (:require
    [clojure.test :refer :all]
    [katas.roman-numerals :refer :all]))

(deftest test-get-positive
  (is (= 42 (get-positive 42)))
  (is (= 42 (get-positive -42)))
  (is (= 3.14 (get-positive 3.14)))
  (is (= 3.14 (get-positive -3.14)))
  (is (= 0 (get-positive 0))))

(deftest test-num->vector
  (is (thrown? Exception (num->vector -3.14)))
  (is (thrown? Exception (num->vector 0)))
  (is (= [1] (num->vector 1)))
  (is (= [4 2] (num->vector 42)))
  (is (= [1 3 3 7] (num->vector 1337))))

(deftest test-num->vector-decimals
  (is (= [4] (num->vector 4.2)))
  (is (= [1 3] (num->vector 13.37))))

(deftest test-find-highest-symbol
  (is (=
    { :n 1 :s "I" }
    (find-highest-symbol symbols 1)))
  (is (=
    { :n 1 :s "I" }
    (find-highest-symbol symbols 2)))
  (is (=
    { :n 5 :s "V" }
    (find-highest-symbol symbols 5)))
  (is (=
    { :n 5 :s "V" }
    (find-highest-symbol symbols 8)))
  (is (=
    { :n 10 :s "X" }
    (find-highest-symbol symbols 10)))
  (is (=
    { :n 1000 :s "M" }
    (find-highest-symbol symbols 2000)))
  )

(deftest test-decimal->roman
  (is (= "I" (decimal->roman 1)))
  (is (= "VI" (decimal->roman 6)))
  (is (= "XXXVIII" (decimal->roman 38)))
  (is (= "XXXIX" (decimal->roman 39)))
  (is (= "CCVII" (decimal->roman 207)))
  (is (= "CCXLVI" (decimal->roman 246)))
  (is (= "DCCLXXXIX" (decimal->roman 789)))
  (is (= "MIX" (decimal->roman 1009)))
  (is (= "MCMLIV" (decimal->roman 1954)))
  (is (= "MMCDXXI" (decimal->roman 2421))))

(deftest test-decimal->roman-negatives
  (is (= "-DCCLXXXVIII" (decimal->roman -788)))
  (is (= "-II" (decimal->roman -2))))

(deftest test-decimal->roman-over-3000
  (is (=
    "MMMMMMMMMMMMMMMMMMMMMCDXXI"
    (decimal->roman 21421))))
