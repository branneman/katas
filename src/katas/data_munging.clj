(ns katas.data-munging
  (:gen-class)
  (:require
    [clojure.string :refer [split trim]]))

(defn data-lines [s]
  (let [
    xs (split s #"\r?\n")
    ys (vec (filter (complement empty?) xs))
    zs (if (> (count ys) 2)
      (subvec ys 1 (- (count ys) 1))
      [])
  ] zs))

(defn str->int [s]
  (Integer/parseInt (trim s)))

(defn line->row [s]
  (let [
    Dy (str->int (subs s 2 4))
    MxT (str->int (subs s 5 8))
    MnT (str->int (subs s 11 14))
  ] { :Dy Dy, :MxT MxT, :MnT MnT }))

(defn temperature-spread [row]
  (- (:MxT row) (:MnT row)))

(defn smallest-temperature-spread [rows]
  (reduce
    (fn [acc curr]
      (if (< (temperature-spread curr) (temperature-spread acc)) curr acc))
    rows))

(defn weather [s]
  (let [
    entry (->> s
      (data-lines)
      (map line->row)
      (smallest-temperature-spread))
    out (str (:Dy entry) ": " (temperature-spread entry))
  ] out))
