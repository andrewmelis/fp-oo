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
