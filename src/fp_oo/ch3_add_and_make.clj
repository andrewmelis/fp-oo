(ns fp-oo.ch3-add-and-make)

(def Point
  (fn [x y]
    {:x x
     :y y
     :__class_symbol__ 'Point}))

(def verbose-y
  (fn [this]
    (get this :y)))

(def shorter-y
  (fn [this]
    (:y this)))

(def y :y) ;; first elt of list is keyword, therefore callable
(def x :x)

(def class-of :__class_symbol__)

(def shift
  (fn [this xinc yinc]
    (Point (+ (x this) xinc)
           (+ (y this) yinc))))

(def Triangle
  (fn [point1 point2 point3]
    {:point1 point1, :point2 point2, :point3 point3
     :__class_symbol__ 'Triangle}))


(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))
;; exercise 1
(def add-without-shift
  (fn [p1 p2]
    (Point (+ (x p1) (x p2))
           (+ (y p1) (y p2)))))

(def add
  (fn [p1 p2]
    (shift p1 (x p2) (y p2))))

;; exercise 2
(def make
  (fn [classname & args]
    (apply classname args)))

;; exercise 3 / 4
(def equal-triangles? =)

;; exercise 5
(def valid-triangle?
  (fn [point1 point2 point3]
    (= 3 (count (distinct [point1 point2 point3])))))
