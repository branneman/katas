(ns katas.data-munging-football
  (:gen-class)
  (:require
    [clojure.string :refer [split trim]]))

(defn data-lines [s]
  (-> s
    ;; split on newlines
    ((fn [xs] (split s #"\r?\n")))

    ;; filter empty lines
    ((fn [xs] (vec (filter (complement empty?) xs))))

    ;; remove the first line
    ((fn [xs] (if (>= (count xs) 2)
      (subvec xs 1 (count xs))
      [])))

    ;; filter dashed lines
    ((fn [xs] (vec (filter
      #(not= "--" (subs % (- (count %) 2) (count %)))
      xs))))))

(defn str->int [s]
  (Integer/parseInt (trim s)))

(defn line->row [s]
  (let [
    Team (trim (subs s 7 23))
    F (str->int (subs s 43 45))
    A (str->int (subs s 50 52))
  ] { :Team Team, :F F, :A A }))

(defn score-spread [row]
  (Math/abs (- (:F row) (:A row))))

(defn smallest-score-spread [rows]
  (reduce
    (fn [acc curr]
      (if (< (score-spread curr) (score-spread acc)) curr acc))
    rows))

(defn football [s]
  (let [
    entry (->> s
      (data-lines)
      (map line->row)
      (smallest-score-spread))
    out (str (:Team entry) ": " (score-spread entry))
  ] out))
