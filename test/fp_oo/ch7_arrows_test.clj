(ns fp-oo.ch7-arrows-test
  (require [midje.sweet :refer :all]))

;; arrow operator practice
(facts "the arrow operator translates ordered functions into proper nested functions"
  (fact "when the next element is a list"
    (fact "the 1st element is inserted into 2nd position"
      (-> 1 (- 2)) => (- 1 2)))
  (fact "when the next element is not a list"
    (fact "the element is first converted into a single-element list"
      (-> 1 inc) => (inc 1))))

(fact "exercise 1"
  (-> [1]
      first
      inc
      list)
  => '(2))

(fact "exercise 2"
  (-> [1]
      first
      inc
      (* 3)
      list)
  => '(6))

(fact "exercise 3"
  (-> 3
      ((fn [n] (* 2 n))) ;; need to wrap in list to pass prev element as argument
      inc)
  => (-> 3 (* 2) inc))

(fact "exercise 4"
  (-> (+ 1 2)
      (* 3)
      (+ 4))
  => (+ (* (+ 1 2) 3) 4))
