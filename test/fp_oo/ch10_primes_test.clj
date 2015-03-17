(ns fp-oo.ch10-primes-test
  (require [midje.sweet :refer :all]
           [fp-oo.ch10-primes :refer :all]))

(facts "primes"
  (facts "givens"
    (range (* 2 2) 101 2)
    => '(4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54
          56 58 60 62 64 66 68 70 72 74 76 78 80 82 84 86 88 90 92 94 96 98 100)
    (range (* 3 2) 101 3)
    => '(6 9 12 15 18 21 24 27 30 33 36 39 42 45 48 51 54 57 60 63 66 69 72 75 78 81
          84 87 90 93 96 99)
    (range (* 4 2) 101 4)
    => '(8 12 16 20 24 28 32 36 40 44 48 52 56 60 64 68 72 76 80 84 88 92 96 100))
  (facts "ex1 - simple solution"
    (non-prime-multiples-less-than-100 2)
    => (range (* 2 2) 101 2)
    (non-prime-multiples-less-than-100 3)
    => (range (* 3 2) 101 3)
    (non-prime-multiples-less-than-100 4)
    => (range (* 4 2) 101 4))
  (fact "ex2 - use for"
    (set non-primes-with-for)
    => (set '(4 6 8 9 10 12 14 15 16 18 20 21 22 24 25 26 27 28 30 32 33 34 35
                36 38 39 40 42 44 45 46 48 49 50 51 52 54 55 56 57 58 60 62 63
                64 65 66 68 69 70 72 74 75 76 77 78 80 81 82 84 85 86 87 88 90
                91 92 93 94 95 96 98 99 100)))
  (fact "ex2 - use Sequence monad"
    (set non-primes-with-sequence-monad)
    => (set non-primes-with-for))
  (fact "ex3 - calculate all the primes with sets"
    (set primes-less-than-100)
    => (set '(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83
                 89 97))))
