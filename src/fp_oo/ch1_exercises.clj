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

;; exercise 6
(def prefix-of?
  (fn [candidate sequence]
    (= candidate
       (take (count candidate)
             sequence))))

;; exercise 7
(def tails
  (fn [sequence]
    (map drop
         (range (inc (count sequence)))
         (repeat (inc (count sequence)) sequence))))

;; exercise 8

;; (def puzzle (fn [list] (list list))
;; (puzzle '(1 2 3)
;;  => ClassCastException clojure.lang.PersistentList cannot be cast to clojure.lang.IFn

;; substitution rule for functions
;; list argument is substituted into first element of method body list, throwing error
