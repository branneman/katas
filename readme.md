# Software Craftsmanship Katas in Clojure

To run these as-is, you'll need to have [Clojure](https://clojure.org/) and [Leiningen](https://leiningen.org/) installed. Then run from the terminal:

```
git clone git@github.com:branneman/katas.git katas
cd katas
lein test
lein run fizzbuzz 1 100
```

## FizzBuzz

Print the numbers between 1 and 100. If the number is divisible by 3, we want to print `Fizz` instead of this number. If the number is divisible by 5, we want to print `Buzz` instead. And if the number is divisible by both 3 and 5, we want to print `FizzBuzz`.

Source: https://josepaumard.github.io/katas/introductory/fizzbuzz-kata.html
