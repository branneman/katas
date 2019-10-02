(ns katas.main
  (:gen-class)
  (:require
    [katas.fizzbuzz :refer [fizzbuzz]]
    [katas.leapyears :refer [is-leap-year?]]
    [katas.rpn-calculator :refer [rpn-evaluate]]
    [katas.roman-numerals :refer [decimal->roman]]
    [katas.hangman :refer [hangman]]
    [katas.wind :refer [cardinal-overlap]]
    [clojure.string :refer [join]]))

(def katas [
  {
    :name "fizzbuzz"
    :fn (fn [x y]
      (fizzbuzz (Integer/parseInt x) (inc (Integer/parseInt y))))
    :arity 2
  }
  {
    :name "leapyears"
    :fn (fn [x]
      (is-leap-year? (Integer/parseInt x)))
    :arity 1
  }
  {
    :name "rpn-calculator"
    :fn (fn [& xs]
      (rpn-evaluate (join " " xs)))
    :arity ##Inf
  }
  {
    :name "roman-numerals"
    :fn (fn [x]
      (decimal->roman (Integer/parseInt x)))
    :arity 1
  }
  {
    :name "hangman"
    :fn hangman
    :arity 2
  }
  {
    :name "wind"
    :fn cardinal-overlap
    :arity 2
  }
])

(defn kata [xs]
  (when (empty? xs)
    (throw (Exception. "Error: No kata provided")))
  (let [name (first xs)
        result (first (filter #(= (:name %) name) katas))]
  (if (nil? result)
    (throw (Exception. "Error: Kata not found"))
    result)))

(defn assert-arity [n xs]
  (cond
    (= n ##Inf) nil
    (not= n (count xs))
      (throw (Exception. (str "Error: Wrong arity! Expected: " n)))))

(defn -main
  [& cli-args]
  (try
    (let [matched-kata (kata cli-args)
          f (:fn matched-kata)
          arity (:arity matched-kata)
          fn-args (rest cli-args)]
      (assert-arity arity fn-args)
      (println (apply f fn-args)))
    (catch Exception e (println (str "Error: " (.getMessage e))))))
