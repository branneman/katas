(ns katas.data-munging-football-test
  (:require
    [clojure.test :refer :all]
    [katas.data-munging-football :refer :all]))

(deftest test-data-lines
  (is (= [] (data-lines "")))
  (is (= [] (data-lines "\n")))
  (is (= [] (data-lines "\n\n\n")))
  (is (= [] (data-lines "aa")))
  (is (= ["bb"] (data-lines "aa\nbb")))
  (is (= ["bb"] (data-lines "aa\r\nbb")))
  (is (= ["bb" "dd"] (data-lines "aa\nbb\n\n\n\ndd\n")))
  (is (= ["bb" "dd"] (data-lines "aa\nbb\n\n\n\ndd\n")))
  (is (= ["dd"] (data-lines "aa\n--\n\n\n\ndd\n")))
  (is (= ["bb" "cc" "dd"] (data-lines "aa\nbb\ncc\ndd\n")))
  (is (= ["bb" "dd"] (data-lines "aa\nbb\n--\ndd\n"))))

(deftest test-str->int
  (is (= 0 (str->int "0")))
  (is (= 1 (str->int " 1")))
  (is (= 2 (str->int "2 ")))
  (is (= 3 (str->int " 3 ")))
  (is (= 4 (str->int "  4 "))))

(deftest test-line->row
  (is (=
    { :Team "Arsenal" :F 79 :A 36 }
    (line->row "    1. Arsenal         38    26   9   3    79  -  36    87")))
  (is (=
    { :Team "Manchester_U" :F 87 :A 45 }
    (line->row "    3. Manchester_U    38    24   5   9    87  -  45    77"))))

(deftest test-score-spread
  (is (= 0 (score-spread { :F 5 :A 75 })))
  (is (= 2 (score-spread { :F 5 :A 7 })))
  (is (= 2 (score-spread { :F 7 :A 5 })))
  (is (= 10 (score-spread { :F 30 :A 20 }))))

(deftest test-smallest-score-spread
  (is (= { :F 7 :A 5 }
    (smallest-score-spread [
      { :F 30 :A 20 }
      { :F 50 :A 20 }
      { :F 7 :A 5 }
    ])))
  (is (= { :F 7 :A 5 }
    (smallest-score-spread [
      { :F 30 :A 20 }
      { :F 7 :A 5 }
      { :F 50 :A 20 }
    ]))))

(deftest test-football
  (is (= "Ipswich: 0" (football
    (str
      "       Team            P     W    L   D    F      A     Pts\n"
      "    1. Arsenal         38    26   9   3    79  -  36    87\n"
      "    2. Liverpool       38    24   8   6    67  -  30    80\n"
      "    3. Manchester_U    38    24   5   9    87  -  45    77\n"
      "    8. Aston_Villa     38    12  14  12    46  -  47    50"
      "   -------------------------------------------------------\n"
      "   18. Ipswich         38     9   9  20    30  -  30    36\n")))))
