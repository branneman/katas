(ns katas.hangman-test
  (:require
    [clojure.test :refer :all]
    [katas.hangman :refer :all]))

(deftest test-str->vector
  (is (= [] (str->vector "")))
  (is (= ["a"] (str->vector "a")))
  (is (= ["a" "b" "c"] (str->vector "abc")))
  (is (=
    ["e" "a" "t" "o" "l" "i" "n" "s" "h" "r"]
    (str->vector "eatolinshr"))))

(deftest test-exists?
  (is (= true (exists? 42 '(0 1 42 37))))
  (is (= true (exists? 42 [0 1 42 37])))
  (is (= false (exists? 1337 '(0 1 42 37))))
  (is (= false (exists? 1337 [0 1 42 37]))))

(deftest test-misses
  (is (= '() (misses "abc" "bbb")))
  (is (= '("d" "e" "f") (misses "abc" "def")))
  (is (= '("g") (misses "boat" "atg")))
  (is (= '() (misses "zeppelin" "")))
  (is (=
    '("a" "t" "o" "s" "h" "r")
    (misses "zeppelin" "eatolinshr"))))

(deftest test-guessed-word
  (is (=
    '("_" "_" "a" "t")
    (guessed-word "boat" "atg")))
  (is (=
    '("_" "e" "_" "_" "e" "l" "i" "n")
    (guessed-word "zeppelin" "eatolinshr")))
  (is (=
    '("_" "_" "_" "_" "_" "_")
    (guessed-word "rhythm" "abcde")))
  (is (=
    '("a" "i" "_" "_" "_" "a" "_" "e")
    (guessed-word "airplane" "ajkei"))))

(deftest test-format-letters
  (is (= "" (format-letters [])))
  (is (= "A" (format-letters ["a"])))
  (is (= "A B C" (format-letters ["a" "b" "c"]))))

(deftest test-hangman
  (is (=
    "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========\n_ _ A T\nG\n"
    (hangman "boat" "atg")))
  (is (=
    "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========\n_ _ _ _ _ _ _ _\n\n"
    (hangman "zeppelin" "")))
  (is (=
    "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========\n_ E _ _ E L I N\nA T O S H R\n"
    (hangman "zeppelin" "eatolinshr")))
  (is (=
    "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========\n_ _ _ _ _ _\nE D C B A\n"
    (hangman "rhythm" "edcba")))
  (is (=
    "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========\nB O A T\n\n"
    (hangman "boat" "atob")))
  (is (=
    "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========\nA I _ _ _ A _ E\nJ K\n"
    (hangman "airplane" "ajkei"))))
