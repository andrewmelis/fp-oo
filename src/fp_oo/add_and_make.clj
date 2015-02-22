(ns fp-oo.add-and-make)

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

;; exercise 1
(def add-without-shift
  (fn [p1 p2]
    (Point (+ (x p1) (x p2))
           (+ (y p1) (y p2)))))

(def add
  (fn [p1 p2]
    (shift p1 (x p2) (y p2))))
