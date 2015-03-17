(ns fp-oo.ch10-primes)

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
  (fn []
    (set (for [n (range 2 101)
          non-primes (non-prime-multiples-less-than-100 n)]
      (identity non-primes)))))
