# Software Craftsmanship Katas in Clojure

To run these as-is, you'll need to have [Clojure](https://clojure.org/) and [Leiningen](https://leiningen.org/) installed. Then run from the terminal:

```
git clone git@github.com:branneman/katas.git katas
cd katas
lein test
lein run fizzbuzz 1 100
lein run leapyears 2016
lein run rpn-calculator 3 5 8 \* 7 + \*
lein run roman-numerals 789
lein run hangman airplane ajkei
```

## FizzBuzz

Print the numbers between 1 and 100. If the number is divisible by 3, we want to print `Fizz` instead of this number. If the number is divisible by 5, we want to print `Buzz` instead. And if the number is divisible by both 3 and 5, we want to print `FizzBuzz`.

Source: https://josepaumard.github.io/katas/introductory/fizzbuzz-kata.html

## LeapYears

Write a function that returns `true` or `false` depending on whether its input integer is a leap year or not. A leap year is defined as one that is divisible by 4, but is not otherwise divisible by 100 unless it is also divisible by 400.

Source: https://josepaumard.github.io/katas/introductory/leapyears-kata.html

## RPN Calculator

An RPN calculator program computes expressions written in Reverse Polish Notation.

The RPN expression: `3 5 8 * 7 + *` equals: `3 * ((5 * 8) + 7)`

Source: https://josepaumard.github.io/katas/introductory/rpncalculator-kata.html

## Roman Numerals

Write a function to convert from decimal to roman numerals.

Source: https://josepaumard.github.io/katas/intermediate/romannumerals-kata.html

## ASCII Hangman

Given two words as input, where the words each match `[a-z]+`, output the current state of the hangman game as ASCII art. The first is to be guessed, the second word is the already-guessed letters.

Source: https://codegolf.stackexchange.com/questions/135936/ascii-hangman-in-progress
