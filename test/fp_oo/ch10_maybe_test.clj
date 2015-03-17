(ns fp-oo.ch10-maybe-test
  (require [midje.sweet :refer :all]
           [fp-oo.ch10-maybe :refer :all]))

(use 'clojure.algo.monads)

(fact "maybe-monad produces nil"
  (with-monad maybe-monad
    (domonad [a nil
              b (+ 1 a)]
             b))
  => nil)

(facts "error-monad halts computation when an oopsie is produced by a step"
  (fact "error-monad produces an oops!"
    (oopsie? result)
    => true)
  (fact "can access values from the 'oops!' that is produced"
    (:reason result)
    => "Factorial can never be less than zero."
    (:number result)
    => -1))
