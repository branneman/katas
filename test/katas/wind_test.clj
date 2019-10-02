(ns katas.wind-test
  (:require
    [clojure.test :refer :all]
    [katas.wind :refer :all]))

(deftest test-scale
  (is (= 0.0 (scale 0 0 1 0 2)))
  (is (= 55.0 (scale 10 0 100 50 100)))
  (is (= 20.0 (scale -10 0 -50 0 100)))
  (is (= -20.0 (scale 10 0 -50 0 100)))
  (is (= 1.0 (scale 12.5 0 100 0 8))))

(deftest test-path->tuples
  (is (= [[1 2]]
    (path->tuples
    [1 2])))
  (is (= [[1 2] [2 3]]
    (path->tuples
    [1 2 3])))
  (is (= [[1 2] [2 3] [3 4]]
    (path->tuples
    [1 2 3 4])))
  (is (= [[1 2] [2 3] [3 4] [4 5]]
    (path->tuples
    [1 2 3 4 5]))))

(deftest test-decimal-degrees->point
  (is (= [0.0 0.0]
    (decimal-degrees->point 0.0 0.0)))
  (is (= '("0.024877" "0.576938")
    (map #(format "%.6f" %)
      (decimal-degrees->point 51.92442400 4.47777778)))))

(deftest test-points->angle
  (is (= 0.0 (points->angle 0 0 0 0)))
  (is (= 45.0 (points->angle 0 0 10 10)))
  (is (= 90.0 (points->angle 0 0 10 0)))
  (is (= 135.0 (points->angle 0 0 10 -10)))
  (is (= 180.0 (points->angle 0 0 0 -10)))
  (is (= 225.0 (points->angle 0 0 -10 -10)))
  (is (= 270.0 (points->angle 0 0 -10 0)))
  (is (= 315.0 (points->angle 0 0 -10 10))))

(deftest test-angle->cardinal
  (is (= "W" (angle->cardinal -450)))
  (is (= "W" (angle->cardinal -90)))
  (is (= "N" (angle->cardinal -10)))
  (is (= "N" (angle->cardinal 0)))
  (is (= "N" (angle->cardinal 22)))
  (is (= "NE" (angle->cardinal 23)))
  (is (= "NE" (angle->cardinal 45)))
  (is (= "E" (angle->cardinal 90)))
  (is (= "SE" (angle->cardinal 135)))
  (is (= "S" (angle->cardinal 180)))
  (is (= "SW" (angle->cardinal 225)))
  (is (= "W" (angle->cardinal 270)))
  (is (= "NW" (angle->cardinal 315)))
  (is (= "N" (angle->cardinal 360)))
  (is (= "NE" (angle->cardinal 405))))

(deftest test-angle-overlap
  (is (= 0.0 (angle-overlap 0 180)))
  (is (= 0.25 (angle-overlap 315 90)))
  (is (= 0.5 (angle-overlap 315 45)))
  (is (= 0.75 (angle-overlap 0 45)))
  (is (= 1.0 (angle-overlap 90 90))))

(deftest test-cardinal->angle
  (is (= 0 (cardinal->angle "N")))
  (is (= 45 (cardinal->angle "NE")))
  (is (= 90 (cardinal->angle "E")))
  (is (= 135 (cardinal->angle "SE")))
  (is (= 180 (cardinal->angle "S")))
  (is (= 225 (cardinal->angle "SW")))
  (is (= 270 (cardinal->angle "W")))
  (is (= 315 (cardinal->angle "NW"))))

(deftest test-cardinal-opposite
  (is (= "N" (cardinal-opposite "S")))
  (is (= "NE" (cardinal-opposite "SW")))
  (is (= "E" (cardinal-opposite "W")))
  (is (= "SE" (cardinal-opposite "NW")))
  (is (= "S" (cardinal-opposite "N")))
  (is (= "SW" (cardinal-opposite "NE")))
  (is (= "W" (cardinal-opposite "E")))
  (is (= "NW" (cardinal-opposite "SE"))))

(deftest test-cardinal-overlap
  (is (= 0.0 (cardinal-overlap "N" "S")))
  (is (= 0.25 (cardinal-overlap "NW" "E")))
  (is (= 0.5 (cardinal-overlap "NW" "NE")))
  (is (= 0.75 (cardinal-overlap "N" "NE")))
  (is (= 1.0 (cardinal-overlap "E" "E"))))

(deftest test-upwind?
  (is (= true (upwind? 0)))
  (is (= true (upwind? 0.2)))
  (is (= true (upwind? 0.49)))
  (is (= false (upwind? 0.5)))
  (is (= false (upwind? 0.51)))
  (is (= false (upwind? 0.9)))
  (is (= false (upwind? 1.0))))

(deftest test-downwind?
  (is (= false (downwind? 0)))
  (is (= false (downwind? 0.2)))
  (is (= false (downwind? 0.49)))
  (is (= true (downwind? 0.5)))
  (is (= true (downwind? 0.51)))
  (is (= true (downwind? 0.9)))
  (is (= true (downwind? 1.0))))

(deftest integration-tests
  ;; I'm going mostly downwind,
  ;;  if the wind is coming from the North,
  ;;  and I'm cyling towards South-East.
  ;; I have a 75% overlap with the wind direction.
  (is (= 0.75
    (cardinal-overlap
      (cardinal-opposite "N")
      (angle->cardinal
        (apply points->angle
          (concat
            (decimal-degrees->point 51.94117 4.49333)
            (decimal-degrees->point 51.93659 4.49905))))))))
