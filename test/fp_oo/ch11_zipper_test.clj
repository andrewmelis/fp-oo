(ns fp-oo.ch11-zipper-test
  (require [midje.sweet :refer :all]
           [clojure.zip :as zip]))

(fact "insane"
  (identity 1)
  => 1)

(facts "about zippers"
  (def zipper (zip/seq-zip '(1 (2 3) 1)))
  (fact "zip/node prints subtree at current location"
    (zip/node zipper)
    => '(1 (2 3) 1))
  (fact "zip/down descends down the tree"
    (-> zipper zip/down zip/node)
    => 1)
  (fact "zippers are immutable, so have to bind a new location to a symbol"
    (def other-zipper (-> zipper zip/down zip/right))
    (zip/node other-zipper)
    => '(2 3))
  (fact "move left-to-right and top-to-bottom depth-first with zip/next"
    (-> other-zipper zip/next zip/node)
    => 2)
  (fact "check if a zipper is at an interior or leaf node with zip/branch?"
    (zip/branch? other-zipper)
    => true
    (-> other-zipper zip/next zip/branch?)
    => false)
  (fact "zip/end? is true when zip/next has 'fallen off the end of the tree'"
    (-> zipper ;; root
        zip/down ;; 1
        zip/right ;; (2 3)
        zip/right ;; 1
        zip/next zip/end?)
    => true))

(fact "flattenize collects all nodes in a tree"
  (fact "with helper method"
    (def flatten-zipper
      (fn [so-far zipper]
        (cond (zip/end? zipper)
              so-far

              (zip/branch? zipper)
              (flatten-zipper so-far (zip/next zipper))

              :else
              (flatten-zipper (cons (zip/node zipper) so-far)
                              (zip/next zipper)))))
    (def flattenize
      (fn [tree]
        (reverse (flatten-zipper '() (zip/seq-zip tree)))))

    (flattenize '(fn [a] (* 2 (inc a))))
    => '[fn [a] * 2 inc a])
  (fact "with letfn form"
    (defn new-flattenize [tree]
      (letfn [(flatten-zipper [so-far zipper]
                (cond (zip/end? zipper)
                      so-far

                      (zip/branch? zipper)
                      (flatten-zipper so-far (zip/next zipper))

                      :else
                      (flatten-zipper (cons (zip/node zipper) so-far)
                                      (zip/next zipper))))]
        (reverse (flatten-zipper '() (zip/seq-zip tree)))))
    (new-flattenize '(fn [a] (* 2 (inc a))))
    => '[fn [a] * 2 inc a]))

(facts "about the do special form"
  (def do-test 
    (fn []
      (if (odd? 9)
        (do
          (println "can put some debugging info here")
          (identity true))
        (identity false))))
  (do-test)
  => true)

(facts "can't use let to define recursive functions due to self-reference"
  ;(let [something (inc something)]
  ;  something)
  ;=> (throws RuntimeException))
  (fact "use letfn for definiting potentially recursive local functions"
    (letfn [(factorial [n]
              (if (or (= n 0) (= n 1))
                1
                (* n (factorial (dec n)))))]
      (factorial 5))
    => 120))

(fact "can use defn form to define top-level function"
  (fact "old form emphasizes that functions are just values"
    (defn new-way-factorial [n]
      (if (or (= n 0) (= n 1))
        1
        (* n (new-way-factorial (dec n)))))
    (def old-way-factorial
      (fn [n]
        (if (or (= n 0) (= n 1))
          1
          (* n (old-way-factorial (dec n))))))
    (new-way-factorial 5)
    => (old-way-factorial 5)))


