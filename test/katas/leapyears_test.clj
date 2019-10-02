(ns katas.leapyears-test
  (:require
    [clojure.test :refer :all]
    [katas.leapyears :refer :all]))

(deftest test-leap-years
  (is (every? true?
    (map is-leap-year?
      '(1600 2000 2008 2016 2020 2400)))))

(deftest test-non-leap-years
  (is (every? false?
    (map is-leap-year?
      '(1700 1800 1900 2010 2100 2200 2300)))))
