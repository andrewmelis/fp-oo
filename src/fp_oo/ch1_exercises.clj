(ns fp-oo.ch1-exercises)

;; exercise 1
(def my-second
  (fn [list]
    (first
      (rest list))))

;; exercise 2
(def third-a
  (fn [list]
    (first
      (rest
        (rest list)))))

(def third-b
  (fn [list]
    (nth list 2)))

;; exercise 3
(def add-squares
  (fn [& numbers]
    (apply + 
      (map (fn [n] (* n n)) numbers))))

;; exercise 4
(def factorial
  (fn [n]
    (apply *
      (cons n (range 1 n)))))

;; exercise 5
(def other-factorial
  (fn [n]
    (apply *
      (concat (list n) (range 1 n))))) ; why not ' ?

(def third-factorial
  (fn [n]
    (apply *
      (flatten
        (list n (range 1 n))))))
