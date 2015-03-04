(ns fp-oo.ch9-higher-order-functions-test
  (require [midje.sweet :refer :all]
           [fp-oo.ch9-higher-order-functions :refer :all]))

(facts "closing over values"
  (fact "manual incrementer"
    (manual-inc5 3)
    => 8)
  (fact "make-incrementer works"
    (inc5 3)
    => 8)
  (fact "can use partial instead"
    (inc5-from-partial 3)
    => 8)
  (fact "map example"
    (increment-all [1 2 3])
    => '(2 3 4))
  (fact "partial can take more than two args"
    (incish [1 2 3])
    => '(101 202 303)))

(facts "lifting functions"
  (fact "not operates on values, while complement operates on functions"
    (value-odd? 2)
    => (complement-odd? 2))
  (fact "we can write our own complement"
    (complement-odd? 3)
    => (my-complement-odd? 3))
  (facts "we can write a generalized lift function that takes a modifier and a function"
    (fact "one-off versions"
      ( (one-off-negate +) 1 2 3)
      => -6
      ( (one-off-madoffize honest-return) {:account 1234})
      => (roughly 3.15))      ;; rounding to within 1/1000
    (facts "lifted versions"
      ( (lifted-negate +) -4 -6 -7)
      => ( (one-off-negate +) -4 -6 -7))
    ( (lifted-madoffize honest-return) {:account 123})
    => (roughly 3.15)))

(facts "point-free definitions"
  (fact "can write method definitions without naming parameters"
    (named-inc5 3)
    => (point-free-inc5 3))
  (fact "can use comp to 'compose' several functions in right to left order"
    ( (comp str +) 8 8 8)
    => "24"
    ( (comp (partial * 2) + ) 8 8 8)
    => 48))

(facts "ex1 - without using fn"
  (fact "can use partials"
    (fact "can use a partial on the f passed to map"
      (map (partial + 2) [1 2 3])
      => (map (fn [n] (+ 2 n)) [1 2 3]))
    (fact "can capture the map as well, only needing the collection"
      ((partial map (partial + 2)) [1 2 3])
      => (map (fn [n] (+ 2 n)) [1 2 3])))
  (fact "can use comp to compose two inc functions"
    (map (comp inc inc) [1 2 3])
    => (map (fn [n] (+ 2 n)) [1 2 3])))
