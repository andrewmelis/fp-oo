(ns fp-oo.ch10-continuation-passing-test
  (require [midje.sweet :refer :all]))

(use 'clojure.algo.monads)

;; 10.1
(facts "about manual nil-patching"
  (fact "nil throws NullPointerException"
    (def nil-func nil)
    (-> nil-func inc (* 3) dec)
    => (throws NullPointerException))

  (fact "can set a default value for nil"
    (def nil-func
      (fn [& throwaways] nil))
    (def tolerant-inc
      (fn [n]
        (if (nil? n) (inc 0) (inc n))))
    (-> 1
        nil-func
        tolerant-inc
        (* 3)
        dec)
    => 2)
  (fact "can 'lift' the nil-patch concept"
    (def nil-patch
      (fn [function replacement]
        (fn [original]
          (function (if (nil? original)
                      replacement
                      original)))))
    (-> 1
        nil-func
        ((nil-patch inc 0))
        (* 3)
        dec)
    => 2)
  (fact "fnil is built-in nil-patch"
    (-> 1 nil-func ((fnil inc 0)) (* 3) dec)
    => (-> 1 nil-func ((nil-patch inc 0)) (* 3) dec)))

(facts "about monads"
  (fact "maybe monad"
    (with-monad maybe-m
      (domonad [step1-value (nil-func 1)
               step2-value (* (inc step1-value) 3)]
        (dec step2-value)))
    => nil))
