(ns katas.fizzbuzz
  (:require
    [clojure.string :refer [join]]))

;; divisible? :: (num, num) -> bool
(defn divisible? [num, div]
  (= 0 (mod num div)))

;; num-to-fizzbuzz :: num -> str
(defn num-to-fizzbuzz [n]
  (cond
    (and (divisible? n 3) (divisible? n 5)) "FizzBuzz"
    (divisible? n 3) "Fizz"
    (divisible? n 5) "Buzz"
    :else n))

;; fizzbuzz :: (num, num) -> str
(defn fizzbuzz [& xs]
  (->> xs
    (apply range)
    (map num-to-fizzbuzz)
    (join "\n")))
