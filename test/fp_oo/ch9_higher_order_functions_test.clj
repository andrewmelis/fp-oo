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
