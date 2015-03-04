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

(facts "ex2 - about juxt"
  (fact "runs several functions on input args and returns them in one vector"
    ((juxt empty? reverse count) [1 2 3])
    => [false [3 2 1] 3])
  (fact "redefine separate"
    (separate odd? [1 2 3])
    => (old-separate odd? [1 2 3])))

(fact "ex3 - substitution rule"
  (def myfun
    (let [x 3]
      (fn [] x)))
  ;(fact "can't access x outside scope of method"
  ;  (x)
  ;  => throws RunTimeException)    ;; can't get the throws checker to work..
  (fact "myfun serves as a closure for x"
    (myfun)
    => 3))

(fact "ex4 - substitution rule WITHOUT let"
  ;(fact "can't access x outside scope of method"
  ;  (x)
  ;  => throws RunTimeException)    ;; can't get the throws checker to work..
  (fact "myfun serves as a closure for x"
    (closure)
    => 3))

(facts "ex5 - make atom always = 3"
  (facts "accept any arguments and always return 3"
    (def my-atom (atom 0))
    (swap! my-atom atom-equal-to-33)
    => 33))

(facts "ex6 - always"
  (facts "always"
    ( (always 8))  ;; so i guess this is point-free, even if author's apply method isn't
    => 8
    ( (always 8) 1 'a :foo)
    => 8)
  (facts "define always in point-free style"
    ((point-free-always 8))
    => 8
    ( (point-free-always 8) 1 'a :foo)
    => 8)
  (fact "works the same as clojure.core/constantly"
    ( (point-free-always 9) [1 'a] :foo)
    => ( (constantly 9) [1 'a] :foo))
  (fact "works with atoms"
    (swap! (atom 22) (always 899))
    => 899
    (swap! (atom 55) (constantly 9))
    => 9))

(facts "isbn? accepts a string, returns boolean"
  (fact "valid isbn has checksum that can be divided by 11 with remainder 0"
    (sort (map isbn? ["0131774115"
                      "0977716614"
                      "1934356190"]))
    => '(false true true))
  (facts "helpers"
    (facts "check-sum"
      (fact "works on given"
        (check-sum [4 8 9 3 2])
        => (+ (* 1 4)
              (* 2 8)
              (* 3 9)
              (* 4 3)
              (* 5 2)))
      (fact "accepts any number of arguments"
        (check-sum [2 3 1 6 5 7 4 9])
        => (+ (* 1 2)
              (* 2 3)
              (* 3 1)
              (* 4 6)
              (* 5 5)
              (* 6 7)
              (* 7 4)
              (* 8 9))))
    (fact "reversed-digits -- provided by author"
      (reversed-digits "123")
      => [3 2 1])))
