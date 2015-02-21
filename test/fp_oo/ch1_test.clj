(ns fp-oo.ch1-test
  (require [clojure.test :refer :all]
           [fp-oo.ch1-exercises :refer :all]))

(deftest second-test
  (testing "return second element of a list"
    (is (= 2 (my-second '(1 2 3 4))))
    (is (nil? (my-second '())))
    (is (nil? (my-second '(1))))))
    ;;(is (throw (my-second))))) how test error case?

(deftest third-a-test
  (testing "return third element of a list v1"
    (is (= 2 (third-a '(0 1 2))))
    (is (nil? (third-a '())))
    (is (nil? (third-a '(1)))))
  (testing "return third element of a list v2"
    (is (= 2 (third-b '(0 1 2))))))
    
(deftest add-squares-test
  (testing "sum the squares each element"
    (is (= 30 (add-squares 1 2 5)))
    (is (zero? (add-squares)))))

(deftest factorial-test
  (testing "factorial w/o iteration or recursion"
    (is (= 120 (factorial 5))))
  (testing "other factorial w/ concat"
    (is (= 120 (other-factorial 5))))
  (testing "third factorial w/ flatten"
    (is (= 120 (third-factorial 5)))))
    
(deftest prefix-of?-test
  (testing "returns true if elements in candidate are first elements in sequence"
    (is (prefix-of? [1 2] [1 2 3 4]))
    (not (prefix-of? '(2 3) [1 2 3 4]))
    (is (prefix-of? '(1 2) [1 2 3 4]))))

(deftest tails-test
  (testing "returns sequence of successively smaller subsequences of the argument"
    (is (= '((1 2 3 4) (2 3 4) (3 4) (4) ()) (tails '(1 2 3 4))))))
