(ns fp-oo.ch10-primes)

;; ex1
(def multiples
  "returns a sequence of all non-prime multiples of input number <= input limit"
  (fn [number limit]
    (range (* number 2) (inc limit) number)))

(def non-prime-multiples-less-than-100
  (fn [number]
    (multiples number 101)))
