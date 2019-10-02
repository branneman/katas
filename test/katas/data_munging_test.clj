(ns katas.data-munging-test
  (:require
    [clojure.test :refer :all]
    [katas.data-munging :refer :all]))

(deftest test-data-lines
  (is (= [] (data-lines "")))
  (is (= [] (data-lines "\n")))
  (is (= [] (data-lines "\n\n\n")))
  (is (= [] (data-lines "aa")))
  (is (= [] (data-lines "aa\nbb")))
  (is (= [] (data-lines "aa\r\nbb")))
  (is (= ["bb"] (data-lines "aa\nbb\n\n\n\ndd\n")))
  (is (= ["bb" "cc"] (data-lines "aa\nbb\ncc\ndd\n"))))

(deftest test-str->int
  (is (= 0 (str->int "0")))
  (is (= 1 (str->int " 1")))
  (is (= 2 (str->int "2 ")))
  (is (= 3 (str->int " 3 ")))
  (is (= 4 (str->int "  4 "))))

(deftest test-line->row
  (is (=
    { :Dy 1 :MxT 88 :MnT 59 }
    (line->row "   1  88    59    74")))
  (is (=
    { :Dy 9 :MxT 86 :MnT 32 }
    (line->row "   9  86    32*   59"))))

(deftest test-temperature-spread
  (is (= 2 (temperature-spread { :MxT 7 :MnT 5 })))
  (is (= 10 (temperature-spread { :MxT 30 :MnT 20 }))))

(deftest test-smallest-temperature-spread
  (is (= { :MxT 7 :MnT 5 }
    (smallest-temperature-spread [
      { :MxT 30 :MnT 20 }
      { :MxT 50 :MnT 20 }
      { :MxT 7 :MnT 5 }
    ])))
  (is (= { :MxT 7 :MnT 5 }
    (smallest-temperature-spread [
      { :MxT 30 :MnT 20 }
      { :MxT 7 :MnT 5 }
      { :MxT 50 :MnT 20 }
    ]))))

(deftest test-weather
  (is (= "2: 16" (weather
    (str
      "  Dy MxT   MnT   AvT   \n"
      "\n"
      "   1  88    59    74   \n"
      "   2  79    63    71   \n"
      "   3  77    55    66   \n"
      "  mo  82.9  60.5  71.7 \n")))))
