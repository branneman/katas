(ns katas.roman-numerals
  (:require
    [clojure.string :refer [join split]]))

(def symbols [
  { :n 1 :s "I" }
  { :n 4 :s "IV" }
  { :n 5 :s "V" }
  { :n 9 :s "IX" }
  { :n 10 :s "X" }
  { :n 40 :s "XL" }
  { :n 50 :s "L" }
  { :n 90 :s "XC" }
  { :n 100 :s "C" }
  { :n 400 :s "CD" }
  { :n 500 :s "D" }
  { :n 900 :s "CM" }
  { :n 1000 :s "M" }
])

;; get-positive :: num -> num
(defn get-positive [n]
  (if (pos? n) n (* n -1)))

;; num->vector :: num -> vector num
(defn num->vector [n]
  (if (> n 0)
    (vec (map #(Integer/parseInt %) (split (str (int n)) #"")))
    (throw (Exception. "n must be bigger than 0"))))

;; find-highest-symbol :: (vector map, num) -> map
(defn find-highest-symbol [symbols n]
  (reduce
    (fn [acc curr] (if (> (curr :n) n) acc curr))
    symbols))

;; decimal->roman :: num -> str
(defn decimal->roman [n] (let
  [nums (num->vector (get-positive n))]
  (reduce-kv
    (fn [acc idx curr]
      (let [
        exponent (- (count nums) 1 idx)
        position (* curr (Math/pow 10 exponent))

        ; find highest matching roman symbol
        symbol (find-highest-symbol symbols position)

        ; commit to symbol
        amount (int (Math/floor (/ position (symbol :n))))
        res (str acc (join (repeat amount (symbol :s))))

        ; calc remaining
        remaining (- position (* (symbol :n) amount))
        res (if (> remaining 0)
          (str res (decimal->roman remaining))
          res)

      ] res))
    (if (pos? n) "" "-")
    nums)
))
