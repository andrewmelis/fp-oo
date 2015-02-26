(ns fp-oo.ch4-embedded-functions)
  ; (require [fp-oo.add-and-make :refer [make]])) ;;this isn't working

(def make
  (fn [classname & args]
    (apply classname args)))

(def send-to
  (fn [object message & args]
    (apply (message (:__methods__ object)) object args)))

(def Point
  (fn [x y]
    { ;; initialize instance variables
     :x x
     :y y

     ;; metadata
     :__class_symbol__ 'Point
     :__methods__ {
       :class :__class_symbol__

       :x :x
       :y :y

       :coordinates
       (fn [this] [(:x this) (:y this)])

       :shift
       (fn [this xinc yinc]
         (make Point (+ (send-to this :x) xinc)
                     (+ (send-to this :y) yinc)))

       :add
       (fn [this other]
         (send-to this :shift (send-to other :x)
                              (send-to other :y)))}}))
