(ns fp-oo.embedded-functions)
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

       :shift
       (fn [this xinc yinc]
         (make Point (+ (:x this) xinc)
                     (+ (:y this) yinc)))}}))
