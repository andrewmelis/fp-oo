(ns fp-oo.ch10-primes)

(use 'clojure.algo.monads)
(use 'clojure.set)

;; ex1
(def multiples
  "returns a sequence of all non-prime multiples of input number <= input limit"
  (fn [number limit]
    (range (* number 2) (inc limit) number)))

(def non-prime-multiples-less-than-100
  (fn [number]
    (multiples number 101)))

;;ex2
(def non-primes-with-for
  (for [n (range 2 101)
        non-primes (non-prime-multiples-less-than-100 n)]
    non-primes))

(def non-primes-with-sequence-monad
  (with-monad sequence-m
    (domonad [n (range 2 101)
              non-primes (non-prime-multiples-less-than-100 n)]
             non-primes)))

;; ex3
(def primes-less-than-100
  (fn []
    (difference (set (range 2 101)) (set non-primes-with-sequence-monad))))
