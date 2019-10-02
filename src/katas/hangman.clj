(ns katas.hangman
  (:require
    [clojure.string :refer [join split upper-case]]))

(def ascii-art [
  "  +---+\n  |   |\n      |\n      |\n      |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n========="
  "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="])

;; str->vector :: str -> vector
(defn str->vector [str]
  (if (> (count str) 0)
    (split str #"")
    []))

;; exists? :: (*, coll) -> bool
(defn exists? [val coll]
  (true? (some #(= val %) coll)))

;; misses :: (str, str) -> list
(defn misses [word letters]
  (filter
    #((complement exists?) % (str->vector word))
    (distinct (str->vector letters))))

;; guessed-word :: (str, str) -> list
(defn guessed-word [word letters]
  (let [
    uniq-letters (distinct (str->vector letters))
  ] (->> word
    (str->vector)
    (map #(if (exists? % uniq-letters) % "_")))))

;; format-letters :: vector str -> str
(defn format-letters [letters]
  (->> letters
    (apply str)
    (join " ")
    (upper-case)))

;; hangman :: (str, str) -> str
(defn hangman [word letters]
  (let [
    missed-letters (misses word letters)
    ascii (nth ascii-art (count missed-letters))
    guessed (format-letters (guessed-word word letters))
    missed (format-letters missed-letters)
  ] (str ascii "\n" guessed "\n" missed "\n")))
