(ns katas.wind)

(def directions
  ["N", "NE", "E", "SE", "S", "SW", "W", "NW"])

;; scale :: (num num num num num) -> float
(defn scale [x domain-min domain-max range-min range-max]
  (let [
    perc (/ (float (- x domain-min)) (- domain-max domain-min))
  ] (+ range-min (* perc (- range-max range-min)))))

;; path->tuples :: list -> list tuple
(defn path->tuples [xs]
  (map vector (butlast xs) (rest xs)))

;; decimal-degrees->point :: (num num) -> [num num]
(defn decimal-degrees->point [lat lng]
  (let [
    x (scale lng 0 180 0 1)
    y (scale lat 0 90 0 1)
  ] [x y]))

;; points->angle :: (num num num num) -> num
(defn points->angle [x1 y1 x2 y2]
  (let [
    deltaX (- x2 x1)
    deltaY (- y2 y1)
    radians (Math/atan2 deltaX deltaY)
    theta (/ (* radians 180) Math/PI)
    degrees (if (< theta 0) (+ 360 theta) theta)
  ] degrees))

;; angle->cardinal :: num -> str
(defn angle->cardinal [x]
  (let [
    degrees (/ 360 (count directions))
    angle (float (mod x 360))
    idx (rem
      (Math/round (/ angle degrees))
      (count directions))
  ] (nth directions idx)))

;; angle-overlap :: (num num) -> float
(defn angle-overlap [x y]
  (let [
    high (max x y)
    low (min x y)
    diff-unbounded (- high low)
    diff (if (> diff-unbounded 180)
      (+ low (- 360 high))
      diff-unbounded)
  ] (scale diff 0 180 1 0)))

;; cardinal->angle :: str -> num
(defn cardinal->angle [s]
  (int (scale (.indexOf directions s) 0 (count directions) 0 360)))

;; cardinal-opposite :: str -> str
(defn cardinal-opposite [s]
  (let [
    angle (cardinal->angle s)
    opposite-angle (- (+ angle 180) 360)
  ] (angle->cardinal opposite-angle)))

;; cardinal-overlap :: (str str) -> float
(defn cardinal-overlap [x y]
  (let [
    angle-x (cardinal->angle x)
    angle-y (cardinal->angle y)
  ] (angle-overlap angle-x angle-y)))

;; upwind? :: num -> bool
(defn upwind? [x]
  (< x 0.5))

;; downwind? :: num -> bool
(defn downwind? [x]
  (>= x 0.5))
