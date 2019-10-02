(ns katas.leapyears)

;; divisible? :: (num, num) -> bool
(defn divisible? [num, div]
  (= 0 (mod num div)))

;; is-leap-year? :: num -> bool
(defn is-leap-year? [n]
  (cond
    (not (divisible? n 4)) false
    (not (divisible? n 100)) true
    (not (divisible? n 400)) false
    :else true))
